package se.groupfish.testrestcasemanagement.testresourse;

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
import se.groupfish.restcasemanagement.data.DTOTeam;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.NullPointException;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static se.groupfish.restcasemanagement.data.DTOTeam.toEntity;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.service.TeamService;
import se.groupfish.springcasemanagement.service.UserService;

@RunWith(SpringRunner.class)
public final class TeamResourseMock {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@MockBean
	private TeamService teamService;

	@MockBean
	private UserService userService;

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
	public void shouldSaveTeam() throws ServiceException {

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Sankt-Petersburg").setState("Active").build("1");

		Response response = webTarget.path("/teams").request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(CREATED, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfTeamIsAlreadyExists() throws ServiceException {

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Yokohama").setState("Active").build("1");

		String exceptionMessage = "This team is already persisted!";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.createTeam(toEntity(dtoTeam))).thenThrow(new BadRequestException(exceptionMessage));

		teamService.createTeam(toEntity(dtoTeam));

		Response response = webTarget.path("/teams").request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenCreatingEmptyTeam() throws ServiceException {

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("").setState("").build("1");

		String exceptionMessage = "Team is null...";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.createTeam(toEntity(dtoTeam))).thenThrow(new NullPointException(exceptionMessage));

		teamService.createTeam(toEntity(dtoTeam));

		Response response = webTarget.path("/teams").request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldUpdateTeam() throws ServiceException {

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Piter").build("1");

		Response response = webTarget.path("teams/118").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfTeamNameIsAlreadyExists() throws ServiceException {

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Sanihatama").build("1");

		String exceptionMessage = "A Team already exists with that name.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.updateTeamName(108l, "Sanihatama")).thenThrow(new BadRequestException(exceptionMessage));

		teamService.updateTeamName(108l, "Sanihatama");

		Response response = webTarget.path("teams/108").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenUpdatingWithEmptyTeamName() throws ServiceException {

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("").build("1");

		String exceptionMessage = "Could not update Team.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.updateTeamName(108l, "")).thenThrow(new NullPointException(exceptionMessage));

		teamService.updateTeamName(108l, "");

		Response response = webTarget.path("teams/108").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldGetAllTeams() {

		Response response = webTarget.path("/teams").request(MediaType.APPLICATION_JSON).header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldAddUserToTeam() throws ServiceException {

		DTOTeam dtoTeam = DTOTeam.builder().setId(117L).build("1");

		Response response = webTarget.path("users/15").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam.getId(), MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());
	}
}
