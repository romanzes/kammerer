package ru.romanzes.kammerer;

import ru.romanzes.kammerer.entities.*;

import java.util.Random;

public class Generator {
	private Random random;
	
	public Generator() {
		this(new Random());
	}
	
	public Generator(Random random) {
		this.random = random;
	}
	
	public Command generateCommand(Environment environment, Program program) {
		switch (random.nextInt(3)) {
		case 0:
			return generateAssignment(environment);
		case 1:
			return generateBifurcation(environment, program);
		case 2:
		default:
			return generateCall(program);
		}
	}
	
	private Assignment generateAssignment(Environment environment) {
		return new Assignment(generateVariableReference(environment), generateExpression(environment));
	}
	
	private Bifurcation generateBifurcation(Environment environment, Program program) {
		return new Bifurcation(generateExpression(environment), generateCall(program), generateCall(program));
	}
	
	private Call generateCall(Program program) {
		int option = random.nextInt(program.getProcedureCount());
		return new Call(program.getProcedureByIndex(option).getName());
	}
	
	private Expression generateExpression(Environment environment) {
		if (random.nextInt(2) == 0) {
			return generateValue(environment);
		} else {
			Expression value1 = generateValue(environment);
			Expression value2 = generateValue(environment);
			Operator operator = generateOperator();
			return new Operation(value1, operator, value2);
		}
	}
	
	private VariableReference generateVariableReference(Environment environment) {
		String name = environment.getVariableName(random.nextInt(environment.getVariableCount()));
		Expression index = null;
		switch (random.nextInt(3)) {
		case 0:
			index = new Constant(random.nextInt(1000));
			break;
		case 1:
			String indexVarName = getVariableName(environment);
			index = new VariableReference(indexVarName, null);
			break;
		}
		return new VariableReference(name, index);
	}
	
	private Expression generateValue(Environment environment) {
		switch (random.nextInt(3)) {
		case 0:
			return new Constant(random.nextInt(1000));
		case 1:
			return generateVariableReference(environment);
		case 2:
		default:
			String variableName = getVariableName(environment);
			return new VariableSize(variableName);
		}
	}
	
	private String getVariableName(Environment environment) {
		int choiceCount = environment.getVariableCount() + 1;
		int choice = random.nextInt(choiceCount);
		if (choice < environment.getVariableCount()) {
			return environment.getVariableName(choice);
		} else {
			return "var" + choice;
		}
	}
	
	private Operator generateOperator() {
		int index = random.nextInt(Operator.values().length);
		return Operator.values()[index];
	}
}
