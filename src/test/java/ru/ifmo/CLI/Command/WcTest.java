package ru.ifmo.CLI.Command;

import org.junit.Test;
import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WcTest {

    private String wcFile(String fileName, boolean catBefore) {
        List<String> arguments = new ArrayList<>();
        arguments.add("src\\test\\resources\\" + fileName);
        Command wcCommand = new WcCommand();
        if (catBefore) {
            Command catCommand = new CatCommand();
            catCommand.addArguments(arguments);
            IOData catResult = catCommand.execute();
            wcCommand.setIOData(catResult);
        } else {
            wcCommand.addArguments(arguments);
        }
        IOData result = wcCommand.execute();
        List<String> resultData = result.getData();
        return resultData.get(0);
    }

    @Test
    public void testWcEmptyFile() {
        String wcResult = wcFile("empty.txt", false);
        assertEquals("0 0 0", wcResult);
    }

    @Test
    public void testFileWithSomeStrings() {
        String wcResult = wcFile("some strings.txt", false);
        assertEquals("3 4 21", wcResult);
    }

    @Test
    public void testWithEmptyStrings() {
        String wcResult = wcFile("with empty strings.txt", false);
        assertEquals("8 8 71", wcResult);
    }

    @Test
    public void testWcEmptyFileFromPipe() {
        String wcResult = wcFile("empty.txt", true);
        assertEquals("0 0 0", wcResult);
    }

    @Test
    public void testWcSummatorInputFromPipe() {
        String wcResult = wcFile("summator input.txt", true);
        assertEquals("2 2 4", wcResult);
    }

    @Test
    public void testWcFileWithEmptyStringsFromPipe() {
        String wcResult = wcFile("with empty strings.txt", true);
        assertEquals("8 8 71", wcResult);
    }

    @Test
    public void testWcFromEcho() {
        List<String> arguments = new ArrayList<>();
        arguments.add("123");
        Command echoCommand = new EchoCommand();
        echoCommand.addArguments(arguments);
        IOData echoResult = echoCommand.execute();
        Command wcCommand = new WcCommand();
        wcCommand.setIOData(echoResult);
        IOData wcResult = wcCommand.execute();
        List<String> wcData = wcResult.getData();
        assertEquals("1 1 3", wcData.get(0));
    }

}
