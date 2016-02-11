package lexer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;

public class LexerMain {
	private static RegexHelper myRegex;
	
	/*public static void main(String[] args) {
		try {
			String fileContents = fileProcess("lib/code.txt"); 
			//myRegexHelper help = new myRegexHelper(fileContents);
			for  (Iterator<Token> it = myRegex.getTokens().iterator(); it.hasNext();) {
				System.out.println(it.next().getFullToken());
			}
		} catch (IOException e) { 
			System.out.println("Error on the fileProcess" + e);
		}
	}*/
	
	public LexerMain(String fileURL) {
		myRegex = new RegexHelper();
		try { 
			fileProcess(fileURL);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	

	public static void fileProcess(String fileName) throws IOException {
		int lineNum = 1;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String prevLine = null;
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null ) {
				prevLine = line;
				myRegex.parseInput(line, lineNum);
				line = br.readLine();
				lineNum++;
			}
			myRegex.checkEndOfFile(prevLine);
			
		} finally {
			br.close();
		}
	}
	
	public static RegexHelper getMyRegex() {
		return myRegex;
	}

	public static void setMyRegex(RegexHelper myRegex) {
		LexerMain.myRegex = myRegex;
	}
}
