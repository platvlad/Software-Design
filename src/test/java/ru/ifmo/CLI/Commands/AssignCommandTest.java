package ru.ifmo.CLI.Commands;

import org.junit.jupiter.api.Test;
import ru.ifmo.CLI.InterpreterEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class AssignCommandTest {
    @Test
    public void testVariableAssigned() {
        List<String> arguments = new ArrayList<>();
        arguments.add("a=10");
        Command command = new AssignCommand();
        command.addArguments(arguments);
        command.execute();
        String aValue = InterpreterEnvironment.getValue("a");
        assertEquals(String.valueOf(10), aValue);
    }

    @Test
    public void testVariableNotAssigned() {
        List<String> arguments = new ArrayList<>();
        arguments.add("a=10");
        Command command = new AssignCommand();
        command.addArguments(arguments);
        command.execute();
        String bValue = InterpreterEnvironment.getValue("b");
        assertEquals("", bValue);
    }

    @Test
    public void testVariableAssignedAgain() {
        List<String> arguments10 = new ArrayList<>();
        arguments10.add("a=10");
        Command command = new AssignCommand();
        command.addArguments(arguments10);
        command.execute();

        List<String> arguments20 = new ArrayList<>();
        arguments20.add("a=20");
        command = new AssignCommand();
        command.addArguments(arguments20);
        command.execute();
        String aValue = InterpreterEnvironment.getValue("a");
        assertEquals(String.valueOf(20), aValue);
    }

    @Test
    public void testAssignMultipleVariables() {
        List<String> argumentsC = new ArrayList<>();
        List<String> argumentsD = new ArrayList<>();
        List<String> argumentsE = new ArrayList<>();

        argumentsC.add("c=1");
        argumentsD.add("d=2");
        argumentsE.add("e=3");

        Command command = new AssignCommand();
        command.addArguments(argumentsC);
        command.execute();
        command = new AssignCommand();
        command.addArguments(argumentsD);
        command.execute();
        command = new AssignCommand();
        command.addArguments(argumentsE);
        command.execute();

        String cValue = InterpreterEnvironment.getValue("c");
        String dValue = InterpreterEnvironment.getValue("d");
        String eValue = InterpreterEnvironment.getValue("e");

        assertEquals(String.valueOf(1), cValue);
        assertEquals(String.valueOf(2), dValue);
        assertEquals(String.valueOf(3), eValue);
    }

    @Test
    public void testAssignString() {
        List<String> arguments = new ArrayList<>();
        arguments.add("a=cat 1.txt");
        Command command = new AssignCommand();
        command.addArguments(arguments);
        command.execute();
        String aValue = InterpreterEnvironment.getValue("a");
        assertEquals("cat 1.txt", aValue);
    }
}
