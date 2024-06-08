package org.fit.rest.server;

import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.fit.exception.UserException;
import org.fit.model.IPLog;
import org.fit.model.PhoneNumber;
import org.fit.model.Users;
import org.fit.model.rest.client.Country;
import org.fit.multipart.MultipartRequest;
import org.fit.rest.client.IPClient;
import org.fit.service.CountryService;
import org.fit.service.UserService;
import org.jboss.resteasy.reactive.RestResponse.Status;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/user")
public class UserRest {

	@Inject
	private UserService userService;

	@Inject
	@RestClient
	private IPClient ipClient;

	@Inject
	private CountryService countryService;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/createUser")
	@Operation(summary = "Web service that creates a new user.", description = "User must be unique.")
	public Response createUser(MultipartRequest request) {
		Users u = new Users();
		u.setName(request.getName());
		u.setLastName(request.getLastName());
		u.setEmail(request.getEmail());

		u.setJmbg(request.getJmbg());

		try {
			// Parse and set the birth date
			u.setBirthDate(request.getParsedBirthDate());
		} catch (ParseException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid date format").build();
		}

		FileUpload fileUpload = request.getFile();

		if (fileUpload == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("File is missing").build();
		}

		// Convert phone numbers from strings to PhoneNumber entities
		Set<PhoneNumber> phoneNumbers = new HashSet<>();
		for (String phoneNumber : request.getPhoneNumbers()) {
			PhoneNumber pn = new PhoneNumber();
			pn.setNumber(phoneNumber);
			phoneNumbers.add(pn);
		}
		u.setPhoneNumbers(phoneNumbers);

		// country
		Country country = countryService.getCountryByName(request.getCountryName());
		if (country == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid country").build();
		}
		u.setCountry(country);
		
		
		try {

			byte[] image = Files.readAllBytes(fileUpload.uploadedFile().toAbsolutePath());
			IPLog ipLog = ipClient.getIP();
			ipLog.setCreatedDate(new Date());

			u = userService.createUser(u, ipLog, image);
			return Response.status(Status.CREATED).entity(u).build();
		} catch (UserException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		} catch (IOException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("File upload failed").build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllUsers")
	public Response getAllUsers() {
		List<Users> users = userService.getAllUsers();
		return Response.ok().entity(users).build();
	}

	@DELETE
	@Path("/deleteUser/{userId}")
	@Operation(summary = "Delete user by ID", description = "Deletes a user based on the provided user ID.")
	public Response deleteUser(@PathParam("userId") Long userId) {
		try {
			userService.deleteUserById(userId);
			return Response.status(Status.OK).build();
		} catch (UserException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getUsersByName/{name}")
	public Response getUsersByName(@PathParam("name") String name) {
		if (name == null || name.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Name parameter is required.").build();
		}

		List<Users> users = userService.getUsersByName(name);
		if (users.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity("No user found with the provided name.").build();
		}
		return Response.ok().entity(users).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateUserEmail/{userId}/{newEmail}")
	public Response updateUserEmail(@PathParam("userId") Long userId, @PathParam("newEmail") String newEmail) {
		try {
			Users updateUser = userService.updateUserEmail(userId, newEmail);
			return Response.ok().entity(updateUser).build();
		} catch (UserException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}

}
