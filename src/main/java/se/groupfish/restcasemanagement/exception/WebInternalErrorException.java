package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public final class WebInternalErrorException extends WebApplicationException {

	private static final long serialVersionUID = 5663992927260108902L;

	public WebInternalErrorException(String message) {
		super(Status.INTERNAL_SERVER_ERROR + "  " + message);
	}

}
