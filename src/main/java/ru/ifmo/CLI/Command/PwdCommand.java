package ru.ifmo.CLI.Command;

import ru.ifmo.CLI.Utils.IOData;


//implements pwd command (write current directory)
public class PwdCommand extends Command {
    public PwdCommand() {
        super();
    }

    public IOData execute() {
        String currentDirectory = System.getProperty("user.dir");
        return stringToIOData(currentDirectory);
    }
}
