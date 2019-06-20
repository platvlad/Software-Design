import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//class representing pipe content
public class IOData {
    public IOData() {
        data = new String[0];
    }

    public IOData(String[] inputData) {
        data = inputData;
    }

    //print output of last command in pipe
    public void printData() {
        for (String line : data) {
            System.out.println(line);
        }
    }

    //concatenate outputs of two commands
    public void add(IOData other) {
        List<String> concatenation = new ArrayList<String>(data.length + other.data.length);
        Collections.addAll(concatenation, data);
        Collections.addAll(concatenation, other.data);
        data = concatenation.toArray(new String[0]);
    }
    String[] getData() {
        return data;
    }

    private String[] data;
}
