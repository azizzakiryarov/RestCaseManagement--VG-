package se.groupfish.restcasemanagement.service;

import java.util.Collection;
import org.springframework.stereotype.Component;
import org.springframework.dao.DataAccessException;
import se.groupfish.restcasemanagement.data.DTOTeam;
import se.groupfish.springcasemanagement.model.Team;
import se.groupfish.springcasemanagement.model.User;
import se.groupfish.springcasemanagement.service.TeamService;
import se.groupfish.springcasemanagement.service.UserService;
import se.groupfish.restcasemanagement.exception.NotFoundException;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import static se.groupfish.restcasemanagement.data.DTOTeam.toEntity;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.WebInternalErrorException;
import static se.groupfish.restcasemanagement.data.DTOTeam.teamListToDTOTeamList;

@Component
public final class RestTeamService {

	private final TeamService teamService;
	private final UserService userService;

	public RestTeamService(TeamService teamService, UserService userService) {
		this.teamService = teamService;
		this.userService = userService;
	}

	public Team createTeam(DTOTeam dtoTeam) {

		try {
			Team createdTeam = toEntity(dtoTeam);
			return teamService.createTeam(createdTeam);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public void updateTeam(Long id, String teamName) {

		try {
			Team updatedTeam = teamService.getTeamById(id);
			teamService.updateTeamName(updatedTeam.getId(), teamName);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NotFoundException e2) {
			throw new NotFoundException(e2.getMessage());
		} catch (NullPointerException e3) {
			throw new NullPointException(e3.getMessage());
		} catch (DataAccessException e4) {
			throw new WebInternalErrorException(e4.getMessage());
		}
	}

	public void disableTeam(Long id) {
		try {
			teamService.disableTeam(id);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NotFoundException e2) {
			throw new NotFoundException(e2.getMessage());
		}
	}

	public void activateTeam(Long id) {
		try {
			teamService.activateTeam(id);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NotFoundException e2) {
			throw new NotFoundException(e2.getMessage());
		}
	}

	public Collection<DTOTeam> getAllDTOTeams(Collection<DTOTeam> dtoTeams) {

		try {
			Collection<Team> list = teamService.getAllTeam();
			Collection<DTOTeam> teams = teamListToDTOTeamList(list);
			return teams;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NotFoundException e2) {
			throw new NotFoundException(e2.getMessage());
		}
	}

	public void addUserToOneTeam(Long teamId, Long userId) {

		try {
			User userAddToTeam = userService.getUserById(userId);
			Team teamToUser = teamService.getTeamById(teamId);
			userAddToTeam.setTeam(teamToUser);
			teamService.addUserToTeam(teamId, userAddToTeam.getId());
		} catch (ServiceException e) {
			throw new BadRequestException(e.getMessage());
		} catch (NullPointerException e3) {
			throw new NotFoundException(e3.getMessage() + "  ''Cannot find this user!!!'' ");
		}
	}
}
