package ru.ifmo.CLI.Command;

import org.junit.Test;
import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExternalCommandTest {

    @Test
    public void testCallExternalWithArguments() {
        List<String> arguments = new ArrayList<>();
        arguments.add("src\\test\\resources\\Summator\\Debug\\Summator");
        arguments.add(String.valueOf(5));
        arguments.add(String.valueOf(7));
        Command command = new ExternalCommand();
        command.addArguments(arguments);
        IOData output = command.execute();
        List<String> outputData = output.getData();
        assertEquals(String.valueOf(12), outputData.get(0));
    }

    @Test
    public void testCallExternalFromPipe() {
        List<String> catArguments = new ArrayList<>();
        catArguments.add("src\\test\\resources\\summator input.txt");
        Command catCommand = new CatCommand();
        catCommand.addArguments(catArguments);
        IOData catResult = catCommand.execute();
        Command externalCommand = new ExternalCommand();
        List<String> externalArguments = new ArrayList<>();
        externalArguments.add("src\\test\\resources\\Summator\\Debug\\Summator");
        externalCommand.addArguments(externalArguments);
        externalCommand.setIOData(catResult);
        IOData output = externalCommand.execute();
        List<String> outputData = output.getData();
        assertEquals(String.valueOf(8), outputData.get(0));
    }
}
