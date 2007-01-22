package wilos.presentation.web.task;

import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.model.spem2.task.TaskDescriptor;

public class TaskViewerBean {
	
	//private Boolean taskClicked = false;
	private String taskId = "";
	private TaskDescriptor taskDescriptor = new TaskDescriptor();
	private TaskDescriptorService taskDescriptorService;
	
	public TaskViewerBean(){
		
	}
	
	/*
	public Boolean getTaskClicked() {
		return taskClicked;
	}

	public void setTaskClicked(Boolean _taskClicked) {
		this.taskClicked = _taskClicked;
	}
	*/
	
	public void buildTaskDescriptor(){
		taskDescriptor = new TaskDescriptor();
		if (!(this.taskId.equals("")) && this.taskId != null){
			this.taskDescriptor = this.taskDescriptorService.getTaskDescriptorDao().getTaskDescriptor(this.taskId);
		}
	}
	
	public TaskDescriptor getTaskDescriptor() {
		this.buildTaskDescriptor();
		return taskDescriptor;
	}

	public void setTaskDescriptor(TaskDescriptor taskDescriptor) {
		this.taskDescriptor = taskDescriptor;
	}


	public TaskDescriptorService getTaskDescriptorService() {
		return taskDescriptorService;
	}


	public void setTaskDescriptorService(TaskDescriptorService taskDescriptorService) {
		this.taskDescriptorService = taskDescriptorService;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}
