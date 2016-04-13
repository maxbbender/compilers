package semantic.helpers;

public class Assignment {
	private String id;
	private int valueInt;
	private String valueString;
	private Boolean valueBoolean;
	private IntExpr intExpr;
	private BoolExpr boolExpr;
	private String type;

	public Assignment() {
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
	}
	/* STRING */
	public Assignment(String newId, String newString) {
		type = "string";
		valueString = newString;
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
	}
	
	public Assignment(String newId, BoolExpr expr) {
		type = "intExpr";
		id = newId;
		boolExpr = expr;
		valueBoolean = null;
		valueInt = -1;
		valueString = null;
		intExpr = null;
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
