package parser.objects;
import java.util.ArrayList;

import lexer.Token;
public class Intop {
	private static int postIndex;
	public Intop() {
		
	}
	
	public static boolean validateIntop(ArrayList<Token> tokens, int currIndex){
		if (tokens.get(currIndex).getTokenType() == "intop") {
			postIndex = currIndex + 1;
			return true;
		} else {
			return false; 
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}
}
