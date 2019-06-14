package ru.ifmo.CLI.Command;

import ru.ifmo.CLI.Utils.IOData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//class implementing external command calls
public class ExternalCommand extends Command {
    public ExternalCommand() {
        super();
    }

    public IOData execute() {
        List<String> processArguments = arguments;
        if (fromPipe()) {
            processArguments.addAll(data.getData());
        }
        ProcessBuilder processBuilder = new ProcessBuilder(processArguments);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(streamReader);
            String line = buffReader.readLine();
            List<String> input = new ArrayList<String>();
            while (line != null) {
                input.add(line);
                line = buffReader.readLine();
            }
            return new IOData(input);
        } catch (IOException ex) {
            String message = arguments.get(0) + " failed";
            return stringToIOData(message);
        }
    }
}
