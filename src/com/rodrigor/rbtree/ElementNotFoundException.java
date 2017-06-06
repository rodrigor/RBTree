package com.rodrigor.rbtree;

/**
 * Thrown when the RBTree tries to delete an element which does not exist.
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class ElementNotFoundException extends Exception {

	private static final long serialVersionUID = 6570885574766607569L;

	public ElementNotFoundException(String msg) {
		super(msg);
	}

}
