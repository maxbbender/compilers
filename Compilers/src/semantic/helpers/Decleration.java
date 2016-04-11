package semantic.helpers;

public class Decleration {
	private String type;
	private String id;
	public Decleration() {
		type = null;
		id = null;
	}
	
	public Decleration(String newType, String newId) {
		type = newType;
		id = newId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
