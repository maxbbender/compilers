package parser.objects;
import java.util.ArrayList;
import java.util.logging.Logger;

import parser.ParserMain;
import parser.ParserTerminalList;

import lexer.Token;
public class Program {
	private static Block block;
	private static int postIndex;
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//private static ParserTerminalList list = new ParserTerminalList();
	public Program(ParserTerminalList newList) {
		//list = newList;
	}
	
	public static boolean validateProgram(ArrayList<Token> tokens) {
		ParserMain.list.addNode("PROGRAM", "program");
		ParserMain.list.inc();
		block = new Block();
		if (block.validateBlock(tokens, currIndex)) {
			currIndex = block.getPostIndex();
			if (tokens.get(currIndex).getTokenType() == "endOfFile") {
				postIndex = block.getPostIndex() + 1;
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

	public static int getPostIndex() {
		return postIndex;
	}

	public static void setPostIndex(int postIndex) {
		Program.postIndex = postIndex;
	}

	public static Logger getLog() {
		return log;
	}

	public static Block getBlock() {
		return block;
	}

	public static void setBlock(Block block) {
		Program.block = block;
	}
}
