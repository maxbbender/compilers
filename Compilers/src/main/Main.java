package main;
import java.util.Iterator;
import lexer.LexerMain;
import lexer.Token;
import parser.ParserMain;
import parser.ParserTerminalList;
import java.util.logging.Logger;

public class Main {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LexerMain lexer = new LexerMain(args[0]);
		ParserMain parser;
		
		if (!lexer.getMyRegex().hasErrors()) {
			System.out.println("-------------------");
			System.out.println("-------LEXER-------");
			System.out.println("-------------------");
			//System.out.println(lexer.getMyRegex().getFullRegex().toString());
			for  (Iterator<Token> it = lexer.getMyRegex().getTokens().iterator(); it.hasNext();) {
				System.out.println(it.next().getFullToken());
			}
			
			
			
			/* Parser */
			System.out.println("--------------------");
			System.out.println("-------PARSER-------");
			System.out.println("--------------------");
			parser = new ParserMain(lexer.getMyRegex().getTokens());
			
		}
		
		
	}

}
