package org.fit.rest.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.fit.model.IPLog;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/data")
@RegisterRestClient
public interface IPClient {

	@GET
	@Path("/client-ip")
	IPLog getIP();
}
