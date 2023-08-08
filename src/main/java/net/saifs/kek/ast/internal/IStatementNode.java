package net.saifs.kek.ast.internal;

import net.saifs.kek.ast.internal.visitor.StatementVisitor;

public interface IStatementNode {
    <R> R accept(StatementVisitor<R> visitor);
}
