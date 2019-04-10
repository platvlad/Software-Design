package ru.ifmo.CLI;

import java.util.List;

//implementing echo Command (Output parameters)
public class EchoCommand extends Command {

    public EchoCommand(List<String> arguments) {
        super.arguments = arguments;
    }

    public EchoCommand(IOData data) {
        this.data = data;
    }

    public IOData execute() {
        if (arguments != null)
        {
            return new IOData(arguments);
        }
        else
        {
            return data;
        }
    }

}
