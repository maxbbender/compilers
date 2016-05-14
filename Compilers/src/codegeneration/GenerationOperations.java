package codegeneration;

import java.util.ArrayList;

public class GenerationOperations {
	private ArrayList<String> exec;
	private ArrayList<String> heap;
	private StaticTable staticTable;
	private JumpTable jumpTable;
	
	public GenerationOperations() {
		heap = new ArrayList<String>();
		exec = new ArrayList<String>();
		staticTable = new StaticTable();
		jumpTable = new JumpTable();
	}
	
	public void backpatch() {
		int execSize = exec.size()+1;
		
		/* Backpath Vars */
		for (StaticVar tempVar : staticTable.getTable()) {
			String hexString = Integer.toHexString(execSize);
			for (int i = 0; i < exec.size(); i++ ) {
				if (exec.get(i).equals(tempVar.getTempNum())) {
					if (hexString.length() == 1) {
						hexString = "0" + hexString;
					}
					exec.set(i, hexString);
					exec.set(i+1, "00");
				}
			}
			tempVar.setTempNum(hexString);
			tempVar.setTempX("00");
			execSize++;
		}
		
		/* Update Jump Values */
		updateJumps();
		
		/* Fill w/ 00 */
		int numberOf00 = 256 - exec.size() - heap.size();
		for (int i = 0; i < numberOf00; i++) {
			doBreak();
		}
		
		/* Append Heap */
		exec.addAll(heap);
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
	
	public void assignString(String string) {
		/* Add to Heap */
////		int startIndex = findOpenEndIndex();
//		heap.set(startIndex, "00");
//		startIndex = startIndex - string.length();
//		for (int i = 0; i < string.length(); i++) {
//			exec.set(startIndex, String.valueOf((int) string.charAt(i)));
//			startIndex++;
//		}
//		
//		startIndex = findOpenEndIndex() - 1;
//		exec
	}
	
	
	public void addToHeap(String string) {
		heap.add(0, "00");
		for (int i = string.length()-1; i >= 0; i--) {
			heap.add(0, Integer.toHexString((int)string.charAt(i)));
		}
		
	}
	
	public String getStartHexHeap() {
		return Integer.toHexString(256 - heap.size());
	}
	
	public int findOpenEndIndex() {
		for (int i = 256; i > 0; i--) {
			if (exec.get(i) == null) {
				return i;
			}
		}
		return -1;
	}
	
	public void updateJumps() {
		for (JumpEntry tempEntry : jumpTable.getJumpTable()) {
			int changeIndex = exec.indexOf(tempEntry.getJumpId());
			exec.set(changeIndex, tempEntry.getDistance());
		}
	}
	public void updateJump() {
		for (int i = exec.size() - 1; i >= 0; i--) {
			if (exec.get(i).equals("D0")) {
				String jumpId = exec.get(i + 1);
				if (jumpTable.getEntry(jumpId).getDistance().equals("XX")) {
					int diff = exec.size() - exec.indexOf(jumpId) - 1;
					jumpTable.updateEntry(jumpId, Integer.toHexString(diff));
				}
			}
		}
	}
	
	public int getHeapSize() {
		return heap.size();
	}
	
	public boolean hasUndefinedJump() {
		return jumpTable.hasUndefined();
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
	
	public boolean isString(String var, String scope){ 
		StaticVar tempSVar = staticTable.getEntryForVar(var, scope);
		if (tempSVar.getType() == "string") {
			return true;
		} else {
			return false;
		}
	}
	
	public void loadMemory(String var, String scope) {
		exec.add("AD");
		StaticVar tempSVar = staticTable.getEntryForVar(var, scope);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void loadXConst(String newConst) {
		exec.add("A2");
		exec.add(newConst);
	}
	
	public void loadXMemory(String var, String scope) {
		exec.add("AE");
		StaticVar tempSVar = staticTable.getEntryForVar(var, scope);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void loadYConst(String newConst) { 
		exec.add("A0");
		exec.add(newConst);
	}
	
	public void loadYMemory(String var, String scope) {
		exec.add("AC");
		StaticVar tempSVar = staticTable.getEntryForInitVar(var, scope);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void storeAccumulator(String var, String scope, boolean init, String type) {
		exec.add("8D");
		if (!staticTable.varExists(var)) {
			staticTable.addEntry(var, scope, type, init);
		} else if (!staticTable.varExistsForScope(var, scope)) {
			staticTable.addEntry(var, scope, type, init);
		}
		
		StaticVar tempSVar = staticTable.getEntryForVar(var, scope);
		if (init) {
			if (!tempSVar.isInit()) {
				tempSVar.setInit(true);
			}
		}
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void setZ1() {
		if (staticTable.hasEntrys()) {
			StaticVar tempSVar = staticTable.getFirst();
			if (tempSVar != null) {
				this.loadXMemory(tempSVar.getVar(), tempSVar.getScope());
				this.compare(tempSVar.getVar(), tempSVar.getScope());
			}
		} else {
			this.loadConst("01");
			this.storeAccumulator("r1", "0", true, "none");
			this.loadXConst("01");
			this.compare("r1", "0");
		}
	}
	
	public void setZ0() {
		if (staticTable.hasEntrys()) {
			StaticVar tempSVar = staticTable.getFirst();
			if (tempSVar != null) {
				this.loadXConst("99");
				this.compare(tempSVar.getVar(), tempSVar.getScope());
			}
		} else {
			this.loadConst("01");
			this.storeAccumulator("r1", "0", true, "none");
			this.loadXConst("00");
			this.compare("r1", "0");
		}
	}
	
	public void stringDecl(String var, String scope) {
		staticTable.addEntry(var, scope, "string", false);
	}
	
	public void booleanDecl(String var, String scope) {
		staticTable.addEntry(var, scope, "boolean", true);
	}
	
	public void systemCall() {
		exec.add("FF");
	}
	
	public void doBreak() {
		exec.add("00");
	}
	
	public void compare(String var, String scope) {
		exec.add("EC");
		StaticVar tempSVar = staticTable.getEntryForVar(var, scope);
		exec.add(tempSVar.getTempNum());
		exec.add(tempSVar.getTempX());
	}
	
	public void jump() {
		exec.add("D0");
		JumpEntry tempEntry = jumpTable.addEntry();
		exec.add(tempEntry.getJumpId());
	}
	
	public void jumpKnown(int whileStart) {
		exec.add("D0");
		JumpEntry tempEntry = jumpTable.addEntry();
		exec.add(tempEntry.getJumpId());
		int jumpNum = 256 + whileStart -1 - exec.size();
		tempEntry.setDistance(Integer.toHexString(jumpNum));
	}
	
}
