package se.groupfish.restcasemanagement.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.groupfish.springcasemanagement.model.Issue;

public final class DTOIssue {

	private final Long id;
	private final String comment;

	public DTOIssue(Long id, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public DTOIssue() {
		this.id = null;
		this.comment = null;
	}

	public DTOIssueBuilder builder() {
		return new DTOIssueBuilder();
	}

	public Long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public static DTOIssue toDTO(Issue entity) {

		DTOIssueBuilder builder = new DTOIssueBuilder();
		builder.setId(entity.getId()).setComment(entity.getComment());
		return builder.build(entity.toString());
	}

	public static Issue toEntity(DTOIssue dataTransferObject) {

		Issue issue = new Issue(dataTransferObject.getComment());
		return issue;
	}

	public static List<DTOIssue> issuesListToDTOIssueList(Collection<Issue> list) {
		List<DTOIssue> listDto = new ArrayList<>();
		for (Issue issues : list) {
			listDto.add(DTOIssue.toDTO(issues));
		}
		return listDto;
	}

	public static final class DTOIssueBuilder {

		private Long id = null;
		private String comment = "";

		public DTOIssueBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public DTOIssueBuilder setComment(String comment) {
			this.comment = comment;
			return this;
		}

		public DTOIssue build(String dtoIssue) {
			return new DTOIssue(id, comment);
		}
	}
}
