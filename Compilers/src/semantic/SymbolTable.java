package semantic;

import java.util.ArrayList;
import java.util.Iterator;

import parser.TerminalNode;
import semantic.helpers.Scope;

public class SymbolTable {
	private static ArrayList<Scope> scope;
	private static int currScope;
	public SymbolTable(AST ast) {
		Scope tempScope;
		scope = new ArrayList();
		ArrayList<TerminalNode> astList = ast.getAst();
		for(TerminalNode node : astList){
			scope.add(tempScope = new Scope(currScope));
		}
			
	}
}

