package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserTerminalList;

import lexer.Token;

public class Expr {
	private static IntExpr intExpr;
	private static StringExpr stringExpr;
	private static BooleanExpr booleanExpr;
	private static Id id;
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static ParserTerminalList list = new ParserTerminalList();
	
	public Expr(ParserTerminalList newList) {
		list = newList;
	}
	
	public static boolean validateExpr(ArrayList<Token> tokens, int currIndex){
		intExpr = new IntExpr(list);
		stringExpr = new StringExpr(list);
		booleanExpr = new BooleanExpr(list);
		id = new Id(list);
		if (intExpr.validateIntExpr(tokens, currIndex)) {
			postIndex = intExpr.getPostIndex();
			log.info("INT EXPR");
			return true;
		} else if (stringExpr.validateStringExpr(tokens, currIndex)) {
			postIndex = stringExpr.getPostIndex();
			log.info("STRING EXPR");
			return true;
		} else if (booleanExpr.validate(tokens, currIndex)) {
			log.info("BOOLEAN EXPR");
			postIndex = booleanExpr.getPostIndex();
			return true;
		} else if (id.validate(tokens, currIndex)) {
			log.info("ID");
			postIndex = id.getPostIndex();
			return true;
		} else {
			log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Expression near " + tokens.get(currIndex).getTokenValue());
			return false; //ERROR ON EXPR
		}
		
	}
	public static IntExpr getIntExpr() {
		return intExpr;
	}

	public static void setIntExpr(IntExpr intExpr) {
		Expr.intExpr = intExpr;
	}

	public static StringExpr getStringExpr() {
		return stringExpr;
	}

	public static void setStringExpr(StringExpr stringExpr) {
		Expr.stringExpr = stringExpr;
	}

	public static BooleanExpr getBooleanExpr() {
		return booleanExpr;
	}

	public static void setBooleanExpr(BooleanExpr booleanExpr) {
		Expr.booleanExpr = booleanExpr;
	}

	public static Id getId() {
		return id;
	}

	public static void setId(Id id) {
		Expr.id = id;
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Expr.postIndex = postIndex;
	}

	public static ParserTerminalList getList() {
		return list;
	}

	public static void setList(ParserTerminalList list) {
		Expr.list = list;
	}

	public static Logger getLog() {
		return log;
	}

	
}
