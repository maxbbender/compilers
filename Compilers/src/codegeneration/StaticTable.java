package codegeneration;

import java.util.ArrayList;

public class StaticTable {
	private ArrayList<StaticVar> table;
	
	public StaticTable() {
		table = new ArrayList<StaticVar>();
	}
	
	public void addEntry(String newTempNum, String newTempX, String newVar, String newAddress, String newScope, String newType) {
		StaticVar tempStaticVar;
		table.add(tempStaticVar= new StaticVar(newTempNum, newTempX, newVar, newAddress, newScope, newType));
	}
	
	public void addEntry(String newVar, String newAddress, String newScope, String newType) {
		StaticVar tempStaticVar;
		String tempT = "T" + String.valueOf(table.size());
		table.add(tempStaticVar = new StaticVar(tempT, "XX", newVar, newAddress, newScope, newType));
	}
	
	public ArrayList<StaticVar> getTable() {
		return table;
	}

	public void setTable(ArrayList<StaticVar> table) {
		this.table = table;
	}

	public void addEntry(String newVar, String newScope, String type) {
		StaticVar tempStaticVar;
		String tempT = "T" + String.valueOf(table.size());
		String address = "+" + String.valueOf(table.size());
		table.add(tempStaticVar = new StaticVar(tempT, "XX", newVar, address, newScope, type));
	}
	
	public StaticVar getEntryForVar(String var) {
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var)) {
				return tempVar;
			}
		}
		return null;
	}
	
	public boolean varExists(String var) {
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var)) {
				return true;
			}
		}
		return false;
	}
	
	public void print() {
		System.out.println("TX|XX|Var|Scope|Address");
		System.out.println("~~~~~~~~~~~~~~~~~");
		for (StaticVar tempVar : table) {
			System.out.println(tempVar.getTempNum()+ "|" + tempVar.getTempX() + "|" + tempVar.getVar() + "|" + tempVar.getScope() + "|" + tempVar.getAddress());
		}
	}
}
