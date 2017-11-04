package ru.romanzes.kammerer;

import org.junit.Test;
import ru.romanzes.kammerer.entities.Program;

public class MutatorTest {
	@Test
	public void testInterpreting() throws ParseException {
		String code = TestUtils.loadResourceAsString("revert_array.kam");
		Parser parser = new Parser();
		Program program = parser.parseProgram(code);
		Environment environment = new Environment();
		environment.setVariable("input", "Hello world!");
		Mutator mutator = new Mutator();
		Generator generator = new Generator();
		mutator.addRandomLine(environment, program, generator);
		mutator.addRandomLine(environment, program, generator);
		mutator.addRandomLine(environment, program, generator);
		System.out.println(program.toString());
	}
}
