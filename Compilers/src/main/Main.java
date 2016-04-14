package main;
import java.io.File;
import java.util.Iterator;
import lexer.LexerMain;
import lexer.Token;
import parser.ParserMain;
import parser.ParserTerminalList;
import semantic.AST;
import semantic.SymbolTable;

import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Main {
	private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static LogFormatter formatter;
	private static boolean verbose = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean toContinue = true;
		int numberOfPrograms = 0;
		String temp = null;
		String temp2 = null;
		boolean valid = false;
		boolean valid2 = false;
		LexerMain lexer = null;
		ParserMain parser;
		Scanner input = new Scanner(System.in);
		
		if (args[0] != null && args[1] != null) {
			valid2 = true;
		}
		/* Logger/Verbose Setup */
		log.setUseParentHandlers(false);
		Handler[] handlers = log.getHandlers();
		for(Handler handler : handlers) {
		    log.removeHandler(handler);
		}
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new LogFormatter());
		log.addHandler(handler);
		
		/* What is the file path for code? */
		while (!valid) {
			if (!valid2) {
				System.out.println("What is the filePath for the code?");
				temp = input.next();
				if (temp.equals("d")) {
					temp = "lib\\code.txt";
				}
				File f = new File(temp);
				if(f.exists() && !f.isDirectory()) { 
				    valid = true;
				} else {
					valid2 = false;
					System.out.println("File is not found please try again");
				}
			} else {
				File f = new File(args[0]);
				if(f.exists() && !f.isDirectory()) {
					System.out.println("File Found at path " + args[0]);
					temp = args[0];
				    valid = true;
				} else {
					valid2 = false;
					System.out.println("File is not found please try again");
				}
			}
		}
		valid = false; 
		/* Are we going to run in Verbose? */
		while (!valid) {
			if (!valid2) {
				System.out.println("Should we run in verbose? (y/n)");
				temp2 = input.next();
				if (temp2.equals("y")) {
					log.setLevel(Level.INFO);
					verbose = true;
					valid = true;
				} else if (temp2.equals("n")) {
					log.setLevel(Level.WARNING);
					valid = true;
				} else {
					System.out.println("Invalid input, try again");
				}
			} else {
				temp2 = args[1];
				if (temp2.equals("y")) {
					log.setLevel(Level.INFO);
					verbose = true;
					valid = true;
				} else if (temp2.equals("n")) {
					log.setLevel(Level.WARNING);
					valid = true;
				} else {
					valid2 = false;
					System.out.println("Invalid input, try again");
				}
			}
		}
		/* Initiate the Lexer with the input */
		lexer = new LexerMain(temp);
		
		
		/* Start the Lexer if there are no errors */
		/* Checks quotes/unknowns and others */
		if (!lexer.getMyRegex().hasErrors()) {
			System.out.println("-------------------");
			System.out.println("-------LEXER-------");
			System.out.println("-------------------");
			
			if (verbose) {
				System.out.println(lexer.getMyRegex().getFullRegex().toString());
			}
			
			for  (Iterator<Token> it = lexer.getMyRegex().getTokens().iterator(); it.hasNext();) {
				Token tempToken = it.next();
				/* Find the number of programs */
				if (tempToken.getTokenType() == "endOfFile") {
					numberOfPrograms++;
				}
				if (verbose) {
					System.out.println(tempToken.getFullToken());
				}
				
			}
			if (verbose) {
				System.out.println("Number of Programs: " + numberOfPrograms);
			}
			System.out.println("Lexer Finished");
		} else {
			toContinue = false; 
		}
		
		if (toContinue) {
			/* Parser */
			System.out.println("--------------------");
			System.out.println("-------PARSER-------");
			System.out.println("--------------------");

			parser = new ParserMain(lexer.getMyRegex().getTokens(), numberOfPrograms);
			System.out.println("Parser Finished");
			System.out.println("#####################");
			/* is there no errors in the parseing? Are we good to go? */
			toContinue = parser.isToContinue();
			
			if (toContinue) {
				System.out.println("CST Generation Started");
				if (verbose) {
					parser.printCst();
				}
				
//				/*AST GENERATION */
//				System.out.println("AST Generation started");
//				AST ast = new AST(parser.getList());
//				System.out.println("AST init");
//				ast.run();
//				System.out.println("AST created");
//				
//				if (verbose) {
//					System.out.println("----AST----");
//					ast.printList();
//				}
//				
//				System.out.println("Symbol Table Generation");
//				SymbolTable symbolTable = new SymbolTable(ast);
//				
//				if (verbose) {
//					System.out.println("----Symbol Table-----");
//					symbolTable.printSymbolTable();
//				}
			}		
		}
	}	
}
