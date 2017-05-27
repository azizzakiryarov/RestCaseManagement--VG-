package se.groupfish.restcasemanagement.service;

import se.groupfish.restcasemanagement.data.DTOWorkItem;
import se.groupfish.restcasemanagement.exception.BadRequestException;
import se.groupfish.restcasemanagement.exception.NullPointException;
import se.groupfish.springcasemanagement.exception.ServiceException;
import se.groupfish.springcasemanagement.model.WorkItem;
import se.groupfish.springcasemanagement.service.IssueService;
import se.groupfish.springcasemanagement.service.WorkItemService;
import static se.groupfish.restcasemanagement.data.DTOWorkItem.toEntity;
import static se.groupfish.restcasemanagement.data.DTOWorkItem.toDTO;
import static se.groupfish.restcasemanagement.data.DTOWorkItem.workItemsListToDTOWorkItemList;
import java.io.IOException;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public final class RestWorkItemService {

	private final WorkItemService workItemService;
	private final IssueService issueService;

	public RestWorkItemService(WorkItemService workItemService, IssueService issueService) {
		this.workItemService = workItemService;
		this.issueService = issueService;
	}

	public WorkItem saveWorkItem(DTOWorkItem dtoWorkItem) {

		try {
			WorkItem savedWorkItem = toEntity(dtoWorkItem);
			return workItemService.createWorkItem(savedWorkItem);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		}
	}

	public void updateWorkItemsState(Long id, String state) {

		try {
			WorkItem updatedWorkItem = workItemService.getWorkItemById(id);
			workItemService.updateWorkItemState(updatedWorkItem.getId(), state);
		} catch (NullPointerException e1) {
			throw new NullPointException(e1.getMessage());
		} catch (ServiceException e2) {
			throw new BadRequestException(e2.getMessage());
		} catch (IOException e3) {
			throw new BadRequestException(e3.getMessage());
		}
	}

	public void removeWorkItem(Long id) {

		try {
			workItemService.removeWorkItem(id);
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		}
	}

	public void addWorkItemToUser(Long workItemId, Long userId) {

		try {
			workItemService.addWorkItemToUser(workItemId, userId);
		} catch (ServiceException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByTeam(Long teamId) {

		try {
			Collection<WorkItem> workItems = workItemService.getAllWorkItemforTeam(teamId);
			Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);
			return dtoWorkItems;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByUser(Long userId) {

		try {
			Collection<WorkItem> workItems;
			workItems = workItemService.getAllWorkItemforUser(userId);
			Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);
			return dtoWorkItems;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByState(String state) {

		try {
			Collection<WorkItem> workItems = workItemService.getWorkItemByState(state);
			Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);
			return dtoWorkItems;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}

	}

	public Collection<DTOWorkItem> getAllWorkItemsByContent(String descriptionContent) {

		try {
			Collection<WorkItem> workItems = workItemService.getAllWorkItemByDescriptionContent(descriptionContent);
			Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);
			return dtoWorkItems;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public Collection<DTOWorkItem> getAllDTOWorkItemsByIssue() {

		try {
			Collection<WorkItem> workItems = issueService.getAllWorkItemsWithIssue();
			Collection<DTOWorkItem> dtoWorkItems = workItemsListToDTOWorkItemList(workItems);
			return dtoWorkItems;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointerException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}

	public DTOWorkItem getWorkItemById(Long id) {

		try {
			WorkItem workItem = workItemService.getWorkItemById(id);
			DTOWorkItem dtoWorkItem = toDTO(workItem);
			return dtoWorkItem;
		} catch (ServiceException e1) {
			throw new BadRequestException(e1.getMessage());
		} catch (NullPointException e2) {
			throw new NullPointException(e2.getMessage());
		}
	}
}
