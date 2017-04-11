package se.groupfish.testrestcasemanagement.testresourse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
public class TeamResourseMock {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@MockBean
	private TeamService teamService;

	@MockBean
	private UserService userService;

	private static Client client;

	private String targetUrl;

	private final String header = "Authorization";
	private final String token = "auth";

	@BeforeClass
	public static void initialize() {
		client = ClientBuilder.newClient();
	}

	@Test
	public void shouldSaveTeam() throws ServiceException {

		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Yokohama").setState("Active").build("1");

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(CREATED, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfTeamIsAlreadyExists() throws ServiceException {

		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Yokohama").setState("Active").build("1");

		String exceptionMessage = "This team is already persisted!";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.createTeam(toEntity(dtoTeam))).thenThrow(new BadRequestException(exceptionMessage));

		teamService.createTeam(toEntity(dtoTeam));

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenCreatingEmptyTeam() throws ServiceException {

		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("").setState("").build("1");

		String exceptionMessage = "Team is null...";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.createTeam(toEntity(dtoTeam))).thenThrow(new NullPointException(exceptionMessage));

		teamService.createTeam(toEntity(dtoTeam));

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldUpdateTeam() throws ServiceException {

		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Sanihatama").build("1");

		when(teamService.updateTeamName(108l, "Sanihatama")).thenReturn(toEntity(dtoTeam));

		teamService.updateTeamName(108l, "Sanihatama");

		Response response = webTarget.path("/108").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfTeamNameIsAlreadyExists() throws ServiceException {

		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Sanihatama").build("1");

		String exceptionMessage = "A Team already exists with that name.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.updateTeamName(108l, "Sanihatama")).thenThrow(new BadRequestException(exceptionMessage));

		teamService.updateTeamName(108l, "Sanihatama");

		Response response = webTarget.path("/108").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenUpdatingWithEmptyTeamName() throws ServiceException {

		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);

		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("").build("1");

		String exceptionMessage = "Could not update Team.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		when(teamService.updateTeamName(108l, "")).thenThrow(new NullPointException(exceptionMessage));

		teamService.updateTeamName(108l, "");

		Response response = webTarget.path("/108").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldGetAllTeams() {

		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldAddUserToTeam() throws ServiceException {

		targetUrl = "http://localhost:8080/users";
		WebTarget webTarget = client.target(targetUrl);

		DTOTeam dtoTeam = DTOTeam.builder().setId(109L).build("1");

		Response response = webTarget.path("/104").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

		/*
		 * Lite osäkert att det funkar som ska...
		 */

		/*
		 * - Lägga till en User till ett team
             Affärslogik:
           - Det får max vara 10 users i ett team
           - En User kan bara ingå i ett team åt gången
		 */
	}
}
