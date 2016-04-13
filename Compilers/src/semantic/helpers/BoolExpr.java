package semantic.helpers;

import java.util.ArrayList;

public class BoolExpr {
	private ArrayList<String> expr;
	
	public BoolExpr() {
		expr = new ArrayList();
	}
	
	public void addExpr(String newExpr) {
		expr.add(newExpr);
	}
	
	public void addExprs(ArrayList<String> newExpr) {
		expr.addAll(newExpr);
	}

	public ArrayList<String> getExpr() {
		return expr;
	}

	public void setExpr(ArrayList<String> expr) {
		this.expr = expr;
	}
}
