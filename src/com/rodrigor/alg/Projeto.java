package com.rodrigor.alg;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.rodrigor.alg.Instruction.Command;
import com.rodrigor.rbtree.ElementNotFoundException;
import com.rodrigor.rbtree.RBElement;
import com.rodrigor.rbtree.RBInputReader;
import com.rodrigor.rbtree.RBTree;

/**
 * This is the main project class.
 * It reads the input file, checks its format and runs its instructions (insert or delete each word from file).
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class Projeto {

	private static final int WORD_MAX_LENGTH = 20; // from project specification

	public static void main(String[] args) {
		if (args.length == 0) {
			printHelp();
			return;
		}

		String arg = args[0];

		File file = new File(arg);
		if (!file.exists()) {
			System.out.println("File not found:\n" + file);
			return;
		}

		Projeto p = new Projeto();
		p.process(file);

	}


	private static void printHelp() {
		println("> Red-Black Tree implementation");
		println("Author: Rodrigo Rebouças de Almeida (http://rodrigor.com)");
		println();
		println("Use: java com.rodrigor.alg.Projeto [fileName]");
	}

	private static void println() {
		println("");
	}

	private static void println(String txt) {
		System.out.println(txt);
	}

	public Projeto() {
	}

	private void process(File file) {
		obeyInstructions(retrieveInstructionsFrom(file));
	}
	

	private List<Instruction> retrieveInstructionsFrom(File file) {
		List<Instruction> instructions = new LinkedList<Instruction>();
		try {
			List<String> lines = RBInputReader.readFrom(file);
			int lineNumber = 0;
			for (String line : lines) {
				lineNumber++;
				StringTokenizer tokens = new StringTokenizer(line, " ");

				if (tokens.countTokens() != 2)
					throw new FileFormatException(lineNumber,
							"Each line must have only one word followed by 0 or 1, separated by an empty space");

				String word = tokens.nextToken();
				if (word.length() > WORD_MAX_LENGTH)
					throw new FileFormatException(lineNumber, "Invalid word \"" + word
							+ "\". Each word must have at least " + WORD_MAX_LENGTH + " characters");

				String number = tokens.nextToken();
				int num = 0;
				try {
					num = Integer.parseInt(number);
					if ((num != 0) & (num != 1))
						throw new Exception();
				} catch (Exception e) {
					throw new FileFormatException(lineNumber, "Invalid number (" + num
							+ "). Each word must be followed by a number 0 or 1, separated by an empty space.\nExample:\n\tbola 1");
				}
				Command c;
				if (num == 1)
					c = Command.INSERT;
				else if (num == 0)
					c = Command.DELETE;
				else
					throw new RuntimeException("BUG! I should never reach this point!");

				instructions.add(new Instruction(c, word));
			}
		} catch (FileFormatException e) {
			printFileFormatMsg(e.getLineNumber(), e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected error:\nSomething is wrong!\n" + e.getMessage());
		}
		return instructions;
	}

	/**
	 * This method follows the project specification.
	 * - For each line the program must insert or delete the respecting word
	 * - After the end of the instructions, the functions RBPrint and RBCheck must be invoked.
	 * @param instructions Set of instructions from the input file
	 */
	private void obeyInstructions(List<Instruction> instructions) {
		RBTree tree = new RBTree();
		for (Instruction instruction : instructions) {
			switch (instruction.command) {
			case INSERT:
				insert(instruction.word, tree);
				break;
			case DELETE:
				delete(instruction.word, tree);
				break;
			}
		}
		RBPrint(tree);
		RBCheck(tree);

	}

	/**
	 * This method inserts a word into the RBTree.
	 * A message is printed if the tree already contains the word.
	 * 
	 * @param word Word that must be inserted
	 * @param tree RBTree to be used
	 */
	private void insert(String word, RBTree tree) {
		if (tree.contains(word))
			println("A Árvore já possui uma palavra \"" + word + "\"");
		else
			tree.insert(word);
	}

	/**
	 * Deletes a word from the tree.
	 * A message is printed if the word does not exist.
	 * @param word word to be deleted
	 * @param tree RBTree to be used
	 */
	private void delete(String word, RBTree tree) {
		if (!tree.contains(word)) {
			println("A palavra " + word + " foi removida anteriormente ou nao foi inserida.\n");
			return;
		}

		try {
			System.out.printf("Removendo a palavra %s\n", word);
			tree.delete(word);
		} catch (ElementNotFoundException e) {
			println("OOPS! I should not be here!");
			e.printStackTrace();
		}
		RBPrint(tree);
		RBCheck(tree);
	}

	/**
	 * This method follows the project specification.
	 * It prints the set of the RBTree elements in pre-order.
	 * Each element is printed with this format:
	 * (parentValue, elementValue, elementColor, elementBlackHeight, leftValue, rightValue)
	 * @param tree RBTree to be used
	 */
	private void RBCheck(RBTree tree) {
		for (RBElement e : tree.getPreOrderedElements()) {
			System.out.printf("(%s, %s, %s, %d, %s, %s)\n\n", e.getParent().getValue(), e.getValue(),
					(e.isBLACK() ? "preto" : "vermelho"), tree.blackHeight(e), e.getLeft().getValue(),
					e.getRight().getValue());
		}

	}

	/**
	 * This method follows the project specification
	 * It prints the list of RBTree element values in ascendent order.
	 * @param tree RBTree to be used
	 */
	private void RBPrint(RBTree tree) {
		for (String value : tree.getOrderedValuesList())
			System.out.printf("%s, ", value);
		System.out.println();

	}

	/**
	 * Prints an file formating error message.
	 * 
	 * @param lNumber line number
	 * @param msg message to be printed
	 */
	private void printFileFormatMsg(int lNumber, String msg) {
		println("ERROR: The input file format is invalid.");
		println("Line: " + lNumber);
		println(" " + msg);

	}

}
