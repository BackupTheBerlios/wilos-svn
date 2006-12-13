package wilos.presentation.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.task.TaskDescriptorService;
import wilos.model.spem2.task.TaskDescriptor;

public class TaskDescriptorBean {

	private List<TaskDescriptor> taskDescriptorList;

	private TaskDescriptor taskDescriptor;

	private TaskDescriptorService taskDescriptorService;

	private String processId = "";

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor.
	 * 
	 */
	public TaskDescriptorBean() {
		this.logger.debug("--- TaskDescriptor --- == creating ..." + this);
		this.taskDescriptor = new TaskDescriptor();
	}

	/**
	 * Getter of activitiesList.
	 * 
	 * @return the activitiesList.
	 */
	public List<TaskDescriptor> getTaskDescriptorList() {
		this.taskDescriptorList = new ArrayList<TaskDescriptor>();
		logger.debug("### TaskDescriptorBean ### getTaskDescriptorList id= "
				+ this.processId);
		taskDescriptorList.addAll(this.taskDescriptorService
				.getTaskDescriptorsFromProcess(this.processId));
		this.logger.debug("### TaskDescriptorBean ### taskDescriptorList ="
				+ this.taskDescriptorList);
		return this.taskDescriptorList;
	}

	/**
	 * Getter of taskDescriptor.
	 * 
	 * @return the taskDescriptor.
	 */
	public TaskDescriptor getTaskDescriptor() {
		return this.taskDescriptor;
	}

	/**
	 * Setter of taskDescriptor.
	 * 
	 * @param _taskDescriptor
	 *            The taskDescriptor to set.
	 */
	public void setTaskDescriptor(TaskDescriptor _taskDescriptor) {
		this.taskDescriptor = _taskDescriptor;
	}

	/**
	 * Getter of taskDescriptorList.
	 * 
	 * @return the taskDescriptorList.
	 */
	/*
	 * public List<RoleDescriptor> getRoleDescriptorList() { return
	 * this.roleDescriptorList ; }
	 */

	/**
	 * Setter of roleDescriptorList.
	 * 
	 * @param _roleDescriptorList
	 *            The roleDescriptorList to set.
	 */
	public void setTaskDescriptorList(List<TaskDescriptor> _taskDescriptorList) {
		this.taskDescriptorList = _taskDescriptorList;
	}

	/**
	 * Getter of taskDescriptorService.
	 * 
	 * @return the taskDescriptorService.
	 */
	public TaskDescriptorService getTaskDescriptorService() {
		return this.taskDescriptorService;
	}

	/**
	 * Setter of roleDescriptorService.
	 * 
	 * @param _roleDescriptorService
	 *            The roleDescriptorService to set.
	 */
	public void setTaskDescriptorService(
			TaskDescriptorService _taskDescriptorService) {
		this.taskDescriptorService = _taskDescriptorService;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
}
