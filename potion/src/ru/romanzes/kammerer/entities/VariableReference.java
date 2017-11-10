package ru.romanzes.kammerer.entities;

public class VariableReference implements Expression {
	private String variableName;
	private Expression index;
	
	public VariableReference(String variableName, Expression index) {
		this.variableName = variableName;
		this.index = index;
	}
	
	public String getVariableName() {
		return variableName;
	}
	
	public Expression getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		String result = variableName;
		if (index != null) {
			result += "[" + index.toString() + "]";
		}
		return result;
	}
}
