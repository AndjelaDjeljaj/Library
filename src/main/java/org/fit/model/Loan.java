package org.fit.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = Loan.GET_ALL_LOANS, query = "Select l from Loan l") })
public class Loan {

	public static final String GET_ALL_LOANS = "getAllLoans";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn(name = "book_id")
	private Book book;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Users user;

	@JsonIgnore
	private Date loanDate;

	private Date returnDate;

	@JsonIgnore
	private boolean returned = false; //default value

	@JsonIgnore
	private double totalPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", book=" + book + ", user=" + user + ", loanDate=" + loanDate + ", returnDate="
				+ returnDate + ", returned=" + returned + ", totalPrice=" + totalPrice + "]";
	}


	public void calculateTotalprice() {
		LocalDate startDate = loanDate.toLocalDate();
		LocalDate endDate = returnDate.toLocalDate();     
		
		Long days = ChronoUnit.DAYS.between(startDate, endDate);
		int months = (int) Math.ceil(days / 30.0);
		this.totalPrice = book.getPricePerMonth() * months;
	}

	
	
}
