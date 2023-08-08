package net.saifs.kek.evaluator;

import java.util.List;

public interface Callable {
    int arity();
    Object call(Evaluator evaluator, List<Object> arguments);
}
