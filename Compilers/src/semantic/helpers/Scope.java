package semantic.helpers;

import java.util.ArrayList;

public class Scope {
	private int scopeNum;
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
		scopeNum = 1;
		levelStart = -1;
		levelEnd = -1;
		decl = new ArrayList();
		assign = new ArrayList();
		currChild = 0;
		printed = false;
	}
	
	public Scope(int nlevelStart, int newScopeNum) {
		scopeNum = newScopeNum;
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
	
	public boolean isInitialized(String id) {
		for (Decleration tempDecl : decl) {
			if (tempDecl.getId().equals(id)) {
				if (tempDecl.isInitialized()) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	
	public void init(String id) {
		for (Decleration tempDecl : decl) {
			if (tempDecl.getId().equals(id)) {
				tempDecl.init();
				break;
			}
		}
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
	
	public void print() {
		StringBuilder output = new StringBuilder();
		
		if (children.size() > 0) { 
			System.out.println("    Children:");
			for (Scope child : children) {
				System.out.println("        Scope " + child.getScopeNum());
			}
		} else {
			System.out.println("    Children: None");

		}
		
		if (parent != null) { 
			System.out.println("    Parent: Scope " + parent.getScopeNum());

		} else { 
			System.out.println("    Parent: NULL");
		}
		
		System.out.println("    Declarations");
		printDeclerations();
		
		
	}
	
	public void printDeclerations() {
		for (Decleration temp : decl) {
			System.out.println("        " + temp.printDecl());
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
	
	public Decleration getDeclarationId(String id) {
		for (Decleration tempDecl : decl) {
			if (tempDecl.getId().equals(id) ) { 
				return tempDecl;
			}
		}
		return null;
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
			if (declNode.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	public String getType(String id) {
		for(Decleration declNode : decl) {
			if (declNode.getId().equals(id)) {
				return declNode.getType();
			}
		}
		return null;
	}
	
	public ArrayList<Decleration> getUnInitialized() {
		ArrayList<Decleration> temp = new ArrayList<Decleration>();
		for (Decleration tempDecl : decl) {
			if (!tempDecl.isInitialized()) {
				temp.add(tempDecl);
			}
		}
		return temp;
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
		return scopeNum;
	}

	public void setScope(int scope) {
		this.scopeNum = scope;
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

	public int getScopeNum() {
		return scopeNum;
	}

	public void setScopeNum(int scopeNum) {
		this.scopeNum = scopeNum;
	}
}
