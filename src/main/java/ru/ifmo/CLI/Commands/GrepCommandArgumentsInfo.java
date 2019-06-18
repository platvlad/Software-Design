package ru.ifmo.CLI.Commands;

import org.apache.commons.cli.*;
import ru.ifmo.CLI.Utils.IOData;

import java.util.Collections;
import java.util.List;

class GrepCommandArgumentsInfo {
    private String[] arguments;
    private boolean caseSensitivity = true;
    private boolean wholeWord = false;
    private int numberOfStrings = 0;

    private String patternString;
    private List<String> fileNames;

    private static Options options;
    private static final CommandLineParser parser = new DefaultParser();
    private static final HelpFormatter helpFormatter = new HelpFormatter();

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

    GrepCommandArgumentsInfo(String[] arguments) {
        this.arguments = arguments;
    }

    private boolean isCorrectNumber(String str) {
        if (str == null) {
            return false;
        }
        if (str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    IOData parseArguments() {
        CommandLine cmd;
        String answer;
        try {
            cmd = parser.parse(options, arguments);
        } catch (ParseException ex) {
            helpFormatter.printHelp("grep", options);
            answer = "Unable to parse grep arguments";
            List<String> output = Collections.singletonList(answer);
            return new IOData(output);
        }
        caseSensitivity = !(cmd.hasOption("i"));
        wholeWord = cmd.hasOption("w");
        if (cmd.hasOption("A")) {
            String aOption = cmd.getOptionValue("A");
            if (isCorrectNumber(aOption)) {
                numberOfStrings = Integer.parseInt(cmd.getOptionValue("A"));
            } else {
                answer = numberOfStrings + " is not a number";
                List<String> output = Collections.singletonList(answer);
                return new IOData(output);
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