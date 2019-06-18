package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepCommand extends Command {

    public GrepCommand() {
        super();
    }

    private List<String> findPatternInFileContent(List<String> fileContent,
                                                  String patternString,
                                                  GrepCommandArgumentsInfo argInfo) {
        List<String> output = new ArrayList<>();
        Pattern pattern;
        if (argInfo.isCaseSensitivity()) {
            pattern = Pattern.compile(patternString);
        } else {
            pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        }
        int remainingStrings = 0;
        for (String line : fileContent) {
            boolean foundPattern = false;
            if (argInfo.isWholeWord()) {
                String[] words = line.split(" ");
                for (String word : words) {
                    Matcher matcher = pattern.matcher(word);
                    if (matcher.matches()) {
                        output.add(line);
                        remainingStrings = argInfo.getNumberOfStrings();
                        foundPattern = true;
                        break;
                    }
                }
            } else {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    output.add(line);
                    remainingStrings = argInfo.getNumberOfStrings();
                    foundPattern = true;
                }
            }
            if (!foundPattern && remainingStrings > 0) {
                output.add(line);
                remainingStrings--;
            }
        }
        return output;
    }

    public IOData execute() {
        String[] argumentsArray = arguments.toArray(new String[0]);
        var argInfo = new GrepCommandArgumentsInfo(argumentsArray);
        IOData parseArgumentMessage = argInfo.parseArguments();
        if (!parseArgumentMessage.isEmpty()) {
            return parseArgumentMessage;
        }

        List<String> output = new ArrayList<>();
        List<String> files = argInfo.getFileNames();
        String patternString = argInfo.getPatternString();
        for (String fileName : files) {
            Path filePath = Paths.get(fileName);
            List<String> fileStrings;

            try {
                fileStrings = Files.readAllLines(filePath);
            } catch (FileNotFoundException ex) {
                output.add("File " + fileName + " not found");
                continue;
            } catch (IOException ex) {
                output.add("Failed to read file " + fileName);
                continue;
            }
            output.addAll(findPatternInFileContent(fileStrings, patternString, argInfo));
        }
        if (fromPipe()) {
            output.addAll(findPatternInFileContent(data.getData(), patternString, argInfo));
        }

        return new IOData(output);
    }

}
