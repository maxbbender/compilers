package parser.objects;
/* ( Expr boolop Expr ) */

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.text.html.HTMLEditorKit.Parser;

import lexer.Token;
import parser.ParserMain;
public class BooleanExpr {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static Expr expr; 
	private static Boolop boolop;
	public BooleanExpr() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("BOOLEXPR", "boolExpr");
		ParserMain.list.inc();
		expr = new Expr();
		boolop = new Boolop();
		if(tokens.get(currIndex).getTokenType() == "openParen") {
			currIndex++; // Plus because of open paren token
			if (expr.validateExpr(tokens, currIndex)){
				ParserMain.list.setInc(level);
				if (boolop.validate(tokens, expr.getPostIndex())) {
					if (expr.validateExpr(tokens, boolop.getPostIndex())){
						ParserMain.list.setInc(level);
						if (tokens.get(expr.getPostIndex()).getTokenType() == "closeParen"){
							log.info("BOOLEAN EXPR");
							postIndex = expr.getPostIndex() + 1;
							return true; // TRUE ON BOOLEAN EXPR
						} else {
							//log.severe("ERROR LINE " + tokens.get(expr.getPostIndex()).getTokenLineNum() + ": Invalid Close Paren near " + tokens.get(expr.getPostIndex()).getTokenValue());
							return false; // FALSE ON CLOSE PAREN
						}
					} else {
						//log.severe("ERROR LINE " + tokens.get(boolop.getPostIndex()).getTokenLineNum() + ": Invalid Second Expression near " + tokens.get(boolop.getPostIndex()).getTokenValue());
						return false; //FALSE ON SECOND EXPRESSION VALIDATION
					}
				} else {
					//log.severe("ERROR LINE " + tokens.get(expr.getPostIndex()).getTokenLineNum() + ": Invalid Boolop near " + tokens.get(expr.getPostIndex()).getTokenValue());
					return false; //FALSE ON BOOLOP VALIDATION
				}
			} else {
				//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid First Expression near " + tokens.get(currIndex).getTokenValue());
				return false; // FALSE ON FIRST EXPRESSION
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Open Paren near " + tokens.get(currIndex).getTokenValue());
			return false; // FALSE ON OPEN PAREN
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		BooleanExpr.postIndex = postIndex;
	}

	public static Expr getExpr() {
		return expr;
	}

	public static void setExpr(Expr expr) {
		BooleanExpr.expr = expr;
	}

	public static Boolop getBoolop() {
		return boolop;
	}

	public static void setBoolop(Boolop boolop) {
		BooleanExpr.boolop = boolop;
	}
}
