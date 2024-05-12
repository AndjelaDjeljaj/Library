package org.fit.rest.server;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fit.model.rest.client.Country;
import org.fit.rest.client.CountryClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/country/")
public class CountyRest {

	@Inject
	@RestClient
	private CountryClient countryClient;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCountries")
	public Response getAllCountries() {
		List<Country> countries = countryClient.getAll();
		return Response.ok().entity(countries).build();
	}
}
