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
	
	private static void varDecl(int level) {
		TerminalNode decl = new TerminalNode("VarDecl", "VarDecl", level);
		TerminalNode type = new TerminalNode("type", cst.get(currIndex).getObjectValue(), level+1);
		TerminalNode id = new TerminalNode("id", cst.get(currIndex+1).getObjectValue(), level+1);
		ast.add(decl);
		ast.add(type);
		ast.add(id);
	}
	
	private static void assignment(int level) {
		TerminalNode assignment = new TerminalNode("AssignmentStmt", "AssignmentStmt", level);
		currIndex++;
		level++;
		TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
		currIndex++;
		parseExpression(level);
		
	}
	
	private static void parseExpression(int level) {
		String type = cst.get(currIndex).getObjectType();
		currIndex++;
		switch (type) {
		case "IntExpression":
			TerminalNode newInt = new TerminalNode("digit", cst.get(currIndex).getObjectValue(), level);
			break;
		case "STRING_EXPR":
			TerminalNode newString = new TerminalNode("stringExpr", cst.get(currIndex).getObjectValue(), level);
			break;
		case "BOOLEXPR":
			parseBoolExpr(level + 1);
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
