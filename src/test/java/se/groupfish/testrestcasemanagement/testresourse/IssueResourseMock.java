package se.groupfish.testrestcasemanagement.testresourse;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static se.groupfish.restcasemanagement.data.DTOIssue.toEntity;

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

import se.groupfish.restcasemanagement.data.DTOIssue;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.NotFoundException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.service.IssueService;

@RunWith(SpringRunner.class)
public final class IssueResourseMock {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@MockBean
	private IssueService issueService;

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
	public void Url() {
		targetUrl = "http://localhost:8080";
		webTarget = client.target(targetUrl);
	}

	@Test
	public void shouldCreateIssueAndAddToWorkItem() {

		DTOIssue dtoIssue = DTOIssue.builder().setComment("Hello").build("hello");

		Response response = webTarget.path("workitems/74").request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoIssue, MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfWorkItemIsNotDone() throws ServiceException {

		DTOIssue dtoIssue = DTOIssue.builder().setComment("Hello").build("hello");

		String exceptionMessage = "Unable to comply. Can only put an issue on 'Done' workitems.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(issueService.createIssue(toEntity(dtoIssue), 75l)).thenThrow(new BadRequestException(exceptionMessage));

		issueService.createIssue(toEntity(dtoIssue), 75l);

		Response response = webTarget.path("workitems/75").request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoIssue, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNotFoundExceptionWhenCreatingIssue() throws ServiceException {

		DTOIssue dtoIssue = DTOIssue.builder().setComment("Hello").build("hello");

		String exceptionMessage = "Unable to comply. Unknown workitem.";

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage(exceptionMessage);

		when(issueService.createIssue(toEntity(dtoIssue), 000000l)).thenThrow(new NotFoundException(exceptionMessage));

		issueService.createIssue(toEntity(dtoIssue), 000000l);

		Response response = webTarget.path("workitems/75").request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoIssue, MediaType.APPLICATION_JSON));

		assertEquals(NOT_FOUND, response.getStatusInfo());
	}

	@Test
	public void shouldUpdateIssue() {

		DTOIssue dtoIssue = DTOIssue.builder().setComment("Salam").build("salam");

		Response response = webTarget.path("issues/114").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoIssue, MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowsNotFoundExceptionWhenUpdatingIssue() throws ServiceException {

		DTOIssue dtoIssue = DTOIssue.builder().setComment("Hello").build("hello");

		String exceptionMessage = "Unable to comply. Unknown issue.";

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NotFoundException(exceptionMessage)).when(issueService).updateIssueComment(000000l, "Hello");

		issueService.updateIssueComment(000000l, "Hello");

		Response response = webTarget.path("issues/000000").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoIssue, MediaType.APPLICATION_JSON));

		assertEquals(NOT_FOUND, response.getStatusInfo());
	}

	@Test
	public void shouldGetAllIssues() {

		Response response = webTarget.path("workitems/getAllWorkItemsWithIssues/getAll")
				.request(MediaType.APPLICATION_JSON).header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowsNotFoundExceptionWhenCannotFindAllWorkItemsWithIssue() throws ServiceException {

		String exceptionMessage = "Unable to comply. Cannot access data.";

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NotFoundException(exceptionMessage)).when(issueService).getAllWorkItemsWithIssue();

		issueService.getAllWorkItemsWithIssue();

		Response response = webTarget.path("issues/000000").request(MediaType.APPLICATION_JSON).header(header, token)
				.get();

		assertEquals(NOT_FOUND, response.getStatusInfo());
	}
}
