package net.saifs.kek.ast.internal.operation;

import net.saifs.kek.token.TokenType;

public enum BinaryOperation {
    ADD, SUBTRACT, MUL, DIV, EXP, EQ, NOT_EQ, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL;

    public static BinaryOperation of(TokenType type) {
        return switch (type) {
            case PLUS -> ADD;
            case MINUS -> SUBTRACT;
            case MUL -> MUL;
            case DIV -> DIV;
            case EXP -> EXP;
            case EQ_EQ -> EQ;
            case BANG_EQ -> NOT_EQ;
            case GREATER -> GREATER;
            case GREATER_EQ -> GREATER_EQUAL;
            case LESS -> LESS;
            case LESS_EQ -> LESS_EQUAL;
            default -> throw new RuntimeException("invalid binary operation: " + type);
        };
    }
}
