package ru.ifmo.CLI.Commands;

import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.InterpreterEnvironment;
import ru.ifmo.CLI.Utils.IOData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest {

    @Test
    public void testPwd() {
        Command cdCommand = new CdCommand();
        List<String> cdCommandArguments = new ArrayList<>();
        cdCommandArguments.add("src");
        cdCommand.addArguments(cdCommandArguments);
        cdCommand.execute();
        Command pwdCommand = new PwdCommand();
        IOData result = pwdCommand.execute();
        List<String> resultData = result.getData();
        String currentDirectory = resultData.get(0);
        String regex = "\\" + File.separator;
        String[] pathParts = currentDirectory.split(regex);
        assertEquals("src", pathParts[pathParts.length - 1]);
    }
}
