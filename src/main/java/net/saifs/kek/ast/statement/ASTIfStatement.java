package net.saifs.kek.ast.statement;

import net.saifs.kek.ast.internal.IExpressionNode;
import net.saifs.kek.ast.internal.IStatementNode;
import net.saifs.kek.ast.internal.visitor.StatementVisitor;

public record ASTIfStatement(IExpressionNode condition, IStatementNode thenBranch, IStatementNode elseBranch) implements IStatementNode {
    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.acceptIfStatement(this);
    }
}
