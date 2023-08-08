package net.saifs.kek;

import net.saifs.kek.ast.internal.ASTPrinter;
import net.saifs.kek.ast.internal.IStatementNode;
import net.saifs.kek.lexer.Lexer;
import net.saifs.kek.parser.Parser;
import net.saifs.kek.token.Token;
import net.saifs.kek.token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kek {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();
//        lexer.read("fun thing() { if (25 != 25.2) { return 'teseitng this is a thing'; } }");
        lexer.read("let thing = 12;" +
                "fun something() {" +
                "   thing = 4;" +
                "   print(thing);" +
                "}");
        List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = lexer.next()) != null) {
            tokens.add(token);
        }
        Parser parser = new Parser(tokens);
        var ret = parser.parse();
        for (IStatementNode node : ret) {
            System.out.println(node.toString());
        }
    }
}
