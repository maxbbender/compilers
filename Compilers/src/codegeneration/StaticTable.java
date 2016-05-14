package codegeneration;

import java.util.ArrayList;

public class StaticTable {
	private ArrayList<StaticVar> table;
	
	public StaticTable() {
		table = new ArrayList<StaticVar>();
	}
	
	public void addEntry(String newTempNum, String newTempX, String newVar, String newAddress, String newScope, String newType, boolean init) {
		StaticVar tempStaticVar;
		table.add(tempStaticVar= new StaticVar(newTempNum, newTempX, newVar, newAddress, newScope, newType, init));
	}
	
	public void addEntry(String newVar, String newAddress, String newScope, String newType, boolean init) {
		StaticVar tempStaticVar;
		String tempT = "T" + String.valueOf(table.size());
		table.add(tempStaticVar = new StaticVar(tempT, "XX", newVar, newAddress, newScope, newType, init));
	}
	
	public ArrayList<StaticVar> getTable() {
		return table;
	}

	public void setTable(ArrayList<StaticVar> table) {
		this.table = table;
	}

	public void addEntry(String newVar, String newScope, String type, boolean init) {
		StaticVar tempStaticVar;
		String tempT = "T" + String.valueOf(table.size());
		String address = "+" + String.valueOf(table.size());
		table.add(tempStaticVar = new StaticVar(tempT, "XX", newVar, address, newScope, type, init));
	}
	
	public boolean varIsInitialized(String var, String scope){ 
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var) && tempVar.getScope().equals(scope)) {
				if (tempVar.isInit()) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public void initVar(String var, String scope) {
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var) && tempVar.getScope().equals(scope)) {
				tempVar.setInit(true);
			}
		}
	}
	
	public StaticVar getEntryForVar(String var, String scope) {
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var) && tempVar.getScope().equals(scope)) {
				return tempVar;
			} else if (tempVar.getVar().equals(var) && Integer.valueOf(tempVar.getScope()) > Integer.valueOf(scope)) {
				return tempVar;
			}
		}
		return null;
	}
	
	public boolean hasEntrys() {
		if (table.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public StaticVar getFirst() {
		if (this.hasEntrys()) {
			return table.get(0);
		} else {
			return null;
		}
	}
	
	public StaticVar getEntryForInitVar(String var, String scope) {
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var) && tempVar.getScope().equals(scope) && tempVar.isInit()) {
				return tempVar;
			} else if (tempVar.getVar().equals(var) && Integer.valueOf(tempVar.getScope()) > Integer.valueOf(scope) && tempVar.isInit()) {
				return tempVar;
			}
		}
		return null;
	}
	
	public int getMaxScope() {
		int maxScope = 0;
		for (StaticVar tempVar : table) {
			if (Integer.valueOf(tempVar.getScope()) > maxScope) {
				maxScope = Integer.valueOf(tempVar.getScope());
			}
		}
		return maxScope;
	}
	public boolean varExists(String var) {
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean varExistsForScope(String var, String scope) {
		for (StaticVar tempVar : table) {
			if (tempVar.getVar().equals(var) && tempVar.getScope().equals(scope)) {
				return true;
			}
		}
		return false;
	}
	
	public void print() {
		System.out.println("TX|XX|Var|Scope|Address|Type|Init");
		System.out.println("~~~~~~~~~~~~~~~~~");
		for (StaticVar tempVar : table) {
			System.out.println(tempVar.getTempNum()+ "|" + tempVar.getTempX() + "|" + tempVar.getVar() + "|" + tempVar.getScope() + "|" + tempVar.getAddress() + "|" + tempVar.getType() + "|" + tempVar.isInit());
		}
	}
}
