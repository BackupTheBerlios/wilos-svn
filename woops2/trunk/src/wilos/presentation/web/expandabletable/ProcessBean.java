package wilos.presentation.web.expandabletable;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;

/**
 * @author Sebastien
 * 
 */
public class ProcessBean {

	private ProcessService processService;
	
	private WebSessionService webSessionService;
	
	private ProjectService projectService;

	private String selectedProcessGuid = "default";
	
	private boolean readOnly = false;
	
	private Project project;
	
	private boolean isVisibleExpTable = false;
	
	private boolean isProjectManager = false;

	/**
	 * Give all the processes save in the database
	 * @return
	 */
	public List<SelectItem> getProcesses() {

		List<SelectItem> processesList = new ArrayList<SelectItem>();

		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale());
		processesList.add(new SelectItem("default", bundle
				.getString("component.combobox.processchoice.defaultlabel")));

		List<Process> processes = this.processService.getProcessDao()
				.getAllProcesses();
		for (Process process : processes) {
			processesList.add(new SelectItem(process.getGuid(), process
					.getPresentationName()));
		}
		return processesList;
	}

	/**
	 * listener on the processes selection combobox
	 */
	public void changeProcessSelectionListener(ValueChangeEvent evt) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		ExpTableBean expTableBean = (ExpTableBean) context.getApplication().getVariableResolver()
				.resolveVariable(context, "ExpTableBean");
		
		this.selectedProcessGuid = (String) evt.getNewValue();
		expTableBean.setSelectedProcessGuid((String) evt.getNewValue());
		if (this.selectedProcessGuid.equals("default")) {
			this.isVisibleExpTable = false;
		} else {
			this.isVisibleExpTable = true;
		}
	}

	/**
	 * @return the processService
	 */
	public ProcessService getProcessService() {
		return this.processService;
	}

	/**
	 * @param _processService
	 *            the processService to set
	 */
	public void setProcessService(ProcessService _processService) {
		this.processService = _processService;
	}

	/**
	 * @return the processGuid
	 */
	public String getSelectedProcessGuid() {
		return this.selectedProcessGuid;
	}

	/**
	 * @param _processGuid the processGuid to set
	 */
	public void setSelectedProcessGuid(String _processGuid) {
		this.selectedProcessGuid = _processGuid;
	}

	/**
	 * @return the isVisibleExpTable
	 */
	public boolean getIsVisibleExpTable() {
		return this.isVisibleExpTable;
	}

	/**
	 * @param _isVisibleExpTable the isVisibleExpTable to set
	 */
	public void setIsVisibleExpTable(boolean _isVisibleExpTable) {
		this.isVisibleExpTable = _isVisibleExpTable;
	}

	/**
	 * @return the readOnly
	 */
	public boolean getReadOnly() {
		
		String projectId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID);
		Project project = this.projectService.getProject(projectId);
		
		if (project.getProcess() == null) {
			this.readOnly = false;
		} else {
			this.readOnly = true;
			this.isVisibleExpTable = true;
			this.selectedProcessGuid = project.getProcess().getGuid();
		}
		
		return this.readOnly;
	}

	/**
	 * @param _readOnly the readOnly to set
	 */
	public void setReadOnly(boolean _readOnly) {
		this.readOnly = _readOnly;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return this.webSessionService;
	}

	/**
	 * @param _webSessionService the webSessionService to set
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService;
	}

	/**
	 * @return the projectService
	 */
	public ProjectService getProjectService() {
		return this.projectService;
	}

	/**
	 * @param _projectService the projectService to set
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService;
	}

	/**
	 * @return the isProjectManager
	 */
	public boolean getIsProjectManager() {
		
		String user_id = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID);
		this.project = this.projectService.getProject((String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID));
		this.isProjectManager = false;
		if (this.project.getProjectManager() != null) {
			if (this.project.getProjectManager().getWilosuser_id().equals(user_id)) {
				this.isProjectManager = true;
			}
		}
		
		return this.isProjectManager;
	}

	/**
	 * @param _isProjectManager the isProjectManager to set
	 */
	public void setIsProjectManager(boolean _isProjectManager) {
		this.isProjectManager = _isProjectManager;
	}
}
