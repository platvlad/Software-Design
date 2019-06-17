package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.InterpreterEnvironment;
import ru.ifmo.CLI.Utils.IOData;


/**
 * Class implementing pwd command (write current directory)
 */
public class PwdCommand extends Command {
    public PwdCommand() {
        super();
    }

    /**
     * Executes command
     * @return IOData with current directory name
     */
    public IOData execute() {
        String currentDirectory = InterpreterEnvironment.currentDirectoryName;
        return stringToIOData(currentDirectory, false);
    }
}
