package main;
import java.io.File;
import java.util.Iterator;

import lexer.LexerMain;
import lexer.Token;
import parser.ParserMain;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
public class Main {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static LogFormatter formatter;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numberOfPrograms = 0;
		boolean verbose = false;
		String temp = null;
		String temp2 = null;
		boolean valid = false;
		LexerMain lexer = null;
		ParserMain parser;
		Scanner input = new Scanner(System.in);
		
		/* What is the file path for code? */
		while (!valid) {
			System.out.println("What is the filePath for the code?");
			temp = input.next();
			File f = new File(temp);
			if(f.exists() && !f.isDirectory()) { 
			    valid = true;
			} else {
				System.out.println("File is not found please try again");
			}
		}
		valid = false; 
		/* Are we going to run in Verbose? */
		while (!valid) {
			System.out.println("Should we run in verbose? (y/n)");
			temp2 = input.next();
			if (temp2.equals("y")) {
				log.setLevel(Level.INFO);
				valid = true;
			} else if (temp2.equals("n")) {
				log.setLevel(Level.WARNING);
				valid = true;
			} else {
				System.out.println("Invalid input, try again");
			}
		}
		/* Initiate the Lexer with the input */
		lexer = new LexerMain(temp);
		
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
