package semantic.helpers;

public class Decleration {
	private String type;
	private String id;
	private boolean init;

	public Decleration() {
		type = null;
		id = null;
		init = false;
	}
	
	public Decleration(String newType, String newId) {
		type = newType;
		id = newId;
		init = false;
	}
	
	public void init() {
		init = true;
	}
	
	public boolean isInitialized() {
		return init;
	}
	
	public String printDecl() {
		return id + " : " + type; 
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
