package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;

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
		expr = new Expr();
		ParserMain.list.addTempNode("IntExpression", "intExpr");
		ParserMain.list.inc();
		
		if (digit.validateDigit(tokens, currIndex)) {
			intop = new Intop(digit.getList());
			currIndex = digit.getPostIndex();
			if (intop.validateIntop(tokens, currIndex)) {
				
				currIndex = intop.getPostIndex();
				if (expr.validateExpr(tokens, currIndex)) {
					list = expr.getList();
					postIndex = expr.getPostIndex();
					log.info("INTEXPR");
					return true; 
				} else {
					//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Expression near " + tokens.get(currIndex).getTokenValue());
					return false; //ERROR ON EXPR
				}
			} else {
				postIndex = digit.getPostIndex();
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
