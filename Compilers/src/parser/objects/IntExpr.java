package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;

public class IntExpr {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static Digit digit;
	private static Intop intop;
	private static Expr expr; 
	
	public IntExpr() {
		
	}
	
	public static boolean validateIntExpr(ArrayList<Token> tokens, int currIndex) {
		digit = new Digit();
		intop = new Intop();
		expr = new Expr();
		if (digit.validateDigit(tokens, currIndex)) {
			currIndex = digit.getPostIndex();
			if (intop.validateIntop(tokens, currIndex)) {
				currIndex = intop.getPostIndex();
				if (expr.validateExpr(tokens, currIndex)) {
					postIndex = expr.getPostIndex();
					return true; 
				} else {
					//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Expression near " + tokens.get(currIndex).getTokenValue());
					return false; //ERROR ON EXPR
				}
			} else {
				return true; //TRUE ON DIGIT
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Digit near " + tokens.get(currIndex).getTokenValue());
			return false; //ERROR ON DIGIT
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}
}
