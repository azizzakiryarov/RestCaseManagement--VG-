package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NullPointExceptionMapper implements ExceptionMapper<NullPointException> {

	@Override
	public Response toResponse(NullPointException exception) {
		return Response.status(Status.NO_CONTENT).entity(exception.getMessage()).build();
	}

}
