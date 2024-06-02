package org.fit.rest.client.multipart.server;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/echo")
public class EchoService {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String echo(String requestBody) throws Exception{
		return requestBody;
	}
	
	//This will just return the request body and it’s useful for testing purposes.
}
