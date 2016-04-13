package semantic.helpers;

public class Assignment {
	private String id;
	private String assignId;
	private int valueInt;
	private String valueString;
	private Boolean valueBoolean;
	private IntExpr intExpr;
	private BoolExpr boolExpr;
	private String type;

	public Assignment() {
		assignId = null;
		type = null;
		intExpr = null;
		boolExpr = null;
		id = null;
		valueBoolean = null;
		valueInt = -1;
		valueString = null;
	}
	/* INT */
	public Assignment(String newId, int newInt) {
		type = "int";
		id = newId;
		valueInt = newInt;
		valueBoolean = null;
		intExpr = null;
		valueString = null;
		boolExpr = null;
		assignId = null;
	}
	/* BOOLEAN */
	public Assignment(String newId, Boolean newBool) {
		type = "bool";
		id = newId;
		valueBoolean = newBool;
		valueString = null;
		intExpr = null;
		valueInt = -1;
		boolExpr = null;
		assignId = null;
	}
	/* STRING */
	public Assignment(String newId, String newString) {
		if (newString.startsWith("\"")) {
			type = "string";
			valueString = newString;
			assignId = null;
		} else {
			type = "id";
			assignId = newString;
			valueString = null;
		}
		id = newId;
		valueInt = -1;
		intExpr = null;
		valueBoolean = null;
		boolExpr = null;
	}
	/* IntExpr */
	public Assignment(String newId, IntExpr expr) {
		type = "intExpr";
		id = newId;
		intExpr = expr;
		valueBoolean = null;
		valueInt = -1;
		valueString = null;
		boolExpr = null;
		assignId = null;
	}
	/* BoolExpr */
	public Assignment(String newId, BoolExpr expr) {
		type = "boolExpr";
		id = newId;
		boolExpr = expr;
		valueBoolean = null;
		valueInt = -1;
		valueString = null;
		intExpr = null;
		assignId = null;
	}
	
	public void printAss() {
		String data = null;
		switch (type) {
		case "int":
			data = String.valueOf(valueInt);
			break;
		case "bool":
			data = String.valueOf(valueBoolean);
			break;
		case "string":
			data = valueString;
			break;
		case "id":
			data = assignId;
			break;
		case "intExpr":
			data = intExpr.print();
			break;
		case "boolExpr":
			data = boolExpr.print();
			break;
		}
		
		if (data != null) {
			System.out.println(id + ": " + data);
		} else {
			System.out.println("ERROR PRINTING ASSIGNMENT STATEMENT. Data is null");
		}
		
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getValueInt() {
		return valueInt;
	}

	public void setValueInt(int valueInt) {
		this.valueInt = valueInt;
	}

	public String getValueString() {
		return valueString;
	}

	public void setValueString(String valueString) {
		this.valueString = valueString;
	}

	public Boolean getValueBoolean() {
		return valueBoolean;
	}

	public void setValueBoolean(Boolean valueBoolean) {
		this.valueBoolean = valueBoolean;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
