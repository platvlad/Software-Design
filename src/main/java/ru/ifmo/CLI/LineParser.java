package ru.ifmo.CLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

//class for getting commands from string
public class LineParser {
    LineParser() {}

    //parse and execute commands from string
    public void parse(String line) {
        this.line = line;
        IOData result = null;
        while (counter < line.length()) {
            String[] words = parseCommand();
            String commandName = words[0];
            String[] arguments = Arrays.copyOfRange(words, 1, words.length);
            Command command;
            //create command by its name and arguments
            if (result == null) {
                command = CommandMaker.makeCommand(commandName, arguments);
            }
            else {
                command = CommandMaker.makeCommand(commandName, result);
            }
            result = command.execute();
        }
        result.printData();
    }

    //get array of words from string
    // until getting pipe symbol (|)
    private String[] parseCommand()
    {
        boolean inFullQuoting = false;
        boolean inWeakQuoting = false;
        boolean inVariableHandling = false;
        StringBuilder varName = new StringBuilder();
        List<String> words = new ArrayList<String>();
        StringBuilder currentWord = new StringBuilder();
        int lineLength = line.length();
        while (counter < lineLength) {
            char currentChar = line.charAt(counter);
            if (inWeakQuoting) {
                if (currentChar == '\'') {
                    inWeakQuoting = false;
                    if (!inFullQuoting) {
                        words.add(currentWord.toString());
                    }
                }
                else {
                    currentWord.append(currentChar);
                }
                ++counter;
                continue;
            }
            if (inFullQuoting) {
                if (currentChar == '\'' || currentChar == '\"' || currentChar == '$' || currentChar == ' ') {
                    if (inVariableHandling) {
                        String varValue = InterpreterEnvironment.getValue(varName.toString());
                        currentWord.append(varValue);
                        varName.setLength(0);
                        inVariableHandling = false;
                    }
                }
                if (currentChar == '\'') {
                    inWeakQuoting = true;
                }
                else if (currentChar == '\"') {
                    inFullQuoting = false;
                }
                else if (currentChar == '$') {
                    inVariableHandling = true;
                }
                else if (inVariableHandling) {
                    varName.append(currentChar);
                }
                else {
                    currentWord.append(currentChar);
                }
                ++counter;
                continue;
            }

            if (inVariableHandling) {
                if (currentChar == '\'' || currentChar == '\"' || currentChar == '$' || currentChar == ' ' || currentChar == '|') {
                    if (inVariableHandling) {
                        String varValue = InterpreterEnvironment.getValue(varName.toString());
                        currentWord.append(varValue);
                        varName.setLength(0);
                        inVariableHandling = false;
                    }
                }
            }
            if (currentChar == '\'') {
                inWeakQuoting = true;
            }
            else if (currentChar == '\"') {
                inFullQuoting = true;
            }
            else if ((currentChar == ' ' || currentChar == '|') && !inVariableHandling) {
                if (currentWord.length() != 0) {
                    words.add(currentWord.toString());
                    currentWord.setLength(0);
                }
            }
            else if (currentChar == '$') {
                if (currentWord.length() != 0) {
                    words.add(currentWord.toString());
                    currentWord.setLength(0);
                }
                inVariableHandling = true;
            }
            else {
                if (inVariableHandling) {
                    varName.append(currentChar);
                }
                else {
                    currentWord.append(currentChar);
                }
            }
            ++counter;
            if (currentChar == '|') {
                break;
            }
        }
        if (varName.length() != 0) {
            String varValue = InterpreterEnvironment.getValue(varName.toString());
            currentWord.append(varValue);
            varName.setLength(0);
        }
        if (currentWord.length() != 0) {
            words.add(currentWord.toString());
        }
        return words.toArray(new String[0]);
    }



    private int counter = 0;
    private String line;

}
