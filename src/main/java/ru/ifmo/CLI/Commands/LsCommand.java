package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.InterpreterEnvironment;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;

public class LsCommand extends Command {
    @Override
    public IOData execute() {
        IOData result = new IOData();
        String path = InterpreterEnvironment.currentDirectoryName;
        if (!arguments.isEmpty()) {
            if (arguments.size() > 1) {
                return stringToIOData("arguments of ls should be less 2", true);
            }
            path += '/' + arguments.get(0);
        }
        File file = new File(path);
        if (file.isDirectory()) {
            System.out.println("result");
        }
        return result;
    }
}
