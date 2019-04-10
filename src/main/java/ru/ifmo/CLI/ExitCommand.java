package ru.ifmo.CLI;

import java.util.List;

//implement command for exiting interpreter
public class ExitCommand extends Command {
    public ExitCommand(List<String> arguments) {
        super.arguments = arguments;
    }
    public ExitCommand(IOData data) {
        this.data = data;
    }
    public IOData execute() {
        InterpreterEnvironment.exit = true;
        return new IOData();
    }
}
