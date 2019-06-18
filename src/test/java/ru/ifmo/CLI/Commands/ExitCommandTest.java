package ru.ifmo.CLI.Commands;

import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.InterpreterEnvironment;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExitCommandTest {
    @Test
    public void testExitCommand() {
        Command exitCommand = new ExitCommand();
        exitCommand.execute();
        assertTrue(InterpreterEnvironment.exit);
    }
}
