package ru.ifmo.CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//class for creating Command objects by names and arguments
public class CommandMaker {
    private static Command makeExternal(String name, List<String> arguments) {
        List<String> extraCommandArgs = new ArrayList<String>();
        extraCommandArgs.add(name);
        extraCommandArgs.addAll(arguments);
        return new ExternalCommand(extraCommandArgs);
    }

    //create Command by its name and arguments
    public static Command makeCommand(String name, List<String> arguments) {
        switch(name) {
            case "pwd":
                return new PwdCommand(arguments);
            case "cat":
                return new CatCommand(arguments);
            case "wc":
                return new WcCommand(arguments);
            case "echo":
                return new EchoCommand(arguments);
            case "exit":
                return new ExitCommand(arguments);
            case "grep":
                return new GrepCommand(arguments);
            default:
                int equalIndex = name.indexOf('=');
                if (equalIndex >= 0) {
                    List<String> assignArgument = new ArrayList<>();
                    assignArgument.add(name);
                    return new AssignCommand(assignArgument);
                }
                return makeExternal(name, arguments);
        }
    }

    //create Command by name and previous command output
    public static Command makeCommand(String name, IOData data) {
        switch(name) {
            case "pwd":
                return new PwdCommand(data);
            case "cat":
                return new CatCommand(data);
            case "wc":
                return new WcCommand(data);
            case "echo":
                return new EchoCommand(data);
            case "exit":
                return new ExitCommand(data);
            default:
                int equalIndex = name.indexOf('=');
                if (equalIndex >= 0) {
                    List<String> assignArgument = new ArrayList<>();
                    assignArgument.add(name);
                    return new AssignCommand(assignArgument);
                }
                return makeExternal(name, data.getData());
        }
    }
}
