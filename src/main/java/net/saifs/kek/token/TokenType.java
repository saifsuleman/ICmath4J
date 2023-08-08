package net.saifs.kek.token;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum TokenType {
    NONE(Pattern.compile("^\\s+")),
    NUMBER(Pattern.compile("^-?\\d+(?:\\.\\d+)?")),
    IDENT(Pattern.compile("^[a-zA-Z]+")),
    PLUS(Pattern.compile("^\\+")),
    MINUS(Pattern.compile("^-")),
    MUL(Pattern.compile("^\\*")),
    DIV(Pattern.compile("^/")),
    EXP(Pattern.compile("^\\^")),
    LBRACKET(Pattern.compile("^\\(")),
    RBRACKET(Pattern.compile("^\\)")),
    COMMA(Pattern.compile("^,")),
    DOT(Pattern.compile("^\\.")),
    SEMICOLON(Pattern.compile("^;")),
    LBRACE(Pattern.compile("^\\{")),
    RBRACE(Pattern.compile("^}")),
    EQ(Pattern.compile("^=(?!=)")),
    EQ_EQ(Pattern.compile("^==")),
    BANG(Pattern.compile("^!(?!=)")),
    BANG_EQ(Pattern.compile("^!=")),
    STRING(Pattern.compile("^([\"'])(.*?)\\1")),
    GREATER(Pattern.compile("^>(?!=)")),
    LESS(Pattern.compile("^<(?!=)")),
    GREATER_EQ(Pattern.compile("^>=")),
    LESS_EQ(Pattern.compile("^<=")),
    TRUE, FALSE, AND, OR, FUN, IF, NIL, RETURN, LET, WHILE, FOR, ELSE
    ;

    public static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        for (TokenType tokenType : TokenType.values()) {
            if (tokenType.pattern != null) {
                continue;
            }
            keywords.put(tokenType.name().toLowerCase(), tokenType);
        }
    }

    private Pattern pattern;

    TokenType(String regex) {
        this(Pattern.compile(regex));
    }

    TokenType(Pattern pattern) {
        this.pattern = pattern;
    }

    TokenType() {
    }

    public static List<TokenType> tokensWithRegex() {
        return Arrays.stream(TokenType.values()).filter(type -> type.pattern != null)
                .collect(Collectors.toList());
    }

    public Pattern getPattern() {
        return pattern;
    }
}
