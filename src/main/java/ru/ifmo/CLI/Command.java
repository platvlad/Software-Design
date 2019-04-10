package ru.ifmo.CLI;

import java.util.ArrayList;
import java.util.List;

//class for Command abstraction
//Each command shall inherit this class
public abstract class Command {
    Command () {
    }

    //construct command using provided arguments
    Command(List<String> arguments) { this.arguments = arguments; }

    //construct command using pipe content
    Command(IOData data) {
        arguments = new ArrayList<>();
        this.data = data;
    }

    //implements command logic
    public abstract IOData execute();

    protected List<String> arguments;
    protected IOData data;
}
