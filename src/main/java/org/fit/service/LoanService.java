package org.fit.service;

import java.util.List;

import org.fit.enums.LoanStatus;
import org.fit.exception.LoanException;
import org.fit.model.Book;
import org.fit.model.Loan;
import org.fit.model.Users;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class LoanService {

	@Inject
	private EntityManager em;
	
	@Transactional
	public Loan createLoan(Loan l, Long userId, Long bookId) throws LoanException{
		
		Users user = em.find(Users.class, userId);
		if (user == null) {
			throw new LoanException("User with id " + userId + " not found.");
		}
		
		Book book = em.find(Book.class, bookId);
		if (book == null) {
			throw new LoanException("Book with id " + bookId + " not found.");
		}
		
		if(book.getQuantity() <= 0) {
			throw new LoanException("Book with id " + bookId + " is out of stock");
		}
		
		book.setQuantity(book.getQuantity() - 1);
		
		l.setBook(book);
		l.setUser(user);		
		
		List<Loan> loans = getAllLoans();
		
		if(loans.contains(l)) {
			throw new LoanException(LoanStatus.EXISTS.getLabel());
		}
		return em.merge(l);
	}
	
	@Transactional
	public List<Loan> getAllLoans(){
		List<Loan> loans = em.createNamedQuery(Loan.GET_ALL_LOANS, Loan.class).getResultList();
		return loans;
	}
	
	@Transactional
	public void deleteLoanById(Long loandId) throws LoanException{
		Loan loan = em.find(Loan.class, loandId);
		if(loan == null) {
			throw new LoanException("Loan with Id " + loandId + " not found.");
		}
		em.remove(loan);
	}
	
	
	
}