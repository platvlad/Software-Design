package ru.ifmo.CLI.Command;

import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//implementing echo Command (Output parameters)
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

    public IOData execute() {
        return joinedIOData(arguments);
    }

}
