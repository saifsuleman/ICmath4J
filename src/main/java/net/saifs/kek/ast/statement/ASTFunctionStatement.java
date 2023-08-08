package net.saifs.kek.ast.statement;

import net.saifs.kek.ast.internal.IStatementNode;
import net.saifs.kek.ast.internal.visitor.StatementVisitor;

import java.util.List;

public record ASTFunctionStatement(String name, List<String> parameters, List<IStatementNode> body) implements IStatementNode {
    @Override
    public <R> R accept(StatementVisitor<R> visitor) {
        return visitor.acceptFunctionStatement(this);
    }
}
