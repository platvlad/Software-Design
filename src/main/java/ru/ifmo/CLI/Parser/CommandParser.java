package ru.ifmo.CLI.Parser;

import ru.ifmo.CLI.InterpreterEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for getting arrays of words from line as parts of pipe
 */
class CommandParser {

    private String line;
    private int currentPosition;
    private boolean inFullQuoting;
    private List<String> words;
    private StringBuilder currentWord = new StringBuilder();
    private StringBuilder currentVariable = new StringBuilder();

    /**
     * Initialize parser with string
     * @param line String to parse
     */
    CommandParser(String line) {
        this.line = line;
    }

    /**
     * Get next symbol from line
     * @return Next symbol from line
     */
    private char getSymbol() {
        char symbol = line.charAt(currentPosition);
        currentPosition++;
        return symbol;
    }

    /**
     * Parse part of the line inside weak quotes
     */
    private void parseWeakQuoting() {
        while (currentPosition < line.length()) {
            char symbol = getSymbol();
            if (symbol == '\'') {
                if (!inFullQuoting) {
                    words.add(currentWord.toString());
                    currentWord.setLength(0);
                }
                return;
            }
            currentWord.append(symbol);
        }
    }

    /**
     * Parse variable name and substitute its value
     * @param ignorePipe True if we are inside variable value handling and shall ignore '|' symbol
     * @return True if '|' handled and command ended
     */
    private boolean handleVariable(boolean ignorePipe) {
        while (currentPosition < line.length()) {
            char symbol = getSymbol();
             if (!Character.isLetterOrDigit(symbol)) {
                String variableValue = InterpreterEnvironment.getValue(currentVariable.toString());
                var variableValueParser = new CommandParser(variableValue);
                List<String> variableValueWords = variableValueParser.getWords(true);
                if (variableValueWords.size() > 0) {
                    currentWord.append(variableValueWords.get(0));
                }
                if (variableValueWords.size() > 1) {
                    words.add(currentWord.toString());
                    currentWord.setLength(0);
                    words.addAll(variableValueWords.subList(1, variableValueWords.size()));
                }
                currentVariable.setLength(0);
            }
            switch (symbol) {
                case '\'':
                    parseWeakQuoting();
                    return false;
                case '\"':
                    if (inFullQuoting) {
                        inFullQuoting = false;
                        return false;
                    }
                    inFullQuoting = true;
                    parseFullQuoting(ignorePipe);
                    return false;
                case ' ':
                    if (inFullQuoting) {
                        currentWord.append(symbol);
                    } else {
                        if (currentWord.length() > 0) {
                            words.add(currentWord.toString());
                            currentWord.setLength(0);
                        }
                    }
                    return false;
                case '$':
                    handleVariable(ignorePipe);
                    return false;
                case '|':
                    if (inFullQuoting || ignorePipe) {
                        currentWord.append(symbol);
                        return false;
                    }
                    return true;
                default:
                    if (!Character.isLetterOrDigit(symbol)) {
                        currentWord.append(symbol);
                        return false;
                    }
                    currentVariable.append(symbol);
                    break;
            }
        }
        if (currentVariable.length() > 0) {
            String variableValue = InterpreterEnvironment.getValue(currentVariable.toString());
            currentWord.append(variableValue);
            currentVariable.setLength(0);
        }
        return true;
    }

    /**
     * Parse part of the line inside full quotes
     * @param ignorePipe True if we are inside of variable value handling and shall ignore '|' symbol
     * @return True if '|' symbol handled and command ended
     */
    private boolean parseFullQuoting(boolean ignorePipe) {
        while (currentPosition < line.length()) {
            char symbol = getSymbol();
            switch (symbol) {
                case '\'':
                    parseWeakQuoting();
                case '\"':
                    inFullQuoting = false;
                    return false;
                case '$':
                    boolean finished = handleVariable(ignorePipe);
                    if (!inFullQuoting) {
                        return finished;
                    }
                    break;
                default:
                    currentWord.append(symbol);
                    break;
            }
        }
        return true;
    }

    /**
     * True if whole line is already parsed
     * @return True if whole line is already parsed
     */
    boolean finishedLineHandling() {
        return (currentPosition >= line.length());
    }

    /**
     * Get list of words representing one command with its arguments
     * @param ignorePipe True when parsing variable value
     * @return List of words representing the command
     */
    private List<String> getWords(boolean ignorePipe) {
        words = new ArrayList<>();
        while (currentPosition < line.length()) {
            char symbol = getSymbol();
            boolean finished = false;
            switch (symbol) {
                case '\'':
                    parseWeakQuoting();
                    break;
                case '\"':
                    if (parseFullQuoting(ignorePipe)) {
                        finished = true;
                    }
                    break;
                case ' ':
                    if (currentWord.length() > 0) {
                        words.add(currentWord.toString());
                        currentWord.setLength(0);
                    }
                    break;
                case '|':
                    if (ignorePipe) {
                        currentWord.append(symbol);
                    } else {
                        finished = true;
                    }
                    break;
                case '$':
                    finished = handleVariable(ignorePipe);
                    break;
                default:
                    currentWord.append(symbol);
                    break;
            }
            if (finished) {
                break;
            }
        }
        if (currentWord.length() > 0) {
            words.add(currentWord.toString());
            currentWord.setLength(0);
        }
        return words;
    }

    /**
     * Get list of words representing one command with its arguments
     * until getting pipe symbol (|)
     * @return List of words representing the command
     */
    List<String> getWords() {
        return getWords(false);
    }
}
