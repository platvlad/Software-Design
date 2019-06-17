package ru.ifmo.CLI.Commands;

import org.junit.Test;
import ru.ifmo.CLI.InterpreterEnvironment;

import static org.junit.Assert.assertTrue;

public class ExitCommandTest {
    @Test
    public void testExitCommand() {
        Command exitCommand = new ExitCommand();
        exitCommand.execute();
        assertTrue(InterpreterEnvironment.exit);
    }
}
