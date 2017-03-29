package se.groupfish.restcasemanagement.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.BadRequestMapper;
import se.groupfish.restcasemanagement.exception.NotFoundException;
import se.groupfish.restcasemanagement.exception.NotFoundExceptionMapper;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.restcasemanagement.exception.NullPointExceptionMapper;
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
		register(BadRequestException.class);
		register(BadRequestMapper.class);
		register(NotFoundException.class);
		register(NotFoundExceptionMapper.class);
		register(NullPointException.class);
		register(NullPointExceptionMapper.class);
	}

}
