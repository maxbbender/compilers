package main;
import java.util.Iterator;

import lexer.LexerMain;
import lexer.Token;
import parser.ParserMain;

import java.util.logging.Level;
import java.util.logging.Logger;
public class Main {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static LogFormatter formatter;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numberOfPrograms = 0;
		LexerMain lexer = new LexerMain(args[0]);
		ParserMain parser;
		log.setLevel(Level.WARNING);
		//formatter = new LogFormatter();
		//log.set(formatter);
		if (!lexer.getMyRegex().hasErrors()) {
			System.out.println("-------------------");
			System.out.println("-------LEXER-------");
			System.out.println("-------------------");
			//System.out.println(lexer.getMyRegex().getFullRegex().toString());
			for  (Iterator<Token> it = lexer.getMyRegex().getTokens().iterator(); it.hasNext();) {
				Token tempToken = it.next();
				/* Find the number of programs */
				if (tempToken.getTokenType() == "endOfFile") {
					numberOfPrograms++;
				}
				
				System.out.println(tempToken.getFullToken());
			}
			System.out.println("Number of Programs: " + numberOfPrograms);
			
		
			
			
			
			/* Parser */
			System.out.println("--------------------");
			System.out.println("-------PARSER-------");
			System.out.println("--------------------");
			parser = new ParserMain(lexer.getMyRegex().getTokens(), numberOfPrograms);
			
			
		}
		
		
	}

}
