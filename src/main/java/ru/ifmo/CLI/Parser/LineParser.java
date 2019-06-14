package ru.ifmo.CLI.Parser;

import ru.ifmo.CLI.Command.Command;
import ru.ifmo.CLI.Utils.CommandMaker;
import ru.ifmo.CLI.Utils.IOData;

import java.util.List;

//class for getting commands from string
public class LineParser {

    public LineParser() {}

    //parse and execute commands from string
    public void parse(String line) {
        IOData result = new IOData();
        CommandParser commandParser = new CommandParser(line);
         while (!commandParser.finishedLineHandling()) {
            List<String> words = commandParser.getWords();
             String commandName = words.get(0);

            List<String> arguments = words.subList(1, words.size());
            Command command;
            //create command by its name and arguments
            if (result.isEmpty()) {
                command = CommandMaker.makeCommand(commandName, arguments);
            }
            else {
                command = CommandMaker.makeCommand(commandName, result);
            }
            result = command.execute();
        }
        result.printData();
    }

}
