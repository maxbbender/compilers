package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserTerminalList;

import lexer.Token;

public class StatementList {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static Statement statement;
	private static StatementList statementList;
	private static ParserTerminalList list = new ParserTerminalList();
	public StatementList(ParserTerminalList newList){
		list = newList;
	}
	
	public static boolean validateStatementList(ArrayList<Token> tokens, int currIndex) {
		list.addNode("STATEMENT", "statement", 1);
		statement = new Statement(list);
		if (statement.validateStatement(tokens, currIndex)) {
			statement.getList().addNode("STATEMENT_LIST", "statementList", 1);
			statementList = new StatementList(statement.getList());
			currIndex = statement.getPostIndex();
			if (statementList.validateStatementList(tokens, currIndex)) {
				postIndex = statementList.getPostIndex();
				return true;
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid StatementList near " + tokens.get(currIndex).getTokenValue());
				return false; // ERROR ON STATEMENTLIST
			}
		} else if (tokens.get(currIndex).getTokenType() == "closeBracket"){
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
