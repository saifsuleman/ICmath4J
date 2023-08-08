package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.operation.BinaryOperation;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

public record ASTBinaryNode(IExpressionNode left, IExpressionNode right,
                            BinaryOperation operation) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitBinary(this);
    }
}
