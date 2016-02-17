package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;

public class Id {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static Char charVar;
	public Id() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		charVar = new Char();
		if (charVar.validate(tokens, currIndex)){
			postIndex = currIndex + 1;
			return true; //TRUE ON ID
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Id near " + tokens.get(currIndex).getTokenValue());
			return false; //FALSE ON ID
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Id.postIndex = postIndex;
	}

	public static Char getCharVar() {
		return charVar;
	}

	public static void setCharVar(Char charVar) {
		Id.charVar = charVar;
	}
}
