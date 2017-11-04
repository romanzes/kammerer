package ru.romanzes.kammerer;

import org.junit.Test;
import ru.romanzes.kammerer.entities.*;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void testParsing() throws ParseException {
        String code = TestUtils.loadResourceAsString("revert_array.kam");
        Parser parser = new Parser();
        Program program = parser.parseProgram(code);
        assertEquals(program.getProcedureCount(), 3);

        Procedure main = program.getProcedureByName("main");
        assertEquals(main.getCommandCount(), 2);
        Procedure checkStartReached = program.getProcedureByName("checkStartReached");
        assertEquals(checkStartReached.getCommandCount(), 1);
        Procedure printNextElement = program.getProcedureByName("printNextElement");
        assertEquals(printNextElement.getCommandCount(), 3);

        Assignment counterInit = (Assignment) main.getCommand(0);
        Call checkStartReachedCallFromMain = (Call) main.getCommand(1);
        Bifurcation counterZeroBifurcation = (Bifurcation) checkStartReached.getCommand(0);
        Assignment counterDecrement = (Assignment) printNextElement.getCommand(0);
        Assignment outputAssignment = (Assignment) printNextElement.getCommand(1);
        Call checkStartReachedCallFromPrint = (Call) printNextElement.getCommand(2);

        VariableReference counterInitReference = counterInit.getAssignee();
        assertEquals(counterInitReference.getVariableName(), "counter");
        assertNull(counterInitReference.getIndex());
        assertEquals(((VariableSize) counterInit.getExpression()).getVariableName(), "input");

        assertEquals(checkStartReachedCallFromMain.getProcedureName(), "checkStartReached");

        Operation counterZeroCondition = (Operation) counterZeroBifurcation.getCondition();
        VariableReference counterCheckReference = (VariableReference) counterZeroCondition.getFirstArgument();
        assertEquals(counterCheckReference.getVariableName(), "counter");
        assertNull(counterCheckReference.getIndex());
        assertTrue(((Constant) counterZeroCondition.getSecondArgument()).getValue() == 0);
        assertEquals((Operator) counterZeroCondition.getOperator(), Operator.GRT);
        Call printNextElementCall = (Call) counterZeroBifurcation.getPositiveCommand();
        assertEquals(printNextElementCall.getProcedureName(), "printNextElement");
        assertNull(counterZeroBifurcation.getNegativeCommand());

        VariableReference counterDecrementReference = counterDecrement.getAssignee();
        assertEquals(counterDecrementReference.getVariableName(), "counter");
        assertNull(counterDecrementReference.getIndex());
        Operation counterDecrementOperation = (Operation) counterDecrement.getExpression();
        VariableReference counterDecrementOperationFirstArgument =
                (VariableReference) counterDecrementOperation.getFirstArgument();
        assertEquals(counterDecrementOperationFirstArgument.getVariableName(), "counter");
        assertNull(counterDecrementOperationFirstArgument.getIndex());
        assertTrue(((Constant) counterDecrementOperation.getSecondArgument()).getValue() == 1);
        assertEquals(counterDecrementOperation.getOperator(), Operator.SUB);

        VariableReference outputVariable = outputAssignment.getAssignee();
        assertEquals(outputVariable.getVariableName(), "output");
        assertNull(outputVariable.getIndex());
        VariableReference inputReading = (VariableReference) outputAssignment.getExpression();
        assertEquals(inputReading.getVariableName(), "input");
        VariableReference inputIndex = (VariableReference) inputReading.getIndex();
        assertEquals(inputIndex.getVariableName(), "counter");
        assertNull(inputIndex.getIndex());

        assertEquals(checkStartReachedCallFromPrint.getProcedureName(), "checkStartReached");
    }

    @Test
    public void testSerialization() throws ParseException {
        String code = TestUtils.loadResourceAsString("revert_array.kam");
        Parser parser = new Parser();
        Program program = parser.parseProgram(code);
        assertEquals(program.toString(), code);
    }
}