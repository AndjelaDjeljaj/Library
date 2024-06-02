package org.fit.rest.server;


import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.fit.exception.LoanException;
import org.fit.model.Loan;
import org.fit.service.LoanService;
import org.jboss.resteasy.reactive.RestResponse.Status;

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

@Path("/api/loan")
public class LoanRest {

	@Inject
	private LoanService loanService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createLoan")
	@Operation(summary = "Web service that creates a loan.")
	public Response createLoan(CreateLoanRequest loanRequest) {
		try {
			Loan loan = loanRequest.getLoan();
			Long userId = loanRequest.getUserId();
			Long bookId = loanRequest.getBookId();
			
			Loan createdLoan = loanService.createLoan(loan, userId, bookId);
			
			return Response.ok().entity(createdLoan).build();
		} catch (Exception e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllLoans")
	public Response getAllLoans() {
		List<Loan> loans = loanService.getAllLoans();
		return Response.ok().entity(loans).build();
	}
	
	
	@DELETE
	@Path("/deleteLoan/{loanId}")
	@Operation(summary = "Delete loan by ID", description = "Deletes a loan based on the provided ID")
	public Response deleteLoan(@PathParam("loanId") Long loanId) {
		try {
			loanService.deleteLoanById(loanId);
			return Response.status(Status.OK).build();
		} catch (LoanException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	
	
}
