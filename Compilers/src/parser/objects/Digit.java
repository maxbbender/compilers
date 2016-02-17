package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lexer.Token;
public class Digit {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Digit() {
		
	}
	
	public static boolean validateDigit(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "digit") {
			postIndex = currIndex + 1;
			return true; 
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Digit near " + tokens.get(currIndex).getTokenValue());
			return false; 
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}
}

