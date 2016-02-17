package parser.objects;
import java.util.ArrayList;

import lexer.Token;

public class Boolop {
	private static int postIndex;
	public Boolop() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "boolop") {
			postIndex = currIndex + 1;
			return true; // True on Boolop
		} else {
			return false; // FALSE ON BOOLOP
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Boolop.postIndex = postIndex;
	}
}
