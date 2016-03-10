package parser.objects;

import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;

import lexer.Token;

public class Statement {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static PrintStatement printStatement;
	private static AssignmentStatement assignmentStatement;
	private static VarDecl varDecl;
	private static WhileStatement whileStatement;
	private static IfStatement ifStatement;
	private static Block block;
	public Statement() {
	}
	
	public static boolean validateStatement(ArrayList<Token> tokens, int currIndex){
		int level = ParserMain.list.getInc();
		int baseIndex = ParserMain.list.getSize();
		ParserMain.list.addNode("STATEMENT", "statement");
		ParserMain.list.inc();
		printStatement = new PrintStatement();
		assignmentStatement = new AssignmentStatement();
		varDecl = new VarDecl();
		whileStatement = new WhileStatement();
		ifStatement = new IfStatement();
		block = new Block();
		if (printStatement.validatePrintStatement(tokens, currIndex)) {
			ParserMain.list.setInc(level);
			postIndex = printStatement.getPostIndex();
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex, ParserMain.list.getSize());
		} 
		
		if (assignmentStatement.validate(tokens, currIndex)) {
			ParserMain.list.setInc(level);
			postIndex = assignmentStatement.getPostIndex();
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex, ParserMain.list.getSize());
		}
		
		if (varDecl.validate(tokens, currIndex)) {
			ParserMain.list.setInc(level);
			postIndex = varDecl.getPostIndex();
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex, ParserMain.list.getSize());
		}
		
		if (whileStatement.validate(tokens, currIndex)) {
			ParserMain.list.setInc(level);
			postIndex = whileStatement.getPostIndex();
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex, ParserMain.list.getSize());
		}
		
		if (ifStatement.validate(tokens, currIndex)) {
			ParserMain.list.setInc(level);
			postIndex = ifStatement.getPostIndex();
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex, ParserMain.list.getSize());
		}
		
		if (block.validateBlock(tokens, currIndex)) {
			ParserMain.list.setInc(level);
			postIndex = block.getPostIndex();
			return true;
		} else {
			ParserMain.list.setInc(level);
			ParserMain.list.removeRange(baseIndex, ParserMain.list.getSize());
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid Statement near " + tokens.get(currIndex).getTokenValue());
			return false; // ERROR ON STATEMENT
		}
	}

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Statement.postIndex = postIndex;
	}

	public static PrintStatement getPrintStatement() {
		return printStatement;
	}

	public static void setPrintStatement(PrintStatement printStatement) {
		Statement.printStatement = printStatement;
	}

	public static AssignmentStatement getAssignmentStatement() {
		return assignmentStatement;
	}

	public static void setAssignmentStatement(AssignmentStatement assignmentStatement) {
		Statement.assignmentStatement = assignmentStatement;
	}

	public static VarDecl getVarDecl() {
		return varDecl;
	}

	public static void setVarDecl(VarDecl varDecl) {
		Statement.varDecl = varDecl;
	}

	public static WhileStatement getWhileStatement() {
		return whileStatement;
	}

	public static void setWhileStatement(WhileStatement whileStatement) {
		Statement.whileStatement = whileStatement;
	}

	public static IfStatement getIfStatement() {
		return ifStatement;
	}

	public static void setIfStatement(IfStatement ifStatement) {
		Statement.ifStatement = ifStatement;
	}

	public static Block getBlock() {
		return block;
	}

	public static void setBlock(Block block) {
		Statement.block = block;
	}
}
