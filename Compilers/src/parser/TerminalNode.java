package parser;

public class TerminalNode {
	private String objectType;
	private String objectValue;
	private int objectLevel;
	
	public TerminalNode(){ 
		objectType = null;
		objectValue = null;
		objectLevel = -1;
	}
	
	public TerminalNode(String newObjectType, String newObjectValue, int newObjectLevel){ 
		objectType = newObjectType;
		objectValue = newObjectValue;
		objectLevel = newObjectLevel;
	}

	public String getObjectType() {
		return objectType;
	}

	public int getObjectLevel() {
		return objectLevel;
	}

	public void setObjectLevel(int objectLevel) {
		this.objectLevel = objectLevel;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}
}
