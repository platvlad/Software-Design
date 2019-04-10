package ru.ifmo.CLI;

//class for Command abstraction
//Each command shall inherit this class
public abstract class Command {
    Command () {
    }

    //construct command using provided arguments
    Command(String[] arguments) {
        this.arguments = arguments;
    }

    //construct command using pipe content
    Command(IOData data) {
        arguments = new String[0];
        this.data = data;
    }

    //implements command logic
    public abstract IOData execute();

    protected String[] arguments;
    protected IOData data;
}
