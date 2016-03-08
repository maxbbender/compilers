package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lexer.Token;
import parser.ParserMain;
public class StringExpr {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public StringExpr() {
		
	}
	
	public static boolean validateStringExpr(ArrayList<Token> tokens, int currIndex) {
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("STRING_EXPR", tokens.get(currIndex).getTokenValue());
		ParserMain.list.inc();
		charList = new CharList();
		Pattern p = Pattern.compile("\"([^\"\\d]*)\"");
		Matcher m = p.matcher(tokens.get(currIndex).getTokenValue());
		
		if (m.find()) {
			if (charList.validate(m.group(1), -1)){
				postIndex = currIndex + 1;
				ParserMain.list.setInc(level);
				return true;
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid StringExpr near " + tokens.get(currIndex).getTokenValue());
				return false; //ERROR ON CHARLIST
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Regex Error near " + tokens.get(currIndex).getTokenValue());
			return false; //ERROR ON FINDING REGEX 
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		StringExpr.postIndex = postIndex;
	}

	public static CharList getCharList() {
		return charList;
	}

	public static void setCharList(CharList charList) {
		StringExpr.charList = charList;
	}

	private static CharList charList;
	
	
}
