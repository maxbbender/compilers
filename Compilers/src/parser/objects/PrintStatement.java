package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;

import lexer.Token;

public class PrintStatement {
	private static int postIndex;
	private static Expr expr;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public PrintStatement() {
		
	}

	public static boolean validatePrintStatement(ArrayList<Token> tokens, int currIndex) {
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("STATEMENT_PRINT", "print");
		ParserMain.list.inc();
		expr = new Expr();
		if (tokens.get(currIndex).getTokenType() == "printKeyword") {
			currIndex++;
			if (tokens.get(currIndex).getTokenType() == "openParen") {
				currIndex++;
				if (expr.validateExpr(tokens, currIndex)) {
					currIndex = expr.getPostIndex();
					if (tokens.get(currIndex).getTokenType() == "closeParen") {
						ParserMain.list.setInc(level);
						postIndex = expr.getPostIndex() + 1;
						log.info("PRINT STATEMENT");
						return true;
					} else {
						log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Close Paren near " + tokens.get(currIndex).getTokenValue());
						return false; //ERROR ON CLOSEPAREN
					}
				} else {
					log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Expression near " + tokens.get(currIndex).getTokenValue());
					return false; //ERROR ON EXPR
				}
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Open Paren near " + tokens.get(currIndex).getTokenValue());
				return false; //ERROR ON OPENPAREN
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Print Keyword near " + tokens.get(currIndex).getTokenValue());
			return false; //ERROR ON PRINT KEYWORD
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		PrintStatement.postIndex = postIndex;
	}

	public static Expr getExpr() {
		return expr;
	}

	public static void setExpr(Expr expr) {
		PrintStatement.expr = expr;
	}
}
