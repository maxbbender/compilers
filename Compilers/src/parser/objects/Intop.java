package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserTerminalList;

import lexer.Token;
public class Intop {
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static ParserTerminalList list = new ParserTerminalList();
	public Intop(ParserTerminalList newList) {
		list = newList;
	}
	
	public static boolean validateIntop(ArrayList<Token> tokens, int currIndex){
		if (tokens.get(currIndex).getTokenType() == "intop") {
			list.addNode("INTOP", tokens.get(currIndex).getTokenValue(), 1);
			postIndex = currIndex + 1;
			log.info("INTOP");
			return true;
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Intop near " + tokens.get(currIndex).getTokenValue());
			return false; 
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}

	public static ParserTerminalList getList() {
		return list;
	}

	public static void setList(ParserTerminalList list) {
		Intop.list = list;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setPostIndex(int postIndex) {
		Intop.postIndex = postIndex;
	}
}
