package ru.ifmo.CLI.Parser;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CommandParserTest {

    @Test
    public void testCommandWithMultipleArgs() {
        String directoryName = "src\\test\\resources\\";
        String input = "cat " + directoryName + "empty.txt \"" + directoryName + "some strings.txt\" \"" +
                directoryName + "summator input.txt\"";
        var parser = new CommandParser(input);
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
        var parser = new CommandParser(input);
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
        var lineParser = new LineParser();
        lineParser.parse("a=pw");
        lineParser.parse("b=d");
        var parser = new CommandParser("\"$a$b\"");
        List<String> words = parser.getWords();
        List<String> expected = new ArrayList<>();
        expected.add("pwd");
        assertEquals(expected, words);
    }

    @Test
    public void testLineWithWeakQuotes() {
        var lineParser = new LineParser();
        lineParser.parse("a=5");
        var parser = new CommandParser("echo \'$a\' | wc");
        List<String> words = parser.getWords();
        List<String> expected = Stream.of("echo", "$a").collect(Collectors.toList());
        assertEquals(expected, words);
    }

    @Test
    public void testMultipleAssignments() {
        var lineParser = new LineParser();
        lineParser.parse("a=5");
        lineParser.parse("b=$a");
        lineParser.parse("c=$b");
        var parser = new CommandParser("cat $c.txt");
        List<String> words = parser.getWords();
        List<String> expected = Stream.of("cat", "5.txt").collect(Collectors.toList());
        assertEquals(expected, words);
    }
}
