package lexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	/* TOKENS */
	private static ArrayList<Token> tokens;
	
	/* REGEX */
	private static StringBuilder fullRegex;
	private static StringBuilder keywordRegex;
	
	/* VALID REGEX */
	private final static String id = "([a-z])";
	private final static String alpha = "(\\w)";
	private final static String digit= "(\\d)";
	private final static String strings = "(\"[^\"]*\")";
	private final static String equality = "(==)";
	private final static String notEquality = "(!=)";
	private final static String space = "(\\s)";
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
	private final static String[] keywordArray = {ifKeyword, whileKeyword, printKeyword, intKeyword, stringKeyword, booleanKeyword, booleanTrue, booleanFalse};
	private final static String[] literalArray = {equality, notEquality, assignment, id, strings, plus, digit, space};
	
	/* TOKEN ARRAY */
	
	public RegexHelper(String checkString) {
		fullRegex = new StringBuilder();    // Initialize the fullRegex StringBuilder
		keywordRegex = new StringBuilder(); // Initialize the keywordRegex StringBuilder
		tokens = new ArrayList<Token>();
		int numberOfMatches = buildRegex(); // Build the full regex statements from the defined ones above
		parseInput(checkString, numberOfMatches); //Parse the input.
	}
	
	
	public static void parseInput(String input, int numberOfMatches) {
		Token newToken;
		int counter = 1;
		boolean found = false; 
		
		if (checkForErrors(input)) {
			System.out.println("Number of Matches: " + numberOfMatches);
			System.out.println(fullRegex.toString());
			
			/* Create Java Pattern and Matchers */
			Pattern pattern = Pattern.compile(fullRegex.toString());
			Matcher matcher = pattern.matcher(input);
			while (matcher.find()) { //Find next match
				while (counter <= numberOfMatches && !found) { //Make sure we check all the possible matches
					if (matcher.group(counter) != null) { //If the group doesn't match our regex then don't add it
						tokens.add(newToken = new Token(counter, matcher.group(counter))); //Add a matching regex to the token stream
						found = true; 
					}
					counter++; //Increment counter to check next group against string
				}
				counter = 1;
				found = false; 
			}
			
			for  (Iterator<Token> it = tokens.iterator(); it.hasNext();) {
				System.out.println(it.next().getFullToken());
			}
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
		if (!checkUnknowns(input)) {
			if (!checkQuotes(input)) {
				if(!checkID(removeStrings(input))) {
					return true; // There are no errors
				} else {
					return false; // Errors on ID's
				}
			} else {
				return false; // Errors on Quotes
			}
		} else {
			return false; // Errors on Unknowns.
		}
		
	}
	
	public static boolean checkUnknowns(String input) {
		Pattern p = Pattern.compile(fullRegex.toString());
		Matcher m = p.matcher(input);
		String removedString = m.replaceAll(""); //At this point all known characters have been found
		
		if (removedString.length() > 0) {
			System.out.println("Error(UndefinedChars): " + removedString);
			return true; // There are unknowns
		} else {
			return false; // There are not unknowns
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
			System.out.println("Error(Quotes): There are an odd number of quotes");
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
				System.out.println("Error(ID): " + m.group(1) + " is an unknown/invalid ID");
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


	public static ArrayList<Token> getTokens() {
		return tokens;
	}


	public static void setTokens(ArrayList<Token> tokens) {
		RegexHelper.tokens = tokens;
	}


	public static StringBuilder getFullRegex() {
		return fullRegex;
	}


	public static void setFullRegex(StringBuilder fullRegex) {
		RegexHelper.fullRegex = fullRegex;
	}


	public static StringBuilder getKeywordRegex() {
		return keywordRegex;
	}


	public static void setKeywordRegex(StringBuilder keywordRegex) {
		RegexHelper.keywordRegex = keywordRegex;
	}


	public static String getId() {
		return id;
	}


	public static String getAlpha() {
		return alpha;
	}


	public static String getDigit() {
		return digit;
	}


	public static String getStrings() {
		return strings;
	}


	public static String getEquality() {
		return equality;
	}


	public static String getNotequality() {
		return notEquality;
	}


	public static String getSpace() {
		return space;
	}


	public static String getAssignment() {
		return assignment;
	}


	public static String getIfkeyword() {
		return ifKeyword;
	}


	public static String getWhilekeyword() {
		return whileKeyword;
	}


	public static String getPrintkeyword() {
		return printKeyword;
	}


	public static String getIntkeyword() {
		return intKeyword;
	}


	public static String getStringkeyword() {
		return stringKeyword;
	}


	public static String getBooleankeyword() {
		return booleanKeyword;
	}


	public static String getBooleantrue() {
		return booleanTrue;
	}


	public static String getBooleanfalse() {
		return booleanFalse;
	}


	public static String getOpenparen() {
		return openParen;
	}


	public static String getCloseparen() {
		return closeParen;
	}


	public static String getOpenbracket() {
		return openBracket;
	}


	public static String getClosebracket() {
		return closeBracket;
	}


	public static String getPlus() {
		return plus;
	}


	public static String[] getParenbracketarray() {
		return parenBracketArray;
	}


	public static String[] getKeywordarray() {
		return keywordArray;
	}


	public static String[] getLiteralarray() {
		return literalArray;
	}
}
