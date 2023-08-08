package net.saifs.kek.ast.expression;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

public record ASTIdentifierNode(String identifier) implements IExpressionNode {
    @Override
    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visitIdentifier(this);
    }

    @Override
    public String toString() {
        return "ASTIdentifierNode{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}