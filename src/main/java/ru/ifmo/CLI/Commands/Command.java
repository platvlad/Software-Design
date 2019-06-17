package ru.ifmo.CLI.Commands;

import java.util.ArrayList;
import java.util.List;
import ru.ifmo.CLI.Utils.IOData;

//class for Commands abstraction
//Each command shall inherit this class
public abstract class Command {

    protected List<String> arguments;
    protected IOData data;

    Command () {
        arguments = new ArrayList<>();
    }

    //construct command using provided arguments
    Command(List<String> arguments) { this.arguments = arguments; }

    //construct command using pipe content
    Command(IOData data) {
        arguments = new ArrayList<>();
        this.data = data;
    }

    public void addArguments(List<String> arguments) {
        this.arguments.addAll(arguments);
    }

    public void setIOData(IOData data) {
        this.data = data;
    }

    public boolean hasArguments() {
        return !arguments.isEmpty();
    }

    boolean fromPipe() {
        return (data != null);
    }

    IOData stringToIOData(String message) {
        List<String> output = new ArrayList<>();
        output.add(message);
        return new IOData(output);
    }

    //implements command logic
    public abstract IOData execute();

}
