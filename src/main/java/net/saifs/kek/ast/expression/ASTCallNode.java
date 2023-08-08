package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

import java.util.List;

public record ASTCallNode(String function, List<IExpressionNode> args) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitCall(this);
    }
}
