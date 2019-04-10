package ru.ifmo.CLI;

//implement command for exiting interpreter
public class ExitCommand extends Command {
    public ExitCommand(String[] arguments) {
        this.arguments = arguments;
    }
    public ExitCommand(IOData data) {
        this.data = data;
    }
    public IOData execute() {
        InterpreterEnvironment.exit = true;
        return new IOData();
    }
}
