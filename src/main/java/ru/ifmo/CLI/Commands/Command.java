package ru.ifmo.CLI.Commands;

import java.util.ArrayList;
import java.util.List;
import ru.ifmo.CLI.Utils.IOData;

/**
 * Class for Commands abstraction
 * Each command shall inherit this class
**/
public abstract class Command {

    protected List<String> arguments;
    protected IOData data;

    Command () {
        arguments = new ArrayList<>();
    }

    /**
     * construct command using provided arguments
     * @param arguments command arguments
     */
    Command(List<String> arguments) { this.arguments = arguments; }

    /**
     * Construct command using pipe content
     * @param data IOData from the previous command
     */
    Command(IOData data) {
        arguments = new ArrayList<>();
        this.data = data;
    }

    /**
     * add arguments to the command
     * @param arguments new command arguments
     */
    public void addArguments(List<String> arguments) {
        this.arguments.addAll(arguments);
    }

    /**
     * Set pipe content as command input
     * @param data IOData from previous command
     */
    public void setIOData(IOData data) {
        this.data = data;
    }

    /**
     * Check if arguments added to command
     * @return True if the command has arguments
     */
    public boolean hasArguments() {
        return !arguments.isEmpty();
    }

    /**
     * Check if command get input data from pipe
     * @return True if command get input from pipe
     */
    boolean fromPipe() {
        return (data != null);
    }

    /**
     * Make pipe output using single string
     * @param message Result of the command execution
     * @param isError True if messages describes an error occurred during execution
     * @return Pipe output
     */
    IOData stringToIOData(String message, boolean isError) {
        List<String> output = new ArrayList<>();
        output.add(message);
        return new IOData(output, isError);
    }

    /**
     * Implements command logic
     * @return Command execution result
     */
    public abstract IOData execute();

}
