package org.fit.rest.client.multipart;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/client")
public class MultipartClientResource {

	@Inject
	@RestClient
	private MultipartService service;
	

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String echoFile(String body) {
		return body;
	}
	
	
	
//	@GET
//	public String callEcho() {
//		final MultipartBody multipartBody = new MultipartBody();
//		multipartBody.setFile(new ByteArrayInputStream("Hello World".getBytes()));
//		multipartBody.setName("hello.txt");
//		return service.sendMultipartData(multipartBody);
//	}
}
