package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;
public class Intop {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Intop() {
		
	}
	
	public static boolean validateIntop(ArrayList<Token> tokens, int currIndex){
		if (tokens.get(currIndex).getTokenType() == "intop") {
			postIndex = currIndex + 1;
			return true;
		} else {
			log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Intop near " + tokens.get(currIndex).getTokenValue());
			return false; 
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}
}
