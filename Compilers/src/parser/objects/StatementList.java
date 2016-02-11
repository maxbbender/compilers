package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class StatementList {
	private static Statement statement;
	public StatementList(){
		statement = new Statement();
	}
	
	public static boolean validateStatementList(ArrayList<Token> tokens, int currIndex) {
		if (statement.validateStatement(tokens, currIndex)) { 
			
		}
	}
}
