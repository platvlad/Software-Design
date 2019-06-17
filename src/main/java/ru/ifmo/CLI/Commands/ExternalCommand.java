package ru.ifmo.CLI.Commands;

import ru.ifmo.CLI.Utils.IOData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing external command calls
 */
public class ExternalCommand extends Command {
    public ExternalCommand() {
        super();
    }

    /**
     * Executes command
     * @return Output of external command if successful. Error message otherwise
     */
    public IOData execute() {
        List<String> processArguments = arguments;
        if (fromPipe()) {
            processArguments.addAll(data.getData());
        }
        var processBuilder = new ProcessBuilder(processArguments);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            var streamReader = new InputStreamReader(inputStream);
            var buffReader = new BufferedReader(streamReader);
            String line = buffReader.readLine();
            List<String> input = new ArrayList<String>();
            while (line != null) {
                input.add(line);
                line = buffReader.readLine();
            }
            return new IOData(input);
        } catch (IOException ex) {
            String message = arguments.get(0) + " failed";
            return stringToIOData(message, true);
        }
    }
}
