package net.saifs.kek;

import net.saifs.kek.evaluator.Evaluator;
import net.saifs.kek.lexer.Lexer;
import net.saifs.kek.parser.Parser;
import net.saifs.kek.token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Kek {
    public static void main(String[] args) throws IOException {
        var lexer = new Lexer();

        var string = Files.readString(Path.of("test/program.kek"));
        lexer.read(string);

        List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = lexer.next()) != null) {
            tokens.add(token);
        }

        var parser = new Parser(tokens);
        var statements = parser.parse();

        var evaluator = new Evaluator();
        evaluator.executeProgram(statements);
    }
}
