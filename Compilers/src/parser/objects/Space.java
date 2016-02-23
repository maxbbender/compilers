package parser.objects;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Space {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Space() {
		
	}
	
	public static boolean validate(String[] input, int currIndex) {
		Pattern p = Pattern.compile("(\\s)");
		Matcher m = p.matcher(input[currIndex]);
		if (m.matches()) {
			postIndex = currIndex +1;
			log.info("SPACE");
			return true; // TRUE ON SPACE
		} else {
			return false; // FALSE ON SPACE
		}
				
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Space.postIndex = postIndex;
	}
}
