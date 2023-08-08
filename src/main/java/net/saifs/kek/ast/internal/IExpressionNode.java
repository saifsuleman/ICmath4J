package net.saifs.kek.ast.internal;

import net.saifs.kek.ast.internal.visitor.ExpressionVisitor;

public interface IExpressionNode {
    <T> T accept(ExpressionVisitor<T> visitor);
}