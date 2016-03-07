package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;

import lexer.Token;

public class Block {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static StatementList statementList;
	public Block() {
	}
	
	public static boolean validateBlock(ArrayList<Token> tokens, int currIndex) {
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("BLOCK", "block");
		ParserMain.list.inc();
		statementList = new StatementList();
		if (tokens.get(currIndex).getTokenType() == "openBracket") {
			currIndex++;
			if (statementList.validateStatementList(tokens, currIndex)) {
				ParserMain.list.setInc(level);
				if (tokens.get(statementList.getPostIndex()).getTokenType() == "closeBracket") {
					postIndex = statementList.getPostIndex() + 1;
					log.info("BLOCK");
					return true;
				} else {
					log.severe("ERROR LINE " + tokens.get(statementList.getPostIndex()).getTokenLineNum() + ": Invalid Close Bracket near " + tokens.get(statementList.getPostIndex()).getTokenValue());
					return false; // ERROR ON CLOSE BRACKET
				}
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Statement List near " + tokens.get(currIndex).getTokenValue());
				return false;
				// ERROR ON STATEMENT LIST
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Open Bracket near " + tokens.get(currIndex).getTokenValue());
			return false;
			//ERROR on OPENBRACKET
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Block.postIndex = postIndex;
	}

	public static StatementList getStatementList() {
		return statementList;
	}

	public static void setStatementList(StatementList statementList) {
		Block.statementList = statementList;
	}
}
