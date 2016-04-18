package semantic;

import java.util.ArrayList;
import java.util.Iterator;

import parser.TerminalNode;
import semantic.helpers.Scope;
import semantic.helpers.BoolExpr;
import semantic.helpers.IntExpr;

public class SymbolTable {
	private Scope parent;
	private Scope currScope;
	private ArrayList<TerminalNode> astList;
	private int index;
	private boolean errors; 
	private int currLevel;
	
	public SymbolTable(AST ast) {
		errors = false;
		boolean toContinue = true;
		currLevel = 0;
		parent = new Scope();
		currScope = parent;
		astList = ast.getAst();
		index = 1;
		String id;
		String id1;
		String id2;
		int scopeNum = 2;
		
		/* Try to be at next Stmt/Expr/ect. */
		while(index < astList.size()) {
			if (toContinue) {
				if (index > 2) {
 					if (astList.get(index).getObjectLevel() <= currScope.getLevelStart()) {
						if (currScope.hasParent()) {
							currScope = currScope.getParent();
						}
					}
				}
				
				TerminalNode node = astList.get(index);
				String nodeType = node.getObjectValue();
				/* Where are we */
				switch(nodeType) {
				case "Block": // Go into new scope
					Scope tempScope = new Scope(node.getObjectLevel(), scopeNum);
					currScope.addChild(tempScope);
					tempScope.setParent(currScope);
					currScope = tempScope;
					index++; // @ next stmt
					scopeNum++;
					break;
				case "VarDecl":
					getCurrScope().addDecl(astList.get(index+1).getObjectValue(), astList.get(index+2).getObjectValue());
					index = index + 3; // @ next stmt
					break;
				case "AssignmentStmt":
					index++; // @ id
					id = astList.get(index).getObjectValue();
					index++; // @ digit|IntExpr|stringExpr|BoolExpr|id
					String type = astList.get(index).getObjectType();
					if (type != "IntExpr") { 
						if (checkDeclaration(id)) { // Has the variable been declared
							if (type == "id") {
								if (!checkIdsType(id, astList.get(index).getObjectValue(), "AssignmentStmt")) {
									System.out.println("ERROR: Type Mismatch on ID: " + id + " and ID " + astList.get(index).getObjectValue());
									toContinue = false;
									errors = true;
								}
							} else if (!checkType(type, id)) { // Is the variable type declaration the same
								System.out.println("ERROR: Type Mismatch on ID: " + id + " for type " + type);
								toContinue = false;
								errors = true;
							} 
							
							if (type == "BoolExpr") {
								int backLevel = astList.get(index - 1).getObjectLevel();
								while (astList.get(index).getObjectType() != "Block" 
										&& astList.get(index).getObjectType() != "AssignmentStmt" 
										&& astList.get(index).getObjectType() != "VarDecl" 
										&& astList.get(index).getObjectType() != "IfStmt" 
										&& astList.get(index).getObjectType() != "WhileStmt") {
//									System.out.println(astList.get(index).getObjectType());
									index++;
									if (index >= astList.size()) {
										break;
									}
								}
							} else {
								index++;
							}
						} else {
							toContinue = false;
							System.out.println("ERROR: Variable " + id + " is not declared");
							errors = true;
						}
					} else {
						if (checkDeclaration(id)) { // returns true on declared
							if (typeIntExpr(id)) { // returns false on no errors
								toContinue = false;
								System.out.println("ERROR: Type Mismatch on ID: " + id + " for type " + type);
							}
						} else {
							toContinue = false;
							System.out.println("ERROR: Variable " + id + " is not declared");
							errors = true;
						}
					}
					
					break;
//					switch (type) {
//					case "digit": 
//						getCurrScope().addAssignment(id, Integer.valueOf(astList.get(index).getObjectValue()));
//						index++; // @ next stmt
//						break;
//					case "IntExpr":
//						index++; // @ digit1
//						getCurrScope().addAssignment(id, parseIntExpr()); // @ next stmt
//						// TODO if parseIntExpr == null throw type error
//						break;
//					case "stringExpr":
//						getCurrScope().addAssignment(id, astList.get(index).getObjectValue());
//						index++; // @ next stmt
//						break;
//					case "BoolExpr":
//						getCurrScope().addAssignment(id, parseBoolExpr()); // @ next stmt
//						break;
//					case "id": 
//						getCurrScope().addAssignment(id, astList.get(index).getObjectValue());
//						index++;
//					}
//				} else {
//					System.out.println("ERORR: Type Mismatch on \"" + id + "\" for type \"" + type + "\"");
//				}
//				
//			} else {
//				System.out.println("ERROR: Variable " + id + " is not declared");
//			}
				case "IfStmt":
					index = index + 2; // @id
					id1 = astList.get(index).getObjectValue();
					id2 = astList.get(index + 2).getObjectValue();
					index = index + 3;
					if (!checkIdsType(id1, id2, nodeType)) {
						toContinue = false;
						errors = true;
					}
					break;
				case "WhileStmt": 
					index = index + 2; // @id
					id1 = astList.get(index).getObjectValue();
					id2 = astList.get(index + 2).getObjectValue();
					index = index + 3;
					if (!checkIdsType(id1, id2, nodeType)) {
						toContinue = false;
						errors = true;
					}
					break;
				case "PrintStmt": 
					index++; // @ IntExpr/BoolExpr/StringExpr
					if (astList.get(index).getObjectType() == "IntExpr") {
						while (astList.get(index).getObjectType() == "digit" || astList.get(index).getObjectType() == "IntExpr" || astList.get(index).getObjectType() == "id") {
							
							if (astList.get(index).getObjectType() == "id") {
								if (!checkType("int", astList.get(index).getObjectValue())){ 
									toContinue = false;
									errors = true;
								}
							}
							index++;
							
							if (index >= astList.size()) {
								break;
							}
						}
					} else {
						index++;
					}
					break;	
				default:
					toContinue = false;
					System.out.println("ERROR: Unknown Next Statement. See below");
					System.out.println("Previous Node: " + astList.get(index-1).getObjectType());
					System.out.println("Current Node: " + nodeType);
					System.out.println("Next Node: " + astList.get(index + 1).getObjectType());
					errors = true;
				//case "
				}
			} else {
				break;
			}
			
		}
			
	}
	
	private boolean checkIdsType(String id1, String id2, String nodeType){
		if (checkDeclaration(id1)) {
			if (checkDeclaration(id2)) {
				if (getType(id1) == getType(id2)) {
					return true;
				} else {
					System.out.println("Types do no match. Ids " + id1 + " and " + id2);
					return false;
				}
			} else {
				System.out.println("Second argument is not defined in " + nodeType);
				return false;
			}
		} else {
			System.out.println("First argument is not defined in " + nodeType);
			return false;
		}
	}
	
	public void printSymbolTable() {
		int scopeNum = 1;
		Scope temp = parent;
		do {
			if (!temp.isPrinted()) {
				System.out.println("Scope " + scopeNum);
				temp.print();
//				temp.printDeclerations();
//				System.out.println("Scope " + scopeNum + "('s) Assignments:");
//				temp.printAssignments();
				System.out.println("*****************");
				temp.setPrinted(true);
				scopeNum++;
			}
			if (temp.hasChildren()) {
				temp = temp.getCurrChild();
			} else if (temp.hasParent()) {
				temp = temp.getParent();
			} else {
				temp = null;
			}
		} while (temp != null); 
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
	
	private boolean typeIntExpr(String id) {
		if (checkType("int", id)) {
			index++; // @ digit | id
			while (astList.get(index).getObjectType() == "digit" || astList.get(index).getObjectType() == "IntExpr" || astList.get(index).getObjectType() == "id") {
				if (astList.get(index).getObjectType() == "id") {
					if (!checkIdsType(id, astList.get(index).getObjectValue(), "IntExpr")){ 
						errors = true;
					}
				}
				index++;
			}
		} else {
			errors = true;
		}
		return errors;
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
	
	private String getType(String id) {
		Scope temp = currScope;
		if (temp.hasId(id)) {
			String type = temp.getType(id);
			if (type != null) {
				return type;
			}
		} else {
			do {
				temp = temp.getParent();
				if (temp != null) {
					if (temp.hasId(id)) {
						String type = temp.getType(id);
						if (type != null) {
							return type;
						}
					}
				}
			} while (temp != null);
		}
		return null;
	}
	
	private Boolean checkType(String type, String id) {
		Scope temp = currScope;
		if (temp.checkType(type, id)) {
			return true;
		} else {
			do {
				temp = temp.getParent();
				if (temp != null) {
					if (temp.checkType(type, id)) {
						return true;
					}
				}
			} while (temp != null);
		}
		return false; 
	}
	
//	private Boolean checkType(String type, String id) {
//		Scope temp = currScope;
//		if (temp.checkType(type, id)) {
//			return true;
//		} else {
//			do {
//				temp = temp.getParent();
//				if (temp != null) {
//					if (temp.checkType(type, id)) {
//						return true;
//					}
//				}
//			} while (temp != null);
//		}
//		return false; 
//	}

	private Boolean checkDeclaration(String id) {
		Scope temp = currScope;
		if (temp.checkDeclaration(id)) {
			return true;
		} else {
			do {
				temp = temp.getParent();
				if (temp != null) {
					if (temp.checkDeclaration(id)) {
						return true;
					}
				}
			} while (temp != null);
		}
		return false;
		
	}
	
	private Scope getCurrScope() {
		return currScope;
	}
	
	public boolean hasErrors() {
		return errors;
	}
}

