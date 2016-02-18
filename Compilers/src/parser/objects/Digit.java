package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.ParserTerminalList;

import lexer.Token;
public class Digit {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static ParserTerminalList list = new ParserTerminalList();
	public Digit(ParserTerminalList newList) {
		list = newList;
	}
	
	public static boolean validateDigit(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "digit") {
			list.addNode("DIGIT", tokens.get(currIndex).getTokenValue(), 1);
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

	public static ParserTerminalList getList() {
		return list;
	}

	public static void setList(ParserTerminalList list) {
		Digit.list = list;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setPostIndex(int postIndex) {
		Digit.postIndex = postIndex;
	}
}

