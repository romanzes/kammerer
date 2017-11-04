package ru.romanzes.kammerer.entities;

import java.util.List;
import java.util.StringJoiner;

public class Procedure {
	private String name;
	private List<Command> commands;
	
	public Procedure(String name, List<Command> commands) {
		this.name = name;
		this.commands = commands;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCommandCount() {
		return commands.size();
	}
	
	public Command getCommand(int index) {
		return commands.get(index);
	}
	
	public void insertCommandAtIndex(int index, Command command) {
		commands.add(index, command);
	}
	
	public void removeCommandAtIndex(int index) {
		commands.remove(index);
	}
	
	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("\n\t");
		for (Command command: commands) {
			joiner.add(command.toString());
		}
		return name + " {\n\t" + joiner.toString() + "\n}";
	}
}
