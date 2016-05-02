package codegeneration;

import java.util.ArrayList;

public class GenerationOperations {
	private ArrayList<String> exec;
	private StaticTable staticTable;
	private JumpTable jumpTable;
	
	public GenerationOperations() {
		exec = new ArrayList<String>();
		staticTable = new StaticTable();
		jumpTable = new JumpTable();
	}
	
	public void printExecArray() {
		int counter = 1;
		for (String tempString : exec) {
			if (counter > 8 ) {
				System.out.print("\n");
				counter = 1;
			}
			System.out.print(tempString + " ");
			counter++;
		}
		System.out.println();
	}
	
	public void printStaticTable() {
		staticTable.print();
	}
	
	public void loadConst(String newConst) {
		exec.add("A9");
		exec.add(newConst);
	}
	
	public void loadMemory(String var) {
		exec.add("AD");
		StaticVar tempSVar = staticTable.getEntryForVar(var);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void loadXConst(String newConst) {
		exec.add("A2");
		exec.add(newConst);
	}
	
	public void loadXMemory(String var) {
		exec.add("AE");
		StaticVar tempSVar = staticTable.getEntryForVar(var);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void loadYConst(String newConst) { 
		exec.add("A0");
		exec.add(newConst);
	}
	
	public void loadYMemory(String var) {
		exec.add("AC");
		StaticVar tempSVar = staticTable.getEntryForVar(var);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void storeAccumulator(String var) {
		exec.add("8D");
		if (!staticTable.varExists(var)) {
			staticTable.addEntry(var);
		}
		StaticVar tempSVar = staticTable.getEntryForVar(var);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void systemCall() {
		exec.add("FF");
	}
	
	public void doBreak() {
		exec.add("00");
	}
	
	public void compare(String var) {
		exec.add("EC");
		StaticVar tempSVar = staticTable.getEntryForVar(var);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void jump() {
		exec.add("D0");
		JumpEntry tempEntry = jumpTable.addEntry();
		exec.add(tempEntry.getJumpId());
	}
	
}
