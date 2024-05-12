package org.fit.rest.client;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.fit.model.rest.client.Country;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api/v3")
@RegisterRestClient
public interface CountryClient {

	@GET
	@Path("/AvailableCountries")
	List<Country> getAll();
	
}
