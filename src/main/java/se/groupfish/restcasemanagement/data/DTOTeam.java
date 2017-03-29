package se.groupfish.restcasemanagement.data;

import java.util.ArrayList;
import java.util.Collection;

import se.groupfish.springcasemanagement.model.Team;

public final class DTOTeam {

	private final Long id;
	private final String teamName;
	private final String state;

	private DTOTeam(Long id, String teamName, String state) {
		this.id = id;
		this.teamName = teamName;
		this.state = state;

	}

	private DTOTeam() {
		this.id = null;
		this.teamName = null;
		this.state = null;
	}

	public static DTOTeamBuilder builder() {
		return new DTOTeamBuilder();
	}

	public Long getId() {
		return id;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getState() {
		return state;
	}

	public static DTOTeam toDTO(Team entity) {

		DTOTeamBuilder builder = new DTOTeamBuilder();
		builder.setId(entity.getId()).setTeamName(entity.getTeamName()).setState(entity.getState());
		return builder.build(entity.toString());
	}

	public static Team toEntity(DTOTeam dataTransferObject) {

		if (dataTransferObject.equals(null)) {
			throw new NullPointerException("DataTransferObject is null");
		} else {
			Team team = new Team(dataTransferObject.getTeamName(), dataTransferObject.getState());
			return team;
		}
	}

	public static Collection<DTOTeam> teamListToDTOTeamList(Collection<Team> list) {
		Collection<DTOTeam> listDto = new ArrayList<>();
		for (Team teams : list) {
			listDto.add(DTOTeam.toDTO(teams));
		}
		return listDto;
	}

	public static final class DTOTeamBuilder {

		private Long id = null;
		private String teamName = "";
		private String state = "";

		private DTOTeamBuilder() {
		}

		public DTOTeamBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public DTOTeamBuilder setTeamName(String teamName) {
			this.teamName = teamName;
			return this;
		}

		public DTOTeamBuilder setState(String state) {
			this.state = state;
			return this;
		}

		public DTOTeam build(String dtoTeam) {
			return new DTOTeam(id, teamName, state);
		}
	}
}
