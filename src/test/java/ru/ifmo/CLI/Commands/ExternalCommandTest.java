package ru.ifmo.CLI.Commands;

import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalCommandTest {

    @Test
    public void testCallExternalWithArguments() {
        List<String> arguments = Stream.of("src" + File.separator +
                        "test" + File.separator +
                        "resources" + File.separator +
                        "Summator" + File.separator +
                        "Debug" + File.separator +
                        "Summator",
                String.valueOf(5), String.valueOf(7)).collect(Collectors.toList());
        Command command = new ExternalCommand();
        command.addArguments(arguments);
        IOData output = command.execute();
        List<String> outputData = output.getData();
        assertEquals(String.valueOf(12), outputData.get(0));
    }

    @Test
    public void testCallExternalFromPipe() {
        List<String> catArguments = new ArrayList<>();
        catArguments.add("src" + File.separator +
                        "test" + File.separator +
                        "resources" + File.separator +
                        "summator input.txt");
        Command catCommand = new CatCommand();
        catCommand.addArguments(catArguments);
        IOData catResult = catCommand.execute();
        Command externalCommand = new ExternalCommand();
        List<String> externalArguments = new ArrayList<>();
        externalArguments.add("src" + File.separator +
                        "test" + File.separator +
                        "resources" + File.separator +
                        "Summator" + File.separator +
                        "Debug" + File.separator +
                        "Summator");
        externalCommand.addArguments(externalArguments);
        externalCommand.setIOData(catResult);
        IOData output = externalCommand.execute();
        List<String> outputData = output.getData();
        assertEquals(String.valueOf(8), outputData.get(0));
    }
}
