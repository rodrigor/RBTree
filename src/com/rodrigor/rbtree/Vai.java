package com.rodrigor.rbtree;

/**
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class Vai {

	public static void main(String[] args) {

		RBTestTool util = new RBTestTool("/tests/com/rodrigor/rbtree/testcases/");
		RBTree tree = util.treeFrom("words.txt");

		// String[] values = new String[]{"2","1","3"};
		// String[] values = new String[]{"8","7","6","5","4","3","2","1"};
		// String[] values = new String[]{"03", "07", "10", "12", "14", "15",
		// "16", "17", "19", "20", "21", "23", "26", "28", "30", "35", "38",
		// "39", "41", "47"};
		// Using the array

		// RBTree tree = new RBTree();
		// for(int i = 0; i < values.length; i++) // in order
		// //for(int i = (values.length-1); i >= 0; i--) // inverse order
		// tree.insert(values[i]);

		System.out.println(tree.blackHeight());
		try {
			tree.delete("Trollius");
		} catch (ElementNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(tree.contains("Trollius"));

		System.out.println("-----");

		// List<String> elements = tree.orderedValuesList();
		// for(String element : elements)
		// System.out.println(element);
		//
	}

}
