package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public final class WebInternalErrorExceptionMapper implements ExceptionMapper<WebInternalErrorException> {

	@Override
	public Response toResponse(WebInternalErrorException exception) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception).build();
	}

}
