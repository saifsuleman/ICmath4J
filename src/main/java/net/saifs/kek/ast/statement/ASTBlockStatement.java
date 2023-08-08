package net.saifs.kek.ast.statement;

import net.saifs.kek.ast.internal.IStatementNode;
import net.saifs.kek.ast.internal.visitor.StatementVisitor;

import java.util.List;

public record ASTBlockStatement(List<IStatementNode> statements) implements IStatementNode {
    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitBlockStatement(this);
    }
}
