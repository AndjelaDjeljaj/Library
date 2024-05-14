package org.fit.rest.server;

import java.util.List;

import org.fit.model.Book;

public class CreateBookRequest {

	private Book book;
	private Long authorId;
	private List<Long> genreIds;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public List<Long> getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(List<Long> genreIds) {
		this.genreIds = genreIds;
	}

	@Override
	public String toString() {
		return "CreateBookRequest [book=" + book + ", authorId=" + authorId + ", genreIds=" + genreIds + "]";
	}

}
