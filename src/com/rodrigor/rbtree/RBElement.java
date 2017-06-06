package com.rodrigor.rbtree;

/**
 * Represents a node in the BT Tree.
 * Each node has a left node, right node, parent node and stores a value.
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBElement {

	private String value;
	private RBColor color;

	RBElement parent;
	RBElement left, right;

	public RBElement(String value) {
		this.value = value;
	}

	public void setLeft(RBElement e) {
		this.left = e;
	}

	public RBElement getLeft() {
		return this.left;
	}

	public void setRight(RBElement e) {
		this.right = e;
	}

	public RBElement getRight() {
		return this.right;
	}

	public RBElement getParent() {
		return this.parent;
	}

	public void setParent(RBElement e) {
		this.parent = e;
	}

	public boolean isRED() {
		return this.color == RBColor.RED;
	}

	public boolean isBLACK() {
		return !isRED();
	}

	public void setBLACK() {
		this.color = RBColor.BLACK;
	}

	public void setRED() {
		this.color = RBColor.RED;
	}

	public void setColor(RBColor color) {
		this.color = color;
	}

	public RBColor getColor() {
		return color;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isNil() {
		return false;
	}

	/**
	 * Compares the values.
	 * 
	 * @param e RBElement to be compared with
	 * @return True if this is less than param, False otherwise.
	 */
	public boolean lessThan(RBElement e) {
		return this.value.compareTo(e.value) < 0;
	}


	/**
	 * String representation of an element, in the form:
	 * leftNodeValue_(thisValue)^parentValue_rightNodeValue [color]
	 * Example: a black node with parent "p", left node "l", right node "r" and value "v" is represented as:
	 * l_(v)^p_r [B]
	 */
	@Override
	public String toString() {
		// return "\""+this.value+"\" ("+this.left.value+",
		// "+this.parent.value+", "+this.right.value+") ["+this.colorStr()+"]";
		return this.left.value + "_(" + this.value + ")^" + this.parent.value + "_" + this.right.value + " ["
				+ (isBLACK() ? "B" : "R") + "]";
	}

}
