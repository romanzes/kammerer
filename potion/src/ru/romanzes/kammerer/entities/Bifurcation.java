package ru.romanzes.kammerer.entities;

public class Bifurcation implements Command {
	private Expression condition;
	private Command positive;
	private Command negative;
	
	public Bifurcation(Expression condition, Command positive, Command negative) {
		this.condition = condition;
		this.positive = positive;
		this.negative = negative;
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public Command getPositiveCommand() {
		return positive;
	}
	
	public Command getNegativeCommand() {
		return negative;
	}
	
	@Override
	public String toString() {
		String result = "if " + condition.toString() + " then " + positive.toString();
		if (negative != null) {
			result += " else " + negative.toString();
		}
		return result;
	}
}
