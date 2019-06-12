package ru.ifmo.CLI.Command;

import ru.ifmo.CLI.IOData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

//implement wc (count number of strings, words and bytes in file)
public class WcCommand extends Command {
    public WcCommand() {
        super();
    }

    public IOData execute() {
        if (inPipe()) {
            List<String> lines = data.getData();
            long size = data.getSizeInBytes();
            int stringsNumber = lines.size();
            int wordsNumber = 0;
            for (String line : lines) {
                String[] words = line.split(" ");
                wordsNumber += words.length;
            }
            String answer = stringsNumber + " " + wordsNumber + " " + size;
            return stringToIOData(answer);
        } else {
            if (arguments.isEmpty()) {
                return stringToIOData("No file provided to wc command");
            }
            String fileName = arguments.get(0);
            int stringsNumber = 0;
            int wordsNumber = 0;
            try {
                File file = new File(fileName);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(" ");
                    wordsNumber += words.length;
                    ++stringsNumber;
                }
                String answer = stringsNumber + " " + wordsNumber + " " + file.length();
                return stringToIOData(answer);
            } catch (FileNotFoundException ex) {
                String message = "File " + fileName + " not found";
                return stringToIOData(message);
            }
        }

    }
}
