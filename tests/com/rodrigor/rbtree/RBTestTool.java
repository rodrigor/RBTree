package com.rodrigor.rbtree;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Class to help with tests.
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Apr 30, 2017
 */
public class RBTestTool {

	private File projectBase;
	private String path;

	public RBTestTool(String path) {
		this.path = path;
		try {
			File file = new File(this.getClass().getResource("/").toURI());
			this.projectBase = new File(file.getParent());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}

	public RBTree treeFrom(String fileName) {
		List<String> lines = RBInputReader.readFrom(getFile(fileName));
		RBTree tree = new RBTree();
		for (String line : lines)
			tree.insert(line);
		return tree;
	}

	public RBTree treeFrom(String[] array) {
		RBTree tree = new RBTree();
		for (int i = 0; i < array.length; i++)
			tree.insert(array[i]);
		return tree;
	}

	public File getFile(String fileName) {
		return new File(this.getAbsolutePath(fileName));
	}

	public File getProjectBasePath() {
		return this.projectBase;
	}

	public String getAbsolutePath(String fileName) {
		try {
			return this.projectBase.getCanonicalPath() + "/" + path + fileName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static String toStr(RBElement e) {
		return toStr(e.getValue(), e.getLeft().getValue(), e.getParent().getValue(), e.getRight().getValue(),
				(e.isBLACK() ? "B" : "R"));
	}

	static String toStr(String value, String left, String parent, String right, String color) {
		return "\"" + value + "\" (" + left + "," + parent + "," + right + ")[" + color + "]";
	}

}
