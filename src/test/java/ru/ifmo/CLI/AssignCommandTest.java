package ru.ifmo.CLI;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class AssignCommandTest {
    @Test
    public void testVariableAssigned() {
        List<String> arguments = new ArrayList<>();
        arguments.add("a=10");
        Command command = new AssignCommand(arguments);
        command.execute();
        String aValue = InterpreterEnvironment.getValue("a");
        assertEquals(String.valueOf(10), aValue);
    }

    @Test
    public void testVariableNotAssigned() {
        List<String> arguments = new ArrayList<>();
        arguments.add("a=10");
        Command command = new AssignCommand(arguments);
        command.execute();
        String bValue = InterpreterEnvironment.getValue("b");
        assertEquals("", bValue);
    }

    @Test
    public void testVariableAssignedAgain() {
        List<String> arguments10 = new ArrayList<>();
        arguments10.add("a=10");
        Command command = new AssignCommand(arguments10);
        command.execute();

        List<String> arguments20 = new ArrayList<>();
        arguments20.add("a=20");
        command = new AssignCommand(arguments20);
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

        Command command = new AssignCommand(argumentsC);
        command.execute();
        command = new AssignCommand(argumentsD);
        command.execute();
        command = new AssignCommand(argumentsE);
        command.execute();

        String cValue = InterpreterEnvironment.getValue("c");
        String dValue = InterpreterEnvironment.getValue("d");
        String eValue = InterpreterEnvironment.getValue("e");

        assertEquals(String.valueOf(1), cValue);
        assertEquals(String.valueOf(2), dValue);
        assertEquals(String.valueOf(3), eValue);
    }
}
