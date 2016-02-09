package lexer;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class LexerMain {

	public static void main(String[] args) {
		try {
			String[] splitString = fileProcess("lib/code.txt"); 
			
			for (String string : splitString) {
				//if ()
			}
			
		} catch (IOException e) { 
			System.out.println("Error on the fileProcess" + e);
		}
		
		RegexHelper help = new RegexHelper("hi");
		
		
	}
	
	public static String[] fileProcess(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		try {
			String[] splitString;
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null ) {
				sb.append(line);
				sb.append(" ");
				line = br.readLine();
			}
			return sb.toString().split(" +");
		} finally {
			br.close();
		}
	}
	
	
	
	
	
	public static void checkForKeywords() {
		//Pattern keywords = Pattern.compile(")
	}
}
