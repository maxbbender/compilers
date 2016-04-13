package semantic.helpers;

import java.util.ArrayList;

public class IntExpr {
	private String id;
	private ArrayList<Integer> integers;
	
	public IntExpr() {
		id = null;
		integers = new ArrayList();
	}
	
	public void addInt(int newInt) {
		integers.add(newInt);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String print() {
		StringBuilder tempString = new StringBuilder();
		for (int temp : integers) {
			if (tempString.length() > 0) { 
				tempString.append(" + ");
			}
			tempString.append(temp);
		}
		
		if (id != null) {
			tempString.append(" + ");
			tempString.append(id);
		}
		return tempString.toString();
	}
	
	public ArrayList<String> forBoolExpr() {
		ArrayList<String> temp = new ArrayList();
		for (int tempInt : integers) {
			temp.add(String.valueOf(tempInt));
		}
		
		if (id != null) {
			temp.add(id);
		}
		
		return temp;
	}
}
