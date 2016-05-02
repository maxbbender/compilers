package codegeneration;

public class StaticVar {
	private String tempNum;
	private String tempX;
	private String var;
	private String address;
	
	public StaticVar() {
		tempNum = null;
		tempX = null;
		var = null;
		address = null;
	}
	
	public StaticVar(String newTempNum, String newTempX, String newVar, String newAddress) {
		tempNum = newTempNum;
		tempX = newTempX;
		var = newVar;
		address = newAddress;
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
