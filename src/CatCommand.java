import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//implementing cat Command (cat 1.txt)
//Write files into output
public class CatCommand extends Command {

    public CatCommand(String [] words) {
        this.arguments = words;
    }

    public CatCommand(IOData data) {
        this.data = data;
    }

    public IOData execute() {
        IOData result = new IOData();
        if (arguments != null) {
            for (String fileName : arguments) {
                result.add(catFile(fileName));
            }
            return result;
        }
        return data;
    }

    private IOData catFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            List<String> lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            String[] output = lines.toArray(new String[0]);
            return new IOData(output, file.length());
        } catch (FileNotFoundException ex) {
            String message = "File " + fileName + " not found";
            String[] output = { message };
            return new IOData(output);
        }
    }
}
