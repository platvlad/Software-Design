package ru.ifmo.CLI.Commands;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatCommandTest {

    private List<String> catFile(String fileName) {
        List<String> arguments = new ArrayList<>();
        arguments.add("src/test/resources/" + fileName);
        CatCommand command = new CatCommand();
        command.addArguments(arguments);
        IOData outputData = command.execute();
        return outputData.getData();
    }

    @Test
    public void testCatEmptyFile() {
        List<String> result = catFile("empty.txt");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCatFileWithStrings() {
        List<String> result = catFile("some strings.txt");
        List<String> expected = Stream.of("Some strings", "here", "!").collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void testCatFileWithEmptyStrings() {
        List<String> result = catFile("with empty strings.txt");
        List<String> expected = Stream.of("Some text", "Empty string:", "",
                "Some other empty strings:", "", "", "        ", "  ").collect(Collectors.toList());
        assertEquals(expected, result);
        assertEquals(8, result.size());
    }

    @Test
    public void testCatFromPipe() {
        List<String> arguments = new ArrayList<>();
        arguments.add("Hello, world!");
        Command echoCommand = new EchoCommand();
        echoCommand.addArguments(arguments);
        IOData fromEcho = echoCommand.execute();
        Command catCommand = new CatCommand();
        catCommand.setIOData(fromEcho);
        IOData outputData = catCommand.execute();
        List<String> result = outputData.getData();
        assertEquals(arguments, result);
    }
}
