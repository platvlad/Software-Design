package ru.ifmo.CLI.Parser;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommandParserTest {

    @Test
    public void testCommandWithMultipleArgs() {
        String directoryName = "src\\test\\resources\\";
        String input = "cat " + directoryName + "empty.txt \"" + directoryName + "some strings.txt\" \"" +
                directoryName + "summator input.txt\"";
        CommandParser parser = new CommandParser(input);
        List<String> words = parser.getWords();
        assertEquals(4, words.size());
        assertEquals("cat", words.get(0));
        assertEquals(directoryName + "empty.txt", words.get(1));
        assertEquals(directoryName + "some strings.txt", words.get(2));
        assertEquals(directoryName + "summator input.txt", words.get(3));
    }
}
