package net.saifs.kek.evaluator;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();
    private final Environment enclosing;
    private ReturnContext returnContext = null;

    public Environment() {
        this.enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public Object get(String key) {
        if (values.containsKey(key)) {
            return values.get(key);
        }

        if (enclosing != null) {
            return enclosing.get(key);
        }

        throw new RuntimeException("undefined variable: " + key);
    }

    public void assign(String key, Object value) {
        if (values.containsKey(key)) {
            values.put(key, value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(key, value);
            return;
        }

        throw new RuntimeException("undefined variable: " + key);
    }

    public void define(String key, Object value) {
        values.put(key, value);
    }

    public void executeReturn(Object value) {
        ReturnContext context = this.getReturnContext();
        if (context == null) {
            throw new RuntimeException("no function context to return from");
        }
        context.setReturn(value);
    }

    public ReturnContext getReturnContext() {
        if (this.returnContext != null) {
            return this.returnContext;
        }

        if (this.enclosing == null) {
            return null;
        }

        return this.enclosing.getReturnContext();
    }

    public void createFunctionContext() {
        this.returnContext = new ReturnContext();
    }
}
