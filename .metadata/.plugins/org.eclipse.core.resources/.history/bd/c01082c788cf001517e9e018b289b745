package lexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	private static StringBuilder fullRegex;
	private static StringBuilder keywordRegex;
	
	/* PARAM ARRAYS */
	private static String[] parenBracketArray;
	private static String[] keywordArray;
	private static String[] literalArray;
	
	/* VALID REGEX */
	private final static String id = "([a-z])";
	private final static String alpha = "(\\w)";
	private final static String digits = "(\\d+)";
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
	
	/* INVALID */
	private final static String badString = "(^\"[^\"]*$)|(^[^\"]*\"$)";
	//private final static String bad
	
	public RegexHelper(String checkString) {
		parenBracketArray = new String[4];
		keywordArray = new String[8];
		literalArray = new String[6];
		fullRegex = new StringBuilder();
		keywordRegex = new StringBuilder();
		 int numberOfMatches = 0;
		/**
		 * Define a keyword Array values
		 */
		keywordArray[0] = ifKeyword;
		keywordArray[1] = whileKeyword;
		keywordArray[2] = printKeyword;
		keywordArray[3] = intKeyword;
		keywordArray[4] = stringKeyword;
		keywordArray[5] = booleanKeyword;
		keywordArray[6] = booleanTrue;
		keywordArray[7] = booleanFalse;
		
		/**
		 * Define the literal Array values
		 */
		
		literalArray[0] = equality;
		literalArray[1] = notEquality;
		literalArray[2] = assignment;
		literalArray[3] = id;
		literalArray[4] = strings;
		literalArray[5] = plus;
		
		/**
		 * Define Paren/Bracket Array values
		 */
		parenBracketArray[0] = openBracket;
		parenBracketArray[1] = closeBracket;
		parenBracketArray[2] = openParen;
		parenBracketArray[3] = closeParen;
		
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
		
		
		
		
		parseInput("if(x==y)b + abc c{while(a != d){print \"Doody\"}}", numberOfMatches);
	}
	
	
	public static void parseInput(String input, int numberOfMatches) {
		ArrayList tokens = new ArrayList();
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
						tokens.add(matcher.group(counter)); //Add a matching regex to the token stream
					}
					counter++; //Increment counter to check next group against string
				}
				counter = 1;
			}
			
			for  (Iterator<String> it = tokens.iterator(); it.hasNext();) {
				System.out.println(it.next());
			}
			
			
			String removedString = matcher.replaceAll("");
			System.out.println(input);
			System.out.println(removedString);
		}
		
		
				
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
