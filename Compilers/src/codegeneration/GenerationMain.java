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
		int index = 1;
		ArrayList<TerminalNode> astList = ast.getAst();
		while (index < astList.size()) {
			String nodeType = astList.get(index).getObjectType();
			
			switch(nodeType) {
			case "VarDecl": 
				index = index + 2; // @ id
				declaration(astList.get(index).getObjectValue());
				index++;
				break;
			}
		}
		doBreak();
	}
	
	public void print() {
		System.out.println("---EXECUTION ARRAY---");
		genOps.printExecArray();
		System.out.println("---Static Table---");
		genOps.printStaticTable();
	}
	
	public void declaration(String var) {
		genOps.loadConst("00");
		genOps.storeAccumulator(var);
	}
	
	public void assignConst(String var, String val) {
		genOps.loadConst(val);
		genOps.storeAccumulator(var);
	}
	
	public void assignMemory(String varToAssign, String varToLookup) {
		genOps.loadMemory(varToLookup);
		genOps.storeAccumulator(varToAssign);
	}
	
	public void printVar(String var) {
		genOps.loadYMemory(var);
		genOps.loadXConst("01");
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
