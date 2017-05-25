package se.groupfish.restcasemanagement.data;

import javax.ws.rs.QueryParam;

public final class WorkItemRequestBean {

	@QueryParam("state")
	private String status;
	
	@QueryParam("description")
	private String description;
	
	@QueryParam("withissue")
	private boolean withIssue;

	public String getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public boolean isWithIssue() {
		return withIssue;
	}

}
