package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class Id {
	private static int postIndex;
	private static Char charVar;
	public Id() {
		charVar = new Char();
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		if (charVar.validate(tokens, currIndex)){
			postIndex = currIndex++;
			return true; //TRUE ON ID
		} else {
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
