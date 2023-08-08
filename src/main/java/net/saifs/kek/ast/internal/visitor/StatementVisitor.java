package net.saifs.kek.ast.internal.visitor;

import net.saifs.kek.ast.statement.*;

public interface StatementVisitor<R> {
    R visitLetStatement(ASTLetStatement statement);
    R visitBlockStatement(ASTBlockStatement statement);
    R visitExpressionStatement(ASTExpressionStatement statement);
    R visitFunctionStatement(ASTFunctionStatement statement);
    R visitIfStatement(ASTIfStatement statement);
    R visitReturnStatement(ASTReturnStatement statement);
    R visitWhileStatement(ASTWhileStatement statement);
}
