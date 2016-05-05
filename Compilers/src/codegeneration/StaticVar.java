package codegeneration;

public class StaticVar {
	private String tempNum;
	private String tempX;
	private String scope;
	private String var;
	private String address;
	private String type;
	
	public StaticVar() {
		scope = null;
		tempNum = null;
		tempX = null;
		var = null;
		address = null;
		type = null;
	}
	
	public StaticVar(String newTempNum, String newTempX, String newVar, String newAddress, String newScope, String newType) {
		scope = newScope;
		tempNum = newTempNum;
		tempX = newTempX;
		var = newVar;
		address = newAddress;
		type = newType;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String fullName() {
		return tempNum + tempX;
	}

	public String getTempNum() {
		return tempNum;
	}

	public void setTempNum(String tempNum) {
		this.tempNum = tempNum;
	}

	public String getTempX() {
		return tempX;
	}

	public void setTempX(String tempX) {
		this.tempX = tempX;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
