package se.groupfish.testrestcasemanagement.testresourse;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.service.UserService;

import static se.groupfish.restcasemanagement.data.DTOUser.toEntity;

@RunWith(SpringRunner.class)
public final class UserResourseMock {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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
		targetUrl = "http://localhost:8080/users";
		webTarget = client.target(targetUrl);
	}

	@Test
	public void shouldSaveUser() throws ServiceException {

		DTOUser user = DTOUser.builder().setFirstName("Alexander").setLastName("Makidonskiy")
				.setUserName("alexandermakidonskiy").setUserNumber("alex-10000000").setState("Active").build("1");

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		assertEquals(CREATED, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfUserIsAlreadyExists() throws ServiceException {

		DTOUser user = DTOUser.builder().setFirstName("Ahmad").setLastName("Sultan").setUserName("ahmsul-9999")
				.setUserNumber("ahmad-001").setState("Active").build("2");

		String exceptionMessage = "Cannot create user. Username is already in use.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		when(userService.createUser(toEntity(user))).thenThrow(new BadRequestException(exceptionMessage));

		userService.createUser(toEntity(user));

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenCreatingEmptyUser() throws ServiceException {

		DTOUser user = DTOUser.builder().setFirstName("").setLastName("").setUserName("").setUserNumber("").setState("")
				.build("3");

		String exceptionMessage = "Unable to create User.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		when(userService.createUser(toEntity(user))).thenThrow(new NullPointException(exceptionMessage));

		userService.createUser(toEntity(user));

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldUpdateUser() throws ServiceException {

		DTOUser user = DTOUser.builder().setUserName("axel-200001212212").build("1");

		Response response = webTarget.path("/107").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user, MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfUserUserNameIsLessThanTenLetter() throws ServiceException {

		DTOUser user = DTOUser.builder().setUserName("amerika").build("5");

		String exceptionMessage = "Username must be 10 characters long minimum.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new BadRequestException(exceptionMessage)).when(userService).updateUserUsername(107l, "amerika");

		userService.updateUserUsername(107l, "amerika");

		Response response = webTarget.path("/107").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getUserName(), MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenUpdatingWithEmptyUsersUserName() throws ServiceException {

		DTOUser user = DTOUser.builder().setUserName("").build("6");

		String exceptionMessage = "Unable to update user.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NullPointException(exceptionMessage)).when(userService).updateUserUsername(107l, "");

		userService.updateUserUsername(107l, "");

		Response response = webTarget.path("107").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getUserName(), MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void shouldInactivateUser() throws ServiceException {

		DTOUser dtoUser = DTOUser.builder().setState("Inactive").build("aaaa");

		Response response = webTarget.path("/13").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(dtoUser.getState(), MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfUsersStateIsWrong() throws ServiceException {

		DTOUser user = DTOUser.builder().setState("I").build("5");

		String exceptionMessage = "Unable to update user.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new BadRequestException(exceptionMessage)).when(userService).updateUserState(107l, "I");

		userService.updateUserState(107l, "I");

		Response response = webTarget.path("/107").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getState(), MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenInactivateWithEmptyUsersState() throws ServiceException {

		DTOUser user = DTOUser.builder().setState("").build("6");

		String exceptionMessage = "Unable to update user. User does not exist.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NullPointException(exceptionMessage)).when(userService).updateUserState(107l, "");

		userService.updateUserState(107l, "");

		Response response = webTarget.path("/107").request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getState(), MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void getUserByNumber() {

		Response response = webTarget.queryParam("userNumber", "003").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfUsersNumberIsWrong() throws ServiceException {

		String exceptionMessage = "No user exist with that number!";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new BadRequestException(exceptionMessage)).when(userService).findUserByNumber("00000000");

		userService.findUserByNumber("00000000");
		;

		Response response = webTarget.queryParam("userNumber", "00000000").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenFindByUsersNumberWithEmptyLetters() throws ServiceException {

		String exceptionMessage = "Unable to find users by user number.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NullPointException(exceptionMessage)).when(userService).findUserByNumber("");

		userService.findUserByNumber("");

		Response response = webTarget.queryParam("userNumber", "").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void getUserByFirstName() {

		Response response = webTarget.queryParam("firstName", "Erik1").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfUsersFirstNameIsWrong() throws ServiceException {

		String exceptionMessage = "No user exist with that firstname!";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new BadRequestException(exceptionMessage)).when(userService).findUserByFirstName("E");

		userService.findUserByFirstName("E");

		Response response = webTarget.queryParam("firstName", "E").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionWhenFindByUsersFirstNameWithEmptyLetters() throws ServiceException {

		String exceptionMessage = "Unable to find users by firstname.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NullPointException(exceptionMessage)).when(userService).findUserByFirstName("");

		userService.findUserByFirstName("");

		Response response = webTarget.queryParam("firstName", "").request(MediaType.APPLICATION_JSON)
				.header(header, token).get();

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void getAllUsersByTeamId() {

		Response response = webTarget.path("/2").request(MediaType.APPLICATION_JSON).header(header, token).get();

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfTeamIdIsWrong() throws ServiceException {

		String exceptionMessage = "The team does not exist!";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new BadRequestException(exceptionMessage)).when(userService).findAllUsersFromOneTeam(10000l);

		userService.findAllUsersFromOneTeam(10000l);

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token).get();

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsNullPointExceptionIfTeamIdIsNull() throws ServiceException {

		String exceptionMessage = "Unable to find users from Team.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NullPointException(exceptionMessage)).when(userService).findAllUsersFromOneTeam(null);

		userService.findAllUsersFromOneTeam(null);

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token).get();

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

}
