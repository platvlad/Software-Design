package ru.ifmo.CLI.Command;

import ru.ifmo.CLI.IOData;

//implementing echo Command (Output parameters)
public class EchoCommand extends Command {
    public EchoCommand() {
        super();
    }

    public IOData execute() {
        if (inPipe())
        {
            return data;
        } else {
            return new IOData(arguments);
        }
    }

}
