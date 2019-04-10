package ru.ifmo.CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//class representing pipe content
public class IOData {
    private long sizeInBytes = 0;

    public IOData() {
        data = new String[0];
    }

    public IOData(String[] inputData) {
        data = inputData;
        for (String line : inputData) {
            sizeInBytes += line.length() + 1;
        }
    }

    public IOData(String[] inputData, long sizeInBytes) {
        data = inputData;
        this.sizeInBytes = sizeInBytes;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
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
        sizeInBytes += other.sizeInBytes;
    }
    String[] getData() {
        return data;
    }

    private String[] data;
}
