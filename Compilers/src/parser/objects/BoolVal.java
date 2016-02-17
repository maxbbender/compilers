package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;

public class BoolVal {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	
	public BoolVal() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "boolVal") {
			postIndex = currIndex + 1;
			return true;
		} else {
			log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid BoolVal near " + tokens.get(currIndex).getTokenValue());
			return false; // ERROR ON 
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		BoolVal.postIndex = postIndex;
	}
}
