package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

public record ASTValueNode(Object value) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitValue(this);
    }
}
