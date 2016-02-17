package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class PrintStatement {
	private static int postIndex;
	private static Expr expr;
	
	public PrintStatement() {
		expr = new Expr();
	}

	public static boolean validatePrintStatement(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "printKeyword") {
			currIndex++;
			if (tokens.get(currIndex).getTokenType() == "openParen") {
				currIndex++;
				if (expr.validateExpr(tokens, currIndex)) {
					if (tokens.get(expr.getPostIndex()).getTokenType() == "closeParen") {
						postIndex = expr.getPostIndex() + 1;
						return true;
					} else {
						return false; //ERROR ON CLOSEPAREN
					}
				} else {
					return false; //ERROR ON EXPR
				}
			} else {
				return false; //ERROR ON OPENPAREN
			}
		} else {
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
