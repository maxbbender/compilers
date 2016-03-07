package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;

import lexer.Token;
public class Intop {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Intop() {
	}
	
	public static boolean validateIntop(ArrayList<Token> tokens, int currIndex){
		if (tokens.get(currIndex).getTokenType() == "intop") {
			ParserMain.list.addNode("INTOP", tokens.get(currIndex).getTokenValue());
			postIndex = currIndex + 1;
			log.info("INTOP");
			return true;
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Intop near " + tokens.get(currIndex).getTokenValue());
			return false; 
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setPostIndex(int postIndex) {
		Intop.postIndex = postIndex;
	}
}
