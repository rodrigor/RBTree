package com.rodrigor.rbtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBTreeSearchTest {

	private RBTestTool util = new RBTestTool("/tests/com/rodrigor/rbtree/testcases/");

	@Test
	public void testEmpty() {
		RBTree tree = new RBTree();
		RBElement e2 = tree.search("i");
		assertNull(e2);

	}

	@Test
	public void testOneElement() {
		RBTree tree = new RBTree();
		tree.insert("a");
		RBElement e = tree.search("a");
		assertNotNull(e);
		assertEquals("a", e.getValue());

	}

	@Test
	public void testSearchFind() {
		RBTree tree = util.treeFrom("8order.txt");
		RBElement e = tree.search("c");
		assertNotNull(e);
		assertEquals("c", e.getValue());

		RBElement e2 = tree.search("i");
		assertNull(e2);

	}

	@Test
	public void testSearchNotFound() {
		RBTree tree = util.treeFrom("8order.txt");

		RBElement e2 = tree.search("i");
		assertNull(e2);

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
	public void testSearchCormenFig131EMOrdem() {
		String[] values = new String[] { "03", "07", "10", "12", "14", "15", "16", "17", "19", "20", "21", "23", "26",
				"28", "30", "35", "38", "39", "41", "47" };

		RBTree tree = util.treeFrom(values);

		for (int i = 0; i < values.length; i++) {
			assertEquals(values[i], tree.search(values[i]).getValue());
		}
		RBElement e20 = tree.search("20");
		assertTrue(e20.isBLACK());
		RBElement e19 = tree.search("19");
		assertTrue(e19.isBLACK());
		assertEquals(e19, e20.getLeft());
		assertEquals(e20, e19.getParent());

	}

}
