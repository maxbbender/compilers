package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class Type {
	private static int postIndex;
	
	public Type() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "type") {
			postIndex = currIndex++;
			return true;
		} else {
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
