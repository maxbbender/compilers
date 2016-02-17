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
	public ParserMain(ArrayList<Token> tokens) {
		program = new Program();
		if (program.validateProgram(tokens)) {
			System.out.println("YAY");
		} else {
			System.out.println("NAY");
		}
	}
	
	
}
