package org.fit.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fit.enums.BookStatus;
import org.fit.exception.BookException;
import org.fit.model.Author;
import org.fit.model.Book;
import org.fit.model.Genre;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class BookService {

	@Inject
	private EntityManager em;
	
	@Transactional
	public Book createBook(Book b, Long authorId, List<Long> genreIds) throws BookException{
		
		Author author = em.find(Author.class, authorId);
		if(author == null) {
			throw new BookException("Autor sa id-jem "+ authorId + " nije pronadjen.");
		}
		
		Set<Genre> genres = new HashSet<>();
		for (Long genreId : genreIds) {
			Genre genre = em.find(Genre.class, genreId);
			if(genre == null) {
				throw new BookException("Zanr sa id-jem " + genreId + " nije pronadjen.");
			}
			genres.add(genre);
		}
		
		b.setAuthor(author);
		b.setGenres(genres);
		
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
	
	@Transactional
	public void deleteBookById(Long bookId) throws BookException {
		Book book = em.find(Book.class, bookId);
		if(book == null) {
			throw new BookException("Book with id " + bookId + " not found.");
		}
		em.remove(book);
	}
	
	
	
	
	
	
	
}
