package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class BadRequestMapper implements ExceptionMapper<BadRequestException> {

	@Override
	public Response toResponse(BadRequestException exception) {
		return Response.status(Status.BAD_REQUEST).build();
	}

}
