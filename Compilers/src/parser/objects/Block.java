package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class Block {
	private static StatementList statementList;
	public Block() {
		statementList = new StatementList();
	}
	
	public static boolean validateBlock(ArrayList<Token> tokens, int currIndex) {
		if (tokens.get(currIndex).getTokenType() == "openBracket") {
			currIndex++;
			if (statementList.validateStatementList(tokens, currIndex)) {
				
			}
		} else {
			//ERROR on OPENBRACKET
		}
	}
}
