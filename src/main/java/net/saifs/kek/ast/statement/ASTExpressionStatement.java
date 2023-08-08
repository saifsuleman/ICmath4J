package net.saifs.kek.ast.statement;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.IStatementNode;
import net.saifs.kek.ast.internal.visitor.StatementVisitor;

public record ASTExpressionStatement(IExpressionNode node) implements IStatementNode {
    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.visitExpressionStatement(this);
    }
}
