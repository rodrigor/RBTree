package com.rodrigor.alg;

/**
 * This class represents an instruction from the project specification.
 * The specification determines that each word must be inserted or deleted from the RBTree.
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class Instruction {

	public enum Command {
		INSERT, DELETE
	};

	public String word;
	public Command command;

	public Instruction(Command c, String word) {
		this.command = c;
		this.word = word;
	}

}
