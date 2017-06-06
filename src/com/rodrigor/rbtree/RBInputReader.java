package com.rodrigor.rbtree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Read lines from a given file.
 * Ignore lines beginning with the "#" character.
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class RBInputReader {

	public static List<String> readFrom(File file) {
		LinkedList<String> list = new LinkedList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("#"))
					list.add(line);
			}
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
