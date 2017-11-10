package ru.romanzes.kammerer;

import ru.romanzes.kammerer.entities.Command;
import ru.romanzes.kammerer.entities.Procedure;
import ru.romanzes.kammerer.entities.Program;

import java.util.Collections;
import java.util.Random;

public class Mutator {
	private Random random = new Random();
	
	public void mutate(Environment environment, Program program, Generator generator) {
		switch (random.nextInt(3)) {
		case 0:
			addRandomLine(environment, program, generator);
			break;
		case 1:
			deleteRandomLine(program, generator);
			break;
		case 2:
			changeRandomLine(environment, program, generator);
			break;
		}
	}
	
	public void addRandomLine(Environment environment, Program program, Generator generator) {
		int choiceCount = program.getProcedureCount() + 1;
		int choice = random.nextInt(choiceCount);
		Command command = generator.generateCommand(environment, program);
		if (choice < program.getProcedureCount()) {
			Procedure procedure = program.getProcedureByIndex(choice);
			procedure.insertCommandAtIndex(random.nextInt(procedure.getCommandCount() + 1), command);
		} else {
			program.addProcedure(new Procedure("proc" + choice, Collections.singletonList(command)));
		}
	}
	
	public void deleteRandomLine(Program program, Generator generator) {
		int procedureIndex = random.nextInt(program.getProcedureCount());
		Procedure procedure = program.getProcedureByIndex(procedureIndex);
		if (procedure.getCommandCount() > 0) {
			int commandIndex = random.nextInt(procedure.getCommandCount());
			procedure.removeCommandAtIndex(commandIndex);
			if (procedure.getCommandCount() == 0 && procedureIndex != 0) {
				program.removeProcedureAtIndex(procedureIndex);
			}
		}
	}
	
	public void changeRandomLine(Environment environment, Program program, Generator generator) {
		Procedure procedure = program.getProcedureByIndex(random.nextInt(program.getProcedureCount()));
		Command command = generator.generateCommand(environment, program);
		int commandIndex = random.nextInt(procedure.getCommandCount());
		procedure.removeCommandAtIndex(commandIndex);
		procedure.insertCommandAtIndex(commandIndex, command);
	}
}
