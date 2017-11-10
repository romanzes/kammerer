package ru.romanzes.kammerer.entities;

public class VariableSize implements Expression {
	private String variableName;
	
	public VariableSize(String variableName) {
		this.variableName = variableName;
	}
	
	public String getVariableName() {
		return variableName;
	}
	
	@Override
	public String toString() {
		return variableName + "[]";
	}
}
