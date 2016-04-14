package semantic.helpers;

import java.util.ArrayList;

public class Scope {
	private int scope;
	private ArrayList<Decleration> decl;
	private ArrayList<Assignment> assign;
	private Scope parent; 
	private ArrayList<Scope> children;
	private int currChild;
	private int levelStart;
	private int levelEnd;
	private boolean printed;
	
	public Scope() {
		children = new ArrayList();
		parent = null;
		scope = -1;
		levelStart = -1;
		levelEnd = -1;
		decl = new ArrayList();
		assign = new ArrayList();
		currChild = 0;
		printed = false;
	}
	
	public Scope(int nlevelStart) {
		children = new ArrayList();
		parent = null;
		assign = new ArrayList();
		decl = new ArrayList();
		levelStart = nlevelStart;
		levelEnd = -1;
		currChild = 0;
		printed = false;
	}
	
	public boolean hasParent() {
		if (parent != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public Scope getParent() {
		return parent;
	}
	public boolean hasChildren() {
		if (children.size() > 0) {
			if (currChild < children.size()) {
				if (children.get(currChild) != null) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Scope getCurrChild() {
		Scope returnScope = children.get(currChild);
		currChild++;
		return returnScope;
		
	}
	public void printDeclerations() {
		for (Decleration temp : decl) {
			temp.printDecl();
		}
	}
	
	public void printAssignments() {
		for (Assignment temp : assign) {
			temp.printAss();
		}
	}
	public void addAssignment(Assignment newAss) {
		assign.add(newAss);
	}
	
	public void addAssignment(String id, int newInt) {
		Assignment temp = new Assignment(id, newInt);
		assign.add(temp);
	}
	
	public void addAssignment(String id, String newString) {
		Assignment temp = new Assignment(id, newString);
		assign.add(temp);
	}
	
	public void addAssignment(String id, Boolean newBool) {
		Assignment temp = new Assignment(id, newBool);
		assign.add(temp);
	}
	
	public void addAssignment(String id, IntExpr expr) {
		Assignment temp = new Assignment(id, expr);
		assign.add(temp);
	}
	
	public void addAssignment(String id, BoolExpr expr) {
		Assignment temp = new Assignment(id, expr);
		assign.add(temp);
	}
	
	public void addDecl(Decleration newDecl) {
		decl.add(newDecl);
	}
	
	public void addDecl(String type, String id) {
		Decleration temp = new Decleration(type,id);
		decl.add(temp);
	}
	
	public ArrayList<Assignment> getAssign() {
		return assign;
	}

	public void setAssign(ArrayList<Assignment> assign) {
		this.assign = assign;
	}

	public ArrayList<Scope> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Scope> children) {
		this.children = children;
	}

	public boolean isPrinted() {
		return printed;
	}

	public void setPrinted(boolean printed) {
		this.printed = printed;
	}

	public void setCurrChild(int currChild) {
		this.currChild = currChild;
	}

	public void addChild(Scope newScope) {
		children.add(newScope);
	}
	
	public void setParent(Scope newScope) {
		parent = newScope;
	}
	
	public boolean checkDeclaration(String id) {
		for(Decleration declNode : decl) {
//			System.out.println("Input ID: " + id);
//			System.out.println("DeclNode ID: " + declNode.getId());
			if (declNode.getId().equals(id)){
				return true;
			}
		} 
		return false;
	}
	
	public boolean hasId(String id) {
		for(Decleration declNode : decl) {
			if (declNode.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public String getType(String id) {
		for(Decleration declNode : decl) {
			if (declNode.getId() == id) {
				return declNode.getType();
			}
		}
		return null;
	}
	public boolean checkType(String type, String id) {
		for(Decleration declNode : decl) {
			if (declNode.getId().equals(id)) {
				/* IntExpression Type set */
				if (type == "IntExpr") {
					type = "int";
				} else if (type == "BoolExpr") {
					type = "boolean";
				} else if (type == "digit") {
					type = "int";
				} else if (type == "stringExpr") {
					type = "string";
				}
				
				if (declNode.getType().equals(type)) {
					return true;
				}
			}
		}
		return false;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public ArrayList<Decleration> getDecl() {
		return decl;
	}

	public void setDecl(ArrayList<Decleration> newDecl) {
		decl = newDecl;
	}

	public int getLevelStart() {
		return levelStart;
	}

	public void setLevelStart(int levelStart) {
		this.levelStart = levelStart;
	}

	public int getLevelEnd() {
		return levelEnd;
	}

	public void setLevelEnd(int levelEnd) {
		this.levelEnd = levelEnd;
	}
}
