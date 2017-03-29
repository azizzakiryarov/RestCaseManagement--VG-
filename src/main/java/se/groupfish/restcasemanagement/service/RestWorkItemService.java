package se.groupfish.restcasemanagement.service;

import se.groupfish.restcasemanagement.data.DTOWorkItem;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.WorkItem;
import se.groupfish.springcasemanagement.service.IssueService;
import se.groupfish.springcasemanagement.service.WorkItemService;
import static se.groupfish.restcasemanagement.data.DTOWorkItem.toEntity;
import static se.groupfish.restcasemanagement.data.DTOWorkItem.workItemsListToDTOWorkItemList;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

@Component
public final class RestWorkItemService {

	private final WorkItemService workItemService;
	private final IssueService issueService;

	public RestWorkItemService(WorkItemService workItemService, IssueService issueService) {
		this.workItemService = workItemService;
		this.issueService = issueService;
	}

	public WorkItem saveWorkItem(DTOWorkItem dtoWorkItem) throws ServiceException {

			WorkItem savedWorkItem = toEntity(dtoWorkItem);
			return workItemService.createWorkItem(savedWorkItem);
	}

	public void updateWorkItemsState(Long id, String state) throws ServiceException, IOException {

		try {
			WorkItem updatedWorkItem = workItemService.getWorkItemById(id);
			workItemService.updateWorkItemState(updatedWorkItem.getId(), state);
		} catch (NullPointerException e) {
			throw new WebApplicationException(Status.NO_CONTENT);
		}
	}

	public void removeWorkItem(Long id) throws ServiceException {

		workItemService.removeWorkItem(id);

	}

	public void addWorkItemToUser(Long workItemId, Long userId) throws ServiceException {

		workItemService.addWorkItemToUser(workItemId, userId);
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByTeam(Long teamId) throws ServiceException {

		Collection<WorkItem> workItems = workItemService.getAllWorkItemforTeam(teamId);
		Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);

		return dtoWorkItems;
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByUser(Long userId) throws ServiceException {

		Collection<WorkItem> workItems = workItemService.getAllWorkItemforUser(userId);
		Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);

		return dtoWorkItems;
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByState(String state) throws ServiceException {

		Collection<WorkItem> workItems = workItemService.getWorkItemByState(state);
		Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);

		return dtoWorkItems;
	}

	public Collection<DTOWorkItem> getAllWorkItemsByContent(String descriptionContent) throws ServiceException {

		Collection<WorkItem> workItems = workItemService.getAllWorkItemByDescriptionContent(descriptionContent);
		Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);

		return dtoWorkItems;
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByIssue() throws ServiceException {

		Collection<WorkItem> workItems = issueService.getAllWorkItemsWithIssue();
		Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);

		return dtoWorkItems;
	}

}
