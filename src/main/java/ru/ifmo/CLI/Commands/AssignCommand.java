package ru.ifmo.CLI;

import java.util.List;

//class implementing Assign command (a=$b)
public class AssignCommand extends Command {
    public AssignCommand(List<String> arguments) {
        super.arguments = arguments;
    }

    public AssignCommand(IOData data) {
        this.data = data;
    }

    public IOData execute() {
        int equalIndex = arguments.get(0).indexOf('=');
        String varName = arguments.get(0).substring(0, equalIndex);
        String varValue = arguments.get(0).substring(equalIndex + 1);
        InterpreterEnvironment.setValue(varName, varValue);
        return new IOData();
    }
}
