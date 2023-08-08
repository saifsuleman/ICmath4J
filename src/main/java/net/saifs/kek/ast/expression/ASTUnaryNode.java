package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;
import net.saifs.kek.ast.internal.operation.UnaryOperation;

public record ASTUnaryNode(UnaryOperation operation, IExpressionNode node) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitUnary(this);
    }

    @Override
    public String toString() {
        return "ASTUnaryNode{" +
                "operation=" + operation +
                ", node=" + node +
                '}';
    }
}
