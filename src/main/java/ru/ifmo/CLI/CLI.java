package ru.ifmo.CLI;

import ru.ifmo.CLI.Parser.LineParser;

import java.util.Scanner;

/**
 * Commands line interpreter.
 * Commands implemented: cat, echo, wc, pwd, exit, grep
 */
public class CLI {
    /**
     * Read lines while exit signal was not sent
     * Process lines subsequently
     * Create LineParser object for each line
     */
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        while (!InterpreterEnvironment.exit) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                var parser = new LineParser();
                parser.parse(line);
            }
        }
    }
}
