package net.saifs.kek.parser;

import net.saifs.kek.ast.expression.*;
import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.IStatementNode;
import net.saifs.kek.ast.internal.operation.BinaryOperation;
import net.saifs.kek.ast.internal.operation.UnaryOperation;
import net.saifs.kek.ast.statement.*;
import net.saifs.kek.token.Token;
import net.saifs.kek.token.TokenType;
import org.w3c.dom.ls.LSParser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int cursor = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Token nibble(TokenType...types) {
        if (this.cursor >= this.tokens.size()) {
            return null;
        }

        Token token = this.tokens.get(cursor);

        if (token == null) {
            return null;
        }

        if (!List.of(types).contains(token.type())) {
            return null;
        }

        this.cursor++;
        return token;
    }

    public Token eat(TokenType... types) {
        Token token = this.tokens.get(cursor);

        if (token == null) {
            throw new ParserException("unexpected end of input");
        }

        if (!List.of(types).contains(token.type())) {
            throw new ParserException("expected " + token.type());
        }

        this.cursor++;

        return token;
    }

    public boolean is(TokenType... types) {
        if (this.cursor >= this.tokens.size()) {
            return false;
        }

        Token token = this.tokens.get(cursor);

        if (token == null) {
            return false;
        }

        return List.of(types).contains(token.type());
    }

    public List<IStatementNode> parse() {
        List<IStatementNode> statements = new ArrayList<>();

        while (this.cursor < this.tokens.size()) {
            IStatementNode statement = statement();
            statements.add(statement);
        }

        return statements;
    }

   private IStatementNode statement() {
        if (is(TokenType.FUN)) return functionDeclaration();
        if (is(TokenType.LET)) return variableDeclaration();
        if (is(TokenType.FOR)) return forStatement();
        if (is(TokenType.RETURN)) return returnStatement();
        if (is(TokenType.WHILE)) return whileStatement();
        if (is(TokenType.IF)) return ifStatement();
        if (is(TokenType.LBRACE)) return block();
        return expressionStatement();
    }

    private ASTFunctionStatement functionDeclaration() {
        this.eat(TokenType.FUN);
        Token identifier = this.eat(TokenType.IDENT);
        this.eat(TokenType.LBRACKET);
        List<String> params = new ArrayList<>();

        if (!this.is(TokenType.RBRACKET)) {
            params.add(this.eat(TokenType.IDENT).literal());
            while (this.is(TokenType.COMMA)) {
                this.eat(TokenType.COMMA);
                params.add(this.eat(TokenType.IDENT).literal());
            }
        }

        this.eat(TokenType.RBRACKET);

        ASTBlockStatement statement = this.block();
        return new ASTFunctionStatement(identifier.literal(), params, statement.statements());
    }

    private ASTLetStatement variableDeclaration() {
        this.eat(TokenType.LET);
        Token identifier = this.eat(TokenType.IDENT);
        this.eat(TokenType.EQ);
        IExpressionNode expression = this.expression();
        this.nibble(TokenType.SEMICOLON);
        return new ASTLetStatement(identifier.literal(), expression);
    }

    private ASTIfStatement ifStatement() {
        eat(TokenType.IF);
        eat(TokenType.LBRACKET);
        IExpressionNode condition = expression();
        eat(TokenType.RBRACKET);
        IStatementNode thenBranch = statement();
        IStatementNode elseBranch = null;
        if (is(TokenType.ELSE)) {
            this.eat(TokenType.ELSE);
            elseBranch = statement();
        }
        return new ASTIfStatement(condition, thenBranch, elseBranch);
    }

    private ASTReturnStatement returnStatement() {
        eat(TokenType.RETURN);
        IExpressionNode expression = expression();
        nibble(TokenType.SEMICOLON);
        return new ASTReturnStatement(expression);
    }

    private IStatementNode whileStatement() {
        eat(TokenType.WHILE);
        eat(TokenType.LBRACKET);
        IExpressionNode condition = expression();
        eat(TokenType.RBRACKET);
        IStatementNode statement = statement();
        return new ASTWhileStatement(condition, statement);
    }

    private IStatementNode forStatement() {
        return null;
    }

    private ASTBlockStatement block() {
        this.eat(TokenType.LBRACE);
        List<IStatementNode> statements = new ArrayList<>();

        while (!this.is(TokenType.RBRACE)) {
            IStatementNode statement = statement();
            statements.add(statement);
        }

        this.eat(TokenType.RBRACE);
        return new ASTBlockStatement(statements);
    }

    private ASTExpressionStatement expressionStatement() {
        IExpressionNode expression = expression();
        nibble(TokenType.SEMICOLON);
        return new ASTExpressionStatement(expression);
    }

    private IExpressionNode expression() {
        return assignment();
    }

    private IExpressionNode assignment() {
        IExpressionNode expression = equality();

        if (this.is(TokenType.EQ)) {
            this.eat(TokenType.EQ);
            IExpressionNode value = assignment();

            if (expression instanceof ASTIdentifierNode ident) {
                return new ASTAssignNode(ident.identifier(), value);
            }

            throw new ParserException("invalid assignment target");
        }

        return expression;
    }

    private IExpressionNode equality() {
        IExpressionNode node = comparison();

        while (this.is(TokenType.BANG_EQ, TokenType.EQ_EQ)) {
            Token operator = this.eat(TokenType.BANG_EQ, TokenType.EQ_EQ);
            IExpressionNode right = this.comparison();
            node = new ASTBinaryNode(node, right, BinaryOperation.of(operator.type()));
        }

        return node;
    }

    // TODO
    private IExpressionNode comparison() {
        IExpressionNode left = term();

        while (is(TokenType.GREATER, TokenType.LESS, TokenType.GREATER_EQ, TokenType.LESS_EQ)) {
            Token token = eat(TokenType.GREATER, TokenType.LESS, TokenType.GREATER_EQ, TokenType.LESS_EQ);
            IExpressionNode right = term();
            left = new ASTBinaryNode(left, right, BinaryOperation.of(token.type()));
        }

        return left;
    }

    private IExpressionNode term() {
        IExpressionNode node = this.factor();

        while (this.is(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = this.eat(TokenType.PLUS, TokenType.MINUS);
            IExpressionNode right = this.factor();
            node = new ASTBinaryNode(node, right, BinaryOperation.of(operator.type()));
        }

        return node;
    }

    private IExpressionNode factor() {
        IExpressionNode node = this.exponential();

        while (this.is(TokenType.MUL, TokenType.DIV)) {
            Token operator = this.eat(TokenType.MUL, TokenType.DIV);
            IExpressionNode right = this.exponential();
            node = new ASTBinaryNode(node, right, BinaryOperation.of(operator.type()));
        }

        return node;
    }

    private IExpressionNode exponential() {
        IExpressionNode node = this.unary();

        while (this.is(TokenType.EXP)) {
            this.eat(TokenType.EXP);
            IExpressionNode right = this.unary();
            node = new ASTBinaryNode(node, right, BinaryOperation.EXP);
        }

        return node;
    }

    private IExpressionNode unary() {
        if (this.is(TokenType.BANG, TokenType.MINUS)) {
            Token operator = eat(TokenType.BANG, TokenType.MINUS);
            IExpressionNode right = unary();
            return new ASTUnaryNode(UnaryOperation.of(operator.type()), right);
        }

        return call();
    }

    private IExpressionNode call() {
        IExpressionNode callee = this.primary();

        if (callee instanceof ASTIdentifierNode identifier) {
            if (this.is(TokenType.NUMBER, TokenType.IDENT)) {
                return new ASTCallNode(identifier.identifier(), List.of(this.call()));
            }

            if (this.is(TokenType.LBRACKET)) {
                this.eat(TokenType.LBRACKET);
                List<IExpressionNode> args = new ArrayList<>();

                if (this.is(TokenType.RBRACKET)) {
                    this.eat(TokenType.RBRACKET);
                    return new ASTCallNode(identifier.identifier(), args);
                }

                args.add(this.expression());

                while (this.is(TokenType.COMMA)) {
                    this.eat(TokenType.COMMA);
                    args.add(this.expression());
                }

                this.eat(TokenType.RBRACKET);
                return new ASTCallNode(identifier.identifier(), args);
            }
        }

        return callee;
    }

    private IExpressionNode primary() {
        if (this.is(TokenType.LBRACKET)) {
            this.eat(TokenType.LBRACKET);
            IExpressionNode expression = this.expression();
            this.eat(TokenType.RBRACKET);
            return expression;
        }

        if (this.is(TokenType.TRUE, TokenType.FALSE, TokenType.NUMBER, TokenType.NIL, TokenType.STRING)) {
            Token token = this.eat(TokenType.TRUE, TokenType.FALSE, TokenType.NUMBER, TokenType.NIL, TokenType.STRING);
            Object value = switch (token.type()) {
                case TRUE -> true;
                case FALSE -> false;
                case NUMBER -> Double.valueOf(token.literal());
                case STRING -> token.literal();
                default -> null;
            };
            return new ASTValueNode(value);
        }

        if (this.is(TokenType.IDENT)) {
            Token token = this.eat(TokenType.IDENT);
            return new ASTIdentifierNode(token.literal());
        }

        throw new ParserException("unexpected input: " + this.tokens.get(cursor) + " --- cursor: " + this.cursor + " --- tokens size: " + this.tokens.size());
    }
}
