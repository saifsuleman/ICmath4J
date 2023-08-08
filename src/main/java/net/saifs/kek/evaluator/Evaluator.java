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
    public Void visitLetStatement(ASTLetStatement statement) {
        return null;
    }

    @Override
    public Void visitBlockStatement(ASTBlockStatement statement) {
        return null;
    }

    @Override
    public Void visitExpressionStatement(ASTExpressionStatement statement) {
        return null;
    }

    @Override
    public Void visitFunctionStatement(ASTFunctionStatement statement) {
        return null;
    }

    @Override
    public Void visitIfStatement(ASTIfStatement statement) {
        return null;
    }

    @Override
    public Void visitReturnStatement(ASTReturnStatement statement) {
        return null;
    }
}
