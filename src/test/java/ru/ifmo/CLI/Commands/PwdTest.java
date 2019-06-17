package ru.ifmo.CLI.Commands;

import org.junit.Test;
import ru.ifmo.CLI.Utils.IOData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PwdTest {
    @Test
    public void testPwd() {
        List<String> arguments = new ArrayList<>();
        Command command = new PwdCommand();
        command.addArguments(arguments);
        IOData result = command.execute();
        List<String> resultData = result.getData();
        String currentDirectory = resultData.get(0);
        String[] pathParts = currentDirectory.split("\\\\");
        assertEquals("Software-Design", pathParts[pathParts.length - 1]);
    }
}
