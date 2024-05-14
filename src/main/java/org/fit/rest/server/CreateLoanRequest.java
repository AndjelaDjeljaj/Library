package org.fit.rest.server;

import org.fit.model.Loan;

public class CreateLoanRequest {

	private Loan loan;
    private Long userId;
    private Long bookId;
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	@Override
	public String toString() {
		return "CreateLoanRequest [loan=" + loan + ", userId=" + userId + ", bookId=" + bookId + "]";
	}
    
}
