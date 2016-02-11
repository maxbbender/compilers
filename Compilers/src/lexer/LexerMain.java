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
			String fileContents = fileProcess("lib/code.txt"); 
			RegexHelper help = new RegexHelper(fileContents);
		} catch (IOException e) { 
			System.out.println("Error on the fileProcess" + e);
		}
	}
	
	public static String fileProcess(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null ) {
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}
