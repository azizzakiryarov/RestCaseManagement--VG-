package se.groupfish.restcasemanagement.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.data.DTOUser;
import se.groupfish.restcasemanagement.service.RestTeamService;
import se.groupfish.restcasemanagement.service.RestUserService;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.User;

@Component
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class UserResource {

	@Autowired
	private RestUserService userService;
	@Autowired
	private RestTeamService teamService;

	@Context
	private UriInfo uriInfo;

	@POST
	public Response addUser(DTOUser dtoUser) throws ServiceException {

		User savedUser = userService.saveUser(dtoUser);
		URI location = uriInfo.getAbsolutePathBuilder().path(savedUser.getId().toString()).build();
		return Response.created(location).build();
	}

	@PUT
	@Path("{id}")
	public Response updateAndInactivateUserAndAddUserToTeam(@PathParam("id") Long id, DTOUser dtoBody)
			throws ServiceException {

		if (dtoBody != null && dtoBody.getUserName() != null) {
			userService.updateUser(id, dtoBody.getUserName());
			return Response.status(Status.OK).build();
		}
		if (dtoBody != null && dtoBody.getState() != null) {
			userService.disableUser(id, dtoBody.getState());
			return Response.status(Status.OK).build();
		}

		if (dtoBody != null && dtoBody.getTeamId() != null) {
			teamService.addUserToOneTeam(dtoBody.getTeamId(), id);
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	public Response getUserByNumberFirstName(@QueryParam("number") String number,
			@QueryParam("firstName") String firstName) throws ServiceException {

		if (number != null) {
			DTOUser toUserByNumber = userService.getUserByNumber(number);
			return Response.ok(toUserByNumber).build();
		}
		if (firstName != null) {
			DTOUser toUser = userService.getUserByFirstName(firstName);
			return Response.ok(toUser).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Path("{teamId}")
	public Response getAllUsersByTeamId(@PathParam("teamId") Long teamId) throws ServiceException {

		if (teamId != null) {
			List<DTOUser> getAllUsers = userService.getAllDTOUsers(teamId);
			return getAllUsers == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(getAllUsers).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
