package com.rodrigor.rbtree;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBTreeBrunoTest {

	/*
	 * teste 0 abuso 1 carro 1 doce 1 gola 1 gola 0 palhaço 1 taturana 1 pacote
	 * 1 abusado 0 bolha 1 fussura 1
	 */
	@Test
	public void testInsertBrunoCase() {
		RBTree tree = new RBTree();
		try {
			tree.delete("teste");
			fail("'teste' can't exist!");
		} catch (ElementNotFoundException e) {
			assertNotNull(e);
		}
		tree.insert("abuso");
		tree.insert("carro");
		tree.insert("doce");
		tree.insert("gola");
		try {
			tree.delete("gola");
		} catch (ElementNotFoundException e) {
			fail("Problem with 'gola' deletion");
		}
		tree.insert("palhaço");
		tree.insert("taturana");
		tree.insert("pacote");
		try {
			tree.delete("abusado");
			fail("'abusado' can't exist!");
		} catch (ElementNotFoundException e) {
			assertNotNull(e);
		}
		tree.insert("bolha");
		tree.insert("fussura");
	}

}
