package semantic.helpers;

import java.util.ArrayList;

public class Scope {
	private int scope;
	private static ArrayList<Decleration> decl;
	private static ArrayList<Assignment> assign;
	private int levelStart;
	private int levelEnd;
	
	public Scope() {
		scope = -1;
		levelStart = -1;
		levelEnd = -1;
		decl = new ArrayList();
		assign = new ArrayList();
	}
	
	public Scope(int nlevelStart) {
		assign = new ArrayList();
		decl = new ArrayList();
		levelStart = nlevelStart;
		levelEnd = -1;
	}
	
	public static void addAssignment(Assignment newAss) {
		assign.add(newAss);
	}
	
	public static void addAssignment(String id, int newInt) {
		Assignment temp = new Assignment(id, newInt);
		assign.add(temp);
	}
	
	public static void addAssignment(String id, String newString) {
		Assignment temp = new Assignment(id, newString);
		assign.add(temp);
	}
	
	public static void addAssignment(String id, Boolean newBool) {
		Assignment temp = new Assignment(id, newBool);
		assign.add(temp);
	}
	
	public static void addAssignment(String id, IntExpr expr) {
		Assignment temp = new Assignment(id, expr);
		assign.add(temp);
	}
	
	public static void addAssignment(String id, BoolExpr expr) {
		Assignment temp = new Assignment(id, expr);
		assign.add(temp);
	}
	
	public static void addDecl(Decleration newDecl) {
		decl.add(newDecl);
	}
	
	public static void addDecl(String type, String id) {
		Decleration temp = new Decleration(type,id);
		decl.add(temp);
	}
	
	public static boolean checkDeclaration(String id) {
		for(Decleration declNode : decl) {
			if (declNode.getId() == id){
				return true;
			}
		} 
		return false;
	}
	
	public static boolean checkType(String type, String id) {
		for(Decleration declNode : decl) {
			if (declNode.getId() == id) {
				/* IntExpression Type set */
				if (type == "IntExpr") {
					type = "digit";
				} else if (type == "BoolExpr") {
					type = "boolean";
				}
				
				if (declNode.getType() == type) {
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

	public static ArrayList<Decleration> getDecl() {
		return decl;
	}

	public static void setDecl(ArrayList<Decleration> decl) {
		Scope.decl = decl;
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
