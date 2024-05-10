package org.fit.enums;

public enum BookStatus {
	EXISTS("Book already exists");
	
	private String label;

	private BookStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
