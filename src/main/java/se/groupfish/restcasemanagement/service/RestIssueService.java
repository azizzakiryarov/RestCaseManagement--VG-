package se.groupfish.restcasemanagement.service;

import static se.groupfish.restcasemanagement.data.DTOIssue.toEntity;

import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.data.DTOIssue;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.Issue;
import se.groupfish.springcasemanagement.model.WorkItem;
import se.groupfish.springcasemanagement.service.IssueService;
import se.groupfish.springcasemanagement.service.WorkItemService;

@Component
public final class RestIssueService {

	private final IssueService issueService;
	private final WorkItemService workItemService;

	public RestIssueService(IssueService issueService, WorkItemService workItemService) {
		this.issueService = issueService;
		this.workItemService = workItemService;
	}

	public Issue saveIssue(DTOIssue dtoIssue, Long workItemId) {

		try {
			Issue savedIssue = toEntity(dtoIssue);
			WorkItem savedWorkItem = workItemService.getWorkItemById(workItemId);
			savedWorkItem.setIssue(savedIssue);
			return issueService.createIssue(savedIssue, savedWorkItem.getId());
		} catch (ServiceException e) {
			throw new BadRequestException(e.getMessage());
		}

	}

	public void updateIssue(Long issueId, String comment) {

		try {
			Issue updatedIssue = issueService.getById(issueId);
			issueService.updateIssueComment(updatedIssue.getId(), comment);
		} catch (ServiceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
}
