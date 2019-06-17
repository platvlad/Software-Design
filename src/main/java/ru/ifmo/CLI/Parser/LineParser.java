package ru.ifmo.CLI.Parser;

import ru.ifmo.CLI.Commands.Command;
import ru.ifmo.CLI.Utils.CommandMaker;
import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for getting commands from string
 */
public class LineParser {

    public LineParser() {}

    /**
     * Parse and execute commands from string
     * @param line String to parse
     */
    public void parse(String line) {
        IOData result = new IOData();
        CommandParser commandParser = new CommandParser(line);
         while (!commandParser.finishedLineHandling()) {
            List<String> words = commandParser.getWords();
            if (words.isEmpty()) {
                List<String> message = new ArrayList<>();
                message.add("Got empty command");
                result = new IOData(message, true);
                break;
            }
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
            if (result.errorOccured()) {
                break;
            }
        }
        result.printData();
    }

}
