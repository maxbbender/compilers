package lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	private final static String id = "([a-z])";
	private final static String alpha = "(\\w)";
	private final static String digits = "(\\d+)";
	private final static String strings = "(\"[^\"].*\")";
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
	
	
	public RegexHelper(String checkString) {
		
		Pattern ifPatternTogether = Pattern 
	}
	
	public static void checkRegex() {
		
	}
	
	public static boolean isIf(String testString){
		Pattern ifPattern = Pattern.compile("(^if)\\(?$");
		Matcher m = ifPattern.matcher(testString);
		return m.matches();
	}
	
	public static boolean isOpenParen(String testString) {
		
	}
	
	public static String[] parseInput(String input) {
		ArrayList tokens = new ArrayList();
		String keywords = 
				ifKeyword + "|" + 
				whileKeyword  + "|" + 
				printKeyword  + "|" + 
				stringKeyword + "|" + 
				booleanKeyword + "|" + 
				booleanTrue + "|" +
				booleanFalse;
		String literals = 
				strings + "|" + 
				id + "|" +
				equality + "|" +	
				notEquality  + "|" +
				assignment  + "|" +
				
		String regexParser = alpha + digits + strings + equality + notEquality + space + assignment;
		Pattern p = Pattern.compile(regexParser);
		Matcher m = p.matcher(input);
		
		for (m.matches()){
			
		}
	}
}
