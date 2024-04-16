package org.fit.exception;

public class AuthorException extends Exception {

	private String message;

	public AuthorException(String message) {
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
