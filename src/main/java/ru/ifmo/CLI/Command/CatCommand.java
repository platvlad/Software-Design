package ru.ifmo.CLI.Command;

import ru.ifmo.CLI.IOData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//implementing cat Command (cat 1.txt)
//Write files into output
public class CatCommand extends Command {
    public CatCommand() {
        super();
    }

    public IOData execute() {
        if (inPipe()) {
            return data;
        }
        IOData result = new IOData();
        for (String fileName : arguments) {
            result.add(catFile(fileName));
        }
        return result;

    }

    private IOData catFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            List<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            return new IOData(lines, file.length());
        } catch (FileNotFoundException ex) {
            String message = "File " + fileName + " not found";
            return stringToIOData(message);
        }
    }
}
