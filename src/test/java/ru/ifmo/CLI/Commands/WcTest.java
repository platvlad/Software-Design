package ru.ifmo.CLI.Commands;

import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WcTest {

    private String getPath(String fileName) {
        return "src" + File.separator + "test" + File.separator + "resources" + File.separator + fileName;
    }

    private long getFileSize(String fileName) {
        fileName = getPath(fileName);
        Path filePath = Paths.get(fileName);
        long fileSize = 0;
        try {
            fileSize = Files.size(filePath);
        } catch (IOException ex) {
            fail();
        }
        return fileSize;
    }

    private String wcFile(String fileName, boolean catBefore) {
        List<String> arguments = new ArrayList<>();
        String path = getPath(fileName);
        arguments.add(path);
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
        long fileSize = getFileSize("some strings.txt");
        assertEquals("3 4 " + fileSize, wcResult);
    }

    @Test
    public void testWithEmptyStrings() {
        String wcResult = wcFile("with empty strings.txt", false);
        long fileSize = getFileSize("with empty strings.txt");
        assertEquals("8 8 " + fileSize, wcResult);
    }

    @Test
    public void testWcEmptyFileFromPipe() {
        String wcResult = wcFile("empty.txt", true);
        assertEquals("0 0 0", wcResult);
    }

    @Test
    public void testWcSummatorInputFromPipe() {
        String wcResult = wcFile("summator input.txt", true);
        long fileSize = getFileSize("summator input.txt");
        assertEquals("2 2 " + fileSize, wcResult);
    }

    @Test
    public void testWcFileWithEmptyStringsFromPipe() {
        String wcResult = wcFile("with empty strings.txt", true);
        long fileSize = getFileSize("with empty strings.txt");
        assertEquals("8 8 " + fileSize, wcResult);
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
