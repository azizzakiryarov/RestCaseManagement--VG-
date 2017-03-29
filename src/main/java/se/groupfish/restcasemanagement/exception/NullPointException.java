package se.groupfish.restcasemanagement.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class NullPointException extends WebApplicationException {

	private static final long serialVersionUID = 2689445639589787390L;

	public NullPointException() {
		super(Status.NO_CONTENT);
	}

}
