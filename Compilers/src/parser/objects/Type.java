package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;
import parser.ParserMain;

public class Type {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Type() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		ParserMain.list.addNode("TYPE", tokens.get(currIndex).getTokenValue());
		if (tokens.get(currIndex).getTokenType() == "type") {
			postIndex = currIndex + 1;
			return true;
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid type near " + tokens.get(currIndex).getTokenValue());
			return false; //ERROR ON TYPE
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Type.postIndex = postIndex;
	}
}
