package ru.romanzes.kammerer.entities;

public class Call implements Command {
	private String procedureName;
	
	public Call(String procedureName) {
		this.procedureName = procedureName;
	}
	
	public String getProcedureName() {
		return procedureName;
	}
	
	@Override
	public String toString() {
		return procedureName;
	}
}
