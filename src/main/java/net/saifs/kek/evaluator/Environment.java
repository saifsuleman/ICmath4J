package net.saifs.kek.evaluator;

import net.saifs.kek.ast.expression.ASTGetterNode;
import net.saifs.kek.ast.expression.ASTIdentifierNode;
import net.saifs.kek.ast.internal.IExpressionNode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    private Object reflectionGet(Object object, String key) throws IllegalAccessException {
        Class<?> clazz = object.getClass();

        Field[] fields = clazz.getFields();
        Method[] methods = clazz.getMethods();

        for (Field field : fields) {
            if (field.getName().equals(key)) {
                field.setAccessible(true);
                return field.get(object);
            }
        }

        for (Method method : methods) {
            if (method.getName().equals(key)) {
                method.setAccessible(true);
                return new Callable() {
                    @Override
                    public int arity() {
                        return method.getParameterCount();
                    }

                    @Override
                    public Object call(Evaluator evaluator, List<Object> arguments) {
                        try {
                            return method.invoke(object, arguments.toArray());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                };
            }
        }

        return null;
    }

    public Object get(IExpressionNode node) {
        if (node instanceof ASTIdentifierNode ident) {
            String key = ident.identifier();
            return this.get(key);
        }

        if (node instanceof ASTGetterNode getter) {
            if (getter.parent() == null) {
                return this.get(getter.key());
            }

            Object parent = get(getter.parent());
            String key = getter.key();
            Object ret = null;
            try {
                ret = reflectionGet(parent, key);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.println("doing reflection get on " + parent.getClass().getName() + " with key " + key + " and returning: " + ret);
            return ret;
        }

        return null;
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
