package se.groupfish.restcasemanagement.service;

import static se.groupfish.restcasemanagement.data.DTOUser.toEntity;
import static se.groupfish.restcasemanagement.data.DTOUser.usersListToDTOUserList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import static se.groupfish.restcasemanagement.data.DTOUser.toDTO;
import org.springframework.stereotype.Component;
import se.groupfish.restcasemanagement.data.DTOUser;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.User;
import se.groupfish.springcasemanagement.service.UserService;

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
		} catch (NullPointerException e1) {
			throw new NullPointException(e1.getMessage());
		} catch (ServiceException e2) {
			throw new BadRequestException(e2.getMessage());
		}
	}

	public void disableUser(Long id, String state) {

		try {
			User userForDisactivate = userService.getUserById(id);
			userService.updateUserState(userForDisactivate.getId(), state);
		} catch (NullPointerException e1) {
			throw new NullPointException(e1.getMessage());
		} catch (ServiceException e2) {
			throw new BadRequestException(e2.getMessage());
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
