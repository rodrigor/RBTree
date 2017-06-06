package com.rodrigor.alg;

/**
 * Thrown to indicate that the input file does not have the appropriate format.
 * This exception stores the line number from where the exception was thrown.
 * 
 * @author Rodrigo Rebouças de Almeida (http://github.com/rodrigor)
 * @date Jun, 2017
 */
public class FileFormatException extends Exception {

	private static final long serialVersionUID = -6417469234919734432L;

	private int lineNumber;

	public FileFormatException(int line, String msg) {
		super(msg);
		this.lineNumber = line;
	}

	public int getLineNumber() {
		return lineNumber;
	}

}
