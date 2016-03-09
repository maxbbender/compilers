package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lexer.Token;
import parser.ParserMain;

import java.util.logging.Logger;

public class Char {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Char() {
		
	}
	
	public static boolean validate(String[] input, int currIndex) {
		//ParserMain.list.addNode("CHAR", input[currIndex]);
		Pattern p = Pattern.compile("([a-z])");
		Matcher m = p.matcher(input[currIndex]);
		if (m.matches()) {
			log.info("CHAR");
			postIndex = currIndex + 1;
			return true; //It is a valid Char
		} else {
			return false; //Not a valid Char
		}
				
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		//ParserMain.list.addNode("CHAR", tokens.get(currIndex).getTokenValue());
		Pattern p = Pattern.compile("([a-z])");
		Matcher m = p.matcher(tokens.get(currIndex).getTokenValue());
		if (m.matches()) {
			postIndex = currIndex + 1;
			return true; //It is a valid Char
		} else {
			return false; //Not a valid Char
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Char.postIndex = postIndex;
	}

}
