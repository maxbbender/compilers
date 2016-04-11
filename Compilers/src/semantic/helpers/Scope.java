package semantic.helpers;

import java.util.ArrayList;

public class Scope {
	private int scope;
	private static ArrayList<Decleration> decl;
	private int levelStart;
	private int levelEnd;
	
	public Scope() {
		scope = -1;
		levelStart = -1;
		levelEnd = -1;
		decl = new ArrayList();
	}
	
	public Scope(int newScope, int nlevelStart) {
		scope = newScope;
		decl = new ArrayList();
		levelStart = nlevelStart;
		levelEnd = -1;
	}
	
	public static void addDecl(Decleration newDecl) {
		decl.add(newDecl);
	}
	
	public static void addDecl(String type, String id) {
		Decleration temp = new Decleration(type,id);
		decl.add(temp);
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
