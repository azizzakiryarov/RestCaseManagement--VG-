package se.groupfish.restcasemanagement.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.groupfish.restcasemanagement.data.DTOIssue;
import se.groupfish.restcasemanagement.service.RestIssueService;
import se.groupfish.springcasemanagement.exception.ServiceException;

@Component
@Path("issues")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class IssueResource {

	@Autowired
	private RestIssueService issueService;

	@PUT
	@Path("{issueId}")
	public Response updateIssue(@PathParam("issueId") Long issueId, DTOIssue dtoIssue) throws ServiceException {

		issueService.updateIssue(issueId, dtoIssue.getComment());
		return Response.status(Status.OK).build();
	}
}
