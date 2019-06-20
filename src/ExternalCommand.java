import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//class implementing external command calls
public class ExternalCommand extends Command {
    public ExternalCommand(String[] arguments) {
        this.arguments = arguments;
    }

    public ExternalCommand(IOData data) {
        this.data = data;
    }

    public IOData execute() {
        List<String> argList = Arrays.asList(arguments);
        ProcessBuilder processBuilder = new ProcessBuilder(argList);
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
            return new IOData(input.toArray(new String[0]));
        } catch (IOException ex) {
            String message = arguments[0] + " failed";
            String[] result = { message };
            return new IOData(result);
        }
    }
}
