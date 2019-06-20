package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;
import ru.ifmo.CLI.InterpreterEnvironment;

/**
 * Class implementing Assign command (a=$b)
 */
public class AssignCommand extends Command {
    public AssignCommand() {
        super();
    }

    /**
     * execute the command
     * @return empty IOData for successful assignment. Error message otherwise
     */
    public IOData execute() {
        if (arguments.size() != 1) {
            String message = "Failed to assign variable with " + arguments.size() + " values";
            return stringToIOData(message, true);
        }
        String assignment = arguments.get(0);
        int equalIndex = assignment.indexOf('=');
        String varName = assignment.substring(0, equalIndex);
        String varValue = assignment.substring(equalIndex + 1);
        InterpreterEnvironment.setValue(varName, varValue);
        return new IOData();
    }
}
