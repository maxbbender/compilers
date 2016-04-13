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
