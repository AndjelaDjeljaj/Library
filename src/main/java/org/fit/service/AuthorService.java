package org.fit.service;


import java.util.List;

import org.fit.enums.AuthorStatus;
import org.fit.exception.AuthorException;
import org.fit.model.Author;
import org.fit.model.Book;
import org.fit.model.Genre;
import org.fit.model.Loan;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Dependent
public class AuthorService {

	@Inject
	private EntityManager em;
	
	@Transactional
	public Author createAuthor(Author a) throws AuthorException {
		List<Author> authors = getAllAuthors();
		
		if(authors.contains(a)) {
			throw new AuthorException(AuthorStatus.EXISTS.getLabel());
		}
		return em.merge(a);
	}
	
	@Transactional
	public List<Author> getAllAuthors() {
		List<Author> authors = em.createNamedQuery(Author.GET_ALL_AUTHORS, Author.class).getResultList();
		return authors;
	}
	
	@Transactional
	public void deleteAuthorById(Long authorId) throws AuthorException {
		Author author = em.find(Author.class, authorId);
		if(author == null) {
			throw new AuthorException("Author with id " + authorId + " not found.");
		}
		
        // Disassociate books from the author and delete the books
        for (Book book : author.getBooks()) {
            // Do not remove genre associations
           
        	  for (Loan loan : book.getLoans()) {
                  em.remove(loan);
              }
              // Remove loan associations
              book.getLoans().clear();
            
            for (Genre genre : book.getGenres()) {
				genre.getBooks().remove(book);
				em.merge(genre);
			}
            
            book.getGenres().clear();
            
          

            // Remove the book
            em.remove(book);
      }

        // Clear the author's books collection
        author.getBooks().clear();
        em.merge(author);

        // Remove the author
        em.remove(author);
	}
}
