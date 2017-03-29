package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class MessageNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = -1163744052296700697L;
	
	public MessageNotFoundException() {
		super(Status.NOT_FOUND);
		
	}
}
