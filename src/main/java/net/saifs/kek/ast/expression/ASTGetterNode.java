package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

public record ASTGetterNode(IExpressionNode parent, String key) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitGetter(this);
    }
}
