package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class IfStatement {
	private static int postIndex;
	private static BooleanExpr booleanExpr;
	private static Block block;
	
	public IfStatement() {
		booleanExpr = new BooleanExpr();
		block = new Block();
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "ifKeyword") {
			if (booleanExpr.validate(tokens, currIndex + 1)) {
				if (block.validateBlock(tokens, booleanExpr.getPostIndex())) {
					postIndex = block.getPostIndex();
					return true;
				} else {
					return false; //ERROR ON block
				}
			} else {
				return false; // ERROR ON BOOLEANEXPR
			}
		} else {
			return false; // ERROR ON IFKEYWORD
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		IfStatement.postIndex = postIndex;
	}

	public static BooleanExpr getBooleanExpr() {
		return booleanExpr;
	}

	public static void setBooleanExpr(BooleanExpr booleanExpr) {
		IfStatement.booleanExpr = booleanExpr;
	}

	public static Block getBlock() {
		return block;
	}

	public static void setBlock(Block block) {
		IfStatement.block = block;
	}
}
