package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;

import lexer.Token;

public class StatementList {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static Statement statement;
	private static StatementList statementList;
	public StatementList(){
	}
	
	public static boolean validateStatementList(ArrayList<Token> tokens, int currIndex) {
		int baseIndex = ParserMain.list.getSize();
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("STATEMENT_LIST", "statementList");
		ParserMain.list.inc();
		statement = new Statement();
		if (statement.validateStatement(tokens, currIndex)) {
			ParserMain.list.setInc(level);
			statementList = new StatementList();
			currIndex = statement.getPostIndex();
			if (statementList.validateStatementList(tokens, currIndex)) {
				ParserMain.list.setInc(level);
				postIndex = statementList.getPostIndex();
				return true;
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid StatementList near " + tokens.get(currIndex).getTokenValue());
				return false; // ERROR ON STATEMENTLIST
			}
		} else if (tokens.get(currIndex).getTokenType() == "closeBracket"){
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex, ParserMain.list.getSize());
			postIndex = currIndex;
			return true;
		} else {
			log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Statement near " + tokens.get(currIndex).getTokenValue());
			return false; // ERROR ON STATEMENT
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		StatementList.postIndex = postIndex;
	}

	public static Statement getStatement() {
		return statement;
	}

	public static void setStatement(Statement statement) {
		StatementList.statement = statement;
	}

	public static StatementList getStatementList() {
		return statementList;
	}

	public static void setStatementList(StatementList statementList) {
		StatementList.statementList = statementList;
	}
}
