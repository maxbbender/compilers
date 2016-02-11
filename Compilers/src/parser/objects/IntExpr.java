package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class IntExpr {
	private static int postIndex;
	private static Digit digit;
	private static Intop intop;
	private static Expr expr; 
	
	public IntExpr() {
		digit = new Digit();
		intop = new Intop();
		expr = new Expr();
	}
	
	public static boolean validateIntExpr(ArrayList<Token> tokens, int currIndex) {
		if (digit.validateDigit(tokens, currIndex)) {
			currIndex = digit.getPostIndex();
			if (intop.validateIntop(tokens, currIndex)) {
				currIndex = intop.getPostIndex();
				if (expr.validateExpr(tokens, currIndex)) {
					postIndex = expr.getPostIndex();
					return true; 
				} else {
					return false; //ERROR ON EXPR
				}
			} else {
				return false; //ERROR ON INTOP
			}
		} else {
			return false; //ERROR ON DIGIT
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}
}
