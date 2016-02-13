package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class Expr {
	private static IntExpr intExpr;
	private static StringExpr stringExpr;
	private static BooleanExpr booleanExpr;
	private static Id id;
	private static int postIndex;
	
	public Expr() {
		intExpr = new IntExpr();
		stringExpr = new StringExpr();
		booleanExpr = new BooleanExpr();
		id = new Id();
	}
	
	public static boolean validateExpr(ArrayList<Token> tokens, int currIndex){
		if (intExpr.validateIntExpr(tokens, currIndex)) {
			postIndex = intExpr.getPostIndex();
			return true;
		} else if (stringExpr.validateStringExpr(tokens, currIndex)) {
			postIndex = stringExpr.getPostIndex();
			return true;
		} else if (booleanExpr.validate(tokens, currIndex)) {
			postIndex = booleanExpr.getPostIndex();
			return true;
		} else if (id.validate(tokens, currIndex)) {
			postIndex = id.getPostIndex();
			return true;
		} else {
			return false; //ERROR ON EXPR
		}
		
	}
	public static IntExpr getIntExpr() {
		return intExpr;
	}

	public static void setIntExpr(IntExpr intExpr) {
		Expr.intExpr = intExpr;
	}

	public static StringExpr getStringExpr() {
		return stringExpr;
	}

	public static void setStringExpr(StringExpr stringExpr) {
		Expr.stringExpr = stringExpr;
	}

	public static BooleanExpr getBooleanExpr() {
		return booleanExpr;
	}

	public static void setBooleanExpr(BooleanExpr booleanExpr) {
		Expr.booleanExpr = booleanExpr;
	}

	public static Id getId() {
		return id;
	}

	public static void setId(Id id) {
		Expr.id = id;
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Expr.postIndex = postIndex;
	}

	
}
