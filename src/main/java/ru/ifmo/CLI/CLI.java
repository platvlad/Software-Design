package ru.ifmo.CLI;

import java.util.Scanner;

//Command line interpreter.
//Commands implemented: cat, echo, wc, pwd, exit
public class CLI {
    //read lines while exit signal was not sent
    //Process lines subsequently
    //create LineParser object for each line
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (!InterpreterEnvironment.exit) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                LineParser parser = new LineParser();
                parser.parse(line);
            }
        }
    }
}
