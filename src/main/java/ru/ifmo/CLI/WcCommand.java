package ru.ifmo.CLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//implement wc (count number of strings, words and bytes in file)
public class WcCommand extends Command {
    public WcCommand(String[] arguments) {
        this.arguments = arguments;
    }
    public WcCommand(IOData data) {
        this.data = data;
    }
    public IOData execute() {
        if (arguments != null) {
            String fileName = arguments[0];
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
                String[] output = { answer };
                return new IOData(output);
            } catch (FileNotFoundException ex) {
                String message = "File " + fileName + " not found";
                String[] output = { message };
                return new IOData(output);
            }
        }
        else {
            String[] lines = data.getData();
            long size = data.getSizeInBytes();
            int stringsNumber = lines.length;
            int wordsNumber = 0;
            for (String line : lines) {
                String[] words = line.split(" ");
                wordsNumber += words.length;
            }
            String answer = stringsNumber + " " + wordsNumber + " " + size;
            String[] output = { answer };
            return new IOData(output);
        }

    }
}
