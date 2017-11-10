package ru.romanzes.cauldron;

import ru.romanzes.kammerer.Environment;
import ru.romanzes.kammerer.Generator;
import ru.romanzes.kammerer.Mutator;
import ru.romanzes.kammerer.Parser;
import ru.romanzes.kammerer.entities.Program;

public class Launcher {
    public static void main(String[] args) throws Exception {
        String code = Utils.loadResourceAsString("revert_array.kam");
        Parser parser = new Parser();
        Program program = parser.parseProgram(code);
        Environment environment = new Environment();
        environment.setVariable("input", "Hello world!");
        Mutator mutator = new Mutator();
        Generator generator = new Generator();
        mutator.addRandomLine(environment, program, generator);
        mutator.addRandomLine(environment, program, generator);
        mutator.addRandomLine(environment, program, generator);
        System.out.println(program.toString());
    }
}
