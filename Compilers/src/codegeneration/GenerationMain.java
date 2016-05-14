package codegeneration;

import java.util.ArrayList;

import parser.TerminalNode;
import semantic.AST;

public class GenerationMain {
	private GenerationOperations genOps;
	private AST ast;
	
	public GenerationMain(AST newAST) {
		genOps = new GenerationOperations();
		ast = newAST;
	}
	
	public void run() {
		int whileStart = 0;
		boolean error = false;
		int scope = 0;
		int currScopeLevel = 0;
		int jumpLevel = 0;
		boolean jumped = false;
		int index = 0;
		ArrayList<TerminalNode> astList = ast.getAst();
		while (index < astList.size() && !error) {
			if (astList.get(index).getObjectLevel() <= currScopeLevel) {
				if (jumped) {
					if (whileStart > 0) {
						loopWhile(whileStart, String.valueOf(scope));
					}
					
					genOps.updateJump();
					jumped = false;
				}
				scope--;
				currScopeLevel = astList.get(index).getObjectLevel() -1;
			}
			
			String nodeType = astList.get(index).getObjectType();
			
			switch(nodeType) {
			case "VarDecl": 
				index++;
				String type = astList.get(index).getObjectValue();
				if (type.equals("int")) {
					index++;
					declaration(astList.get(index).getObjectValue(), String.valueOf(scope));
				} else if (type.equals("string")){
					index++;
					stringDeclaration(astList.get(index).getObjectValue(), String.valueOf(scope));
				} else if (type.equals("boolean")) {
					index++;
					booleanDeclaration(astList.get(index).getObjectValue(), String.valueOf(scope));
				}
				index++;
				break;
			case "AssignmentStmt":
				index++;
				String var = astList.get(index).getObjectValue();
				index++;
				String typeA = astList.get(index).getObjectType();
				String valueA = astList.get(index).getObjectValue();
				if (typeA.equals("digit")) {
					assignConst(var, valueA, String.valueOf(scope));
					index++;
				} else if (typeA.equals("id")) {
					assignMemory(var, valueA, String.valueOf(scope));
					index++;
				} else if (typeA.equals("stringExpr")) {
					assignString(var, valueA , String.valueOf(scope));
					index++;
				} else if (typeA.equals("BoolExpr")) {
					index++;
					typeA = astList.get(index).getObjectType();
					valueA = astList.get(index).getObjectValue();
					if (typeA.equals("BoolVal")) {
						assignBoolean(var, valueA, String.valueOf(scope));
					}
					index++;
				}
				break;
			case "PrintStmt":
				index++; // @ id|digit|BoolExpr|StringExpr
				String objectType = astList.get(index).getObjectType();
				if (objectType == "id") {
					printVar(astList.get(index).getObjectValue(), String.valueOf(scope));
					index++;
				} else if (objectType == "digit") {
					printDigitLiteral(astList.get(index).getObjectValue());
					index++;
				} else if (objectType == "BoolExpr") {
					index++;
					if (astList.get(index).getObjectType().equals("BoolVal")) {
						printBoolLiteral(astList.get(index).getObjectValue());
					}
					index++;
				} else if (objectType == "stringExpr") {
					printStringLiteral(astList.get(index).getObjectValue());
					index++;
				}
				break;
			case "IfStmt": 
				index++;
				if (astList.get(index).getObjectType().equals("BoolExpr")) {
					index++;
					String typeB = astList.get(index).getObjectType();
					if (astList.get(index).getObjectType().equals("id")) {
						if (astList.get(index+2).getObjectType().equals("id")) {
							ifStmtVar(astList.get(index).getObjectValue(), astList.get(index + 2).getObjectValue(), String.valueOf(scope));
							index = index + 3;
							jumpLevel = astList.get(index).getObjectLevel();
							jumped = true;
						}
					} else if (typeB.equals("BoolVal")) {
						
					}
				}
				break;
			case "WhileStmt":
				whileStart = astList.size();
				index++;
				if (astList.get(index).getObjectType().equals("BoolExpr")) {
					index++;
					if (astList.get(index).getObjectType().equals("id")) {
						String idVal = astList.get(index).getObjectValue();
						index++;
						index++; // @ second arg
						if (astList.get(index).getObjectType().equals("digit")) {
							String digitVal = astList.get(index).getObjectValue();
							whileStmt("iddigit", idVal, digitVal, String.valueOf(scope));
							jumped = true;
						}
					}
					index++;
					jumpLevel = astList.get(index).getObjectLevel();
					jumped = true;
//					if (astList.get(index).getObjectType().equals("BoolVal")) {
//						 
//					} else if ()
					break;
				}
			case "Block":
				scope++;
				currScopeLevel = astList.get(index).getObjectLevel();
				index++;
				break;
			default:
				System.out.println("ERRORRRR");
				System.out.println(astList.get(index-1).getObjectType() + " " + astList.get(index-1).getObjectValue());
				System.out.println(astList.get(index).getObjectType() + " " + astList.get(index).getObjectValue());
				error = true;
				break;
			}
		}
		if (genOps.hasUndefinedJump()) {
			genOps.updateJump();
		}
		
		/* Backtrace start */
		genOps.backpatch();
//		doBreak();
	}
	
	
	public void print() {
		System.out.println("---EXECUTION ARRAY---");
		genOps.printExecArray();
		System.out.println("---Static Table---");
		genOps.printStaticTable();
		System.out.println("---JUMP TABLE---");
		genOps.printJumpTable();
	}
	
	public void declaration(String var, String scope) {
		genOps.loadConst("00");
		genOps.storeAccumulator(var, scope, false, "int");
	}
	
	public void stringDeclaration(String var, String scope) {
		genOps.stringDecl(var, scope);
	}
	
	public void booleanDeclaration(String var, String scope) {
		genOps.booleanDecl(var, scope);
		genOps.loadConst("00");
		genOps.storeAccumulator(var, scope, false, "boolean");
	}
	
	public void assignConst(String var, String val, String scope) {
		genOps.loadConst(val);
		genOps.storeAccumulator(var, scope, true, "int");
	}
	
	public void assignMemory(String varToAssign, String varToLookup, String scope) {
		genOps.loadMemory(varToLookup, scope);
		genOps.storeAccumulator(varToAssign, scope, true, "int");
	}
	
	public void assignString(String var, String string, String scope) {
 		String tempString = string.split("\"")[1];
		int starting = genOps.getHeapSize() + tempString.length() + 1;
		String hex = Integer.toHexString(256 - starting);
		genOps.addToHeap(tempString);
		genOps.loadConst(hex);
		genOps.storeAccumulator(var, scope, true, "string");
	}
	
	public void assignBoolean(String var, String boolVal, String scope) {
		if (boolVal.equals("true")) {
			genOps.loadConst("01");
		} else {
			genOps.loadConst("00");
		}
		genOps.storeAccumulator(var, scope, true, "boolean");
	}
	
	public void printVar(String var, String scope) {
		genOps.loadYMemory(var, scope);
		if (genOps.isString(var, scope)) {
			genOps.loadXConst("02");
		} else {
			genOps.loadXConst("01");
		}
		genOps.systemCall();
	}
	
	public void printBoolLiteral(String bool) {
		if (bool.equals("true")) {
			genOps.loadYConst("01");
		} else if (bool.equals("false")){
			genOps.loadYConst("00");
		}
		genOps.loadXConst("01");
		genOps.systemCall();
	}
	
	public void printDigitLiteral(String digit) {
		if (digit.length() == 1) {
			digit = "0" + digit;
		}
		genOps.loadYConst(digit);
		genOps.loadXConst("01");
		genOps.systemCall();
	}
	
	public void printStringLiteral(String stringToPrint) {
		String tempString = stringToPrint.split("\"")[1];
		genOps.addToHeap(tempString);
		String hexStart = genOps.getStartHexHeap();
		genOps.loadYConst(hexStart);
		genOps.loadXConst("02");
		genOps.systemCall();
	}
	
	public void ifStmtVar(String varToX, String varToCheck, String scope) {
		genOps.loadXMemory(varToX, scope);
		genOps.compare(varToCheck, scope);
		genOps.jump();
	}
	
	public void ifStmtLiteral(String booleanCheck) {
		if (booleanCheck.equals("true")) {
			genOps.setZ1();
		}
	}
	
	public void whileStmt(String type, String val1, String val2, String scope) {
		switch (type) {
		case "iddigit": 
			//val 1 = id | val2 = digit
			if (val2.length() == 1) {
				genOps.loadXConst("0" + val2);
			} else {
				genOps.loadXConst(val2);
			}
			
			genOps.compare(val1, scope);
			genOps.jump();
		}
	}
	
	public void loopWhile(int whileStart, String scope) {
		genOps.loadXConst("02");
		genOps.loadConst("01");
		genOps.storeAccumulator("rr", "0", true, "int");
		genOps.compare("rr", scope);
		genOps.jumpKnown(whileStart);
	}
	
	public void doBreak() {
		genOps.doBreak();
	}
}
