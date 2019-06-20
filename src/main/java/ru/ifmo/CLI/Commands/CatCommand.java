package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class implements cat Commands (cat 1.txt)
 * Write files into output
 */
public class CatCommand extends Command {
    public CatCommand() {
        super();
    }

    /**
     * Execute command
     * @return IOData with file content if file is red successfully. IOData with error message otherwise
     */
    public IOData execute() {
        if (fromPipe()) {
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
            var scanner = new Scanner(file);
            List<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            return new IOData(lines, file.length());
        } catch (FileNotFoundException ex) {
            String message = "File " + fileName + " not found";
            return stringToIOData(message, true);
        }
    }
}
