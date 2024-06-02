package org.fit.rest.client.multipart;


import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

public class MultipartBody {

	@FormParam("picture")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	private byte[] file;

	@FormParam("name")
	@PartType(MediaType.TEXT_PLAIN)
	public String name;


	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartBody() {
		super();
	}





	// @FormParam is a standard Jakarta REST annotation used to define a form
	// parameter contained within a request entity body
	// @PartType is a RESTEasy specific annotation required when a client performs a
	// multipart request and defines the content type for the part.

}
