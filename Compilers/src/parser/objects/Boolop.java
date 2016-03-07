package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;
import parser.ParserMain;

public class Boolop {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	public Boolop() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("BOOLOP", tokens.get(currIndex).getTokenValue());
		if (tokens.get(currIndex).getTokenType() == "boolop") {
			postIndex = currIndex + 1;
			log.info("BOOLOP");
			return true; // True on Boolop
		} else {
			log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Boolop near " + tokens.get(currIndex).getTokenValue());
			return false; // FALSE ON BOOLOP
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Boolop.postIndex = postIndex;
	}
}
