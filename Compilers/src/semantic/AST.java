package semantic;

import java.util.ArrayList;
import java.util.Iterator;

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
		parseStatementList(level);
		
		
	}
	
	private static void parseStatementList(int level) {
		if (currIndex < cst.size() - 1) {
			parseStatement(level);
		}
		if (currIndex < cst.size() - 1) {
			parseStatementList(level);
		}
	}
	
	
	private static void parseStatement(int level) {
		currIndex++; // @ VarDecl|WhileStmt|IfStmt|AssignmentStmt|PrintStmt|Block
		String type = cst.get(currIndex).getObjectValue();
		//System.out.println("parseStatement type: " + type);
		switch (type) {
		case "varDecl":
			varDecl(level); // @ StatementList
			break;
		case "assignment":
			assignment(level); // @ StatementList
			break;
		case "while_Statement":
			whileStatement(level); // @ StatementList
			break;
		case "ifStatement": 
			ifStatement(level); // @ StatementList
			break;
		case "print": 
			printStatement(level); // @ StatementList
			break;
			
		}
	}
	
	private static void ifStatement(int level) {
		TerminalNode ifStmt = new TerminalNode("IfStmt", "IfStmt", level);
		ast.add(ifStmt);
		level++; // Under IfStmt
		currIndex++; // @ BoolExpr
		parseBoolExpr(level); // @ Block
		parseBlock(level); // @ StatementList
	}
	
	private static void varDecl(int level) {
		TerminalNode decl = new TerminalNode("VarDecl", "VarDecl", level);
		currIndex++; // @ Type
		level++; // Under VarDecl
		TerminalNode type = new TerminalNode("type", cst.get(currIndex).getObjectValue(), level);
		currIndex++; // @ ID
		TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
		currIndex++; // @ StatementList
		ast.add(decl);
		ast.add(type);
		ast.add(id);
	}
	
	private static void assignment(int level) {
		TerminalNode assignment = new TerminalNode("AssignmentStmt", "AssignmentStmt", level);
		ast.add(assignment);
		currIndex++; // @id
		level++; // Under Assignment
		TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
		ast.add(id);
		currIndex++; // @ Expression
		parseExpression(level); // @ StatementList
		
	}
	
	private static void printStatement(int level) {
		TerminalNode printStatement = new TerminalNode("PrintStmt", "PrintStmt", level);
		ast.add(printStatement);
		currIndex++; // @ Expression
		level++; // Under PrintStmt
		parseExpression(level); // @ StatementList
	}
	
	private static void whileStatement(int level) {
		TerminalNode whileStmt = new TerminalNode("WhileStmt", "WhileStmt", level);
		ast.add(whileStmt);
		currIndex++; // @ Boolean Expression
		level++; // inc level
		parseBoolExpr(level); // @ Block
		parseBlock(level); // @ StatementList
	}
	
	private static void parseBlock(int level) {
		TerminalNode block = new TerminalNode("Block", "Block", level);
		ast.add(block);
		currIndex++; // @ Statement List
		parseStatementList(level + 1);
	}
	
	/**
	 * Need to be @ Expression
	 * @param level
	 */
	private static void parseExpression(int level) {
		currIndex++; // At IntExpr|StringExpr|BoolExpr|ID
		String type = cst.get(currIndex).getObjectType();
		//System.out.println("parseExpression type: " + type);
		switch (type) {
		case "IntExpression":
			intExpr(level); // @ Element after Expression
			break;
		case "STRING_EXPR":
			TerminalNode newString = new TerminalNode("stringExpr", cst.get(currIndex).getObjectValue(), level);
			ast.add(newString);
			currIndex++; // @ Charlist. Now we iterate through to pass CharList/Chars
			while (cst.get(currIndex).getObjectType() == "CHARLIST" || cst.get(currIndex).getObjectType() == "CHAR") {
				currIndex++;
			} // After this we are outside of the Char/CharList
			break;
		case "BOOLEXPR":
			parseBoolExpr(level); // @ Block
			break;
		case "ID":
			TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
			ast.add(id);
			currIndex++; // At the element after ID
			break;
		}
	}
	
	/* Start @ IntExpression */
	private static void intExpr(int level) {
		currIndex++; // @ Digit
		TerminalNode newInt = new TerminalNode("digit", cst.get(currIndex).getObjectValue(), level);
		TerminalNode newIntExpr = new TerminalNode("IntExpr", "IntExpr", level);
		if (cst.get(currIndex + 1).getObjectType() == "INTOP") { // Are we looking @ (digit intop Expr)
			newInt.incLevel(); //Set digit to be under the IntopExpr
			ast.add(newIntExpr);
			ast.add(newInt);
			currIndex = currIndex + 2; // @ Expression
			parseExpression(level+1);
		} else { // Are we looking at just a digit
			ast.add(newInt);
			currIndex++; // @ next Element after digit
		}
	}
	
	private static void parseBoolop(int level) {
		TerminalNode boolop = new TerminalNode("boolop", cst.get(currIndex).getObjectValue() , level);
		ast.add(boolop);
		currIndex++; // At Second Expression of BoolExpr
	}
	
	private static void parseBoolExpr(int level) {
		TerminalNode boolExpr = new TerminalNode("BoolExpr", "BoolExpr", level);
		ast.add(boolExpr);
		level++; // We are in BoolExpr
		currIndex++; // At Expression|BoolVal
		String type = cst.get(currIndex).getObjectType();
		switch (type) {
		/* This would be the (Expr boolop Expr) */
		case "EXPRESSION":
			parseExpression(level); //returns @ Boolop
			parseBoolop(level); //returns @ Expression
			parseExpression(level);
			break;
		/* This would be for (boolval) */
		case "BoolVal":
			TerminalNode newBool = new TerminalNode("BoolVal", cst.get(currIndex).getObjectValue(), level);
			currIndex++; //At Block
			ast.add(newBool);
		}
	}
	
	public static void printList() {
		for (Iterator<TerminalNode> it = ast.iterator(); it.hasNext();) {
			System.out.println(it.next().getTerminalNode());
		}
	}

	public static ArrayList<TerminalNode> getAst() {
		return ast;
	}

	public static void setAst(ArrayList<TerminalNode> ast) {
		AST.ast = ast;
	}

	public static ArrayList<TerminalNode> getCst() {
		return cst;
	}

	public static void setCst(ArrayList<TerminalNode> cst) {
		AST.cst = cst;
	}

	public static int getCurrIndex() {
		return currIndex;
	}

	public static void setCurrIndex(int currIndex) {
		AST.currIndex = currIndex;
	}
}
