package org.fit.enums;

public enum LoanStatus {

	EXISTS("Loan already exists");
	
	private String label;

	private LoanStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
