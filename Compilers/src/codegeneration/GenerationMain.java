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
						loopWhile(whileStart);
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
				}
				
				index++;
				break;
			case "AssignmentStmt": 
				if (astList.get(index + 2).getObjectType() == "digit") {
					assignConst(astList.get(index + 1).getObjectValue(), astList.get(index + 2).getObjectValue() , String.valueOf(scope));
				} else if (astList.get(index + 2).getObjectType() == "id") {
					assignMemory(astList.get(index + 1).getObjectValue(), astList.get(index + 2).getObjectValue(), String.valueOf(scope));
				} else if (astList.get(index + 2).getObjectType().equals("stringExpr")) {
					assignString(astList.get(index + 1).getObjectValue(), astList.get(index + 2).getObjectValue() , String.valueOf(scope));
				}
				index = index + 3;
				break;
			case "PrintStmt":
				index++; // @ id|digit|BoolExpr|StringExpr
				String objectType = astList.get(index).getObjectType();
				if (objectType == "id") {
					printVar(astList.get(index).getObjectValue());
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
					if (astList.get(index).getObjectType().equals("id")) {
						if (astList.get(index+2).getObjectType().equals("id")) {
							ifStmtVar(astList.get(index).getObjectValue(), astList.get(index + 2).getObjectValue());
							index = index + 3;
							jumpLevel = astList.get(index).getObjectLevel();
							jumped = true;
						}
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
							whileStmt("iddigit", idVal, digitVal);
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
		genOps.storeAccumulator(var, scope);
	}
	
	public void stringDeclaration(String var, String scope) {
		genOps.stringDecl(var, scope);
	}
	
	public void assignConst(String var, String val, String scope) {
		genOps.loadConst(val);
		genOps.storeAccumulator(var, scope);
	}
	
	public void assignMemory(String varToAssign, String varToLookup, String scope) {
		genOps.loadMemory(varToLookup);
		genOps.storeAccumulator(varToAssign, scope);
	}
	
	public void assignString(String var, String string, String scope) {
 		String tempString = string.split("\"")[1];
		int starting = genOps.getHeapSize() + tempString.length() + 1;
		String hex = Integer.toHexString(256 - starting);
		genOps.addToHeap(tempString);
		genOps.loadConst(hex);
		genOps.storeAccumulator(var, scope);
	}
	
	public void printVar(String var) {
		genOps.loadYMemory(var);
		if (genOps.isString(var)) {
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
	
	public void ifStmtVar(String varToX, String varToCheck) {
		genOps.loadXMemory(varToX);
		genOps.compare(varToCheck);
		genOps.jump();
	}
	
	public void whileStmt(String type, String val1, String val2) {
		switch (type) {
		case "iddigit": 
			//val 1 = id | val2 = digit
			if (val2.length() == 1) {
				genOps.loadXConst("0" + val2);
			} else {
				genOps.loadXConst(val2);
			}
			
			genOps.compare(val1);
			genOps.jump();
		}
	}
	
	public void loopWhile(int whileStart) {
		genOps.loadXConst("02");
		genOps.loadConst("01");
		genOps.storeAccumulator("rr", "0");
		genOps.compare("rr");
		genOps.jumpKnown(whileStart);
	}
	
	public void doBreak() {
		genOps.doBreak();
	}
}
