package ru.ifmo.CLI.Parser;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class CommandParserTest {

    @Test
    public void testCommandWithMultipleArgs() {
        String directoryName = "src\\test\\resources\\";
        String input = "cat " + directoryName + "empty.txt \"" + directoryName + "some strings.txt\" \"" +
                directoryName + "summator input.txt\"";
        CommandParser parser = new CommandParser(input);
        List<String> words = parser.getWords();
        List<String> expected = Stream.of("cat",
                directoryName + "empty.txt",
                directoryName + "some strings.txt",
                directoryName + "summator input.txt").collect(Collectors.toList());
        assertEquals(expected, words);
    }

    @Test
    public void testLineWithPipe() {
        String input = "cat src/test/resources/empty.txt | wc";
        CommandParser parser = new CommandParser(input);
        List<String> words = parser.getWords();
        List<String> expected = Stream.of("cat", "src/test/resources/empty.txt").collect(Collectors.toList());
        assertEquals(expected, words);
        List<String> wordsAfterPipe = parser.getWords();
        List<String> expectedAfterPipe = new ArrayList<>();
        expectedAfterPipe.add("wc");
        assertEquals(expectedAfterPipe, wordsAfterPipe);
        List<String> wordsAtEnd = parser.getWords();
        List<String> expectedAtEnd = new ArrayList<>();
        assertEquals(expectedAtEnd, wordsAtEnd);
    }

    @Test
    public void testLineWithFullQuotes() {
        LineParser lineParser = new LineParser();
        lineParser.parse("a=pw");
        lineParser.parse("b=d");
        CommandParser parser = new CommandParser("\"$a$b\"");
        List<String> words = parser.getWords();
        List<String> expected = new ArrayList<>();
        expected.add("pwd");
        assertEquals(expected, words);
    }

    @Test
    public void testLineWithWeakQuotes() {
        LineParser lineParser = new LineParser();
        lineParser.parse("a=5");
        CommandParser parser = new CommandParser("echo \'$a\' | wc");
        List<String> words = parser.getWords();
        List<String> expected = Stream.of("echo", "$a").collect(Collectors.toList());
        assertEquals(expected, words);
    }

    @Test
    public void testMultipleAssignments() {
        LineParser lineParser = new LineParser();
        lineParser.parse("a=5");
        lineParser.parse("b=$a");
        lineParser.parse("c=$b");
        CommandParser parser = new CommandParser("cat $c.txt");
        List<String> words = parser.getWords();
        List<String> expected = Stream.of("cat", "5.txt").collect(Collectors.toList());
        assertEquals(expected, words);
    }
}
