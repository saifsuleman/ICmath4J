package net.saifs.kek.evaluator;

import net.saifs.kek.ast.statement.ASTFunctionStatement;

import java.util.List;

public class Function implements Callable {
    private final ASTFunctionStatement statement;
    private final Environment closure;

    public Function(ASTFunctionStatement statement, Environment closure) {
        this.statement = statement;
        this.closure = closure;
    }


    @Override
    public int arity() {
        return statement.parameters().size();
    }

    @Override
    public Object call(Evaluator evaluator, List<Object> arguments) {
        Environment environment = new Environment(this.closure);

        for (int i = 0; i < this.arity(); i++) {
            environment.define(statement.parameters().get(i), arguments.get(i));
        }

        evaluator.executeBlock(statement.body(), environment);

        if (environment.getReturnContext().hasReturn()) {
            return environment.getReturnContext().getReturnValue();
        }

        return null;
    }
}
