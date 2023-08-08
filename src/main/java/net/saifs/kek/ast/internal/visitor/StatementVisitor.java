package net.saifs.kek.ast.internal.visitor;

import net.saifs.kek.ast.statement.*;

public interface StatementVisitor<R> {
    R acceptLetStatement(ASTLetStatement statement);
    R acceptBlockStatement(ASTBlockStatement statement);
    R acceptExpressionStatement(ASTExpressionStatement statement);
    R acceptFunctionStatement(ASTFunctionStatement statement);
    R acceptIfStatement(ASTIfStatement statement);
    R acceptReturnStatement(ASTReturnStatement statement);
}
