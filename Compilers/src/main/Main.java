package main;
import java.util.Iterator;

import lexer.LexerMain;
import lexer.Token;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LexerMain lexer = new LexerMain(args[0]);
		
		if (!lexer.getMyRegex().hasErrors()) {
			System.out.println("-------------------");
			System.out.println("-------LEXER-------");
			System.out.println("-------------------");
			for  (Iterator<Token> it = lexer.getMyRegex().getTokens().iterator(); it.hasNext();) {
				System.out.println(it.next().getFullToken());
			}
		}
	}

}