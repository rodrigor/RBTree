package com.rodrigor.rbtree;

/**
 * This class represents the T.nil sentinel described by Cormen at "Introduction to Algorithms 3rd edition, chapter 13".
 * It is a RBElement
 * Cormen says "we use the one sentinel T:nil to represent all the NILs—all leaves and the root’s parent."
 * This implementation ensures that there is only one instance of the Nil element in the RBTree.
 * Obs.: This is an weak singleton implementation, but enough to use on the RBTree.
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class Nil extends RBElement {

	private static RBElement singleton;

	public static RBElement instance() {
		if (singleton == null)
			singleton = new Nil("NIL");
		return singleton;
	}

	private Nil(String str) {
		super(str);
		super.setBLACK();
		parent = this;
		left = this;
		right = this;
	}


	@Override
	public boolean isNil() {
		return true;
	}

}
