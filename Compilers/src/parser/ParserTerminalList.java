package parser;
import java.util.ArrayList;
import java.util.Iterator;

import lexer.Token;
import parser.TerminalNode;
public class ParserTerminalList {
	private static ArrayList<TerminalNode> list;
	private static ArrayList<TerminalNode> tempList;
	private static int currIndent;
	
	public ParserTerminalList () {
		list = new ArrayList();
		currIndent = 1;
	}
	
	public ParserTerminalList (ArrayList<TerminalNode> newParserList) {
		list = newParserList;
	}
	
	public static void addNode(TerminalNode newNode) {
		list.add(newNode);
	}
	
	public static void addNode(String nodeType, String nodeValue) {
		TerminalNode node;
		list.add(node = new TerminalNode(nodeType, nodeValue, currIndent));
	}
	
	public ArrayList<TerminalNode> clone() {
		return list;
	}
	
	public static void addTempNode(String nodeType, String nodeValue) {
		TerminalNode node = new TerminalNode(nodeType, nodeValue, currIndent);
		tempList.add(node);
	}
	
	public static void clearTemp() {
		tempList.clear();
	}
	
	public static void removeRange(int low, int high) {
		for (int lowVal = low; lowVal < high; lowVal++) {
			list.remove(low);
		}
	}
	
	public static void printList() {
		for (Iterator<TerminalNode> it = list.iterator(); it.hasNext();) {
			System.out.println(it.next().getTerminalNode());
		}
	}
	
	public static int getSize() {
		return list.size();
	}
	public static void inc() {
		currIndent++;
	}
	
	public static void deinc() {
		currIndent--;
	}
	
	public static int getInc() {
		return currIndent;
	}
	
	public static void setInc(int newInc) {
		currIndent = newInc;
	}
	public static String getLast() {
		return list.get(list.size()-1).getObjectType();
	}
}
