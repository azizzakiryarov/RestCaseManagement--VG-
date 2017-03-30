package se.groupfish.restcasemanagement.service;

import java.util.List;
import org.springframework.stereotype.Component;
import se.groupfish.restcasemanagement.data.DTOUser;
import se.groupfish.springcasemanagement.model.User;
import se.groupfish.springcasemanagement.service.UserService;
import static se.groupfish.restcasemanagement.data.DTOUser.toDTO;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import static se.groupfish.restcasemanagement.data.DTOUser.toEntity;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.NotFoundException;

import static se.groupfish.restcasemanagement.data.DTOUser.usersListToDTOUserList;

@Component
public final class RestUserService {

	private final UserService userService;

	public RestUserService(UserService userService) {
		this.userService = userService;
	}

	public User saveUser(DTOUser dtoUser) {

		try {
			User savedUser = toEntity(dtoUser);
			return userService.createUser(savedUser);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public void updateUser(Long userId, String userName) {

		try {
			User userForUpdate = userService.getUserById(userId);
			userService.updateUserUsername(userForUpdate.getId(), userName);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public void disableUser(Long id, String state) {

		try {
			User userForDisactivate = userService.getUserById(id);
			userService.updateUserState(userForDisactivate.getId(), state);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public DTOUser getUserByNumber(String number) {

		try {
			User getUser = userService.findUserByNumber(number);
			DTOUser user = toDTO(getUser);
			return user;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NotFoundException e2) {
			throw new NotFoundException(e2.getMessage());
		}
	}

	public DTOUser getUserByFirstName(String firstName) {

		try {
			User getUser = userService.findUserByFirstName(firstName);
			DTOUser user = toDTO(getUser);
			return user;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NotFoundException e2) {
			throw new NotFoundException(e2.getMessage());
		}
	}

	public List<DTOUser> getAllDTOUsers(Long teamId) {

		try {
			List<User> listUsers = userService.findAllUsersFromOneTeam(teamId);
			List<DTOUser> dtoUsersList = usersListToDTOUserList(listUsers);
			return dtoUsersList;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NotFoundException e2) {
			throw new NotFoundException(e2.getMessage());
		}
	}

}
