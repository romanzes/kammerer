package ru.romanzes.kammerer;

import ru.romanzes.kammerer.entities.*;

public class Interpreter {
	private Environment environment;
	private Program program;
	
	public Interpreter(Environment environment, Program program) {
		this.environment = environment;
		this.program = program;
	}
	
	public void interpret() {
		runMainProcedure();
	}
	
	private void runMainProcedure() {
		runProcedure(program.getProcedureByName("main"));
	}
	
	private void runProcedure(Procedure procedure) {
		for (int i = 0; i < procedure.getCommandCount(); i++) {
			runCommand(procedure.getCommand(i));
		}
	}
	
	private void runCommand(Command command) {
		if (command == null) {
			return;
		} else if (command instanceof Assignment) {
			runAssignment((Assignment) command);
		} else if (command instanceof Bifurcation) {
			runBifurcation((Bifurcation) command);
		} else if (command instanceof Call) {
			runCall((Call) command);
		}
	}
	
	private void runAssignment(Assignment assignment) {
		String variableName = assignment.getAssignee().getVariableName();
		Integer index = evaluateExpression(assignment.getAssignee().getIndex());
		int value = evaluateExpression(assignment.getExpression());
		environment.setVariableAtIndex(variableName, index, value);
	}
	
	private void runBifurcation(Bifurcation bifurcation) {
		if (evaluateExpression(bifurcation.getCondition()) > 0) {
			runCommand(bifurcation.getPositiveCommand());
		} else {
			runCommand(bifurcation.getNegativeCommand());
		}
	}

	private void runCall(Call call) {
		runProcedure(program.getProcedureByName(call.getProcedureName()));
	}
	
	private Integer evaluateExpression(Expression expression) {
		if (expression == null) {
			return null;
		} else if (expression instanceof VariableSize) {
			VariableSize variableSize = (VariableSize) expression;
			return environment.getVariableSize(variableSize.getVariableName());
		} else if (expression instanceof VariableReference) {
			VariableReference variableReference = (VariableReference) expression;
			Integer index = evaluateExpression(variableReference.getIndex());
			return environment.getVariableAtIndex(variableReference.getVariableName(), index);
		} else if (expression instanceof Operation) {
			Operation operation = (Operation) expression;
			return calcOperation(operation);
		} else if (expression instanceof Constant) {
			Constant constant = (Constant) expression;
			return constant.getValue();
		}
		return 0;
	}
	
	private int calcOperation(Operation operation) {
		int first = evaluateExpression(operation.getFirstArgument());
		int second = evaluateExpression(operation.getSecondArgument());
		switch (operation.getOperator()) {
		case ADD:
			return first + second;
		case SUB:
			return first - second;
		case MUL:
			return first * second;
		case DIV:
			return first / second;
		case MOD:
			return first % second;
		case EQL:
			return first == second? 1: 0;
		case NEQ:
			return first != second? 1: 0;
		case LES:
			return first < second? 1: 0;
		case GRT:
			return first > second? 1: 0;
		default:
			return 0;
		}
	}
}
