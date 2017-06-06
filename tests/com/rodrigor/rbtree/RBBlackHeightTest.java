package com.rodrigor.rbtree;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBBlackHeightTest {
	private RBTestTool util = new RBTestTool("/tests/com/rodrigor/rbtree/testcases/");

	@Test
	public void testArvoreVazia() {

		RBTree tree = new RBTree();
		assertEquals(0, tree.blackHeight());

	}

	@Test
	public void testArvore1Elemento() {

		RBTree tree = new RBTree();
		tree.insert("a");
		assertEquals(1, tree.blackHeight());

	}

	@Test
	public void testCormenFig131() {

		RBTree tree = util.treeFrom("cormenFig13.1.txt");
		assertEquals(3, tree.blackHeight());
		RBElement e12 = tree.search("12");
		assertTrue(e12.isRED());
		assertEquals(3, tree.blackHeight(e12));

		RBElement e3 = tree.search("03");
		assertTrue(e3.isBLACK());
		assertEquals(1, tree.blackHeight(e3));

		RBElement e39 = tree.search("39");
		assertTrue(e39.isRED());
		assertEquals(2, tree.blackHeight(e39));

	}

}
