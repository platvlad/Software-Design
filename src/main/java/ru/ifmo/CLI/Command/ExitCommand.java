package ru.ifmo.CLI.Command;

import ru.ifmo.CLI.IOData;
import ru.ifmo.CLI.InterpreterEnvironment;


//implement command for exiting interpreter
public class ExitCommand extends Command {
    public ExitCommand() {
        super();
    }

    public IOData execute() {
        InterpreterEnvironment.exit = true;
        return new IOData();
    }
}
