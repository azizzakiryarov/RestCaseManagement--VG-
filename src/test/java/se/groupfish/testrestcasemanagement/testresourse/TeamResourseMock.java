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

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static se.groupfish.restcasemanagement.data.DTOTeam.toEntity;

import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.service.TeamService;

@RunWith(SpringRunner.class)
public class TeamResourseMock {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@MockBean
	private TeamService teamService;

	private static Client client;

	private String targetUrl;

	private final String header = "Authorization";
	private final String token = "auth";

	@BeforeClass
	public static void initialize() {
		client = ClientBuilder.newClient();
	}
	
	@Test
	public void saveTeam() throws ServiceException{
		
		targetUrl = "http://localhost:8080/teams";
		WebTarget webTarget = client.target(targetUrl);
		
		DTOTeam dtoTeam = DTOTeam.builder().setTeamName("Yokohama").setState("Active").build("1");
		
		when(teamService.createTeam(toEntity(dtoTeam))).thenReturn(toEntity(dtoTeam));
		
		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(dtoTeam, MediaType.APPLICATION_JSON));

		assertEquals(CREATED, response.getStatusInfo());
		
	}
}
