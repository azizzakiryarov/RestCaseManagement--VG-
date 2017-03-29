package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class NullPointExceptionMapper implements ExceptionMapper<NullPointerException> {

	@Override
	public Response toResponse(NullPointerException exception) {
		
		return Response.status(Status.NO_CONTENT).build();
	}

}
