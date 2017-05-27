package se.groupfish.restcasemanagement.data;

import javax.ws.rs.QueryParam;

public final class WorkItemRequestBean {

	@QueryParam("state")
	private String state;
	
	@QueryParam("description")
	private String description;
	
	@QueryParam("teamId")
	private Long teamId;
	
	@QueryParam("userId")
	private Long userId;

	public String getState() {
		return state;
	}

	public String getDescription() {
		return description;
	}

	public Long getTeamId() {
		return teamId;
	}

	public Long getUserId() {
		return userId;
	}
	
	

	

}
