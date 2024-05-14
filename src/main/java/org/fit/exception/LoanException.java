package org.fit.exception;

public class LoanException extends Exception {
	
	private String message;


	public LoanException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
