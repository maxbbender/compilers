package codegeneration;

import java.util.ArrayList;

public class JumpTable {
	private ArrayList<JumpEntry> jumpTable;
	
	public JumpTable() {
		jumpTable = new ArrayList<JumpEntry>();
	}
	
	public JumpEntry addEntry() {
		JumpEntry tempEntry;
		jumpTable.add(tempEntry = new JumpEntry("J" + String.valueOf(jumpTable.size()), "XX"));
		return tempEntry;
	}
}
