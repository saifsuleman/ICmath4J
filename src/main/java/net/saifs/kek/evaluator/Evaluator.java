package net.saifs.kek.evaluator;

import net.saifs.kek.ast.expression.*;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;
import net.saifs.kek.ast.internal.visitor.StatementVisitor;
import net.saifs.kek.ast.statement.*;

public class Evaluator implements StatementVisitor<Void>, ExpressionVisitor<Object> {

    @Override
    public Object visitBinary(ASTBinaryNode node) {
        return null;
    }

    @Override
    public Object visitUnary(ASTUnaryNode node) {
        return null;
    }

    @Override
    public Object visitValue(ASTValueNode node) {
        return null;
    }

    @Override
    public Object visitCall(ASTCallNode node) {
        return null;
    }

    @Override
    public Object visitIdentifier(ASTIdentifierNode node) {
        return null;
    }

    @Override
    public Object visitAssignment(ASTAssignNode node) {
        return null;
    }

    @Override
    public Void acceptLetStatement(ASTLetStatement statement) {
        return null;
    }

    @Override
    public Void acceptBlockStatement(ASTBlockStatement statement) {
        return null;
    }

    @Override
    public Void acceptExpressionStatement(ASTExpressionStatement statement) {
        return null;
    }

    @Override
    public Void acceptFunctionStatement(ASTFunctionStatement statement) {
        return null;
    }

    @Override
    public Void acceptIfStatement(ASTIfStatement statement) {
        return null;
    }

    @Override
    public Void acceptReturnStatement(ASTReturnStatement statement) {
        return null;
    }
}
