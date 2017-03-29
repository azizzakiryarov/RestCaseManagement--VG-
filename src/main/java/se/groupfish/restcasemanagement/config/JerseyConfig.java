package se.groupfish.restcasemanagement.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.resource.IssueResource;
import se.groupfish.restcasemanagement.resource.TeamResource;
import se.groupfish.restcasemanagement.resource.UserResource;
import se.groupfish.restcasemanagement.resource.WorkItemResource;

@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(UserResource.class);
		register(TeamResource.class);
		register(WorkItemResource.class);
		register(IssueResource.class);
	}

}
