package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;
import parser.ParserMain;

public class IfStatement {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static BooleanExpr booleanExpr;
	private static Block block;
	
	public IfStatement() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("STATEMENT_IF", "ifStatement");
		ParserMain.list.inc();
		booleanExpr = new BooleanExpr();
		block = new Block();
		if (tokens.get(currIndex).getTokenType() == "ifKeyword") {
			if (booleanExpr.validate(tokens, currIndex + 1)) {
				ParserMain.list.setInc(level);
				if (block.validateBlock(tokens, booleanExpr.getPostIndex())) {
					ParserMain.list.setInc(level);
					postIndex = block.getPostIndex();
					log.info("IF STATEMENT");
					return true;
				} else {
					log.severe("ERROR LINE " + tokens.get(booleanExpr.getPostIndex()).getTokenLineNum() + ": Invalid Block near " + tokens.get(booleanExpr.getPostIndex()).getTokenValue());
					return false; //ERROR ON BLOCK
				}
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex + 1).getTokenLineNum() + ": Invalid Boolean Expression near " + tokens.get(currIndex + 1).getTokenValue());
				return false; // ERROR ON BOOLEANEXPR
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid If Keyword near " + tokens.get(currIndex).getTokenValue());
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
