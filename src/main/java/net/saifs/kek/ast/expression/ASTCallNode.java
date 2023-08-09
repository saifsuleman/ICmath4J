package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

import java.util.List;

public record ASTCallNode(ASTGetterNode parent, List<IExpressionNode> args) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitCall(this);
    }

    @Override
    public String toString() {
        return "ASTCallNode{" +
                "function='" + parent + '\'' +
                ", args=" + args +
                '}';
    }
}
