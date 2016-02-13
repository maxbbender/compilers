package parser.objects;
import java.util.ArrayList;

import lexer.Token;
public class Program {
	private Block block;
	public Program() {
		block = new Block();
	}
	
	public static boolean validateProgram(ArrayList<Token> tokens) {
		if (block.validateBlock(tokens)) {
			return true;
		}
	}
}
