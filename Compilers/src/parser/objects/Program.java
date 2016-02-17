package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;

import lexer.Token;
public class Program {
	private static Block block;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public Program() {
		
	}
	
	public static boolean validateProgram(ArrayList<Token> tokens) {
		block = new Block();
		int currIndex = -1;
		if (block.validateBlock(tokens, 0)) {
			currIndex = block.getPostIndex();
			if (tokens.get(currIndex).getTokenType() == "endOfFile") {
				log.info("PROGRAM");
				return true;
			} else {
				log.severe("ERROR LINE " + tokens.get(currIndex).getTokenLineNum() + ": Invalid EndOfFile near " + tokens.get(currIndex).getTokenValue());
				return false; // ERROR EndOfFile
			}	
		} else {
			log.severe("ERROR LINE " + tokens.get(0).getTokenLineNum() + ": Invalid Block near " + tokens.get(0).getTokenValue());
			return false; // ERROR Block
		}
	}

	public static Block getBlock() {
		return block;
	}

	public static void setBlock(Block block) {
		Program.block = block;
	}
}