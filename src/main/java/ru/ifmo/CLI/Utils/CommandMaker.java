package ru.ifmo.CLI.Utils;

import ru.ifmo.CLI.Commands.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating Commands objects by names and arguments
 */
public class CommandMaker {

    private static Command makeEmptyCommand(String name) {
        switch(name) {
            case "pwd":
                return new PwdCommand();
            case "cat":
                return new CatCommand();
            case "wc":
                return new WcCommand();
            case "echo":
                return new EchoCommand();
            case "exit":
                return new ExitCommand();
            case "grep":
                return new GrepCommand();
            case "cd":
                return new CdCommand();
            case "ls":
                return new LsCommand();
            default:
                ArrayList<String> nameArgument = new ArrayList<>();
                nameArgument.add(name);
                Command command;
                int equalIndex = name.indexOf('=');
                if (equalIndex >= 0) {
                    command = new AssignCommand();
                } else {
                    command = new ExternalCommand();
                }
                command.addArguments(nameArgument);
                return command;
        }
    }

    /**
     * Create Commands by its name and arguments
     * @param name Command name
     * @param arguments Arguments for the command
     * @return Command object with provided arguments
     */
    public static Command makeCommand(String name, List<String> arguments) {
        return makeCommand(name, arguments, new IOData());
    }

    /**
     * Create Commands by name and previous command output
     * @param name Command name
     * @param data Pipe input from previous command
     * @return Command object
     */
    public static Command makeCommand(String name, List<String> arguments, IOData data) {
        Command command = makeEmptyCommand(name);
        command.addArguments(arguments);
        if (!data.isEmpty()) {
            command.setIOData(data);
        }
        return command;
    }
}
