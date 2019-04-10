package ru.ifmo.CLI;

//class implementing Assign command (a=$b)
public class AssignCommand extends Command {
    public AssignCommand(String[] arguments) {
        this.arguments = arguments;
    }

    public AssignCommand(IOData data) {
        this.data = data;
    }

    public IOData execute() {
        int equalIndex = arguments[0].indexOf('=');
        String varName = arguments[0].substring(0, equalIndex);
        String varValue = arguments[0].substring(equalIndex + 1);
        InterpreterEnvironment.setValue(varName, varValue);
        return new IOData();
    }
}
