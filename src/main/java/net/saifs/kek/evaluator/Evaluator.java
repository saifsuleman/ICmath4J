package net.saifs.kek.evaluator;

import net.saifs.kek.ast.expression.*;
import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.IStatementNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;
import net.saifs.kek.ast.internal.visitor.StatementVisitor;
import net.saifs.kek.ast.statement.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Evaluator implements StatementVisitor<Void>, ExpressionVisitor<Object> {
    private Environment environment;

    public Evaluator() {
        this.environment = new Environment();
        this.environment.define("print", new Callable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Evaluator evaluator, List<Object> arguments) {
                System.out.println(arguments.get(0));
                return null;
            }
        });

        this.environment.define("string", new Callable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Evaluator evaluator, List<Object> arguments) {
                return arguments.get(0).toString();
            }
        });
    }


    public void executeProgram(List<IStatementNode> statements) {
        for (IStatementNode statement : statements) {
            execute(statement);
        }
    }

    public void executeBlock(List<IStatementNode> statements, Environment environment) {
        Environment previous = this.environment;

        try {
            this.environment = environment;

            for (IStatementNode statement : statements) {
                this.execute(statement);

                ReturnContext context = environment.getReturnContext();
                if (context != null && context.hasReturn()) {
                    break;
                }
            }

        } finally {
            this.environment = previous;
        }
    }

    public void execute(IStatementNode statement) {
        statement.accept(this);
    }

    public Object evaluate(IExpressionNode expression) {
        return expression.accept(this);
    }

    @Override
    public Object visitBinary(ASTBinaryNode node) {
        Object left = this.evaluate(node.left());
        Object right = this.evaluate(node.right());

        switch (node.operation()) {
            case ADD -> {
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }

                if (left instanceof String && right instanceof String) {
                    return left + (String) right;
                }

                throw new RuntimeException("left and right must be both numbers or doubles for '+'");
            }
            case SUBTRACT -> {
                return (double) left - (double) right;
            }
            case MUL -> {
                return (double) left * (double) right;
            }
            case DIV -> {
                return (double)left/(double)right;
            }
            case EXP -> {
                return Math.pow((double)left,(double)right);
            }
            case EQ -> {
                return Objects.equals(left, right);
            }
            case NOT_EQ -> {
                return !Objects.equals(left, right);
            }
            case GREATER -> {
                return (double) left > (double) right;
            }
            case GREATER_EQUAL -> {
                return (double) left >= (double) right;
            }
            case LESS -> {
                return (double) left < (double) right;
            }
            case LESS_EQUAL -> {
                return (double) left <= (double) right;
            }
        }

        throw new RuntimeException("AAAAAAAAAAAAAAAAAAAAAAAA");
    }

    @Override
    public Object visitUnary(ASTUnaryNode node) {
        Object right = this.evaluate(node.node());

        switch (node.operation()) {
            case NOT -> {
                return !isTruthy(right);
            }
            case NEGATIVE -> {
                if (right instanceof Double d) {
                    return -d;
                }

                throw new RuntimeException("unary '-' requires number!");
            }
        }

        return null;
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean b) return b;
        if (object instanceof Double d)  return d != 0.0;
        return true;
    }

    @Override
    public Object visitValue(ASTValueNode node) {
        return node.value();
    }

    @Override
    public Object visitCall(ASTCallNode node) {
        Object callee = this.environment.get(node.function());

        List<Object> arguments = new ArrayList<>();
        for (IExpressionNode argument : node.args()) {
            arguments.add(this.evaluate(argument));
        }

        if (!(callee instanceof Callable callable)) {
            throw new RuntimeException("only functions are callable - attempted to call: " + callee);
        }

        return callable.call(this, arguments);
    }

    @Override
    public Object visitIdentifier(ASTIdentifierNode node) {
        return environment.get(node.identifier());
    }

    @Override
    public Object visitAssignment(ASTAssignNode node) {
        Object value = evaluate(node.value()); // TODO: for now, don't give a fuck about distances - need to implement it later though
        this.environment.assign(node.identifier(), value);
        return null;
    }

    @Override
    public Void visitLetStatement(ASTLetStatement statement) {
        Object value = evaluate(statement.value());
        this.environment.define(statement.identifier(), value);
        return null;
    }

    @Override
    public Void visitBlockStatement(ASTBlockStatement statement) {
        Environment environment = new Environment(this.environment);
        executeBlock(statement.statements(), environment);
        return null;
    }

    @Override
    public Void visitExpressionStatement(ASTExpressionStatement statement) {
        this.evaluate(statement.node());
        return null;
    }

    @Override
    public Void visitFunctionStatement(ASTFunctionStatement statement) {
        Function function = new Function(statement, environment);
        environment.define(statement.name(), function);
        return null;
    }

    @Override
    public Void visitIfStatement(ASTIfStatement statement) {
        if (isTruthy(evaluate(statement.condition()))) {
            this.execute(statement.thenBranch());
        } else if (statement.elseBranch() != null) {
            this.execute(statement.elseBranch());
        }
        return null;
    }

    @Override
    public Void visitReturnStatement(ASTReturnStatement statement) {
        Object returnValue =  evaluate(statement.node());
        this.environment.executeReturn(returnValue);
        return null;
    }

    @Override
    public Void visitWhileStatement(ASTWhileStatement statement) {
        while (isTruthy(evaluate(statement.condition()))) {
            execute(statement.statement());

            ReturnContext context = this.environment.getReturnContext();
            if (context != null && context.hasReturn()) {
                break;
            }
        }
        return null;
    }
}
