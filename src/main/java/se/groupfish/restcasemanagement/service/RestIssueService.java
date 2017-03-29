package se.groupfish.restcasemanagement.service;

import static se.groupfish.restcasemanagement.data.DTOIssue.toEntity;

import org.springframework.stereotype.Component;

import se.groupfish.restcasemanagement.data.DTOIssue;
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

	public Issue saveIssue(DTOIssue dtoIssue, Long workItemId) throws ServiceException {

		Issue savedIssue = toEntity(dtoIssue);
		WorkItem savedWorkItem = workItemService.getWorkItemById(workItemId);
		savedWorkItem.setIssue(savedIssue);
		return issueService.createIssue(savedIssue, savedWorkItem.getId());

	}

	public void updateIssue(Long issueId, String comment) throws ServiceException {

		Issue updatedIssue = issueService.getById(issueId);
		issueService.updateIssueComment(updatedIssue.getId(), comment);

	}
}
