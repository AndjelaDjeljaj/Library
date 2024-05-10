package org.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.fit.exception.BookException;
import org.fit.model.Book;
import org.fit.service.BookService;
import org.jboss.resteasy.reactive.RestResponse.Status;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
	public Response createBook(Book book) {
		Book b = null;
		try {
			b = bookService.createBook(book);
		} catch (BookException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok().entity(b).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllBooks")
	public Response getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		return Response.ok().entity(books).build();
	}
}
