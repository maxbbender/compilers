package parser;
import java.util.ArrayList;
import java.util.Iterator;

import lexer.Token;
import parser.TerminalNode;
public class ParserTerminalList {
	private ArrayList<TerminalNode> list;
	private ArrayList<TerminalNode> tempList;
	private int currIndent;
	
	public ParserTerminalList () {
		list = new ArrayList();
		currIndent = 1;
	}
	
	public ParserTerminalList (ArrayList<TerminalNode> newParserList) {
		list = newParserList;
	}
	
	public void addNode(TerminalNode newNode) {
		list.add(newNode);
	}
	
	public void addNode(String nodeType, String nodeValue) {
		TerminalNode node;
		list.add(node = new TerminalNode(nodeType, nodeValue, currIndent));
	}
	
	public ArrayList<TerminalNode> clone() {
		return list;
	}
	
	public void addTempNode(String nodeType, String nodeValue) {
		TerminalNode node = new TerminalNode(nodeType, nodeValue, currIndent);
		tempList.add(node);
	}
	
	public void clearTemp() {
		tempList.clear();
	}
	
	public void removeRange(int low, int high) {
		for (int lowVal = low; lowVal < high; lowVal++) {
			list.remove(low);
		}
	}
	
	public void printList() {
		for (Iterator<TerminalNode> it = list.iterator(); it.hasNext();) {
			System.out.println(it.next().getTerminalNode());
		}
	}
	
	public int getSize() {
		return list.size();
	}
	public void inc() {
		currIndent++;
	}
	
	public void deinc() {
		currIndent--;
	}
	
	public int getInc() {
		return currIndent;
	}
	
	public void setInc(int newInc) {
		currIndent = newInc;
	}
	public String getLast() {
		return list.get(list.size()-1).getObjectType();
	}
	public TerminalNode get(int index) {
		return list.get(index);
	}
	public ArrayList<TerminalNode> getList() {
		return list;
	}
}
