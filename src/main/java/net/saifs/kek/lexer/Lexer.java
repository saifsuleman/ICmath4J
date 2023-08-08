package net.saifs.kek.lexer;

import net.saifs.kek.token.Token;
import net.saifs.kek.token.TokenType;

import java.util.regex.Matcher;

public class Lexer {
    private int cursor = 0;
    private String contents = "";

    public void read(String contents) {
        this.contents = contents;
        this.cursor = 0;
    }

    public Token next() {
        if (this.cursor >= this.contents.length()) {
            return null;
        }

        String str = this.contents.substring(this.cursor);
        for (TokenType type : TokenType.tokensWithRegex()) {
            Matcher matcher = type.getPattern().matcher(str);
            if (!matcher.find()) {
                continue;
            }

            this.cursor += matcher.group().length();
            String match = matcher.group(matcher.groupCount());

            if (type == TokenType.NONE) {
                return this.next();
            }

            if (type == TokenType.IDENT && TokenType.keywords.containsKey(match)) {
                return new Token(TokenType.keywords.get(match), match);
            }

            return new Token(type, match);
        }

        throw new RuntimeException("unrecognized input: "  + str);
    }
}
