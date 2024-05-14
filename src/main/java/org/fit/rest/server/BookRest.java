package org.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.fit.exception.BookException;
import org.fit.model.Book;
import org.fit.service.BookService;
import org.jboss.resteasy.reactive.RestResponse.Status;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/book")
public class BookRest {

	@Inject
	private BookService bookService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createBook")
	@Operation(summary = "Web servis koji kreira novu knjigu.", description = "Knjiga mora biti unikatna.")
	public Response createBook(CreateBookRequest bookRequest) {
		
		try {
			Book book = bookRequest.getBook();
			Long authorId = bookRequest.getAuthorId();
			List<Long> genreIds = bookRequest.getGenreIds();
			
			Book createdBook = bookService.createBook(book, authorId, genreIds);
			
			return Response.ok().entity(createdBook).build();
		} catch (Exception e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllBooks")
	public Response getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		return Response.ok().entity(books).build();
	}
	
	@DELETE
	@Path("/deleteBook/{bookId}")
	@Operation(summary = "Delete book by ID", description = "Deletes a book based on the provided ID.")
	public Response deleteBook(@PathParam("bookId") Long bookId) {
		try {
			bookService.deleteBookById(bookId);
			return Response.status(Status.OK).build();
		} catch (BookException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
}
