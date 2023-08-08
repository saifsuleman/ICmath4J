package net.saifs.kek.evaluator;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();
    private final Environment enclosing;

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
}
