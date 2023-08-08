package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

public record ASTAssignNode(String identifier, IExpressionNode value) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitAssignment(this);
    }
}
