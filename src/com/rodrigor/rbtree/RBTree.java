package com.rodrigor.rbtree;

import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of a Red-Black Tree, as described in Cormen, Introduction
 * to Algorithms 3rd edition. This implementation has the limitation of storing
 * only String type values. A future implementation will be able to store
 * generic type values.
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBTree {

	private RBElement root;
	private final RBElement nil = Nil.instance();

	public RBTree() {
		this.root = nil;
	}

	public void insert(String str) {
		this.insert(new RBElement(str));
	}

	
	/**
	 * Implementation of the RB-Insert(T,z) function, as in
	 * Cormen, Introduction to Algorithms 3rd ed. Cap. 13.
	 * @param z element to be inserted
	 */
	private void insert(RBElement z) {
		RBElement y = nil;
		RBElement x = root;
		while (x != nil) {
			y = x;
			if (z.lessThan(x))
				x = x.getLeft();
			else
				x = x.getRight();
		}
		z.setParent(y);
		if (y.isNil())
			root = z;
		else if (z.lessThan(y))
			y.setLeft(z);
		else
			y.setRight(z);

		z.setLeft(nil);
		z.setRight(nil);
		z.setRED();
		RBInsertFixup(z);
	}

	/**
	 * Complete implementation of the RB-Insert-Fixup(T,z) function, as in
	 * Cormen, Introduction to Algorithms 3rd ed. Cap. 13.
	 * This function fixes the red-black properties which may be violated by
	 * the insertion of a new element.
	 * 
	 * @param z element recently added
	 */
	private void RBInsertFixup(RBElement z) {
		while (z.getParent().isRED()) {

			if (z.getParent() == z.getParent().getParent().getLeft()) {
				RBElement y = z.getParent().getParent().getRight();
				if (y.isRED()) {
					z.getParent().setBLACK();
					y.setBLACK();
					z.getParent().getParent().setRED();
					z = z.getParent().getParent();
				} else {
					if (z == z.getParent().getRight()) {
						z = z.getParent();
						leftRotate(z);
					}
					z.getParent().setBLACK();
					z.getParent().getParent().setRED();
					rightRotate(z.getParent().getParent());
				}
			} else {
				RBElement y = z.getParent().getParent().getLeft();
				if (y.isRED()) {
					z.getParent().setBLACK();
					y.setBLACK();
					z.getParent().getParent().setRED();
					z = z.getParent().getParent();
				} else {
					if (z == z.getParent().getLeft()) {
						z = z.getParent();
						rightRotate(z);
					}
					z.getParent().setBLACK();
					z.getParent().getParent().setRED();
					leftRotate(z.getParent().getParent());
				}
			}
		}
		root.setBLACK();
	}

	/**
	 * Implementation of the LEFT-Rotate(T,x) function shown in
	 * Cormen, Introduction to Algorithms 3rd ed. Cap. 13.
	 * @param x element to be rotated left
	 */
	private void leftRotate(RBElement x) {
		RBElement y = x.getRight();
		x.setRight(y.getLeft());
		if (!y.getLeft().isNil())
			y.getLeft().setParent(x);
		y.setParent(x.getParent());
		if (x.getParent().isNil())
			root = y;
		else if (x == x.getParent().getLeft())
			x.getParent().setLeft(y);
		else
			x.getParent().setRight(y);
		y.setLeft(x);
		x.setParent(y);
	}

	/**
	 * Implementation of the RIGHT-Rotate(T,x) function shown in
	 * Cormen, Introduction to Algorithms 3rd ed. Cap. 13.
	 * @param x element to be rotated right
	 */
	private void rightRotate(RBElement x) {
		if (x.isNil())
			return; // check!
		RBElement y = x.getLeft();
		x.setLeft(y.getRight());
		if (!y.getRight().isNil())
			y.getRight().setParent(x);
		y.setParent(x.getParent());
		if (x.getParent().isNil())
			root = y;
		else if (x == x.getParent().getRight())
			x.getParent().setRight(y);
		else
			x.getParent().setLeft(y);
		y.setRight(x);
		x.setParent(y);

	}

	/**
	 * Removes the element which contains the provided string.
	 * 
	 * @param str value to be removed from RBTree
	 * @throws ElementNotFoundException if there is no element with the provided string
	 */
	public void delete(String str) throws ElementNotFoundException {
		RBElement e = this.search(str);
		if (e == null)
			throw new ElementNotFoundException("There is no element with value \"" + str + "\"");
		RBDelete(e);

	}

	/**
	 * Implementation of the RB-Delete(T,z) function shown in
	 * Cormen, Introduction to Algorithms 3rd ed. Cap. 13.
	 * 
	 * @param z element to be removed from the RBTree
	 */
	private void RBDelete(RBElement z) {
		if (Nil.instance().isRED()) // code to prevent bugs
			throw new IllegalStateException("Nil cannot be RED!!");
		RBElement y = z;
		RBColor yOrigColor = y.getColor();
		RBElement x;
		if (z.getLeft().isNil()) {
			x = z.getRight();
			RBTransplant(z, z.getRight());
		} else if (z.getRight().isNil()) {
			x = z.getLeft();
			RBTransplant(z, z.getLeft());
		} else {
			y = treeMin(z.getRight());
			yOrigColor = y.getColor();
			x = y.getRight();
			if (y.getParent() == z)
				x.setParent(y);
			else {
				RBTransplant(y, y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			RBTransplant(z, y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
			y.setColor(z.getColor());
		}
		if (yOrigColor == RBColor.BLACK)
			RBDeleteFixup(x);

	}

	/**
	 * Implementation of the RB-Transplant(T,u,v) function shown in
	 * Cormen, Introduction to Algorithms 3rd ed. Cap. 13.
	 */
	private void RBTransplant(RBElement u, RBElement v) {
		if (u.getParent().isNil())
			root = v;
		else if (u == u.getParent().getLeft())
			u.getParent().setLeft(v);
		else
			u.getParent().setRight(v);
		v.setParent(u.getParent());
	}

	/**
	 * Returns the minimun element of a sub-tree
	 * @param e the root element of the sub-tree
	 * @return the minimun element
	 */
	private RBElement treeMin(RBElement e) {
		RBElement min = e;
		while (!min.getLeft().isNil())
			min = min.getLeft();
		return min;
	}

	/**
	 * Complete implementation of the RB-Delete-Fixup(T,x) function shown in
	 * Cormen, Introduction to Algorithms 3rd ed. Cap. 13.
	 * This method restores the RBTree properties, due to the removal of
	 * an element
	 */
	private void RBDeleteFixup(RBElement x) {
		while ((x != root) && x.isBLACK()) {
			if (x == x.getParent().getLeft()) {
				RBElement w = x.getParent().getRight();
				if (w.isRED()) {
					w.setBLACK();
					x.getParent().setRED();
					leftRotate(x.getParent());
					w = x.getParent().getRight();
				}
				if (w.getLeft().isBLACK() && w.getRight().isBLACK()) {
					w.setRED();
					x = x.getParent();
				} else {
					if (w.getRight().isBLACK()) {
						w.getLeft().setBLACK();
						w.setRED();
						rightRotate(w);
						w = x.getParent().getRight(); // checar
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setBLACK();
					w.getRight().setBLACK();
					leftRotate(x.getParent());
					x = root;
				}
			} else {
				RBElement w = x.getParent().getLeft();
				if (w.isRED()) {
					w.setBLACK();
					x.getParent().setRED();
					rightRotate(x.getParent());
					w = x.getParent().getLeft();
				}
				if (w.getRight().isBLACK() && w.getLeft().isBLACK()) {
					w.setRED();
					x = x.getParent();
				} else {
					if (!w.isNil() && w.getLeft().isBLACK()) {
						w.getRight().setBLACK();
						w.setRED();
						leftRotate(w);
						w = x.getParent().getLeft();
					}
					w.setColor(x.getParent().getColor());
					x.getParent().setBLACK();
					w.getLeft().setBLACK();
					rightRotate(x.getParent());
					x = root;
				}
			}
		}
		x.setBLACK();
	}

	public boolean contains(String str) {
		return search(str) != null;
	}

	/**
	 * Binary search for a string
	 * 
	 * @param str String to search for
	 * @return the element which has the found string, or null case the string is not found.
	 */
	public RBElement search(String str) {
		RBElement e = root;
		while (e != nil) {
			String eVal = e.getValue();
			if (str.equals(eVal))
				return e;
			if (str.compareTo(eVal) < 0)
				e = e.getLeft();
			else
				e = e.getRight();
		}
		return null;
	}

	/**
	 * Returns the black height, as specified by Cormen in Introduction to Algorithms 3rd ed.
	 * The Black height of the tree is the number of black nodes on a simple path from, 
	 * but not including, the root node down to the leaf.
	 * 
	 * @return the black height from the root node.
	 */
	public int blackHeight() {
		return blackHeight(root);
	}

	/**
	 * Since the RB Tree has the property where for each node, all simple paths
	 * from the node to descendant leaves contain the same number of black
	 * nodes, to calculate the Black Height from any element, this method
	 * descends on the left elements until it reaches the Nil element, and
	 * counts the black nodes.
	 * 
	 * @param e the element from which the height must be calculated.
	 * @return the black height of the element e
	 */
	public int blackHeight(RBElement e) {
		if (e.isNil())
			return 0;
		int h = 1; // counting with the black leaf.
		RBElement x = e.getLeft();
		while (!x.isNil()) {
			if (x.isBLACK())
				h++;
			x = x.getLeft();
		}
		return h;
	}

	/**
	 * Returns the values stored in the RBTree in order.
	 * @return a list with the value strings stored in the RBTree.
	 */
	public List<String> getOrderedValuesList() {
		LinkedList<String> values = new LinkedList<String>();
		List<RBElement> elements = getOrderedElements();
		for (RBElement e : elements)
			values.add(e.getValue());
		return values;
	}

	/**
	 * Returns the elements stored in the RBTree in order.
	 * @return a list with the elements stored in the RBTree.
	 */
	public List<RBElement> getOrderedElements() {
		LinkedList<RBElement> elements = new LinkedList<RBElement>();
		walkThrough(root, elements);
		return elements;
	}

	/**
	 * Performs a recursive in-order walk in the tree and adds the
	 * visited elements to the list.
	 * 
	 * @param element element to be evaluated
	 * @param elements ordered list of the RBTree elements
	 */
	private void walkThrough(RBElement element, LinkedList<RBElement> elements) {
		if (element.isNil())
			return;
		walkThrough(element.getLeft(), elements);
		elements.add(element);
		walkThrough(element.getRight(), elements);
	}

	/**
	 * Returns the list of elements stored in the RBTree, in pre-order.
	 * 
	 * @return the list of elements stored in the RBTree
	 */
	public List<RBElement> getPreOrderedElements() {
		LinkedList<RBElement> elements = new LinkedList<RBElement>();
		preOrderWalk(root, elements);
		return elements;
	}


	/**
	 * Performs a recursive pre-order walk in the tree and adds the
	 * visited elements to the list, in order.
	 * 
	 * @param element element to be evaluated
	 * @param values List of elements to be populated
	 */
	private void preOrderWalk(RBElement element, LinkedList<RBElement> elements) {
		if (element.isNil())
			return;
		elements.add(element);
		preOrderWalk(element.getLeft(), elements);
		preOrderWalk(element.getRight(), elements);
	}

	/**
	 * Returns a matrix of elements from the RBTree.
	 * Each line of the matrix contains a list of all elements from the same level
	 * This method is used to buld a String representation of the RBTree (toString),
	 * and for some test classes.
	 * 
	 * @return the list of lists of RBElements stored in the RBTree
	 */
	List<List<RBElement>> treeMatrix() {
		List<List<RBElement>> values = new LinkedList<List<RBElement>>();
		buildMatrixFromRBTree(root, 0, values);
		return values;
	}

	/**
	 * Recursive method to build a matrix of elements from the RBTree.
	 * Each line of the matrix contains a list of all elements from the same level
	 * 
	 * @param element current element
	 * @param treeLevel tree level
	 * @param matrix the list of lists of RBElements from the RBTree
	 */
	private void buildMatrixFromRBTree(RBElement element, int treeLevel, List<List<RBElement>> matrix) {
		if (element.isNil())
			return;
		if (matrix.size() < (treeLevel + 1))
			matrix.add(new LinkedList<RBElement>());
		matrix.get(treeLevel).add(element);
		buildMatrixFromRBTree(element.getLeft(), treeLevel + 1, matrix);
		buildMatrixFromRBTree(element.getRight(), treeLevel + 1, matrix);
	}

	/**
	 * Returns the String representation of this RBTree.
	 *  
	 */
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		List<List<RBElement>> matrix = treeMatrix();
		int altura = 0;
		for (List<RBElement> line : matrix) {
			str.append(altura + ": ");
			for (RBElement element : line)
				str.append(element + " - ");
			str.append("\n");
			altura++;
		}
		return str.toString();
	}

}
