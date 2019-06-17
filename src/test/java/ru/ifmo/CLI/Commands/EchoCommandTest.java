package ru.ifmo.CLI.Commands;

import org.junit.Test;
import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class EchoCommandTest {
    @Test
    public void testEchoString() {
        List<String> arguments = new ArrayList<>();
        arguments.add("Hello, world!");
        EchoCommand command = new EchoCommand();
        command.addArguments(arguments);
        IOData output = command.execute();
        String result = output.getData().get(0);
        assertEquals("Hello, world!", result);
    }

    @Test
    public void testEchoMultipleStrings() {
        List<String> arguments = Stream.of("Hello, world!", "Another greeting").collect(Collectors.toList());
        EchoCommand command = new EchoCommand();
        command.addArguments(arguments);
        IOData output = command.execute();
        List<String> outputData = output.getData();
        String result = outputData.get(0);
        assertEquals(1, outputData.size());
        assertEquals("Hello, world! Another greeting", result);
    }
}
