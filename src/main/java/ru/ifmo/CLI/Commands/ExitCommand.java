package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;
import ru.ifmo.CLI.InterpreterEnvironment;


/**
 * Class implementing command for exiting interpreter
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        super();
    }

    /**
     * Executes command
     * @return Empty IOData
     */
    public IOData execute() {
        InterpreterEnvironment.exit = true;
        return new IOData();
    }
}
