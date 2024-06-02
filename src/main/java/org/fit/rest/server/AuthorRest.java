package org.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.fit.exception.AuthorException;
import org.fit.model.Author;
import org.fit.service.AuthorService;
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

@Path("/api/author")
public class AuthorRest {

	@Inject
	private AuthorService authorService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createAuthor")
	@Operation(summary = "Web service that creates a new author.", description = "Author must be unique.")
	public Response createAuthor(Author author) {
		Author a = null;
		try {
			a = authorService.createAuthor(author);
		} catch (AuthorException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok().entity(a).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllAuthors")
	public Response getAllAuthors() {
		List<Author> authors = authorService.getAllAuthors();
		return Response.ok().entity(authors).build();
	}
	
	@DELETE
	@Path("/deleteAuthor/{authorId}")
	@Operation(summary = "Delete author by ID", description = "Deletes an author based on the provided author ID.")
	public Response deleteAuthor(@PathParam("authorId") Long authorId) {
	    try {
	        authorService.deleteAuthorById(authorId);
	        return Response.status(Status.OK).build();
	    } catch (AuthorException e) {
	        return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
	    }
	}

}
