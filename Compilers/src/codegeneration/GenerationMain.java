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
				String objectType = astList.get(index + 1).getObjectType();
				if (objectType == "id") {
					printVar(astList.get(index + 1).getObjectValue());
				} else if (objectType == "digit") {
					
				}
				index = index + 2;
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
		String hex = Integer.toHexString(96 - starting);
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
	
	public void ifStmtVar(String varToX, String varToCheck) {
		genOps.loadXMemory(varToX);
		genOps.compare(varToCheck);
		genOps.jump();
	}
	
	public void doBreak() {
		genOps.doBreak();
	}
}
