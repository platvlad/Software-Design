package ru.ifmo.CLI.Utils;

import ru.ifmo.CLI.Command.*;

import java.util.ArrayList;
import java.util.List;

//class for creating Command objects by names and arguments
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

    //create Command by its name and arguments
    public static Command makeCommand(String name, List<String> arguments) {
        Command command = makeEmptyCommand(name);
        command.addArguments(arguments);
        return command;
    }

    //create Command by name and previous command output
    public static Command makeCommand(String name, IOData data) {
        Command command = makeEmptyCommand(name);
        if (command.hasArguments()) {
            command.addArguments(data.getData());
            return command;
        }
        command.setIOData(data);
        return command;
    }
}
