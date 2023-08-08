package net.saifs.kek.ast.internal;

import net.saifs.kek.ast.expression.*;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;
import net.saifs.kek.ast.internal.visitor.StatementVisitor;
import net.saifs.kek.ast.statement.*;

import java.util.Objects;
import java.util.stream.Collectors;

public class ASTPrinter implements StatementVisitor<String>, ExpressionVisitor<String> {
    public String print(IStatementNode node) {
        return node.accept(this);
    }

    @Override
    public String visitLetStatement(ASTLetStatement statement) {
        return "(assign " + statement.identifier() + " = " + statement.value().accept(this) + ")";
    }

    @Override
    public String visitBlockStatement(ASTBlockStatement statement) {
        StringBuilder builder = new StringBuilder();
        builder.append("(block ");
        for (IStatementNode node : statement.statements()) {
            builder.append(node.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    public String visitExpressionStatement(ASTExpressionStatement statement) {
        return parenthesize(";", statement.node());
    }

    @Override
    public String visitFunctionStatement(ASTFunctionStatement statement) {
        StringBuilder builder = new StringBuilder();
        builder.append("(function ").append(statement.name()).append("(");
        for (String param : statement.parameters()) {
            if (!Objects.equals(param, statement.parameters().get(0))) {
                builder.append(" ");
            }

            builder.append(param);
        }

        builder.append(") ");

        for (IStatementNode body : statement.body()) {
            builder.append(body.accept(this));
        }

        builder.append(")");
        return builder.toString();
    }

    @Override
    public String visitIfStatement(ASTIfStatement statement) {
        return "(if " + statement.condition().accept(this) + " " + statement.thenBranch().accept(this) + ")";
    }

    @Override
    public String visitReturnStatement(ASTReturnStatement statement) {
        return "(return " + statement.node().accept(this) + ")";
    }

    @Override
    public String visitWhileStatement(ASTWhileStatement statement) {
        return null;
    }

    private String parenthesize(String name, IExpressionNode... expressions) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for (IExpressionNode expr : expressions) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }

    @Override
    public String visitBinary(ASTBinaryNode node) {
        return "(" + node.operation().name() + "(" + node.left().accept(this) + ", " + node.right().accept(this) + "))";
    }

    @Override
    public String visitUnary(ASTUnaryNode node) {
        return "(" + node.operation().name() + " (" + node.node().accept(this) + "))";
    }

    @Override
    public String visitValue(ASTValueNode node) {
        return node.value().toString();
    }

    @Override
    public String visitCall(ASTCallNode node) {
        return "(call " + node.function() + "(" + node.args().stream().map(arg -> arg.accept(this)).collect(Collectors.joining()) + "))";
    }

    @Override
    public String visitIdentifier(ASTIdentifierNode node) {
        return node.identifier();
    }

    @Override
    public String visitAssignment(ASTAssignNode node) {
        return null;
    }
}
