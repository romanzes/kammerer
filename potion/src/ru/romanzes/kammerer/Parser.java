package ru.romanzes.kammerer;

import ru.romanzes.kammerer.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	private static final Pattern procedurePattern = Pattern.compile(
			"(?<name>\\w+)\\s*\\{(?<code>[^{}]*)\\}",
			Pattern.DOTALL);
	private static final Pattern bifurcationPattern = Pattern.compile(
			"if\\s+(?<condition>.*)\\s+then\\s+(?<positive>.*)(\\s+else\\s+(?<negative>.*))?");
	private static final Pattern assignmentPattern = Pattern.compile(
			"(?<assignee>[^\\s]*)\\s*=\\s*(?<expression>.*)");
	private static final Pattern callPattern = Pattern.compile(
			"\\s*(?<procedure>\\w*)\\s*");
	private static final Pattern variableRefPattern = Pattern.compile(
			"(?<name>\\w+)\\s*\\[(?<index>.*)\\]");
	private static final Pattern operationPattern = Pattern.compile(
			"(?<first>[\\w\\[\\]]+)\\s*(?<operator>\\+|\\-|\\*|\\/|%|==|\\!=|\\<|\\>)\\s*(?<second>[\\w\\[\\]]+)");
	
	public Program parseProgram(String programCode) throws ParseException {
		Program result = new Program();
		
		Matcher matcher = procedurePattern.matcher(programCode);
		while (matcher.find()) {
			String name = matcher.group("name");
			String code = matcher.group("code");
			result.addProcedure(new Procedure(name, parseCommands(code)));
		}
		
		return result;
	}
	
	private List<Command> parseCommands(String programCode) {
		List<Command> result = new ArrayList<>();
		String[] lines = programCode.split("\n");
		for (String line : lines) {
			if (line != null) {
				String trimmedLine = line.trim();
				if (!trimmedLine.isEmpty()) {
					result.add(parseCommand(trimmedLine));
				}
			}
		}
		return result;
	}
	
	private Command parseCommand(String programLine) {
		Matcher matcher;
		if ((matcher = bifurcationPattern.matcher(programLine)).matches()) {
			Expression condition = parseExpression(matcher.group("condition"));
			Command positive = parseCommand(matcher.group("positive"));
			Command negative = null;
			try {
				negative = parseCommand(matcher.group("negative"));
			} catch (Exception ex) {}
			return new Bifurcation(condition, positive, negative);
		} else if ((matcher = assignmentPattern.matcher(programLine)).matches()) {
			VariableReference assignee = parseAssignee(matcher.group("assignee"));
			Expression expression = parseExpression(matcher.group("expression"));
			return new Assignment(assignee, expression);
		} else if ((matcher = callPattern.matcher(programLine)).matches()) {
			String procedureName = matcher.group("procedure");
			return new Call(procedureName);
		}
		return null;
	}
	
	private Expression parseExpression(String expressionString) {
		Matcher matcher;
		if (expressionString.matches("\\d+")) {
			return new Constant(Integer.parseInt(expressionString));
		} else if (expressionString.matches("\\w+")) {
			return new VariableReference(expressionString, null);
		} else if ((matcher = variableRefPattern.matcher(expressionString)).matches()) {
			String name = matcher.group("name");
			String indexString = matcher.group("index");
			if (indexString.isEmpty()) {
				return new VariableSize(name);
			} else {
				Expression index = parseExpression(indexString);
				return new VariableReference(name, index);
			}
		} else if ((matcher = operationPattern.matcher(expressionString)).matches()) {
			Expression first = parseExpression(matcher.group("first"));
			Expression second = parseExpression(matcher.group("second"));
			Operator operator = parseOperator(matcher.group("operator"));
			return new Operation(first, operator, second);
		}
		return null;
	}
	
	private VariableReference parseAssignee(String refString) {
		Expression expression = parseExpression(refString);
		if (expression instanceof VariableReference) {
			return (VariableReference) expression;
		}
		return null;
	}
	
	private Operator parseOperator(String operatorString) {
		switch (operatorString) {
		case "+":
			return Operator.ADD;
		case "-":
			return Operator.SUB;
		case "*":
			return Operator.MUL;
		case "/":
			return Operator.DIV;
		case "%":
			return Operator.MOD;
		case "==":
			return Operator.EQL;
		case "!=":
			return Operator.NEQ;
		case "<":
			return Operator.LES;
		case ">":
			return Operator.GRT;
		}
		return null;
	}
}
