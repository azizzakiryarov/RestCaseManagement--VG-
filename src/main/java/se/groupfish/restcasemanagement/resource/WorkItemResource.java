package se.groupfish.restcasemanagement.resource;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.data.DTOIssue;
import se.groupfish.restcasemanagement.data.DTOWorkItem;
import se.groupfish.restcasemanagement.service.RestIssueService;
import se.groupfish.restcasemanagement.service.RestWorkItemService;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.WorkItem;

@Component
@Path("workitems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class WorkItemResource {

	@Autowired
	private RestWorkItemService workItemService;

	@Autowired
	private RestIssueService issueService;

	@Context
	private UriInfo uriInfo;

	@POST
	public Response createWorkItem(DTOWorkItem dtoWorkItem) throws ServiceException {

		WorkItem savedWorkItem = workItemService.saveWorkItem(dtoWorkItem);
		URI location = uriInfo.getAbsolutePathBuilder().path(savedWorkItem.getId().toString()).build();
		return Response.created(location).build();
	}

	@POST
	@Path("{workitemId}")
	public Response createIssueAndAddToWorkItem(DTOIssue dtoIssue, @PathParam("workitemId") Long workItemId)
			throws ServiceException {

		issueService.saveIssue(dtoIssue, workItemId);
		return Response.status(Status.OK).build();
	}

	@PUT
	@Path("{id}")
	public Response updateWorkItemsStateAndAddWorkItemToUser(@PathParam("id") Long id, DTOWorkItem dtoWorkItem)
			throws ServiceException, IOException {

		if (dtoWorkItem != null && dtoWorkItem.getState() != null) {
			workItemService.updateWorkItemsState(id, dtoWorkItem.getState());
			return Response.status(Status.OK).build();
		}
		if (dtoWorkItem != null && dtoWorkItem.getUserId() != null) {
			workItemService.addWorkItemToUser(id, dtoWorkItem.getUserId());
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("{id}")
	public Response removeWorkItem(@PathParam("id") Long id) throws ServiceException {

		workItemService.removeWorkItem(id);
		return Response.status(Status.OK).build();
	}

	@GET
	public Response getAllWorkItemsByStateByTeamIdByUserId(@QueryParam("state") String state,
			@QueryParam("teamId") Long teamId, @QueryParam("userId") Long userId) throws ServiceException {

		Collection<DTOWorkItem> getAllWorkItems;

		if (state != null) {
			getAllWorkItems = workItemService.getAllDTOWorkItemsByState(state);
			return Response.ok(getAllWorkItems).build();
		}

		if (teamId != null) {
			getAllWorkItems = workItemService.getAllDTOWorkItemsByTeam(teamId);
			return Response.ok(getAllWorkItems).build();
		}

		if (userId != null) {
			getAllWorkItems = workItemService.getAllDTOWorkItemsByUser(userId);
			return Response.ok(getAllWorkItems).build();

		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Path("{descriptionContent}")
	public Response getAllWorkItemByDescriptionContent(@PathParam("descriptionContent") String descriptionContent)
			throws ServiceException {

		Collection<DTOWorkItem> getAllWorkItemsByDescription;

		if (descriptionContent != null) {
			getAllWorkItemsByDescription = workItemService.getAllWorkItemsByContent(descriptionContent);
			return Response.ok(getAllWorkItemsByDescription).build();

		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@GET
	@Path("/getAllWorkItemsWithIssues/{getAll}")
	public Response getAllWorkItemWithIssues(@PathParam("getAll") String getAll)
			throws ServiceException {

		Collection<DTOWorkItem> getAllWorkItemWithIssues;

		if (getAll != null) {
			getAllWorkItemWithIssues = workItemService.getAllDTOWorkItemsByIssue();
			return Response.ok(getAllWorkItemWithIssues).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
}
