package parser.objects;
import java.util.ArrayList;

import lexer.Token;
public class Program {
	private static Block block;
	public Program() {
		block = new Block();
	}
	
	public static boolean validateProgram(ArrayList<Token> tokens, int currIndex) {
		if (block.validateBlock(tokens, 0)) {
			if (tokens.get(block.getPostIndex()).getTokenType() == "endOfFile") {
				return true;
			} else {
				return false;
			}	
		} else {
			return false;
		}
	}

	public static Block getBlock() {
		return block;
	}

	public static void setBlock(Block block) {
		Program.block = block;
	}
}
