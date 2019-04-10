package ru.ifmo.CLI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//class representing pipe content
public class IOData {
    private long sizeInBytes = 0;
    private List<String> data;

    public IOData() {
        data = new ArrayList<>();
    }

    public IOData(List<String> inputData) {
        data = inputData;
        for (String line : inputData) {
            sizeInBytes += line.length() + 1;
        }
    }

    public IOData(List<String> inputData, long sizeInBytes) {
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
        List<String> concatenation = new ArrayList<String>(data.size() + other.data.size());
        data.addAll(other.data);
        sizeInBytes += other.sizeInBytes;
    }
    List<String> getData() {
        return data;
    }


}
