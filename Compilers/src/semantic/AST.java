package semantic;

import java.util.ArrayList;
import java.util.Iterator;

import parser.ParserTerminalList;
import parser.TerminalNode;

public class AST {
	
	private ArrayList<TerminalNode> ast;
	private ArrayList<TerminalNode> cst;
	private int currIndex;
	private ArrayList<Integer> blockLevelsCST;
	private ArrayList<Integer> blockLevelsAST;
	private int blockIndex;
	
	public AST(ParserTerminalList list) {
		blockIndex = 0;
		currIndex = 0;
		ast = new ArrayList();
		cst = list.getList();
		blockLevelsCST = new ArrayList();
		blockLevelsAST = new ArrayList();
		
	}
	
	public void run() {
		int level = 0;
		TerminalNode baseBlock = new TerminalNode("Block", "Block", level);
		blockLevelsCST.add(level);
		blockLevelsAST.add(level+1);
		level++;
		ast.add(baseBlock);
		currIndex = 2; // This will put us at the first StatementList
		parseStatementList(level);
		
		
	}
	
	private void parseStatementList(int level) {
		if (currIndex < cst.size() - 1) {
			parseStatement(level);
		}
		if (currIndex < cst.size() - 1) {
			if (cst.get(currIndex).getObjectLevel() < blockLevelsCST.get(blockIndex)) {
				if (blockIndex > 0) {
					blockIndex--;
				}
				parseStatementList(blockLevelsAST.get(blockIndex));
			} else {
				parseStatementList(level);
			}
			
		}
	}
	
	
	private void parseStatement(int level) {
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
		case "block":
			parseBlock(level, cst.get(currIndex).getObjectLevel()); // @ StatementList
		}
	}
	
	private void ifStatement(int level) {
		TerminalNode ifStmt = new TerminalNode("IfStmt", "IfStmt", level);
		ast.add(ifStmt);
		level++; // Under IfStmt
		currIndex++; // @ BoolExpr
		parseBoolExpr(level); // @ Block
		parseBlock(level, cst.get(currIndex).getObjectLevel()); // @ StatementList
	}
	
	private void varDecl(int level) {
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
	
	private void assignment(int level) {
		TerminalNode assignment = new TerminalNode("AssignmentStmt", "AssignmentStmt", level);
		ast.add(assignment);
		currIndex++; // @id
		level++; // Under Assignment
		TerminalNode id = new TerminalNode("id", cst.get(currIndex).getObjectValue(), level);
		ast.add(id);
		currIndex++; // @ Expression
		parseExpression(level); // @ StatementList
		
	}
	
	private void printStatement(int level) {
		TerminalNode printStatement = new TerminalNode("PrintStmt", "PrintStmt", level);
		ast.add(printStatement);
		currIndex++; // @ Expression
		level++; // Under PrintStmt
		parseExpression(level); // @ StatementList
	}
	
	private void whileStatement(int level) {
		TerminalNode whileStmt = new TerminalNode("WhileStmt", "WhileStmt", level);
		ast.add(whileStmt);
		currIndex++; // @ Boolean Expression
		level++; // inc level
		parseBoolExpr(level); // @ Block
		parseBlock(level, cst.get(currIndex).getObjectLevel()); // @ StatementList
	}
	
	private void parseBlock(int level, int cstLevel) {
		TerminalNode block = new TerminalNode("Block", "Block", level);
		blockLevelsAST.add(level+1);
		blockLevelsCST.add(cstLevel);
		blockIndex++;
		ast.add(block);
		currIndex++; // @ Statement List | 
		if (cst.get(currIndex).getObjectLevel() > cst.get(currIndex-1).getObjectLevel()) {
			parseStatementList(level + 1);
		} else {
			parseStatementList(level);
		}
		
	}
	
	/**
	 * Need to be @ Expression
	 * @param level
	 */
	private void parseExpression(int level) {
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
				
				if (currIndex >= cst.size()) {
					break;
				}
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
	private void intExpr(int level) {
		currIndex++; // @ Digit
		TerminalNode newInt = new TerminalNode("digit", cst.get(currIndex).getObjectValue(), level);
		TerminalNode newIntExpr = new TerminalNode("IntExpr", "IntExpr", level);
		if (currIndex + 1 < cst.size()) {
			if (cst.get(currIndex + 1).getObjectType() == "INTOP") { // Are we looking @ (digit intop Expr)
				newInt.incLevel(); //Set digit to be under the IntopExpr
				ast.add (newIntExpr);
				ast.add(newInt);
				currIndex = currIndex + 2; // @ Expression
				parseExpression(level+1);
			} else { // Are we looking at just a digit
				ast.add(newInt);
				currIndex++; // @ next Element after digit
			}
		} else {
			ast.add(newInt);
			currIndex++; // @ next Element after digit
		}
		
	}
	
	private void parseBoolop(int level) {
		TerminalNode boolop = new TerminalNode("boolop", cst.get(currIndex).getObjectValue() , level);
		ast.add(boolop);
		currIndex++; // At Second Expression of BoolExpr
	}
	
	private void parseBoolExpr(int level) {
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
	
	public void printList() {
		for (Iterator<TerminalNode> it = ast.iterator(); it.hasNext();) {
			System.out.println(it.next().getTerminalNode());
		}
	}

	public ArrayList<TerminalNode> getAst() {
		return ast;
	}

	public void setAst(ArrayList<TerminalNode> ast) {
		ast = ast;
	}

	public ArrayList<TerminalNode> getCst() {
		return cst;
	}

	public void setCst(ArrayList<TerminalNode> cst) {
		cst = cst;
	}

	public int getCurrIndex() {
		return currIndex;
	}

	public void setCurrIndex(int currIndex) {
		currIndex = currIndex;
	}
}
