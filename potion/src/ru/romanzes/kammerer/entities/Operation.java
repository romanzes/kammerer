package ru.romanzes.kammerer.entities;

public class Operation implements Expression {
	private Expression first, second;
	private Operator operator;
	
	public Operation(Expression first, Operator operator, Expression second) {
		this.first = first;
		this.operator = operator;
		this.second = second;
	}
	
	public Expression getFirstArgument() {
		return first;
	}
	
	public Expression getSecondArgument() {
		return second;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return first.toString() + " " + operator.toString() + " " + second.toString();
	}
}
