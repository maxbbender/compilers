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
	private static boolean toContinue = true;
	private static Program program;
	public static ParserTerminalList list;
	public static ArrayList<ParserTerminalList> programs = new ArrayList();
	public ParserMain(ArrayList<Token> tokens, int numPrograms) {
		int index = 0;
		for (int i = 0; i < numPrograms; i++ ) {
			program = new Program();
			list = new ParserTerminalList();
			if (program.validateProgram(tokens, index)) {
				System.out.println("Program " + (i+1) + " is valid");
				programs.add(list);
			} else {
				System.out.println("Program " + (i+1) + " is not valid");
				toContinue = false;
				break;
			}
		}	
	}
	
	public static void printCst() {
		int currCst = 1; 
		int numberOfPrograms = programs.size();
		if (numberOfPrograms > 0) {
			System.out.println("Printing CST for Programs");
			System.out.println("Number of Programs: " + programs.size());
			for (ParserTerminalList temp : programs) {
				System.out.println("---------------------------");
				System.out.println("CST for Program " + currCst);
				System.out.println("---------------------------");
				temp.printList();
				System.out.println("****************************");
				System.out.println("Program " + currCst + " finished printing");
				System.out.println("****************************");
				currCst++;
			}
		}
	}
	
	public static Program getProgram() {
		return program;
	}
	public static void setProgram(Program program) {
		ParserMain.program = program;
	}
	public static ParserTerminalList getList() {
		return list;
	}
	public static void setList(ParserTerminalList list) {
		ParserMain.list = list;
	}
	public static Logger getLog() {
		return log;
	}

	public static boolean isToContinue() {
		return toContinue;
	}

	public static void setToContinue(boolean toContinue) {
		ParserMain.toContinue = toContinue;
	}

	public static ArrayList<ParserTerminalList> getPrograms() {
		return programs;
	}

	public static void setPrograms(ArrayList<ParserTerminalList> programs) {
		ParserMain.programs = programs;
	}
	
}
