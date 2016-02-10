package lexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	private static StringBuilder fullRegex;
	private static StringBuilder keywordRegex;
	
	/* VALID REGEX */
	private final static String id = "([a-z])";
	private final static String alpha = "(\\w)";
	private final static String digit= "(\\d)";
	private final static String strings = "(\"[^\"]*\")";
	private final static String equality = "(==)";
	private final static String notEquality = "(!=)";
	private final static String space = "(\\S)";
	private final static String assignment = "(=)";
	private final static String ifKeyword = "(if)";
	private final static String whileKeyword = "(while)";
	private final static String printKeyword = "(print)";
	private final static String intKeyword = "(int)";
	private final static String stringKeyword = "(string)";
	private final static String booleanKeyword = "(boolean)";
	private final static String booleanTrue = "(true)";
	private final static String booleanFalse = "(false)";
	private final static String openParen = "(\\()";
	private final static String closeParen = "(\\))";
	private final static String openBracket = "(\\{)";
	private final static String closeBracket = "(\\})";
	private final static String plus = "(\\+)";
	
	/* PARAM ARRAYS */
	private final static String[] parenBracketArray = {openBracket, closeBracket, openParen, closeParen};
	private final static String[] keywordArray = {ifKeyword, whileKeyword, printKeyword, intKeyword, booleanKeyword, booleanTrue, booleanFalse};
	private final static String[] literalArray = {equality, notEquality, assignment, id, strings, plus, digit, space};
	
	/* TOKEN ARRAY */
	
	public RegexHelper(String checkString) {
		fullRegex = new StringBuilder();
		keywordRegex = new StringBuilder();
		int numberOfMatches = buildRegex();
		
		
		parseInput("if(x==y)b 1 + c{while(a != d){print \"Doody\"}}", numberOfMatches);
	}
	
	
	public static void parseInput(String input, int numberOfMatches) {
		Token newToken;
		ArrayList<Token> tokens = new ArrayList<Token>();
		int counter = 1;
		
		if (checkForErrors(input)) {
			System.out.println("Number of Matches: " + numberOfMatches);
			System.out.println(fullRegex.toString());
			
			/* Create Java Pattern and Matchers */
			Pattern pattern = Pattern.compile(fullRegex.toString());
			Matcher matcher = pattern.matcher(input);
			while (matcher.find()) { //Find next match
				while (counter <= numberOfMatches) { //Make sure we check all the possible matches
					if (matcher.group(counter) != null) { //If the group doesn't match our regex then don't add it
						tokens.add(newToken = new Token(counter, matcher.group(counter))); //Add a matching regex to the token stream
					}
					counter++; //Increment counter to check next group against string
				}
				counter = 1;
			}
			
			for  (Iterator<Token> it = tokens.iterator(); it.hasNext();) {
				System.out.println(it.next().getFullToken());
			}
			
			
			String removedString = matcher.replaceAll("");
			System.out.println(input);
			System.out.println(removedString);
		}
		
		
				
	}
	private int buildRegex() {
		int numberOfMatches = 0;
		
		/**
		 * Append all keywords to regex statement
		 */
		for (String keyword : keywordArray){
			keywordRegex.append(keyword + "|");
			numberOfMatches++;
		}
		fullRegex.append(keywordRegex); // Add the keyword Regex to the fullRegex statement
		
		/**
		 * Append Brackets/Parens to Regex Statement
		 */
		for (String parenBrack : parenBracketArray) {
			fullRegex.append(parenBrack + "|");
			numberOfMatches++;
		}
		
		/**
		 * Append Literals to Regex Statement
		 */
		for(String literal : literalArray) {
			fullRegex.append(literal + "|");
			numberOfMatches++;
		}
		return numberOfMatches;
	}
	
	public static boolean checkForErrors(String input) {
		if (!checkQuotes(input)) {
			if(!checkID(removeStrings(input))) {
				System.out.println("No Errors");
				return true; // There are no errors
			} else {
				System.out.println("ID Errors");
				return false; // Errors
			}
		} else {
			System.out.println("Quotes Errors");
			return false; // Errors
		}
	}
	
	public static boolean checkQuotes(String input) {
		Pattern p = Pattern.compile("\"");
		Matcher m = p.matcher(input);
		int counter = 0;
		while (m.find()) {
			counter++;
		}
		if ( (counter & 1) == 0) {
			return false; //There are an even number of quotes (GOOD)
		} else {
			return true; //There are an odd number of quotes (BAD)
		}
	}
	
	public static String removeStrings(String input) {
		Pattern p = Pattern.compile(strings);
		Matcher m = p.matcher(input);
		return m.replaceAll("");
	}
	
	public static boolean checkID(String input) {
		Pattern p = Pattern.compile("([a-z]{2,})");
		Matcher m = p.matcher(input);
		while (m.find()) {
			if (!checkKeywords(m.group(1))) {
				return true; // Either ID is more than 1 character or there is an undefined keyword (BAD)
			}
		}
		return false; // There were no bad ID's (GOOD)
	}
	
	public static boolean checkKeywords(String input) {
		Pattern p = Pattern.compile(keywordRegex.toString());
		Matcher m = p.matcher(input);
		if (m.matches()){
			return true; // There are keywords
		} else {
			return false; // There are not keywords
		}
	}
}
