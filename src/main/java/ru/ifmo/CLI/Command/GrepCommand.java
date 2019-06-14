package ru.ifmo.CLI.Command;

import org.apache.commons.cli.*;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepCommand extends Command {
    private boolean caseSensitivity = true;
    private boolean wholeWord = false;
    private boolean printStrings = false;
    private int numberOfStrings = 0;

    public GrepCommand() {
        super();
    }

    public IOData execute() {
        Options options = new Options();
        Option caseOption = new Option("i", false, "case insensitivity");
        caseOption.setRequired(false);
        options.addOption(caseOption);

        Option wholeWordOption = new Option("w", false, "find whole word");
        wholeWordOption.setRequired(false);
        options.addOption(wholeWordOption);

        Option printOption = new Option("A", true, "print n strings after matched string");
        printOption.setRequired(false);
        options.addOption(printOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine cmd;
        String answer;
        try {
            String[] argumentsArray = arguments.toArray(new String[0]);
            cmd = parser.parse(options, argumentsArray);
        } catch (ParseException ex) {
            helpFormatter.printHelp("grep", options);
            answer = "Unable to parse grep arguments";
            List<String> output = new ArrayList<>();
            output.add(answer);
            return new IOData(output);
        }
        caseSensitivity = !(cmd.hasOption("i"));
        wholeWord = cmd.hasOption("w");
        printStrings = cmd.hasOption("A");
        if (cmd.hasOption("A")) {
            numberOfStrings = Integer.parseInt(cmd.getOptionValue("A"));
        }
        List<String> remainingArguments = cmd.getArgList();
        String patternString = remainingArguments.get(0);
        List<String> files = remainingArguments.subList(1, remainingArguments.size());

        List<String> output = new ArrayList<String>();
        for (String fileName : files) {
            File file = new File(fileName);
            Scanner scanner;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException ex) {
                output.add("File " + fileName + " not found");
                continue;
            }
            int remainingStrings = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Pattern pattern;
                if (caseSensitivity) {
                    pattern = Pattern.compile(patternString);
                } else {
                    pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                }
                boolean foundPattern = false;
                if (wholeWord) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        Matcher matcher = pattern.matcher(word);
                        if (matcher.matches()) {
                            output.add(line);
                            remainingStrings = numberOfStrings;
                            foundPattern = true;
                            break;
                        }
                    }

                } else {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        output.add(line);
                        remainingStrings = numberOfStrings;
                        foundPattern = true;
                    }
                }
                if (!foundPattern && remainingStrings > 0) {
                    output.add(line);
                    remainingStrings--;
                }

            }
        }

        return new IOData(output);
    }

}
