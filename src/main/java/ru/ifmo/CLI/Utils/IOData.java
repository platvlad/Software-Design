package ru.ifmo.CLI.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing pipe content
 */
public class IOData {
    private long sizeInBytes = 0;
    private List<String> data;
    private boolean empty = false;
    private boolean isErrorMessage = false;

    public IOData() {
        data = new ArrayList<>();
        empty = true;
    }

    /**
     * Creates IOData object using its content
     * @param inputData Pipe content
     */
    public IOData(List<String> inputData) {
        data = inputData;
        for (String line : inputData) {
            sizeInBytes += line.length() + 1;
        }
        sizeInBytes -= 1;
    }

    /**
     * Creates IOData object by content with size in bytes provided
     * @param inputData Pipe content
     * @param sizeInBytes Size of content in bytes
     */
    public IOData(List<String> inputData, long sizeInBytes) {
        data = inputData;
        this.sizeInBytes = sizeInBytes;
    }

    /**
     * IOData object representing an error can be created through this constructor
     * @param inputData Pipe content
     * @param isErrorMessage Flag if inputData represents an error message
     */
    public IOData(List<String> inputData, boolean isErrorMessage) {
        this(inputData);
        this.isErrorMessage = isErrorMessage;
    }

    /**
     * Check if IOData object has no content
     * @return True if IOData object has no content
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Get size of the content in bytes
     * @return Size of the content in bytes
     */
    public long getSizeInBytes() {
        return sizeInBytes;
    }

    /**
     * Print output of last command in pipe
     */
    public void printData() {
        for (String line : data) {
            System.out.println(line);
        }
    }

    /**
     * Concatenate outputs of two commands
     * @param other IOData object to concatenate it with the current
     */
    public void add(IOData other) {
        empty = false;
        List<String> concatenation = new ArrayList<String>(data.size() + other.data.size());
        data.addAll(other.data);
        sizeInBytes += other.sizeInBytes;
    }

    /**
     * Get pipe content
     * @return Content of the pipe
     */
    public List<String> getData() {
        return data;
    }

    /**
     * Check if pipe contains an error message
     * @return True if pipe contains an error message
     */
    public boolean errorOccured() {
        return isErrorMessage;
    }

}
