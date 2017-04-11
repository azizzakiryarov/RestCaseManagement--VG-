package se.groupfish.testrestcasemanagement.testresourse;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static se.groupfish.restcasemanagement.data.DTOWorkItem.toEntity;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import se.groupfish.restcasemanagement.data.DTOUser;
import se.groupfish.restcasemanagement.data.DTOWorkItem;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.service.WorkItemService;

@RunWith(SpringRunner.class)
public class WorkItemResourseMock {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@MockBean
	private WorkItemService workItemService;

	private static Client client;
	private static String targetUrl;
	private static WebTarget webTarget;

	private final String header = "Authorization";
	private final String token = "auth";

	@BeforeClass
	public static void initialize() {
		client = ClientBuilder.newClient();
	}

	@Before
	public void LinkToValues() {
		targetUrl = "http://localhost:8080/workitems";
		webTarget = client.target(targetUrl);
	}

	@Test
	public void shouldSaveWorkItem() throws ServiceException {

		DTOWorkItem dtoWorkItem = DTOWorkItem.builder().setTitle("W3").setDescription("You need to complite it")
				.setState("Unstarted").build("1");

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoWorkItem, MediaType.APPLICATION_JSON));

		assertEquals(CREATED, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfWorkItemIsAlreadyExists() throws ServiceException {

		DTOWorkItem dtoWorkItem = DTOWorkItem.builder().setTitle("W3").setDescription("You need to complite it")
				.setState("Unstarted").build("1");

		String exceptionMessage = "Unable to comply. WorkItem already exist.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(workItemService.createWorkItem(toEntity(dtoWorkItem)))
				.thenThrow(new BadRequestException(exceptionMessage));

		workItemService.createWorkItem(toEntity(dtoWorkItem));

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoWorkItem, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenCreatingEmptyWorkItem() throws ServiceException {

		DTOWorkItem dtoWorkItem = DTOWorkItem.builder().setTitle("").setDescription("").setState("").build("");

		String exceptionMessage = "Unable to create User.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		when(workItemService.createWorkItem(toEntity(dtoWorkItem))).thenThrow(new NullPointException(exceptionMessage));

		workItemService.createWorkItem(toEntity(dtoWorkItem));

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoWorkItem, MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldUpdateWorkItem() throws ServiceException {

		DTOWorkItem dtoWorkItem = DTOWorkItem.builder().setState("Done").build("1");

		Response response = webTarget.path("/69").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoWorkItem, MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfYouWantToUpdateWrongWorkItemId() throws ServiceException, IOException {

		DTOWorkItem dtoWorkItem = DTOWorkItem.builder().setState("Done").build("1");

		String exceptionMessage = "Unable to comply. Unknown workitem.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(workItemService.updateWorkItemState(99999999L, dtoWorkItem.getState()))
				.thenThrow(new BadRequestException(exceptionMessage));

		workItemService.updateWorkItemState(99999999L, dtoWorkItem.getState());

		Response response = webTarget.path("/99999999").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoWorkItem, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenUpdatingAnEmptyWorkItemId() throws ServiceException, IOException {

		DTOWorkItem dtoWorkItem = DTOWorkItem.builder().setState("Done").build("1");

		String exceptionMessage = "Could not update workItem.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		when(workItemService.updateWorkItemState(null, dtoWorkItem.getState()))
				.thenThrow(new NullPointException(exceptionMessage));

		workItemService.updateWorkItemState(null, dtoWorkItem.getState());

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoWorkItem, MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldDeleteWorkItem() {

		Response response = webTarget.path("/110").request(MediaType.APPLICATION_JSON).header(header, token).delete();

		assertEquals(OK, response.getStatusInfo());
	}

	@Test
	public void shouldThrowBadRequestExceptionIfYouWantToDeleteWrongWorkItemId() throws ServiceException, IOException {

		String exceptionMessage = "Unable to comply. Unknown workitem.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new BadRequestException(exceptionMessage)).when(workItemService).removeWorkItem(110L);

		workItemService.removeWorkItem(110L);

		Response response = webTarget.path("/110").request(MediaType.APPLICATION_JSON).header(header, token).delete();

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenDeletingAnEmptyWorkItemId() throws ServiceException, IOException {

		String exceptionMessage = "Could not update workItem.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NullPointException(exceptionMessage)).when(workItemService).removeWorkItem(null);

		workItemService.removeWorkItem(null);

		Response response = webTarget.path("/110").request(MediaType.APPLICATION_JSON).header(header, token).delete();

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldAddWorkItemToOneUser() { // Fix it!!!

		DTOUser dtoUser = DTOUser.builder().setId(15l).build("UserId");

		Response response = webTarget.path("/81").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoUser.getId(), MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());
	}

	@Test
	public void shouldGetAllWorkItemsByState() {

		Response response = webTarget.queryParam("state", "Done").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldGetAllWorkItemsByUserId() {

		Response response = webTarget.queryParam("userId", "13").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldGetAllWorkItemsByTeamId() {

		Response response = webTarget.queryParam("teamId", "72").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldGetAllWorkItemsByDescription() {

		Response response = webTarget.path("Code").request(MediaType.APPLICATION_JSON).header(header, token).get();

		assertEquals(OK, response.getStatusInfo());
	}

}
