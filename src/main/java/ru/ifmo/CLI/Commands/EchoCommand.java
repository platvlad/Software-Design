package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing echo Commands (Output parameters)
 */
public class EchoCommand extends Command {
    public EchoCommand() {
        super();
    }

    private IOData joinedIOData(List<String> input) {
        String joined = String.join(" ", input);
        List<String> output = new ArrayList<>();
        output.add(joined);
        return new IOData(output);
    }

    /**
     * Executes command
     * @return IOData with arguments joining into single string
     */
    public IOData execute() {
        return joinedIOData(arguments);
    }

}
