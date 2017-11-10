package ru.romanzes.kammerer;

import org.junit.Test;
import ru.romanzes.kammerer.entities.Program;

import static org.junit.Assert.assertEquals;

public class InterpreterTest {
	@Test
	public void testInterpreting() throws ParseException {
		String code = TestUtils.loadResourceAsString("revert_array.kam");
		Parser parser = new Parser();
		Program program = parser.parseProgram(code);
		Environment environment = new Environment();
		environment.setVariable("input", "Hello world!");
		Interpreter interpreter = new Interpreter(environment, program);
		interpreter.interpret();
		assertEquals(environment.getVariableAsString("output"), "!dlrow olleH");
	}
}
