package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class implementing wc (count number of strings, words and bytes in file)
 */
public class WcCommand extends Command {

    public WcCommand() {
        super();
    }

    private int countWords(List<String> lines) {
        int wordsNumber = 0;
        for (String line : lines) {
            if (line.length() > 0) {
                String[] words = line.split(" ");
                wordsNumber += words.length;
            }
        }
        return wordsNumber;
    }

    /**
     * Executes command
     * @return IOData with number of strings, words and bytes in file. Error message if an error occurred.
     */
    public IOData execute() {
        if (fromPipe()) {
            List<String> lines = data.getData();
            long size = data.getSizeInBytes();
            int stringsNumber = lines.size();
            int wordsNumber = countWords(lines);
            String answer = stringsNumber + " " + wordsNumber + " " + size;
            return stringToIOData(answer, false);
        } else {
            if (arguments.isEmpty()) {
                return stringToIOData("No file provided to wc command", true);
            }
            String fileName = arguments.get(0);
            try {
                Path filePath = Paths.get(fileName);
                List<String> fileStrings = Files.readAllLines(filePath);
                long fileSize = Files.size(filePath);
                int stringsNumber = fileStrings.size();
                int wordsNumber = countWords(fileStrings);
                String answer = stringsNumber + " " + wordsNumber + " " + fileSize;
                return stringToIOData(answer, false);
            } catch (FileNotFoundException ex) {
                String message = "File " + fileName + " not found";
                return stringToIOData(message, true);
            } catch (IOException ex) {
                String message = "Failed to read file " + fileName;
                return stringToIOData(message, true);
            }
        }

    }
}
