package ru.ifmo.CLI.Commands;


import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrepCommandTest {

    private String fileName = "src" + File.separator + "test" + File.separator +
            "resources" + File.separator + "GrepTest.txt";

    private IOData executeCat() {
        Command cat = new CatCommand();
        List<String> catArguments = Collections.singletonList(fileName);
        cat.addArguments(catArguments);
        return cat.execute();
    }

    @Test
    public void testFindWordInFile() {
        Command grep = new GrepCommand();
        List<String> arguments = Stream.of("test", fileName).collect(Collectors.toList());
        grep.addArguments(arguments);
        List<String> expected = Stream.of("This is a string with word test",
                                          "Here is something about contest")
                                .collect(Collectors.toList());
        List<String> result = grep.execute().getData();
        assertEquals(expected, result);
    }

    @Test
    public void testFindWordFromPipe() {
        IOData pipe =  executeCat();
        Command grep = new GrepCommand();
        List<String> grepArguments = Collections.singletonList("This");
        grep.addArguments(grepArguments);
        grep.setIOData(pipe);
        List<String> expected = Stream.of("This is the first string",
                                          "This is a string with word test",
                                           "This is the last string.").collect(Collectors.toList());
        List<String> result = grep.execute().getData();
        assertEquals(expected, result);
    }

    @Test
    public void testWholeWord() {
        Command grep = new GrepCommand();
        List<String> arguments = Stream.of("test", fileName, "-w").collect(Collectors.toList());
        grep.addArguments(arguments);
        List<String> expected = Collections.singletonList("This is a string with word test");
        List<String> result = grep.execute().getData();
        assertEquals(expected, result);
    }

    @Test
    public void testIAKeysFromPipe() {
        IOData pipe = executeCat();
        Command grep = new GrepCommand();
        List<String> grepArguments = Stream.of("test", "-i", "-A", "1").collect(Collectors.toList());
        grep.addArguments(grepArguments);
        grep.setIOData(pipe);
        List<String> expected = Stream.of("This is a string with word test",
                "Nothing",
                "Here is something about contest",
                "TeSt! TesT TEsT!!",
                ".....").collect(Collectors.toList());
        List<String> result = grep.execute().getData();
        assertEquals(expected, result);
    }

    @Test
    public void testIAWKeys() {
        Command grep = new GrepCommand();
        List<String> arguments = Stream.of("test", fileName, "-w", "-i", "-A", "2").collect(Collectors.toList());
        grep.addArguments(arguments);
        List<String> expected = Stream.of("This is a string with word test",
                "Nothing",
                "Something",
                "TeSt! TesT TEsT!!",
                ".....",
                "This is the last string.").collect(Collectors.toList());
        List<String> result = grep.execute().getData();
        assertEquals(expected, result);
    }

}
