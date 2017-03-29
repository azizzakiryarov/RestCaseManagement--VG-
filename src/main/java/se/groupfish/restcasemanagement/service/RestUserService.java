package se.groupfish.restcasemanagement.service;

import static se.groupfish.restcasemanagement.data.DTOUser.toEntity;
import static se.groupfish.restcasemanagement.data.DTOUser.usersListToDTOUserList;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import static se.groupfish.restcasemanagement.data.DTOUser.toDTO;

import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.data.DTOUser;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.User;
import se.groupfish.springcasemanagement.service.UserService;

@Component
public final class RestUserService {

	private final UserService userService;

	public RestUserService(UserService userService) {
		this.userService = userService;
	}

	public User saveUser(DTOUser dtoUser) throws ServiceException {

		try {
			User savedUser = toEntity(dtoUser);
			return userService.createUser(savedUser);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
	}

	public void updateUser(Long userId, String userName) throws ServiceException {

		try {
			User userForUpdate = userService.getUserById(userId);
			userService.updateUserUsername(userForUpdate.getId(), userName);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
	}

	public void disableUser(Long id, String state) throws ServiceException {

		try {
			User userForDisactivate = userService.getUserById(id);
			userService.updateUserState(userForDisactivate.getId(), state);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
	}

	public DTOUser getUserByNumber(String number) throws ServiceException {

		User getUser = userService.findUserByNumber(number);
		DTOUser user = toDTO(getUser);
		return user;
	}

	public DTOUser getUserByFirstName(String firstName) throws ServiceException {

		User getUser = userService.findUserByFirstName(firstName);
		DTOUser user = toDTO(getUser);
		return user;
	}

	public List<DTOUser> getAllDTOUsers(Long teamId) throws ServiceException {

		List<User> listUsers = userService.findAllUsersFromOneTeam(teamId);
		List<DTOUser> dtoUsersList = usersListToDTOUserList(listUsers);
		return dtoUsersList;
	}

}
