package parser.objects;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexer.Token;
import parser.ParserMain;

public class AssignmentStatement {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static int postIndex;
	private static Id id;
	private static Expr expr;
	
	public AssignmentStatement() {
		
	}
	
	public static boolean validate(ArrayList<Token> tokens, int currIndex) {
		ParserMain.list.addNode("STATEMENT_ASSIGNMENT", "assignment");
		ParserMain.list.inc();
		int level = ParserMain.list.getInc();
		id = new Id();
		expr = new Expr();
		if (id.validate(tokens, currIndex)) {
			if (tokens.get(id.getPostIndex()).getTokenType() == "assignment") {
				ParserMain.list.setInc(level);
				if (expr.validateExpr(tokens, id.getPostIndex() + 1)) {
					ParserMain.list.setInc(level);
					log.info("ASSIGNMENT STATEMENT");
					postIndex = expr.getPostIndex();
					return true;
				} else {
					log.severe("ERROR LINE " + tokens.get(id.getPostIndex()).getTokenLineNum() + ": Invalid Expression near " + tokens.get(id.getPostIndex() + 1).getTokenValue());
					return false; //ERROR on EXPR
				}
				
			} else {
				log.severe("ERROR LINE " + tokens.get(id.getPostIndex()).getTokenLineNum() + ": Invalid Assignment Statement near " + tokens.get(id.getPostIndex()).getTokenValue());
				return false; //ERROR ON assignment
			}
		} else {
			//log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid ID " + tokens.get(currIndex).getTokenValue());
			return false; //ERROR ON ID
		}
	}
	
	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		AssignmentStatement.postIndex = postIndex;
	}

	public static Id getId() {
		return id;
	}

	public static void setId(Id id) {
		AssignmentStatement.id = id;
	}

	public static Expr getExpr() {
		return expr;
	}

	public static void setExpr(Expr expr) {
		AssignmentStatement.expr = expr;
	}
}
