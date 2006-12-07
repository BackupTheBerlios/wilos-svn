package wilos.presentation.web;

import javax.swing.tree.DefaultTreeModel;

import wilos.business.services.process.ProcessService;

public class TreeViewBean {
	private DefaultTreeModel model;

	private ProcessService processService;

	private String processId;

	public DefaultTreeModel getModel() {
		this.model = this.processService.buildTree(this.processId);
		return model;
	}

	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
}
