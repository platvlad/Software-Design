package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class implementing grep command (find regular expression in a file)
 * Options:
 * -i: case insensitivity
 * -w: find only whole words
 * -A n: print n strings after matching
 */
public class GrepCommand extends Command {

    public GrepCommand() {
        super();
    }

    /**
     * Find pattern in list of strings
     * @param fileContent List of strings where to find pattern
     * @param pattern Pattern o find
     * @param argInfo Arguments for this command
     * @return List of strings to print
     */
    private List<String> findPatternInLines(List<String> fileContent,
                                                  Pattern pattern,
                                                  GrepArgumentsInfo argInfo) {
        List<String> output = new ArrayList<>();

        int remainingStrings = 0;
        for (String line : fileContent) {
            boolean foundPattern = false;
            List<String> forMatching = List.of(line);
            if (argInfo.isWholeWord()) {
                String[] words = line.split(" ");
                forMatching = Arrays.asList(words);
            }
            for (String word : forMatching) {
                var matcher = pattern.matcher(word);
                if (!argInfo.isWholeWord() && matcher.find() || argInfo.isWholeWord() && matcher.matches()) {
                    output.add(line);
                    remainingStrings = argInfo.getNumberOfStrings();
                    foundPattern = true;
                    break;
                }
            }
            if (!foundPattern && remainingStrings > 0) {
                output.add(line);
                remainingStrings--;
            }
        }
        return output;
    }

    /**
     * Find pattern in provided files
     * @param fileNames File names
     * @param pattern Pattern to search
     * @param argInfo Grep command arguments
     * @return List of strings to print
     */
    private List<String> findPatternInFiles(List<String> fileNames, Pattern pattern, GrepArgumentsInfo argInfo) {
        List<String> output = new ArrayList<>();
        for (var fileName : fileNames) {
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
            output.addAll(findPatternInLines(fileStrings, pattern, argInfo));
        }
        return output;
    }

    /**
     * Execute grep command
     * @return IOData with strings that should be printed
     */
    public IOData execute() {
        String[] argumentsArray = arguments.toArray(new String[0]);
        var argInfo = new GrepArgumentsInfo(argumentsArray);
        IOData parseArgumentMessage = argInfo.parseArguments();
        if (!parseArgumentMessage.isEmpty()) {
            return parseArgumentMessage;
        }

        List<String> files = argInfo.getFileNames();
        String patternString = argInfo.getPatternString();
        Pattern pattern;
        if (argInfo.isCaseSensitivity()) {
            pattern = Pattern.compile(patternString);
        } else {
            pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        }

        List<String> output = findPatternInFiles(files, pattern, argInfo);
        if (fromPipe()) {
            output.addAll(findPatternInLines(data.getData(), pattern, argInfo));
        }

        return new IOData(output);
    }

}
