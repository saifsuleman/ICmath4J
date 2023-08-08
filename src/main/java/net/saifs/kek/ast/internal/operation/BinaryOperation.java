package net.saifs.kek.ast.internal.operation;

import net.saifs.kek.token.TokenType;

public enum BinaryOperation {
    ADD, SUBTRACT, MUL, DIV, EXP, EQ, NOT_EQ, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL;

    public static BinaryOperation of(TokenType type) {
        switch (type) {
            case PLUS -> {
                return ADD;
            }
            case MINUS -> {
                return SUBTRACT;
            }
            case MUL -> {
                return MUL;
            }
            case DIV -> {
                return DIV;
            }
            case EXP -> {
                return EXP;
            }
            case EQ -> {
                return EQ;
            }
            case BANG_EQ -> {
                return NOT_EQ;
            }
            default -> throw new RuntimeException("invalid binary operation: " + type);
        }
    }
}
