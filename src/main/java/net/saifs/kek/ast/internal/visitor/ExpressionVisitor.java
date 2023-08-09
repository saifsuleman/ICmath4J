package net.saifs.kek.ast.internal.visitor;

import net.saifs.kek.ast.expression.*;

public interface ExpressionVisitor<R> {
    R visitBinary(ASTBinaryNode node);
    R visitUnary(ASTUnaryNode node);
    R visitValue(ASTValueNode node);
    R visitCall(ASTCallNode node);
    R visitIdentifier(ASTIdentifierNode node);
    R visitAssignment(ASTAssignNode node);
    R visitGetter(ASTGetterNode node);
}
