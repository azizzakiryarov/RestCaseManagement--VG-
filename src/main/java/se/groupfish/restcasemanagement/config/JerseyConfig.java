package se.groupfish.restcasemanagement.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.exception.BadRequestMapper;
import se.groupfish.restcasemanagement.exception.NotFoundExceptionMapper;
import se.groupfish.restcasemanagement.exception.NullPointExceptionMapper;
import se.groupfish.restcasemanagement.exception.WebInternalErrorExceptionMapper;
import se.groupfish.restcasemanagement.filter.RequestFilter;
import se.groupfish.restcasemanagement.resource.IssueResource;
import se.groupfish.restcasemanagement.resource.TeamResource;
import se.groupfish.restcasemanagement.resource.UserResource;
import se.groupfish.restcasemanagement.resource.WorkItemResource;

@Component
public final class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(UserResource.class);
		register(TeamResource.class);
		register(WorkItemResource.class);
		register(IssueResource.class);
		register(BadRequestMapper.class);
		register(NotFoundExceptionMapper.class);
		register(NullPointExceptionMapper.class);
		register(WebInternalErrorExceptionMapper.class);
		register(RequestFilter.class);
	}

}
