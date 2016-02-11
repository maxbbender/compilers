package parser.objects;

import java.util.ArrayList;

import lexer.Token;

public class Statement {
	private static PrintStatement printStatement;
	private static AssignmentStatement assignmentStatement;
	private static VarDecl varDecl;
	private static WhileStatement whileStatement;
	private static IfStatement ifStatement;
	private static Block block;
	public Statement() {
		printStatement = new PrintStatement();
		assignmentStatement = new AssignmentStatement();
		varDecl = new VarDecl();
		whileStatement = new WhileStatement();
		ifStatement = new IfStatement();
		block = new Block();
	}
	
	public static boolean validateStatement(ArrayList<Token> tokens, int currIndex){ 
		
	}
}
