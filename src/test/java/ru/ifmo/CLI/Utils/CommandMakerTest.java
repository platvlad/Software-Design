package ru.ifmo.CLI.Utils;

import org.junit.Test;
import ru.ifmo.CLI.Command.CatCommand;
import ru.ifmo.CLI.Command.Command;
import ru.ifmo.CLI.Command.ExternalCommand;
import ru.ifmo.CLI.Command.WcCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CommandMakerTest {

    @Test
    public void testCommandWithArguments() {
        List<String> arguments = Stream.of("1.txt", "2.txt").collect(Collectors.toList());
        Command commandFromMaker =  CommandMaker.makeCommand("cat", arguments);
        CatCommand catCommand = new CatCommand();
        catCommand.addArguments(arguments);
        assertEquals(catCommand.execute().getData(), commandFromMaker.execute().getData());
    }

    @Test
    public void testCommandFromPipe() {
        CatCommand catCommand = new CatCommand();
        List<String> arguments = new ArrayList<>();
        arguments.add("src/test/resources/some strings.txt");
        catCommand.addArguments(arguments);
        IOData result = catCommand.execute();
        Command commandFromMaker = CommandMaker.makeCommand("wc", result);
        WcCommand wcCommand = new WcCommand();
        wcCommand.addArguments(arguments);
        assertEquals(wcCommand.execute().getData(), commandFromMaker.execute().getData());
    }

    @Test
    public  void testCommandWithNameAsArgument() {
        List<String> arguments = new ArrayList<>();
        arguments.add("src\\test\\resources\\Summator\\Debug\\Summator");
        arguments.add(String.valueOf(5));
        arguments.add(String.valueOf(7));
        Command command = new ExternalCommand();
        command.addArguments(arguments);
        List<String> commandMakerArguments =
                Stream.of(String.valueOf(5), String.valueOf(7)).collect(Collectors.toList());
        Command commandFromMaker = CommandMaker.makeCommand("src\\test\\resources\\Summator\\Debug\\Summator",
                commandMakerArguments);
        assertEquals(command.execute().getData(), commandFromMaker.execute().getData());
    }
}
