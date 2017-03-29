package se.groupfish.restcasemanagement.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.groupfish.springcasemanagement.model.User;

public final class DTOUser {

	private final Long id;
	private final String firstName;
	private final String lastName;
	private final String userName;
	private final String userNumber;
	private final String state;
	private Long teamId;

	private DTOUser(Long id, String firstName, String lastName, String userName, String userNumber, String state) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.userNumber = userNumber;
		this.state = state;
	}

	private DTOUser(Long id, String firstName, String lastName, String userName, String userNumber, String state,
			Long teamId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.userNumber = userNumber;
		this.state = state;
		this.teamId = teamId;
	}

	private DTOUser() {
		this.id = null;
		this.firstName = null;
		this.lastName = null;
		this.userName = null;
		this.userNumber = null;
		this.state = null;
	}

	public static DTOUserBuilder builder() {
		return new DTOUserBuilder();
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public String getState() {
		return state;
	}

	public Long getTeamId() {
		return teamId;
	}

	public static DTOUser toDTO(User entity) {

		DTOUserBuilder builder = new DTOUserBuilder();
		builder.setId(entity.getId()).setFirstName(entity.getFirstName()).setLastName(entity.getLastName())
				.setUserName(entity.getUserName()).setUserNumber(entity.getUserNumber()).setState(entity.getState())
				.setTeamId(entity.getTeam().getId());

		return builder.build(entity.toString());
	}

	public static User toEntity(DTOUser dataTransferObject) {

		User user = new User(dataTransferObject.getFirstName(), dataTransferObject.getLastName(),
				dataTransferObject.getUserName(), dataTransferObject.getUserNumber(), dataTransferObject.getState());

		return user;
	}

	public static List<DTOUser> usersListToDTOUserList(Collection<User> list) {
		List<DTOUser> listDto = new ArrayList<>();
		for (User users : list) {
			listDto.add(DTOUser.toDTO(users));
		}
		return listDto;
	}

	public static final class DTOUserBuilder {

		private Long id = null;
		private String firstName = "";
		private String lastName = "";
		private String userName = "";
		private String userNumber = "";
		private String state = "";
		private Long teamId = null;

		private DTOUserBuilder() {
			;
		}

		public DTOUserBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public DTOUserBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public DTOUserBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public DTOUserBuilder setUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public DTOUserBuilder setUserNumber(String userNumber) {
			this.userNumber = userNumber;
			return this;
		}

		public DTOUserBuilder setState(String state) {
			this.state = state;
			return this;
		}

		public DTOUserBuilder setTeamId(Long teamId) {
			this.teamId = teamId;
			return this;
		}

		public DTOUser build(String dtoUser) {
			return new DTOUser(id, firstName, lastName, userName, userNumber, state, teamId);
		}
	}
}
