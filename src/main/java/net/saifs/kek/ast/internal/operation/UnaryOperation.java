package net.saifs.kek.ast.internal.operation;

import net.saifs.kek.token.TokenType;

public enum UnaryOperation {
    NOT, NEGATIVE;

    public static UnaryOperation of(TokenType type) {
        return switch (type) {
            case BANG -> NOT;
            case MINUS -> NEGATIVE;
            default -> throw new RuntimeException("invalid unary operator: " + type);
        };
    }
}
