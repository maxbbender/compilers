package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;

public class CharList {
	private static int postIndex;
	private static Char charVar;
	private static CharList charList;
	private static Space space;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public CharList() {
		
	}
	
	public static boolean validate(String input, int currIndex) {
		ParserMain.list.addNode("CHARLIST", input);
		ParserMain.list.inc();
		charVar = new Char();
		charList = new CharList();
		space = new Space();
		if (currIndex == -1) {
			currIndex = 1;
		}
		String[] result = null;
		if (input.length() > 0) {
			result = input.split("");
		} else {
			return true;
		}

		if(validate(result, currIndex)) {
			log.info("CHARLIST");
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean validate(String[] input, int currIndex) {
		int level = ParserMain.list.getInc();
		StringBuilder temp = new StringBuilder();
		ParserMain.list.addNode("CHARLIST", temp.toString());
		ParserMain.list.inc();
		
		if (currIndex < input.length) {
			if (charVar.validate(input, currIndex)) {
				if (charList.validate(input, charVar.getPostIndex())) {
					return true; //TRUE ON CHAR|CHARLIST
				} else {
					return false; // FALSE ON INVALID CHARLIST
				}
			} else if (space.validate(input, currIndex)) {
				if (charList.validate(input, space.getPostIndex())) {
					return true; //TRUE ON SPACE
				} else {
					return false; // FALSE ON SPACE|CHARLIST
				}
			} else {
				return false; // INVALIDE CHARLIST
			}
		} else {
			return true;
		}
		
	}
}
