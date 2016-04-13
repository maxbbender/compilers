package semantic;

import java.util.ArrayList;
import java.util.Iterator;

import parser.TerminalNode;
import semantic.helpers.Scope;
import semantic.helpers.BoolExpr;
import semantic.helpers.IntExpr;

public class SymbolTable {
	private static ArrayList<Scope> scope;
	private static ArrayList<TerminalNode> astList;
	private static int index;
	private static int currScope;
	private static int currLevel;
	public SymbolTable(AST ast) {
		currScope = -1;
		currLevel = 0;
		Scope tempScope;
		scope = new ArrayList();
		astList = ast.getAst();
		index = 0;
		
		/* Try to be at next Stmt/Expr/ect. */
		while(index < astList.size()) {
			// TODO DECRIMINT currSCOPE if the level is < currScope.getStartLevel
			TerminalNode node = astList.get(index);
			String nodeType = node.getObjectValue();
			/* Where are we */
			switch(nodeType) {
			case "Block":
				currScope++; // Increment the current scope we are in. Starts @ 0
				scope.add(tempScope = new Scope(node.getObjectLevel()));
				index++; // @ next stmt
				break;
			case "VarDecl":
				getCurrScope().addDecl(astList.get(index+1).getObjectValue(), astList.get(index+2).getObjectValue());
				index = index + 3; // @ next stmt
				break;
			case "AssignmentStmt":
				index++; // @ id
				String id = astList.get(index).getObjectValue();
				index++; // @ digit|IntExpr|stringExpr|BoolExpr
				String type = astList.get(index).getObjectType();
			
				if (checkDeclaration(id)) { // Has the variable been declared
					if (checkType(id, type)) { // Is the variable type declaration the same
						switch (type) {
						case "digit": 
							getCurrScope().addAssignment(id, Integer.valueOf(astList.get(index).getObjectValue()));
							index++; // @ next stmt
							break;
						case "IntExpr":
							index++; // @ digit1
							getCurrScope().addAssignment(id, parseIntExpr()); // @ next stmt
							// TODO if parseIntExpr == null throw type error
							break;
						case "stringExpr":
							getCurrScope().addAssignment(id, astList.get(index).getObjectValue());
							index++; // @ next stmt
							break;
						case "BoolExpr":
							getCurrScope().addAssignment(id, parseBoolExpr()); // @ next stmt
							break;
						case "id": 
							getCurrScope().addAssignment(id, astList.get(index).getObjectValue());
							index++;
						}
					} else {
						System.out.println("ERORR: Type Mismatch on \"" + id + "\" for type \"" + type + "\"");
					}
					
				} else {
					System.out.println("ERROR: Variable " + id + " is not declared");
				}
				break; 
			}
		}
			
	}
	public void printSymbolTable() {
		int scopeNum = 0;
		for (Scope temp : scope) {
			System.out.println("Scope " + scopeNum + "('s) Declerations:");
			temp.printDeclerations();
			System.out.println("Scope " + scopeNum + "('s) Assignments:");
			temp.printAssignments();
			System.out.println("---------------------");
			scopeNum++;
		}
	}
	private BoolExpr parseBoolExpr() { // @ 
		int startLevel = astList.get(index).getObjectLevel();
		int currLevel = startLevel;
		BoolExpr temp = new BoolExpr();
		while(astList.get(index).getObjectLevel() >= startLevel) {
			
			
			if (astList.get(index).getObjectType() == "BoolExpr" && astList.get(index + 1).getObjectType() != "BoolVal") {
				currLevel++;
				temp.addExpr("(");
				index++; // @ digit| TODO
			} else if (astList.get(index).getObjectType() == "digit") {
				temp.addExpr(astList.get(index).getObjectValue());
				index++;
			} else if (astList.get(index).getObjectType() == "boolop") {
				temp.addExpr(astList.get(index).getObjectValue());
				index++; // @ BoolExpr | id
			} else if (astList.get(index).getObjectType() == "IntExpr") {
				index++; // @ digit1
				IntExpr temp1 = parseIntExpr(); // @ boolop | next stmt
				temp.addExprs(temp1.forBoolExpr());
			} else if (astList.get(index).getObjectType() == "stringExpr") {
				temp.addExpr(astList.get(index).getObjectValue());
				index++; // @ boolop | next stmt
			} else if (astList.get(index).getObjectType() == "BoolExpr" && astList.get(index + 1).getObjectType() == "BoolVal") {
				index++;
				temp.addExpr(astList.get(index).getObjectValue());
				index++; // @ boolop | next stmt
			} else {
				temp = null; // Type Error
			}
			if (index >= astList.size()) {
				if (temp.getExpr().get(temp.getExpr().size()-1) != ")"){
					temp.addExpr(")");
				}
				break;
			}
			
			if (astList.get(index).getObjectLevel() < currLevel) {
				temp.addExpr(")");
				currLevel--;
			}
		}
		return temp;
	}
	
	private IntExpr parseIntExpr() { // @ digit1
		int startLevel = astList.get(index).getObjectLevel();
		IntExpr temp = new IntExpr();
		while (astList.get(index).getObjectLevel() >= startLevel) { // This will end @ next Stmt
			temp.addInt(Integer.valueOf(astList.get(index).getObjectValue()));
			index++; // @ digit2|id|IntExpr
			if (astList.get(index).getObjectType() == "digit") { // We can break here as no more recursive Expressions
				temp.addInt(Integer.valueOf(astList.get(index).getObjectValue()));
				index++; // @ next stmt
				break;
			} else if (astList.get(index).getObjectType() == "id") { // We can break here as no more recursive Expressions
				temp.setId(astList.get(index).getObjectValue());
				index++; // @ next stmt
				break;
			} else if (astList.get(index).getObjectType() == "IntExpr") {
				index++; // @ digit1
			} else {
				temp = null;
				System.out.println("ERROR"); // Error on Type. Can only be digit|id|IntExpr
			}
		}
		return temp;
		
	}
	private Boolean checkType(String type, String id) {
		/* Check current scope for type */
		if (getCurrScope().checkType(id, type)) {
			return true;
		}
		
		/* Check previous scopes for type */
		int tempS = currScope - 1;
		while (tempS >= 0) {
			if (scope.get(tempS).checkType(id, type)) {
				return true;
			}
			tempS--;
		}
		return false;
	}
	private Boolean checkDeclaration(String id) {
		/* Check current scope for declaration */
		if (getCurrScope().checkDeclaration(id)) {
			return true;
		}
		/* Check previous scopes for declaration */
		int tempS = currScope - 1;
		while (tempS >= 0) {
			if(scope.get(tempS).checkDeclaration(id)) {
				return true;
			}
			tempS--;
		}
		return false;
	}
	
	private Scope getCurrScope() {
		return scope.get(currScope);
	}
}

