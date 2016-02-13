package parser.objects;

import java.util.ArrayList;

public class CharList {
	private static int postIndex;
	private static Char charVar;
	private static CharList charList;
	private static Space space;
	public CharList() {
		charVar = new Char();
		charList = new CharList();
		space = new Space();
	}
	
	public static boolean validate(String input, int currIndex) {
		if (currIndex == -1) {
			currIndex = 0;
		}
		
		String[] result = input.split("");

		if(validate(result, currIndex)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean validate(String[] input, int currIndex) {
		if (charVar.validate(input, currIndex)) {
			if (charList.validate(input, charVar.getPostIndex())) {
				return true; //TRUE ON CHAR|CHARLIST
			} else if (space.validate(input, currIndex)) {
				if (charList.validate(input, space.getPostIndex())) {
					return true; //TRUE ON SPACE
				} else {
					return false; // FALSE ON SPACE|CHARLIST
				}
			} else {
				return false; // FALSE ON INVALID CHARLIST
			}
		} else {
			return false; // INVALIDE CHARLIST
		}
	}
}
