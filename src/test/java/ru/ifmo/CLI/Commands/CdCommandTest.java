package ru.ifmo.CLI.Commands;

import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.InterpreterEnvironment;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CdCommandTest {

    private CdCommand makeCdCommand(String destination) {
        var command = new CdCommand();
        List<String> arguments = new ArrayList<>();
        arguments.add(destination);
        command.addArguments(arguments);
        return command;
    }

    @Test
    public void testCdSrc() {
        Command command = makeCdCommand("src");
        command.execute();
        String currentDirectory = InterpreterEnvironment.currentDirectoryName;
        String regex = "\\" + File.separator;
        String[] pathParts = currentDirectory.split(regex);
        assertEquals("src", pathParts[pathParts.length - 1]);
    }

    @Test
    public void testCdTwoDirs() {
        Command firstCd = makeCdCommand("src");
        firstCd.execute();
        Command secondCd = makeCdCommand("main");
        secondCd.execute();
        String currentDirectory = InterpreterEnvironment.currentDirectoryName;
        String regex = "\\" + File.separator;
        String[] pathParts = currentDirectory.split(regex);
        assertEquals("main", pathParts[pathParts.length - 1]);
        assertEquals("src", pathParts[pathParts.length - 2]);
    }

    @Test
    public void testCdSame() {
        Command command = makeCdCommand("./");
        String oldDirectory = InterpreterEnvironment.currentDirectoryName;
        command.execute();
        assertEquals(oldDirectory, InterpreterEnvironment.currentDirectoryName);
    }

    @Test
    public void testCdBack() {
        Command firstCd = makeCdCommand("src");
        firstCd.execute();
        Command secondCd = makeCdCommand("main");
        secondCd.execute();
        String oldDirectory = InterpreterEnvironment.currentDirectoryName;
        String regex = "\\" + File.separator;
        String[] oldPathParts = oldDirectory.split(regex);
        Command backCd = makeCdCommand("../");
        backCd.execute();
        String newDirectory = InterpreterEnvironment.currentDirectoryName;
        String[] newPathParts = newDirectory.split(regex);
        assertEquals(oldPathParts[oldPathParts.length - 2], newPathParts[newPathParts.length - 1]);
    }

    @Test
    public void testNonExisting() {
        Command firstCd = makeCdCommand("src");
        firstCd.execute();
        String oldDirectory = InterpreterEnvironment.currentDirectoryName;
        Command secondCd = makeCdCommand("foo");
        IOData result = secondCd.execute();
        String newDirectory = InterpreterEnvironment.currentDirectoryName;
        assertEquals(oldDirectory, newDirectory);
        assertTrue(result.errorOccured());
    }
}
