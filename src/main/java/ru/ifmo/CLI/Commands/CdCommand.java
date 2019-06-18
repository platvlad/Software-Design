package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.InterpreterEnvironment;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.util.List;

/**
 * Class implementing cd command
 */
public class CdCommand extends Command {

    public CdCommand() {
        super();
    }

    /**
     * Execute command
     * @return Empty IOData in case of success. Error message otherwise
     */
    public IOData execute() {
        List<String> folderNames = arguments;
        if (fromPipe()) {
            folderNames.addAll(data.getData());
        }
        if (folderNames.isEmpty()) {
            return stringToIOData("No arguments provided to cd", true);
        }
        if (folderNames.size() > 1) {
            return stringToIOData("Too many arguments provided to cd", true);
        }
        String directoryName = folderNames.get(0);
        String sameDirectory = "." + File.separator;
        if (directoryName.equals(sameDirectory) || directoryName.equals("./")) {
            return new IOData();
        }
        String parentDirectory = ".." + File.separator;
        if (directoryName.equals(parentDirectory) || directoryName.equals("../")) {
            File directory = new File(InterpreterEnvironment.currentDirectoryName);
            InterpreterEnvironment.currentDirectoryName = directory.getParentFile().getAbsolutePath();
            return new IOData();
        }
        File directory = new File(directoryName);
        if (!directory.isAbsolute()) {
            directoryName = InterpreterEnvironment.currentDirectoryName + File.separator + directoryName;
            directory = new File(directoryName);
        }

        if (directory.exists()) {
            if (directory.isDirectory()) {
                InterpreterEnvironment.currentDirectoryName = directory.getAbsolutePath();
                return new IOData();
            }
            return stringToIOData(directoryName + " is not a directory", true);
        }
        return stringToIOData("No such file or directory: " + directoryName, true);
    }
}
