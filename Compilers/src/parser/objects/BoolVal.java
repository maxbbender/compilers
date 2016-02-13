package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class BoolVal {
	private static int postIndex;
	
	public BoolVal() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "boolVal") {
			postIndex = currIndex + 1;
			return true;
		} else {
			return false; // ERROR ON 
		}
	}
}
