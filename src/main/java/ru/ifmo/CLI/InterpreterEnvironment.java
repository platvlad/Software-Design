package ru.ifmo.CLI;

import java.util.HashMap;

/**
 * Class for storing variable values
 */
public class InterpreterEnvironment {
    private static HashMap<String, String> variablesDictionary = new HashMap<String, String>();
    public static boolean exit = false;
    public static String currentDirectoryName = System.getProperty("user.dir");

    /**
     * Get value of given variable if it was set
     * If variable was not declared, returns empty string
     * @param varName Name of the variables
     * @return Value of the provided variable
     */
    public static String getValue(String varName) {
        String value = variablesDictionary.get(varName);
        if (value == null) {
            return "";
        }
        return value;
    }

    /**
     * Sets variable name with provided value
     * @param varName Name of the variable
     * @param value Value to set
     */
    public static void setValue (String varName, String value) {
        variablesDictionary.put(varName, value);
    }
}
