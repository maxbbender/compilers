package lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	private final static String alpha = "([a-z])";
	private final static String digits = "(\\d+)";
	private final static String strings = "\".*\"";
	private final static String equality = "(==)";
	private final static String notEquality = "(!=)";
	private final static String space = ("(\\S)");
	private final static String assignment = ("(=)");
	
	
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
		String regexParser = alpha + digits + strings + equality + notEquality + space + assignment;
		Pattern p = Pattern.compile(regexParser);
		Matcher m = p.matcher(input);
		
		for (m.matches()){
			
		}
	}
}
