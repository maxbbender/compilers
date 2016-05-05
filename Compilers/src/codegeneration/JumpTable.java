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
	
	public JumpEntry getEntry(String entryId) {
		for (JumpEntry tempEntry : jumpTable) {
			if (tempEntry.getJumpId().equals(entryId)) {
				return tempEntry;
			}
		}
		return null;
	}
	
	public void updateEntry(String entryId, String newDistance) {
		for (JumpEntry tempEntry : jumpTable) {
			if (tempEntry.getJumpId().equals(entryId)) {
				tempEntry.setDistance(newDistance);
			}
		}
	}
	
	public boolean hasUndefined() {
		for (JumpEntry tempEntry : jumpTable ) {
			if (tempEntry.getDistance() == "XX") {
				return true;
			}
		}
		return false;
	}
	public void print() {
		for (JumpEntry tempEntry : jumpTable) {
			System.out.println(tempEntry.getJumpId() + "|" + tempEntry.getDistance());
		}
	}


	public ArrayList<JumpEntry> getJumpTable() {
		return jumpTable;
	}


	public void setJumpTable(ArrayList<JumpEntry> jumpTable) {
		this.jumpTable = jumpTable;
	}
}
