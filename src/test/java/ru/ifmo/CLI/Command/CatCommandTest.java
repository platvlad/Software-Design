package ru.ifmo.CLI.Command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;

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
        assertEquals(3, result.size());
        assertEquals("Some strings", result.get(0));
        assertEquals("here", result.get(1));
        assertEquals("!", result.get(2));
    }

    @Test
    public void testCatFileWithEmptyStrings() {
        List<String> result = catFile("with empty strings.txt");
        assertEquals(8, result.size());
        assertEquals("Some text", result.get(0));
        assertEquals("Empty string:", result.get(1));
        assertEquals("Some other empty strings:", result.get(3));
        assertEquals("        ", result.get(6));
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
        assertEquals(1, result.size());
        assertEquals("Hello, world!", result.get(0));
    }
}
