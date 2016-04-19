package semantic;

import java.util.ArrayList;
import java.util.Iterator;

import parser.TerminalNode;
import semantic.helpers.Scope;
import semantic.helpers.BoolExpr;
import semantic.helpers.Decleration;
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
 						while (astList.get(index).getObjectLevel() <= currScope.getLevelStart()) {
 							if (currScope.hasParent()) {
 								currScope = currScope.getParent();
 							}
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
					index++; // @ type
					String tempType = astList.get(index).getObjectValue();
					index++; // @ id
					String tempId = astList.get(index).getObjectValue();
					
					if (checkCurrScopeDecl(tempId)) {
						Decleration tempDecl = currScope.getDeclarationId(tempId);
						System.out.println("ERROR: Trying to declare var " + tempId + " to be type " + tempType);
						if (tempDecl != null) {
							System.out.println("ERROR: var " + tempId + " has already been declared as \"" + currScope.getDeclarationId(tempId).getType() + " " + currScope.getDeclarationId(tempId).getId() + "\"" );
						} else {
							System.out.println("ERROR: var " + tempId + " has already been declared");
							System.out.println("ERROR: Check VARDECL. Our declaration was returned null and shouldn't be. w00ps");
						}
						toContinue = false;
						errors = true;
					} else {
						getCurrScope().addDecl(tempType, tempId);
					}
					index++; // @ next stmt
					break;
				case "AssignmentStmt":
					index++; // @ id
					id = astList.get(index).getObjectValue();
					index++; // @ digit|IntExpr|stringExpr|BoolExpr|id
					String type = astList.get(index).getObjectType();
					if (checkDeclaration(id)) {
						if (type != "IntExpr" && type != "BoolExpr") { 
							if (type == "id") {
								if (!checkIdsType(id, astList.get(index).getObjectValue(), "AssignmentStmt")) {
									System.out.println("ERROR: Type Mismatch on var " + id + " and var " + astList.get(index).getObjectValue());
									toContinue = false;
									errors = true;
								} else {
									currScope.init(id);
									index++;
								}
							} else if (!checkType(type, id)) { // Is the variable type declaration the same
								System.out.println("ERROR: Type Mismatch on var " + id + " for type " + type);
								toContinue = false;
								errors = true;
							} else {
								currScope.init(id);
								index++;
							}
						} else if (type == "BoolExpr") {
//							int backLevel = astList.get(index - 1).getObjectLevel();
							while (astList.get(index).getObjectType() != "Block" 
									&& astList.get(index).getObjectType() != "AssignmentStmt" 
									&& astList.get(index).getObjectType() != "VarDecl" 
									&& astList.get(index).getObjectType() != "IfStmt" 
									&& astList.get(index).getObjectType() != "WhileStmt") {
//								System.out.println(astList.get(index).getObjectType());
								index++;
								if (index >= astList.size()) {
									break;
								}
							}
							currScope.init(id);
						} else if (type == "IntExpr") { 
							if (typeIntExpr(id)) { // returns false on no errors
								toContinue = false;
								
							} else {
								currScope.init(id);
							}
						}
					} else {
						System.out.println("ERROR: var " + id + " is not declared");
						toContinue = false;
						errors = true;
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
					index++; // @ IntExpr/BoolExpr/StringExpr/id
					type = astList.get(index).getObjectType();		
					if (type == "id") {
						if (!checkDeclaration(astList.get(index).getObjectValue())) {
							toContinue = false;
							errors = true;
							System.out.println("ERROR: var " + astList.get(index).getObjectValue() + " is not declared");
						}
//						if (!isInitialized(astList.get(index).getObjectValue())) {
////							toContinue = false;
////							errors = true;
//							System.out.println("WARNING: var " + astList.get(index).getObjectValue() + " is not initialized");
//							
//						} 
						index++;
					} else if (astList.get(index).getObjectType() == "IntExpr") {
						while (astList.get(index).getObjectType() == "digit" || astList.get(index).getObjectType() == "IntExpr" || astList.get(index).getObjectType() == "id") {
							if (astList.get(index).getObjectType() == "id") {
								if (checkDeclaration(astList.get(index).getObjectValue())) {
									if (!checkType("int", astList.get(index).getObjectValue())){ 
										toContinue = false;
										errors = true;
										System.out.println("ERROR: var " + astList.get(index).getObjectValue() + "is not type int");
									}
									
								} else {
									toContinue = false;
									errors = true;
									System.out.println("ERROR: var " + astList.get(index).getObjectValue() + " is not declared");
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
					System.out.println("Previous Node: " + astList.get(index-1).getObjectType() + "(NodeNum: " + (index-1) + ")");
					System.out.println("Current Node: " + nodeType  + "(NodeNum: " + (index) + ")");
					if (index+1 < astList.size()) {
						System.out.println("Next Node: " + astList.get(index + 1).getObjectType()  + "(NodeNum: " + (index+1) + ")");
					} else {
						System.out.println("Next Node: END OF PROGRAM");
					}
					errors = true;
				//case "
				}
			} else {
				break;
			}
			
		}
			
	}
	
	private boolean isInitialized(String id) {
		Scope temp = currScope;
		if (temp.isInitialized(id)) {
			return true;
		} else { 
			do {
				temp = temp.getParent();
				if (temp != null) {
					if (temp.isInitialized(id)) {
						return true;
					}
				}
			} while (temp != null);
		}
		return false;
	}
	
	private boolean checkIdsType(String id1, String id2, String nodeType){
		if (checkDeclaration(id1)) {
			if (checkDeclaration(id2)) {
				if (getType(id1).equals(getType(id2))) {
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
				ArrayList<Decleration> tempDeclList = temp.getUnInitialized();
				if (tempDeclList.size() > 0) {
					for (Decleration tempDecl : tempDeclList) {
						System.out.println("WARNING: Uninitialized var " + tempDecl.getId());
					}
				}
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
				index++; // TODO@ digit| 
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
						System.out.println("ERROR: Type Mismatch on ID: " + id + " for type IntExpr");
						errors = true;
						break;
					} else {
						if (!isInitialized(astList.get(index).getObjectValue())) {
							System.out.println("ERROR: var " + astList.get(index).getObjectValue() + " is not initialized");
							errors = true;
							break;
						}
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
	private boolean checkCurrScopeDecl(String id) {
		if (currScope.checkDeclaration(id)) {
			return true;
		} else {
			return false;
		}
	}
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

