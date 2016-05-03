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
	
	public void updateJumps() {
		for (JumpEntry tempEntry : jumpTable.getJumpTable()) {
			int changeIndex = exec.indexOf(tempEntry.getJumpId());
			exec.set(changeIndex, tempEntry.getDistance());
		}
	}
	public void printStaticTable() {
		staticTable.print();
	}
	
	public void printJumpTable() {
		jumpTable.print();
	}
	
	public void loadConst(String newConst) {
		exec.add("A9");
		if (newConst.length() == 1) {
			String tempConst = "0" + newConst;
			exec.add(tempConst);
		} else {
			exec.add(newConst);
		}
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
	
	public void storeAccumulator(String var, String scope) {
		exec.add("8D");
		if (!staticTable.varExists(var)) {
			staticTable.addEntry(var, scope);
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
	
	public void updateJump() {
		for (int i = exec.size() - 1; i >= 0; i--) {
			if (exec.get(i).equals("D0")) {
				String jumpId = exec.get(i + 1);
				if (jumpTable.getEntry(jumpId).getDistance().equals("XX")) {
					int diff = exec.size() - exec.indexOf(jumpId);
					jumpTable.updateEntry(jumpId, String.valueOf(diff));
				}
			}
		}
	}
	
}
