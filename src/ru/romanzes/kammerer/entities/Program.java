package ru.romanzes.kammerer.entities;

import java.util.*;

public class Program {
	private List<Procedure> procedures = new ArrayList<>();
	private Map<String, Procedure> procedureMap = new HashMap<>();
	
	public void addProcedure(Procedure procedure) {
		procedures.add(procedure);
		procedureMap.put(procedure.getName(), procedure);
	}
	
	public int getProcedureCount() {
		return procedures.size();
	}
	
	public Procedure getProcedureByIndex(int index) {
		return procedures.get(index);
	}
	
	public Procedure getProcedureByName(String name) {
		return procedureMap.get(name);
	}
	
	public void removeProcedureAtIndex(int index) {
		procedureMap.remove(procedures.remove(index).getName());
	}
	
	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("\n\n");
		for (Procedure procedure: procedures) {
			joiner.add(procedure.toString());
		}
		return joiner.toString();
	}
}
