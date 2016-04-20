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
//							while (astList.get(index).getObjectType() != "Block" 
//									&& astList.get(index).getObjectType() != "AssignmentStmt" 
//									&& astList.get(index).getObjectType() != "VarDecl" 
//									&& astList.get(index).getObjectType() != "IfStmt" 
//									&& astList.get(index).getObjectType() != "WhileStmt") {
////								System.out.println(astList.get(index).getObjectType());
//								index++;
//								if (index >= astList.size()) {
//									break;
//								}
//							}
							if (typeBoolExpr("AssignmentStmt")) {
								currScope.init(id);
							} else {
								toContinue = false;
								System.out.println("ERROR: Invalid BoolExpr for assignment for var " + id);
							}
							
						} else if (type == "IntExpr") { 
							if (!typeIntExpr(id)) { // returns false on no errors
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
					index++; // @ BoolExpr
					if (!typeBoolExpr("IfStmt")) {
						toContinue = false;
					}
//					id1 = astList.get(index).getObjectValue();
//					id2 = astList.get(index + 2).getObjectValue();
//					index = index + 3;
//					if (!checkIdsType(id1, id2, nodeType)) {
//						toContinue = false;
//						errors = true;
//					}
					break;
				case "WhileStmt": 
					index++; // @ BoolExpr
					if (!typeBoolExpr("WhileStmt")) {
						toContinue = false;
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
					} else if (type == "IntExpr") {
						if (!typeIntExpr(null)) {
							toContinue = false;
							errors = true;
							System.out.println("ERROR: Invalid IntExpr for PrintStmt");
						}
//						while (astList.get(index).getObjectType() == "digit" || astList.get(index).getObjectType() == "IntExpr" || astList.get(index).getObjectType() == "id") {
//							if (astList.get(index).getObjectType() == "id") {
//								if (checkDeclaration(astList.get(index).getObjectValue())) {
//									if (!checkType("int", astList.get(index).getObjectValue())){ 
//										toContinue = false;
//										errors = true;
//										System.out.println("ERROR: var " + astList.get(index).getObjectValue() + "is not type int");
//									}
//									
//								} else {
//									toContinue = false;
//									errors = true;
//									System.out.println("ERROR: var " + astList.get(index).getObjectValue() + " is not declared");
//								}
//							}
//							index++;
//							
//							if (index >= astList.size()) {
//								break;
//							}
//						}
					} else if (type == "BoolExpr") {
						if (!typeBoolExpr("PrintStmt")) {
							toContinue = false;
							errors = true;
							System.out.println("ERROR: Invalid BoolExpr in PrintStmt");
						}
					} else if (type == "stringExpr") {
						index++;
					} else {
						toContinue = false;
						errors = true;
						System.out.println("ERROR: Unknown argument type for Print Statement");
						System.out.println("ERROR: Arg Type: " + type);
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
	
	private boolean typeIntExpr(String id) {
		boolean justDigits = true;
		if (id != null && id != "null") {
			if (checkType("int", id)) {
				index++; // @ digit | id
				while (astList.get(index).getObjectType() == "digit" || astList.get(index).getObjectType() == "IntExpr" || astList.get(index).getObjectType() == "id") {
					if (astList.get(index).getObjectType() == "id") {
						if (!checkType("int", astList.get(index).getObjectValue())){ 
							System.out.println("ERROR: Type Mismatch on ID: " + astList.get(index).getObjectValue() + " for type IntExpr");
							errors = true;
							return false;
						} else {
							if (!isInitialized(astList.get(index).getObjectValue())) {
								System.out.println("WARNING: var " + astList.get(index).getObjectValue() + " is not initialized");
							} 
							index++;
							return true;
						}
					}
					index++;
				}
			} else {
				System.out.println("ERROR: var " + id + " is not type int");
				errors = true;
				return false;
			}
		} else {
			index++; // @ digit | id
			while (astList.get(index).getObjectType() == "digit" || astList.get(index).getObjectType() == "IntExpr" || astList.get(index).getObjectType() == "id") {
				if (astList.get(index).getObjectType() == "id") {
					if (!checkType("int", astList.get(index).getObjectValue())){ 
						System.out.println("ERROR: Type Mismatch on ID: " + astList.get(index).getObjectValue() + " type IntExpr");
						errors = true;
						return false;
					} else {
						if (!isInitialized(astList.get(index).getObjectValue())) {
							System.out.println("WARNING: var " + astList.get(index).getObjectValue() + " is not initialized");
						}
						index++;
						return true;
					}
				}
				index++;
			}
		}
		return true;
	}
	
	private boolean typeBoolExpr(String stmtType) {
		index++; // @ first expr
		TerminalNode node1 = astList.get(index);
		TerminalNode node2 = astList.get(index + 2);
		if (node1.getObjectType().equals("id")) { // id
			if (checkDeclaration(node1.getObjectValue())) {
				index = index + 2; // @ second expr
				if (node2.getObjectType().equals("id")) { // id id
					if (checkDeclaration(node2.getObjectValue())) {
						if (checkIdsType(node1.getObjectValue(), node2.getObjectValue(), stmtType)) {
							index++;
							return true;
						} else {
							errors = true;
							System.out.println("ERROR: var " + node1.getObjectValue() + " and var " + node2.getObjectValue() + " types do not match");
							return false;
						}
					} else {
						errors = true;
						System.out.println("ERROR: var " + node2.getObjectValue() + " is not declared");
						return false;
					}
				} else if (node2.getObjectType().equals("digit")) { // id digit
					if (checkType("int", node1.getObjectValue())) {
						index++;
						return true;
					} else {
						errors = true;
						System.out.println("ERROR: var " + node1.getObjectValue() + " does not match type int");
						return false;
					}
				} else if (node2.getObjectType().equals("stringExpr")) { // id string
					if (checkType("string", node1.getObjectValue())) { 
						index++;
						return true;
					} else {
						errors = true;
						System.out.println("ERROR: var " + node1.getObjectValue() + " does not match type string");
						return false;
					}
				} else if (node2.getObjectType().equals("IntExpr")) { // id IntExpr
					if (typeIntExpr(node1.getObjectValue())) {
						return true;
					} else {
						errors = true;
						System.out.println("ERROR: Invalid IntExpr in " + stmtType);
						return false;
					}
				} else if (node2.getObjectType().equals("BoolExpr")) { // id BoolExpr
//					index++;
					if (checkType("boolean", node1.getObjectValue())) {
						if (astList.get(index + 1).getObjectType().equals("BoolVal")) {
							index = index + 2; // @ next stmt
							return true;
						} else if (astList.get(index).getObjectType().equals("BoolExpr")) {
							if (typeBoolExpr(stmtType)) {
								return true;
							} else {
								errors = true;
								System.out.println("ERROR: Invalid BoolExpr in " + stmtType);
								return false;
							}
						} else {
							errors = true;
							System.out.println("ERROR: Expecting BoolExpr|BoolVal in stmt " + stmtType);
							return false;
						}
					} else {
						errors = true;
						System.out.println("ERROR: var " + node1.getObjectValue() + " does not match type boolean");
						return false;
					}
				} else {
					errors = true;
					System.out.println("ERROR: Unkown second argument type");
					System.out.println("ERROR: Arg2 Type/Value" + node2.getObjectType() + "/" + node2.getObjectValue());
					return false;
				}
			} else {
				errors = true;
				System.out.println("ERROR: var " + node1.getObjectValue() + " is not declared");
				return false;
			}
		} else if (node1.getObjectType().equals("digit")) { //digit
			if (node2.getObjectType().equals("id")) { // digit id
				if (checkDeclaration(node2.getObjectValue())) {
					if (checkType("int", node2.getObjectValue())) {
						index = index + 3;
						return true;
					} else {
						errors = true;
						System.out.println("ERROR: var " + node2.getObjectType() + " does not match type int");
						return false;
					}
				} else {
					errors = true;
					System.out.println("ERROR: Arg2 (var " + node2.getObjectValue() + ") is not declared for stmt " + stmtType);
					return false;
				}
			} else if (node2.getObjectType().equals("digit")) { // digit digit
				index = index + 3;
				return true;
			} else if (node2.getObjectType().equals("IntExpr")) { // digit IntExpr
				index = index + 2;
				if (typeIntExpr(null)) {
					return true;
				} else {
					errors = true;
					System.out.println("Invalid IntExpr for stmt " + stmtType);
					return false;
				}
			} else {
				System.out.println("ERROR: The second argument in " + stmtType + " does not match type \"int\"");
				return false;
			}
		} else if (node1.getObjectType().equals("stringExpr")) { // string
			if (node2.getObjectType().equals("id")) { // string id
				if (checkType("string", node2.getObjectValue())) {
					index = index + 3;
					return true;
				} else {
					errors = true;
					System.out.println("ERROR: var " + node2.getObjectType() + " does not match type string");
					return false;
				}
			} else if (node2.getObjectType().equals("stringExpr")) { // string string
				index = index + 3;
				return true;
			} else {
				errors = true;
				System.out.println("ERROR: The second argument in the " + stmtType + " does not match type \"string\"");
				return false;
			}
		} else if (node1.getObjectType().equals("IntExpr")) { // IntExpr
			index = index + 2;
			if (typeIntExpr(null)) {
				index++;
				node2 = astList.get(index); // reset node 2 to next.
				if (node2.getObjectType().equals("digit")) { // IntExpr digit
					index++;
					return true;
				} else if (node2.getObjectType().equals("id")) {
					if (checkDeclaration(node2.getObjectValue())) {
						if (checkType("int", node2.getObjectValue())) {
							index++;
							return true;
						} else {
							errors = true;
							System.out.println("ERROR: var " + node2.getObjectValue() + " does not match type string");
							return false;
						}
					} else {
						errors = true;
						System.out.println("ERROR: var " + node2.getObjectValue() + " is not declared");
						return false;
					}
				} else if (node2.getObjectType().equals("IntExpr")) { // IntExpr IntExpr
					if (typeIntExpr(null)) {
						return true;
					} else {
						errors = true;
						System.out.println("ERROR: IntExpr error for Arg2 of statement" + stmtType);
						return false;
					}
				} else {
					errors = true;
					System.out.println("ERROR: Invalid second argument for statement " + stmtType);
					return false;
				}
			} else {
				errors = true;
				System.out.println("ERROR: Arg1(IntExpr) is not valid for statement " + stmtType);
				return false;
			}
		} else if (node1.getObjectType().equals("BoolExpr")) { // BoolExpr
			index++; // @ BoolVal | other
			if (astList.get(index).getObjectType().equals("BoolVal")) { // BoolVal
				index = index + 2; // @ second expr;
				node2 = astList.get(index);
				if (node2.getObjectType().equals("BoolExpr")) { // BoolVal ?
					index++; // @ BoolVal | other
					if (astList.get(index).getObjectType().equals("BoolVal")) {  // BoolVal BoolVal?
						index++;
						return true;
					} else {
						index--;
						if (typeBoolExpr(stmtType)) { // BoolVal BoolExpr(recursive)
							return true;
						} else {
							errors = true;
							System.out.println("ERROR: Invalid BoolExpr for statement " + stmtType);
							return false;
						}
					}
				} else if (node2.getObjectType().equals("id")) { // BoolVal id
					if (checkType("boolean", node2.getObjectValue())) {
						index++; // @ next stmt
						return true;
					} else {
						errors = true;
						System.out.println("ERROR: var " + node2.getObjectValue() + " does not match type \"boolean\"");
						return false;
					}
				} else {
					errors = true;
					System.out.println("ERROR: Invalid argument " + astList.get(index).getObjectType() + ". Expected BoolExpr");
					return false;
				}
			} else if (astList.get(index - 1).getObjectType().equals("BoolExpr")){ // BoolExpr
				index--;
				if (typeBoolExpr(stmtType)) {
					index = index + 2; // @ BoolVal | other
					if (index < astList.size()) {
						if (astList.get(index).getObjectType().equals("BoolVal")) { // BoolExpr BoolVal
							index++;
							return true;
						} else if (astList.get(index -1).getObjectType().equals("BoolExpr")) { // BoolExpr BoolExpr
							index--;
							if (typeBoolExpr(stmtType)) {
								return true;
							} else {
								errors = true;
								System.out.println("ERROR: Invalid arg2 for statement " + stmtType + ". Expected BoolExpr");
								return false;
							}
						} else if (astList.get(index -1).getObjectType().equals("id")) { // BoolExpr id
							if (checkType("boolean", astList.get(index -1).getObjectValue())) {
								return true;
							} else {
								errors = true;
								System.out.println("ERROR: var " + astList.get(index -1).getObjectValue() + " does not match type \"boolean\"");
								return false;
							}
						} else {
							errors = true;
							System.err.println("ERROR: Invalid arg2 for statement " + stmtType + ". Expected BoolExpr. Got " + astList.get(index-1).getObjectType());
							return false;
						}
					} else {
						errors = true;
						System.out.println("ERROR: Invalid BoolExpr for statement " + stmtType);
						return false;
					}
				} else {
					errors = true;
					System.out.println("ERROR: Invalid BoolExpr for statement " + stmtType);
					return false;
				}
			} else { // TODO BoolExpr recurisve
				errors = true;
				System.out.println("ERROR: Expected BoolVal|BoolExpr, recieved " + astList.get(index).getObjectType());
				return false;
			}
		} else {
			errors = true;
			System.out.println("ERROR: Unknown expression type in typeBoolExpr");
			System.out.println("ERROR: Arg1 Type/Value: " + node1.getObjectType() + "/" + node1.getObjectValue());
			System.out.println("ERROR: Arg2 Type/Value: " + node2.getObjectType() + "/" + node2.getObjectValue());
			return false;
		}
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
//				ArrayList<Decleration> tempDeclList = temp.getUnInitialized();
//				if (tempDeclList.size() > 0) {
//					for (Decleration tempDecl : tempDeclList) {
//						System.out.println("WARNING: Uninitialized var " + tempDecl.getId());
//					}
//				}
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
	
//	private BoolExpr parseBoolExpr() { // @ 
//		int startLevel = astList.get(index).getObjectLevel();
//		int currLevel = startLevel;
//		BoolExpr temp = new BoolExpr();
//		while(astList.get(index).getObjectLevel() >= startLevel) {
//			
//			if (astList.get(index).getObjectType() == "BoolExpr" && astList.get(index + 1).getObjectType() != "BoolVal") {
//				currLevel++;
//				temp.addExpr("(");
//				index++; // TODO@ digit| 
//			} else if (astList.get(index).getObjectType() == "digit") {
//				temp.addExpr(astList.get(index).getObjectValue());
//				index++;
//			} else if (astList.get(index).getObjectType() == "boolop") {
//				temp.addExpr(astList.get(index).getObjectValue());
//				index++; // @ BoolExpr | id
//			} else if (astList.get(index).getObjectType() == "IntExpr") {
//				index++; // @ digit1
//				IntExpr temp1 = parseIntExpr(); // @ boolop | next stmt
//				temp.addExprs(temp1.forBoolExpr());
//			} else if (astList.get(index).getObjectType() == "stringExpr") {
//				temp.addExpr(astList.get(index).getObjectValue());
//				index++; // @ boolop | next stmt
//			} else if (astList.get(index).getObjectType() == "BoolExpr" && astList.get(index + 1).getObjectType() == "BoolVal") {
//				index++;
//				temp.addExpr(astList.get(index).getObjectValue());
//				index++; // @ boolop | next stmt
//			} else {
//				temp = null; // Type Error
//			}
//			if (index >= astList.size()) {
//				if (temp.getExpr().get(temp.getExpr().size()-1) != ")"){
//					temp.addExpr(")");
//				}
//				break;
//			}
//			
//			if (astList.get(index).getObjectLevel() < currLevel) {
//				temp.addExpr(")");
//				currLevel--;
//			}
//		}
//		return temp;
//	}
	
	
	
//	private IntExpr parseIntExpr() { // @ digit1
//		int startLevel = astList.get(index).getObjectLevel();
//		IntExpr temp = new IntExpr();
//		while (astList.get(index).getObjectLevel() >= startLevel) { // This will end @ next Stmt
//			temp.addInt(Integer.valueOf(astList.get(index).getObjectValue()));
//			index++; // @ digit2|id|IntExpr
//			if (astList.get(index).getObjectType() == "digit") { // We can break here as no more recursive Expressions
//				temp.addInt(Integer.valueOf(astList.get(index).getObjectValue()));
//				index++; // @ next stmt
//				break;
//			} else if (astList.get(index).getObjectType() == "id") { // We can break here as no more recursive Expressions
//				temp.setId(astList.get(index).getObjectValue());
//				index++; // @ next stmt
//				break;
//			} else if (astList.get(index).getObjectType() == "IntExpr") {
//				index++; // @ digit1
//			} else {
//				temp = null;
//				System.out.println("ERROR"); // Error on Type. Can only be digit|id|IntExpr
//			}
//		}
//		return temp;
//		
//	}
	
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

	//	private BoolExpr parseBoolExpr() { // @ 
	//		int startLevel = astList.get(index).getObjectLevel();
	//		int currLevel = startLevel;
	//		BoolExpr temp = new BoolExpr();
	//		while(astList.get(index).getObjectLevel() >= startLevel) {
	//			
	//			if (astList.get(index).getObjectType() == "BoolExpr" && astList.get(index + 1).getObjectType() != "BoolVal") {
	//				currLevel++;
	//				temp.addExpr("(");
	//				index++; // TODO@ digit| 
	//			} else if (astList.get(index).getObjectType() == "digit") {
	//				temp.addExpr(astList.get(index).getObjectValue());
	//				index++;
	//			} else if (astList.get(index).getObjectType() == "boolop") {
	//				temp.addExpr(astList.get(index).getObjectValue());
	//				index++; // @ BoolExpr | id
	//			} else if (astList.get(index).getObjectType() == "IntExpr") {
	//				index++; // @ digit1
	//				IntExpr temp1 = parseIntExpr(); // @ boolop | next stmt
	//				temp.addExprs(temp1.forBoolExpr());
	//			} else if (astList.get(index).getObjectType() == "stringExpr") {
	//				temp.addExpr(astList.get(index).getObjectValue());
	//				index++; // @ boolop | next stmt
	//			} else if (astList.get(index).getObjectType() == "BoolExpr" && astList.get(index + 1).getObjectType() == "BoolVal") {
	//				index++;
	//				temp.addExpr(astList.get(index).getObjectValue());
	//				index++; // @ boolop | next stmt
	//			} else {
	//				temp = null; // Type Error
	//			}
	//			if (index >= astList.size()) {
	//				if (temp.getExpr().get(temp.getExpr().size()-1) != ")"){
	//					temp.addExpr(")");
	//				}
	//				break;
	//			}
	//			
	//			if (astList.get(index).getObjectLevel() < currLevel) {
	//				temp.addExpr(")");
	//				currLevel--;
	//			}
	//		}
	//		return temp;
	//	}
		
		
		
	//	private IntExpr parseIntExpr() { // @ digit1
	//		int startLevel = astList.get(index).getObjectLevel();
	//		IntExpr temp = new IntExpr();
	//		while (astList.get(index).getObjectLevel() >= startLevel) { // This will end @ next Stmt
	//			temp.addInt(Integer.valueOf(astList.get(index).getObjectValue()));
	//			index++; // @ digit2|id|IntExpr
	//			if (astList.get(index).getObjectType() == "digit") { // We can break here as no more recursive Expressions
	//				temp.addInt(Integer.valueOf(astList.get(index).getObjectValue()));
	//				index++; // @ next stmt
	//				break;
	//			} else if (astList.get(index).getObjectType() == "id") { // We can break here as no more recursive Expressions
	//				temp.setId(astList.get(index).getObjectValue());
	//				index++; // @ next stmt
	//				break;
	//			} else if (astList.get(index).getObjectType() == "IntExpr") {
	//				index++; // @ digit1
	//			} else {
	//				temp = null;
	//				System.out.println("ERROR"); // Error on Type. Can only be digit|id|IntExpr
	//			}
	//		}
	//		return temp;
	//		
	//	}
		
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

