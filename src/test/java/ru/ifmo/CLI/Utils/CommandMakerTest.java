package ru.ifmo.CLI.Utils;

import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.Commands.CatCommand;
import ru.ifmo.CLI.Commands.Command;
import ru.ifmo.CLI.Commands.ExternalCommand;
import ru.ifmo.CLI.Commands.WcCommand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandMakerTest {

    @Test
    public void testCommandWithArguments() {
        List<String> arguments = Stream.of("1.txt", "2.txt").collect(Collectors.toList());
        Command commandFromMaker =  CommandMaker.makeCommand("cat", arguments);
        var catCommand = new CatCommand();
        catCommand.addArguments(arguments);
        assertEquals(catCommand.execute().getData(), commandFromMaker.execute().getData());
    }

    @Test
    public void testCommandFromPipe() {
        var catCommand = new CatCommand();
        List<String> arguments = new ArrayList<>();
        arguments.add("src/test/resources/some strings.txt");
        catCommand.addArguments(arguments);
        IOData result = catCommand.execute();
        Command commandFromMaker = CommandMaker.makeCommand("wc", new ArrayList<>(), result);
        var wcCommand = new WcCommand();
        wcCommand.addArguments(arguments);
        assertEquals(wcCommand.execute().getData(), commandFromMaker.execute().getData());
    }

    @Test
    public  void testCommandWithNameAsArgument() {
        List<String> arguments = new ArrayList<>();
        String argument = "src" + File.separator +
                "test" + File.separator +
                "resources" + File.separator +
                "Summator" + File.separator +
                "Debug" + File.separator +
                "Summator";
        arguments.add(argument);
        arguments.add(String.valueOf(5));
        arguments.add(String.valueOf(7));
        Command command = new ExternalCommand();
        command.addArguments(arguments);
        List<String> commandMakerArguments =
                Stream.of(String.valueOf(5), String.valueOf(7)).collect(Collectors.toList());
        Command commandFromMaker = CommandMaker.makeCommand(argument,
                commandMakerArguments);
        assertEquals(command.execute().getData(), commandFromMaker.execute().getData());
    }
}
