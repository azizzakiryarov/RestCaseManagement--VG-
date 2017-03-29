package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

	@Override
	public Response toResponse(ConflictException exception) {
		return Response.status(Status.CONFLICT).entity(exception.getMessage()).build();
	}

}
