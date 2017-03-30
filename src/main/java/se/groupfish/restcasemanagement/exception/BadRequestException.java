package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class BadRequestException extends WebApplicationException {

	private static final long serialVersionUID = -3792867500952758785L;

	public BadRequestException(String exception) {
		super(Status.BAD_REQUEST + "  " + exception);
	}
}
