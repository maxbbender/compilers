package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;
import parser.ParserMain;

public class VarDecl {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static Type type;
	private static Id id;
	
	public VarDecl() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		int level = ParserMain.list.getInc();
		ParserMain.list.addNode("VARDECL", "varDecl");
		ParserMain.list.inc();
		type = new Type();
		id = new Id();
		if (type.validate(tokens, currIndex)) {
			currIndex = type.getPostIndex();
			if (id.validate(tokens, currIndex)) {
				postIndex = id.getPostIndex();
				return true;
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Id near " + tokens.get(currIndex).getTokenValue());
				return false; // ERROR ON ID
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Type near " + tokens.get(currIndex).getTokenValue());
			return false; // ERROR ON TYPE
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		VarDecl.postIndex = postIndex;
	}

	public static Type getType() {
		return type;
	}

	public static void setType(Type type) {
		VarDecl.type = type;
	}

	public static Id getId() {
		return id;
	}

	public static void setId(Id id) {
		VarDecl.id = id;
	}
}
