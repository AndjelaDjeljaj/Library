package org.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.fit.exception.AuthorException;
import org.fit.model.Author;
import org.fit.service.AuthorService;
import org.jboss.resteasy.reactive.RestResponse.Status;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
	@Operation(summary = "Web servis koji kreira novog autora.", description = "Autor mora biti unikatan.")
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
}
