package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class NotFoundException extends WebApplicationException {

	private static final long serialVersionUID = -1163744052296700697L;
	
	public NotFoundException(String exception) {
		super(Status.NOT_FOUND + "  " + exception);
		
	}
}
