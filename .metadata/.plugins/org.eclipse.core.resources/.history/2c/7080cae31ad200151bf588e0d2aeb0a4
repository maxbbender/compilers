package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class VarDecl {
	private static int postIndex;
	private static Type type;
	private static Id id;
	
	public VarDecl() {
		type = new Type();
		id = new Id();
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		if (type.validate(tokens, currIndex)) {
			if (id.validate(tokens, type.getPostIndex())) {
				postIndex = id.getPostIndex();
				return true;
			} else {
				return false; // ERROR ON ID
			}
		} else {
			return false; // ERROR ON TYPE
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		VarDecl.postIndex = postIndex;
	}

	public static Type getType() {
		return type;
	}

	public static void setType(Type type) {
		VarDecl.type = type;
	}

	public static Id getId() {
		return id;
	}

	public static void setId(Id id) {
		VarDecl.id = id;
	}
}
