package ru.romanzes.kammerer.entities;

public class Assignment implements Command {
	private VariableReference assignee;
	private Expression expression;
	
	public Assignment(VariableReference assignee, Expression expression) {
		this.assignee = assignee;
		this.expression = expression;
	}
	
	public VariableReference getAssignee() {
		return assignee;
	}
	
	public Expression getExpression() {
		return expression;
	}
	
	@Override
	public String toString() {
		return assignee.toString() + " = " + expression.toString();
	}
}
