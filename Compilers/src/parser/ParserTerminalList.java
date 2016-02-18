package parser;
import java.util.ArrayList;
import parser.TerminalNode;
public class ParserTerminalList {
	private static ArrayList<TerminalNode> list;
	
	public ParserTerminalList () {
		list = new ArrayList();
	}
	
	public ParserTerminalList (ArrayList<TerminalNode> newParserList) {
		list = newParserList;
	}
	
	public static void addNode(TerminalNode newNode) {
		list.add(newNode);
	}
	
	public static void addNode(String nodeType, String nodeValue, int nodeLevel) {
		TerminalNode node;
		list.add(node = new TerminalNode(nodeType, nodeValue, nodeLevel));
	}
}
