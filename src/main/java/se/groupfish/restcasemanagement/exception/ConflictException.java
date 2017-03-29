package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ConflictException extends WebApplicationException {

	private static final long serialVersionUID = 4090540500833277369L;
	
	public ConflictException() {
		super(Status.CONFLICT);
	}
}
