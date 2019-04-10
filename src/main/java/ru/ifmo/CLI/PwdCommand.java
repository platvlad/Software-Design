package ru.ifmo.CLI;

import java.util.ArrayList;
import java.util.List;

//implements pwd command (write current directory)
public class PwdCommand extends Command {

    public PwdCommand(List<String> words) { }

    public PwdCommand(IOData data) {}
    public IOData execute() {
        String currentDirectory = System.getProperty("user.dir");
        List<String> output = new ArrayList<>();
        output.add(currentDirectory);
        return new IOData(output);
    }
}
