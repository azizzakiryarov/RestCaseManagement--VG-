package se.groupfish.restcasemanagement.service;

import org.springframework.stereotype.Component;
import static se.groupfish.restcasemanagement.data.DTOTeam.toEntity;
import static se.groupfish.restcasemanagement.data.DTOTeam.teamListToDTOTeamList;

import java.util.Collection;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import se.groupfish.restcasemanagement.data.DTOTeam;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.Team;
import se.groupfish.springcasemanagement.model.User;
import se.groupfish.springcasemanagement.service.TeamService;
import se.groupfish.springcasemanagement.service.UserService;

@Component
public final class RestTeamService {

	private final TeamService teamService;
	private final UserService userService;

	public RestTeamService(TeamService teamService, UserService userService) {
		this.teamService = teamService;
		this.userService = userService;
	}

	public Team createTeam(DTOTeam dtoTeam) throws ServiceException {

		try {
			Team createdTeam = toEntity(dtoTeam);
			return teamService.createTeam(createdTeam);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
	}

	public void updateTeam(Long id, String teamName) throws ServiceException {

		try {
			Team updatedTeam = teamService.getTeamById(id);
			teamService.updateTeamName(updatedTeam.getId(), teamName);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}

	}

	public void disableTeam(Long id) throws ServiceException {
		try {
			teamService.disableTeam(id);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
	}

	public void activateTeam(Long id) throws ServiceException {
		try {
			teamService.activateTeam(id);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
	}

	public Collection<DTOTeam> getAllDTOTeams(Collection<DTOTeam> dtoTeams) throws ServiceException {

		Collection<Team> list = teamService.getAllTeam();
		Collection<DTOTeam> teams = teamListToDTOTeamList(list);
		return teams;

	}

	public void addUserToOneTeam(Long teamId, Long userId) throws ServiceException {

		User userAddToTeam = userService.getUserById(userId);
		Team teamToUser = teamService.getTeamById(teamId);
		userAddToTeam.setTeam(teamToUser);
		teamService.addUserToTeam(teamId, userAddToTeam.getId());

	}


}
