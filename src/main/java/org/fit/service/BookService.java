package org.fit.service;

import java.util.List;

import org.fit.enums.BookStatus;
import org.fit.exception.BookException;
import org.fit.model.Book;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class BookService {

	@Inject
	private EntityManager em;
	
	@Transactional
	public Book createBook(Book b) throws BookException{
		List<Book> books = getAllBooks();
		
		if(books.contains(b)) {
			throw new BookException(BookStatus.EXISTS.getLabel());
		}
		return em.merge(b);
	}
	
	@Transactional
	public List<Book> getAllBooks(){
		List<Book> books = em.createNamedQuery(Book.GET_ALL_BOOKS, Book.class).getResultList();
		return books;
	}
}
