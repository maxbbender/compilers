package parser.objects;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lexer.Token;
public class Digit {
	private static int postIndex;
	public Digit() {
		
	}
	
	public static boolean validateDigit(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "digit") {
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

