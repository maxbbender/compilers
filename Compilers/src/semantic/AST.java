package semantic;

import java.util.ArrayList;

import parser.ParserTerminalList;
import parser.TerminalNode;

public class AST {
	
	private static ArrayList<TerminalNode> ast;
	private static ArrayList<TerminalNode> cst;
	private static int currIndex;
	
	public AST(ParserTerminalList list) {
		currIndex = 0;
		ast = new ArrayList();
		cst = list.getList();
		
		
	}
	
	public static void run() {
		int level = 0;
		TerminalNode baseBlock = new TerminalNode("Block", "Block", level);
		level++;
		ast.add(baseBlock);
		currIndex = 2; // This will put us at the first StatementList
		
		
	}
	
	private static void parseStatementList(int level) {
		parseStatement(level);
	}
	
	private static void parseStatement(int level) {
		currIndex++;
		String type = cst.get(currIndex).getObjectValue();
		switch (type) {
		case "VARDECL":
			varDecl(level);
			break;
		case "STATEMENT_ASSIGNMENT":
			assignment(level);
			break;
		}
	}
	private static void varDecl(int level) {
		TerminalNode decl = new TerminalNode("VarDecl", "VarDecl", level);
		currIndex++;
		level++;
		TerminalNode type = new TerminalNode("type", cst.get(currIndex).getObjectValue(), level);
		currIndex++;
		TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
		currIndex++; //do we need?
		ast.add(decl);
		ast.add(type);
		ast.add(id);
	}
	
	private static void assignment(int level) {
		TerminalNode assignment = new TerminalNode("AssignmentStmt", "AssignmentStmt", level);
		currIndex++;
		level++;
		TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
		currIndex = currIndex + 2;
		parseExpression(level);
		
	}
	
	private static void printStatement(int level) {
		TerminalNode printStatement = new TerminalNode("PrintStmt", "PrintStmt", level);
		currIndex++;
		level++;
		parseExpression(level);
	}
	
	private static void whileStatement(int level) {
		TerminalNode whileStmt = new TerminalNode("WhileStmt", "WhileStmt", level);
		currIndex++;
		level++;
		parseBoolExpr(level);
		parseBlock
	}
	private static void parseExpression(int level) {
		String type = cst.get(currIndex).getObjectType();
		currIndex++;
		switch (type) {
		case "IntExpression":
			intExpr(level);
			break;
		case "STRING_EXPR":
			TerminalNode newString = new TerminalNode("stringExpr", cst.get(currIndex).getObjectValue(), level);
			ast.add(newString);
			break;
		case "BOOLEXPR":
			parseBoolExpr(level + 1);
			break;
		case "ID":
			TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
			ast.add(id);
			break;
		}
	}
	
	private static void intExpr(int level) {
		TerminalNode newInt = new TerminalNode("digit", cst.get(currIndex).getObjectValue(), level);
		TerminalNode newIntExpr = new TerminalNode("IntExpr", "IntExpr", level);
		if (cst.get(currIndex + 1).getObjectType() == "INTOP") {
			newInt.incLevel();
			currIndex = currIndex + 2;
			ast.add(newIntExpr);
			ast.add(newInt);
			parseExpression(level + 1);
		} else {
			ast.add(newInt);
		}
	}
	
	private static void parseBoolExpr(int level) { 
		String type = cst.get(currIndex).getObjectType();
		switch (type) {
		/* This would be the (Expr boolop Expr) */
		case "EXPRESSION":
			break;
		/* This would be for (boolval) */
		case "BoolVal":
			TerminalNode newBool = new TerminalNode("BoolVal", cst.get(currIndex).getObjectValue(), level);
			ast.add(newBool);
		}
	}
}
