package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;

public class WhileStatement {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static BooleanExpr booleanExpr;
	private static Block block;
	
	public WhileStatement() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		booleanExpr = new BooleanExpr();
		block = new Block();
		if (tokens.get(currIndex).getTokenType() == "whileKeyword") {
			currIndex++;
			if (booleanExpr.validate(tokens, currIndex)) {
				currIndex = booleanExpr.getPostIndex();
				if (block.validateBlock(tokens, currIndex)) {
					postIndex = block.getPostIndex();
					return true;
				} else {
					log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Block near " + tokens.get(currIndex).getTokenValue());
					return false; // ERROR Block
				}
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Boolean Expression near " + tokens.get(currIndex).getTokenValue());
				return false; //ERROR BOOLEAN EXPR
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid While Keyword near " + tokens.get(currIndex).getTokenValue());
			return false; //ERROR WHILE KEYWORD
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		WhileStatement.postIndex = postIndex;
	}

	public static BooleanExpr getBooleanExpr() {
		return booleanExpr;
	}

	public static void setBooleanExpr(BooleanExpr booleanExpr) {
		WhileStatement.booleanExpr = booleanExpr;
	}

	public static Block getBlock() {
		return block;
	}

	public static void setBlock(Block block) {
		WhileStatement.block = block;
	}
}
