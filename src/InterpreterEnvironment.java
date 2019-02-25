import java.util.HashMap;

//class for storing variable values
public class InterpreterEnvironment {
    private static HashMap<String, String> variablesDictionary = new HashMap<String, String>();
    static boolean exit = false;
    public static String getValue(String varName) {
        String value = variablesDictionary.get(varName);
        if (value == null) {
            return "";
        }
        return value;
    }
    public static void setValue (String varName, String value) {
        variablesDictionary.put(varName, value);
    }
}
