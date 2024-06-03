package org.fit.rest.server;

import java.util.List;

import org.fit.exception.StudentException;
import org.fit.model.Student;
import org.fit.service.StudentService;

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
import jakarta.ws.rs.core.Response.Status;

@Path("/api/student")
public class StudentRest {

	@Inject
	private StudentService service;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createStudent")
	public Response createStudet(Student student) {
		
		Student s = null;
		try {
			s = service.createStudent(student);
			return Response.status(Status.CREATED).entity(s).build();
		} catch (StudentException e) {
			// TODO: handle exception
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllStudents")
	public Response getAllStudent() {
		List<Student> students = service.getAllStudents();
		return Response.ok().entity(students).build();
	}
	
	
	@DELETE
	@Path("/deleteStuudent/{studentId}")
	public Response deleteStudent(@PathParam("studentId") Long studetId) {
		try {
			service.deleteStudentById(studetId);
			return Response.status(Status.OK).build();
		} catch (StudentException e) {
			// TODO: handle exception
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateName/{studentId}/{newName}")
	public Response updateNameById(@PathParam("studentId") Long studetId, @PathParam("newName") String newName) {
		try {
			Student updateStudent = service.updateStudentNameById(studetId, newName);
			return Response.ok().entity(updateStudent).build();
		} catch (StudentException e) {
			// TODO: handle exception
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	
	
	
	
}
