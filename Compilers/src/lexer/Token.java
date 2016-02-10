package lexer;

public class Token {
	private String tokenType;
	private String tokenValue;
	private static final String[] keywordStrings = {"print","while","if"};
	private static final String[] typeString = {"int","string","boolean"};
	
	public Token() {
		tokenType = null;
		tokenValue = null;
	}
	
	public Token(int matchGroup, String newTokenValue) {
		tokenValue = newTokenValue; //Assign the Token Value to Object
		genTokenType(matchGroup); //Generates the Token Type
	}
	
	private void genTokenType(int matchGroup) {
		switch (matchGroup) {
		case 1: 
			tokenType = "ifKeyword";
			break;
		case 2: 
			tokenType = "whileKeyword";
			break;
		case 3:
			tokenType = "printKeyword";
			break;
		case 4: case 5: case 6: 
			tokenType = "type";
		case 7: case 8:
			tokenType = "boolVal";
			break;
		case 9: 
			tokenType = "openBracket";
			break;
		case 10:
			tokenType = "closeBracket";
			break;
		case 11: 
			tokenType = "openParen";
			break;
		case 12: 
			tokenType = "closeParen";
			break;
		case 13: case 14: 
			tokenType = "boolop";
			break;
		case 15:
			tokenType = "assignment";
			break;
		case 16: 
			tokenType = "char";
			break;
		case 17:
			tokenType = "string";
			break;
		case 18:
			tokenType = "intop";
			break;
		case 19:
			tokenType = "digit";
			break;
		case 20:
			tokenType = "space";
			break;
		}
	}

	public String getFullToken() {
		return tokenType + ": " + tokenValue;
	}
	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public static String[] getKeywordstrings() {
		return keywordStrings;
	}

	public static String[] getTypestring() {
		return typeString;
	}
	
	
	
}
