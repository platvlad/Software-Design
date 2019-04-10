package ru.ifmo.CLI;

//implements pwd command (write current directory)
public class PwdCommand extends Command {

    public PwdCommand(String[] words) { }

    public PwdCommand(IOData data) {}
    public IOData execute() {
        String currentDirectory = System.getProperty("user.dir");
        String[] output = { currentDirectory };
        return new IOData(output);
    }
}
