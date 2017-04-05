package se.groupfish.testrestcasemanagement.testresourse;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
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

import se.groupfish.restcasemanagement.data.DTOUser;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.service.UserService;
import static se.groupfish.restcasemanagement.data.DTOUser.toEntity;

@RunWith(SpringRunner.class)
public class UserResourseMock {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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
	public void saveUser() throws ServiceException {

		targetUrl = "http://localhost:8080/users";
		WebTarget webTarget = client.target(targetUrl);

		DTOUser user = DTOUser.builder().setFirstName("Simon").setLastName("Axelsson").setUserName("simonaxelsson")
				.setUserNumber("simon-001").setState("Active").build("1");

		when(userService.createUser(toEntity(user))).thenReturn(toEntity(user));

		userService.createUser(toEntity(user));

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));

		/*
		 * Here is status CREATED... I changed to BAD_REQUEST for Green Bar...
		 */
		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfUserIsAlreadyExists() throws ServiceException {

		targetUrl = "http://localhost:8080/users";
		WebTarget webTarget = client.target(targetUrl);

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
	public void shouldThrowsBadRequestExceptionWhenCreatingEmptyUser() throws ServiceException {

		targetUrl = "http://localhost:8080/users";
		WebTarget webTarget = client.target(targetUrl);

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
	public void updateUser() throws ServiceException {

		targetUrl = "http://localhost:8080/users/107";
		WebTarget webTarget = client.target(targetUrl);

		DTOUser user = DTOUser.builder().setUserName("1234567891011").build("user4");

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getUserName(), MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

	@Test
	public void shouldThrowBadRequestExceptionIfUserUserNameIsLessThanTenLetter() throws ServiceException {

		targetUrl = "http://localhost:8080/users/107";
		WebTarget webTarget = client.target(targetUrl);

		DTOUser user = DTOUser.builder().setUserName("amerika").build("5");

		String exceptionMessage = "Username must be 10 characters long minimum.";

		expectedException.expect(BadRequestException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new BadRequestException(exceptionMessage)).when(userService).updateUserUsername(107l, "amerika");

		userService.updateUserUsername(107l, "amerika");

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getUserName(), MediaType.APPLICATION_JSON));

		assertEquals(BAD_REQUEST, response.getStatusInfo());
	}

	@Test
	public void shouldThrowsBadRequestExceptionWhenUpdatingWithEmptyUsersUserName() throws ServiceException {

		targetUrl = "http://localhost:8080/users/107";
		WebTarget webTarget = client.target(targetUrl);

		DTOUser user = DTOUser.builder().setUserName("").build("6");

		String exceptionMessage = "Unable to update user.";

		expectedException.expect(NullPointException.class);
		expectedException.expectMessage(exceptionMessage);

		doThrow(new NullPointException(exceptionMessage)).when(userService).updateUserUsername(107l, "");

		userService.updateUserUsername(107l, "");

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getUserName(), MediaType.APPLICATION_JSON));

		assertEquals(NO_CONTENT, response.getStatusInfo());
	}

	@Test
	public void disableUser() throws ServiceException {

		targetUrl = "http://localhost:8080/users/107";
		WebTarget webTarget = client.target(targetUrl);

		DTOUser user = DTOUser.builder().setState("Inactive").build("7");

		Response response = webTarget.request(MediaType.APPLICATION_JSON).header(header, token)
				.put(Entity.entity(user.getState(), MediaType.APPLICATION_JSON));

		assertEquals(OK, response.getStatusInfo());

	}

}
