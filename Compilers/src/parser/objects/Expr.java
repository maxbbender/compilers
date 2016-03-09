package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;
import parser.TerminalNode;
import lexer.Token;

public class Expr {
	private static IntExpr intExpr;
	private static StringExpr stringExpr;
	private static BooleanExpr booleanExpr;
	private static Id id;
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Expr() {
	}
	
	public static boolean validateExpr(ArrayList<Token> tokens, int currIndex){
		ParserMain.list.addNode("EXPRESSION", "EXPR");
		ParserMain.list.inc();
		int level = ParserMain.list.getInc();
		int baseIndex = ParserMain.list.getSize();
		intExpr = new IntExpr();
		stringExpr = new StringExpr();
		booleanExpr = new BooleanExpr();
		id = new Id();
		if (intExpr.validateIntExpr(tokens, currIndex)) {
			postIndex = intExpr.getPostIndex();
			log.info("INT EXPR");
			ParserMain.list.setInc(level);
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex,ParserMain.list.getSize());
		}
		
		if (stringExpr.validateStringExpr(tokens, currIndex)) {
			postIndex = stringExpr.getPostIndex();
			log.info("STRING EXPR");
			ParserMain.list.setInc(level);
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex,ParserMain.list.getSize());
		}
		
		if (booleanExpr.validate(tokens, currIndex)) {
			log.info("BOOLEAN EXPR");
			postIndex = booleanExpr.getPostIndex();
			ParserMain.list.setInc(level);
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex,ParserMain.list.getSize());
		}
		
		if (id.validate(tokens, currIndex)) {
			log.info("ID");
			postIndex = id.getPostIndex();
			ParserMain.list.setInc(level);
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

	public static Logger getLog() {
		return log;
	}

	
}
