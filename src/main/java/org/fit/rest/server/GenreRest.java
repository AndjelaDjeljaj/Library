package org.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.fit.exception.GenreException;
import org.fit.model.Genre;
import org.fit.service.GenreService;

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
import jakarta.ws.rs.core.Response.Status;

@Path("/api/genre")
public class GenreRest {

	@Inject
	private GenreService genreService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createGenre")
	@Operation(summary = "Web service that creates a new genre.", description = "Genre must be unique.")
	public Response createGenre(Genre genre) {
		Genre g = null;
		try {
			g = genreService.createGenre(genre);
		} catch (GenreException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok().entity(g).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllGenres")
	public Response getAllGenres() {
		List<Genre> genres = genreService.getAllGenres();
		return Response.ok().entity(genres).build();
	}
	
	@DELETE
	@Path("/deleteGenre/{genreId}")
	@Operation(summary = "Deletes genre by ID", description = "Deletes a genre based on the provided genre id")
	public Response deleteGenre(@PathParam("genreId") Long genreId) {
		try {
			genreService.deleteGenreById(genreId);
			return Response.status(Status.OK).build();
		} catch (GenreException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
}
