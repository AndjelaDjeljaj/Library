package org.fit.enums;

public enum AuthorStatus {
	EXISTS("Author already exists");
	
	private String label;

	private AuthorStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
