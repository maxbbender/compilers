package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.ParserMain;
import parser.ParserTerminalList;

import lexer.Token;
public class Digit {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Digit() {
	}
	
	public static boolean validateDigit(ArrayList<Token> tokens, int currIndex) {
		ParserMain.list.addNode("digit", tokens.get(currIndex).getTokenValue());
		if (tokens.get(currIndex).getTokenType() == "digit") {
			postIndex = currIndex + 1;
			log.info("DIGIT");
			return true; 
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Digit near " + tokens.get(currIndex).getTokenValue());
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
		Digit.postIndex = postIndex;
	}
}

