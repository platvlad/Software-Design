package ru.ifmo.CLI.Commands;

import org.apache.commons.cli.*;
import ru.ifmo.CLI.Utils.IOData;

import java.util.Collections;
import java.util.List;

/**
 * Class parsing and representing data about grep command arguments
 */
class GrepArgumentsInfo {
    private String[] arguments;
    private boolean caseSensitivity = true;
    private boolean wholeWord = false;
    private int numberOfStrings = 0;

    private String patternString;
    private List<String> fileNames;

    private static Options options;
    private static final CommandLineParser parser = new DefaultParser();
    private static final HelpFormatter helpFormatter = new HelpFormatter();

    // Initialize Options
    static {
        options = new Options();
        Option caseOption = new Option("i", false, "case insensitivity");
        caseOption.setRequired(false);
        options.addOption(caseOption);

        Option wholeWordOption = new Option("w", false, "find whole word");
        wholeWordOption.setRequired(false);
        options.addOption(wholeWordOption);

        Option printOption = new Option("A", true, "print n strings after matched string");
        printOption.setRequired(false);
        options.addOption(printOption);
    }

    /**
     * Initialize object with arguments
     * @param arguments Command arguments
     */
    GrepArgumentsInfo(String[] arguments) {
        this.arguments = arguments;
    }

    /**
     * Parse command arguments
     * @return Error message in case of errors. Empty IOData otherwise
     */
    IOData parseArguments() {
        CommandLine cmd;
        String answer;
        try {
            cmd = parser.parse(options, arguments);
        } catch (ParseException ex) {
            helpFormatter.printHelp("grep", options);
            answer = "Unable to parse grep arguments";
            return new IOData(answer, true);
        }
        caseSensitivity = !(cmd.hasOption("i"));
        wholeWord = cmd.hasOption("w");
        if (cmd.hasOption("A")) {
            String aOption = cmd.getOptionValue("A");
            try {
                numberOfStrings = Integer.parseInt(aOption);
            } catch (NumberFormatException ex) {
                answer = aOption + " is not a number";
                return new IOData(answer, true);
            }
        }
        List<String> remainingArguments = cmd.getArgList();
        patternString = remainingArguments.remove(0);
        fileNames = remainingArguments;
        return new IOData();
    }

    boolean isCaseSensitivity() {
        return caseSensitivity;
    }

    boolean isWholeWord() {
        return wholeWord;
    }

    int getNumberOfStrings() {
        return numberOfStrings;
    }

    String getPatternString() {
        return patternString;
    }

    List<String> getFileNames() {
        return fileNames;
    }
}