package com.rodrigor.rbtree;

import static org.junit.Assert.assertEquals;
import static com.rodrigor.rbtree.RBTestTool.toStr;

import java.util.List;

import org.junit.Test;

/**
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBTreeInsertTest {

	private RBTestTool util = new RBTestTool("/tests/com/rodrigor/rbtree/testcases/");

	@Test
	public void testInsertFewElements() {
		RBTree tree = new RBTree();
		tree.insert("a");
		List<String> elements = tree.getOrderedValuesList();
		assertEquals(1, elements.size());
		assertEquals("a", elements.get(0));

	}

	@Test
	public void testInsertTwoElements() {
		RBTree tree = new RBTree();
		tree.insert("c");
		tree.insert("a");
		List<String> elements = tree.getOrderedValuesList();
		assertEquals(2, elements.size());
		assertEquals("a", elements.get(0));
		assertEquals("c", elements.get(1));

	}

	@Test
	public void testInsertABCElements() {
		RBTree tree = new RBTree();
		tree.insert("a");
		tree.insert("b");
		tree.insert("c");

		List<String> elements = tree.getOrderedValuesList();
		assertEquals(elements.size(), 3);
		assertEquals("a", elements.get(0));
		assertEquals("b", elements.get(1));
		assertEquals("c", elements.get(2));

		List<List<RBElement>> matrix = tree.treeMatrix();
		assertEquals(matrix.size(), 2); // altura 2
		assertEquals(toStr("b", "a", "NIL", "c", "B"), toStr(matrix.get(0).get(0)));
		assertEquals(toStr("a", "NIL", "b", "NIL", "R"), toStr(matrix.get(1).get(0)));
		assertEquals(toStr("c", "NIL", "b", "NIL", "R"), toStr(matrix.get(1).get(1)));

	}

	@Test
	public void testInsertCBAElements() {
		RBTree tree = new RBTree();
		tree.insert("c");
		tree.insert("b");
		tree.insert("a");

		List<String> elements = tree.getOrderedValuesList();
		assertEquals(3, elements.size());
		assertEquals("a", elements.get(0));
		assertEquals("b", elements.get(1));
		assertEquals("c", elements.get(2));

		List<List<RBElement>> matrix = tree.treeMatrix();
		assertEquals(matrix.size(), 2); // altura 2
		assertEquals(toStr("b", "a", "NIL", "c", "B"), toStr(matrix.get(0).get(0)));
		assertEquals(toStr("a", "NIL", "b", "NIL", "R"), toStr(matrix.get(1).get(0)));
		assertEquals(toStr("c", "NIL", "b", "NIL", "R"), toStr(matrix.get(1).get(1)));

	}

	@Test
	public void test8inverseElements() {

		RBTree tree = util.treeFrom("8inverse.txt");

		List<String> elements = tree.getOrderedValuesList();
		assertEquals(8, elements.size());
		assertEquals("a", elements.get(0));
		assertEquals("b", elements.get(1));
		assertEquals("c", elements.get(2));
		assertEquals("d", elements.get(3));
		assertEquals("e", elements.get(4));
		assertEquals("f", elements.get(5));
		assertEquals("g", elements.get(6));
		assertEquals("h", elements.get(7));

		List<List<RBElement>> matrix = tree.treeMatrix();
		assertEquals(matrix.size(), 4); // altura 2
		assertEquals(toStr("e", "c", "NIL", "g", "B"), toStr(matrix.get(0).get(0)));

		assertEquals(toStr("c", "b", "e", "d", "R"), toStr(matrix.get(1).get(0)));
		assertEquals(toStr("g", "f", "e", "h", "R"), toStr(matrix.get(1).get(1)));

		assertEquals(toStr("b", "a", "c", "NIL", "B"), toStr(matrix.get(2).get(0)));
		assertEquals(toStr("d", "NIL", "c", "NIL", "B"), toStr(matrix.get(2).get(1)));
		assertEquals(toStr("f", "NIL", "g", "NIL", "B"), toStr(matrix.get(2).get(2)));
		assertEquals(toStr("h", "NIL", "g", "NIL", "B"), toStr(matrix.get(2).get(3)));

		assertEquals(toStr("a", "NIL", "b", "NIL", "R"), toStr(matrix.get(3).get(0)));

	}

	@Test
	public void test8orderElements() {

		RBTree tree = util.treeFrom("8order.txt");

		List<String> elements = tree.getOrderedValuesList();
		assertEquals(8, elements.size());
		assertEquals("a", elements.get(0));
		assertEquals("b", elements.get(1));
		assertEquals("c", elements.get(2));
		assertEquals("d", elements.get(3));
		assertEquals("e", elements.get(4));
		assertEquals("f", elements.get(5));
		assertEquals("g", elements.get(6));
		assertEquals("h", elements.get(7));

		List<List<RBElement>> matrix = tree.treeMatrix();
		assertEquals(matrix.size(), 4); // altura 2
		assertEquals(toStr("e", "c", "NIL", "g", "B"), toStr(matrix.get(0).get(0)));

		assertEquals(toStr("c", "b", "e", "d", "R"), toStr(matrix.get(1).get(0)));
		assertEquals(toStr("g", "f", "e", "h", "R"), toStr(matrix.get(1).get(1)));

		assertEquals(toStr("b", "a", "c", "NIL", "B"), toStr(matrix.get(2).get(0)));
		assertEquals(toStr("d", "NIL", "c", "NIL", "B"), toStr(matrix.get(2).get(1)));
		assertEquals(toStr("f", "NIL", "g", "NIL", "B"), toStr(matrix.get(2).get(2)));
		assertEquals(toStr("h", "NIL", "g", "NIL", "B"), toStr(matrix.get(2).get(3)));

		assertEquals(toStr("a", "NIL", "b", "NIL", "R"), toStr(matrix.get(3).get(0)));

	}

	/**
	 * Elements:"03", "07", "10", "12", "14", "15", "16", "17", "19", "20",
	 * "21", "23", "26", "28", "30", "35", "38", "39", "41", "47" 
	 * 0: 12_(17)^NIL_23 [B] - 
	 * 1: 07_(12)^17_15 [R] - 20_(23)^17_35 [R] - 
	 * 2: 03_(07)^12_10 [B] - 14_(15)^12_16 [B] - 19_(20)^23_21 [B] - 28_(35)^23_39 [B] - 
	 * 3: NIL_(03)^07_NIL [B] - NIL_(10)^07_NIL [B] - NIL_(14)^15_NIL [B] - NIL_(16)^15_NIL [B] - NIL_(19)^20_NIL [B] - NIL_(21)^20_NIL [B] - 26_(28)^35_30 [R] - 38_(39)^35_41 [R] - 
	 * 4: NIL_(26)^28_NIL [B] - NIL_(30)^28_NIL [B] - NIL_(38)^39_NIL [B] - NIL_(41)^39_47 [B] - 
	 * 5: NIL_(47)^41_NIL [R] - 
	 */
	@Test
	public void testCormenFig131() {

		RBTree tree = util.treeFrom("cormenFig13.1.txt");

		List<String> elements = tree.getOrderedValuesList();
		String[] values = new String[] { "03", "07", "10", "12", "14", "15", "16", "17", "19", "20", "21", "23", "26",
				"28", "30", "35", "38", "39", "41", "47" };
		for (int i = 0; i < values.length; i++) {
			assertEquals(values[i], elements.get(i));
		}

	}

}
