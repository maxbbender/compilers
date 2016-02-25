package parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexer.Token;
import parser.objects.Program;
import parser.ParserTerminalList;

public class ParserMain {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static Program program;
	public static ParserTerminalList list = new ParserTerminalList();
	public ParserMain(ArrayList<Token> tokens) {
		program = new Program(list);
		if (program.validateProgram(tokens)) {
			System.out.println("YAY");
		} else {
			System.out.println("NAY");
		}
	}
	
	
}
