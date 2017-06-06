package com.rodrigor.rbtree;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBTreeDeleteTest {

	private RBTestTool util = new RBTestTool("/tests/com/rodrigor/rbtree/testcases/");

	private void checkNil(){
		assertTrue("ALERT! the Nil color was violated! Nil isn't BLACK!",Nil.instance().isBLACK());
		//assertEquals("ALERT! the Nil parent was violated!",Nil.instance(),Nil.instance().getParent());
		assertEquals("ALERT! the Nil left was violated!",Nil.instance(),Nil.instance().getLeft());
		assertEquals("ALERT! the Nil right was violated!",Nil.instance(),Nil.instance().getRight());
		assertEquals("ALERT! the Nil value was violated!","NIL",Nil.instance().getValue());
	}
	
	@Test
	public void testDeleteOneElement() {
		RBTree tree = new RBTree();
		tree.insert("a");
		List<RBElement> elements = tree.getOrderedElements();
		assertEquals(1, elements.size());
		assertEquals("a", elements.get(0).getValue());
		try {
			tree.delete("a");
		} catch (ElementNotFoundException e) {
			fail("There shoud be an element 'a'!");
		}
		assertNull(tree.search("a"));
		checkNil();
	}

	@Test
	public void test3elements() {
		RBTree tree = util.treeFrom(new String[] { "2", "1", "3" });
		try {
			tree.delete("2");
		} catch (ElementNotFoundException e) {
			fail("Element should exist: 2");
		}
		assertFalse(tree.contains("2"));

		try {
			tree.delete("3");
		} catch (ElementNotFoundException e) {
			fail("Element should exist: 3");
		}
		assertFalse(tree.contains("3"));

		try {
			tree.delete("1");
		} catch (ElementNotFoundException e) {
			fail("Element should exist: 1");
		}
		assertFalse(tree.contains("1"));
		checkNil();
	}

	@Test
	public void test3elementsCase2() {
		RBTree tree = util.treeFrom(new String[] { "2", "1", "3" });

		try {
			tree.delete("3");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 3");
		}
		assertFalse(tree.contains("3"));

		try {
			tree.delete("1");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 1");
		}
		assertFalse(tree.contains("1"));

		try {
			tree.delete("2");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 2");
		}
		assertFalse(tree.contains("2"));
		checkNil();
	}

	@Test
	public void testRemoveRedLeaf() {
		RBTree tree = util.treeFrom(new String[] { "2", "1", "3", "4" });

		try {
			tree.delete("4");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 3");
		}
		assertFalse(tree.contains("4"));
		checkNil();
	}

	/*
	 * {"8","7","6","5","4","3","2","1"}: 
	 * 0: 3_(5)^NIL_7 [B] - 
	 * 1: 2_(3)^5_4 [R] - 6_(7)^5_8 [R] - 
	 * 2: 1_(2)^3_NIL [B] - NIL_(4)^3_NIL [B] - NIL_(6)^7_NIL [B] - NIL_(8)^7_NIL [B] - 
	 * 3: NIL_(1)^2_NIL [R] -
	 */
	@Test
	public void testDeleteLeftNil() {
		String[] values = new String[] { "8", "7", "6", "5", "4", "3", "2", "1" };
		RBTree tree = util.treeFrom(values);
		System.out.println(tree);
		try {
			tree.delete("4");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 4");
		}
		assertNull(tree.search("4"));
		checkNil();
	}

	/*
	 * {"8","7","6","5","4","3","2","1"}: 
	 * 0: 3_(5)^NIL_7 [B] - 
	 * 1: 2_(3)^5_4 [R] - 6_(7)^5_8 [R] - 
	 * 2: 1_(2)^3_NIL [B] - NIL_(4)^3_NIL [B] - NIL_(6)^7_NIL [B] - NIL_(8)^7_NIL [B] - 
	 * 3: NIL_(1)^2_NIL [R] -
	 */
	@Test
	public void testDeleteROOT() {
		String[] values = new String[] { "8", "7", "6", "5", "4", "3", "2", "1" };
		RBTree tree = util.treeFrom(values);

		try {
			tree.delete("5");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 5");
		}
		assertNull(tree.search("5"));
		checkNil();
	}

	/*
	 * {"8","7","6","5","4","3","2","1"}: 
	 * 0: 3_(5)^NIL_7 [B] - 
	 * 1: 2_(3)^5_4 [R] - 6_(7)^5_8 [R] - 
	 * 2: 1_(2)^3_NIL [B] - NIL_(4)^3_NIL [B] - NIL_(6)^7_NIL [B] - NIL_(8)^7_NIL [B] - 
	 * 3: NIL_(1)^2_NIL [R] -
	 */
	@Test
	public void testDelete4() {
		String[] values = new String[] { "8", "7", "6", "5", "4", "3", "2", "1" };
		RBTree tree = util.treeFrom(values);

		try {
			tree.delete("4");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 4");
		}
		assertNull(tree.search("4"));
		checkNil();
	}

	/*
	 * {"8","7","6","5","4","3","2","1"}: 
	 * 0: 3_(5)^NIL_7 [B] - 
	 * 1: 2_(3)^5_4 [R] - 6_(7)^5_8 [R] - 
	 * 2: 1_(2)^3_NIL [B] - NIL_(4)^3_NIL [B] - NIL_(6)^7_NIL [B] - NIL_(8)^7_NIL [B] - 
	 * 3: NIL_(1)^2_NIL [R] -
	 */
	@Test
	public void testDeleteREDLEAF() {
		String[] values = new String[] { "8", "7", "6", "5", "4", "3", "2", "1" };
		RBTree tree = util.treeFrom(values);
		System.out.println("----BEGIN redLeaf ---");
		System.out.println(tree);
		try {
			tree.delete("1");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 1");
		}
		System.out.println(tree);
		System.out.println("----- END redleaf----");
		assertNull(tree.search("1"));
		checkNil();
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
	public void testDeleteROOTCaseCormen() {
		String[] values = new String[] { "03", "07", "10", "12", "14", "15", "16", "17", "19", "20", "21", "23", "26",
				"28", "30", "35", "38", "39", "41", "47" };
		RBTree tree = util.treeFrom(values);

		System.out.println("----BEGIN root Cormen ---");
		System.out.println(tree);
		try {
			tree.delete("17");
		} catch (ElementNotFoundException e) {
			fail("Element must exist: 17");
		}
		System.out.println(tree);
		System.out.println("----- END root----");
		assertNull(tree.search("17"));
		Nil.instance().setBLACK();
		checkNil();
	}

}
