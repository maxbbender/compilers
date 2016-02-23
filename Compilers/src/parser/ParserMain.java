package parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexer.Token;
import parser.objects.Program;


public class ParserMain {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Program program;
	public ParserMain(ArrayList<Token> tokens, int numPrograms) {
		int index = 0;
		for (int i = 0; i < numPrograms; i++ ) {
			program = new Program();
			if (program.validateProgram(tokens, index)) {
				System.out.println("Program " + (i+1) + " is valid");
				index = program.getPostIndex();
			} else {
				System.out.println("Program " + (i+1) + " is not valid");
			}
		}	
	}
}
