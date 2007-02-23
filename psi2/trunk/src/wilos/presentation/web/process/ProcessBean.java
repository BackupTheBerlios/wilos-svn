
package wilos.presentation.web.process ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;

/**
 * @author BlackMilk
 * 
 * This class represents ... TODO
 * 
 */
public class ProcessBean {

	private ProcessService processService ;
	
	private ProjectService projectService;

	private List<HashMap<String, Object>> processesList ;

	private String processesListView ;

	private static final String VIEW_NULL = "processesManagement_null" ;

	private static final String VIEW_NOT_NULL = "processesManagementPanelGroup_not_null" ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public ProcessBean() {

	}

	public void deleteProcess(ActionEvent e) {
		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		FacesMessage message = new FacesMessage() ;
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		boolean allowed = true;
		
		String processId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("processId");
		for(Process process : this.processService.getProcessesList()){
			if(process.getId().equals(processId)){
				for(Project project : this.projectService.getAllProjects()){
					if(project.getProcess()!= null){
						if(project.getProcess().getId().equals(process.getId())){
							allowed = false;
							break;
						}
					}
				}
				//if(process.getProjects() == null || process.getProjects().isEmpty()){
				if(allowed){
					this.processService.getProcessDao().deleteProcess(process);
					message.setSummary(bundle.getString("component.process.management.deletiondone")) ;
					message.setSeverity(FacesMessage.SEVERITY_ERROR) ;					
					facesContext.addMessage(null, message) ;
				}
				else{
					message.setSummary(bundle.getString("component.process.management.deletionforbidden")) ;
					message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
					facesContext.addMessage(null, message) ;
				}
			}
		}
	}

	/**
	 * Getter of processesList.
	 * 
	 * @return the processesList.
	 */
	public List<HashMap<String, Object>> getProcessesList() {
		 if(this.processesList == null || this.processesList.size()!=this.processService.getProcessesList().size()){
			this.processesList = new ArrayList<HashMap<String, Object>>() ;
			for(Process process : this.processService.getProcessesList()){
				HashMap<String, Object> processDescription = new HashMap<String , Object>() ;
				processDescription.put("presentationName", process.getPresentationName()) ;
				processDescription.put("id", process.getId()) ;
				processDescription.put("isEditable", new Boolean(false)) ;
				
				//process = this.processService.getProcessDao().getProcess(process.getId());
				//ProcessManager pm = this.
				//String name = process.getProcessManager().getFirstname();
				//System.out.println(" ####################" + name + "###############################");
				//processDescription.put("owner", process.getProcessManager().getFirstname());
				this.processesList.add(processDescription) ;
			}
			return this.processesList ;
		 }
		return this.processesList ;
	}
	
	
	public void editName(ActionEvent e){
		String processId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("processEditId");
		for(HashMap<String,Object> processDescription : this.processesList){
			if(((String)processDescription.get("id")).equals(processId)){
				processDescription.put("isEditable", new Boolean(true));
			}
		}
	}
	
	public void saveName(ActionEvent e){		
		String processId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("processSaveId");
		Process process = this.processService.getProcessDao().getProcess(processId);
		for(Iterator iter = this.getProcessesList().iterator(); iter.hasNext();){
			HashMap<String,Object> processDescription = (HashMap<String,Object>) iter.next() ;
			if(((String)processDescription.get("id")).equals(processId)){
				process.setPresentationName((String)processDescription.get("presentationName"));
				this.processService.getProcessDao().saveOrUpdateProcess(process);
				processDescription.put("isEditable", new Boolean(false));
			}
		}
	}

	/**
	 * Setter of processesList.
	 * 
	 * @param _processesList
	 *            The processesList to set.
	 */
	public void setProcessesList(List<HashMap<String, Object>> _processesList) {
		this.processesList = _processesList ;
	}

	/**
	 * Getter of processesListView.
	 * 
	 * @return the processesListView.
	 */
	public String getProcessesListView() {
		if(this.getProcessesList().size() == 0){
			this.processesListView = VIEW_NULL ;
		}
		else{
			this.processesListView = VIEW_NOT_NULL ;
		}
		return this.processesListView ;
	}

	/**
	 * Setter of processesListView.
	 * 
	 * @param _processesListView
	 *            The processesListView to set.
	 */
	public void setProcessesListView(String _processesListView) {
		this.processesListView = _processesListView ;
	}

	/**
	 * Getter of processService.
	 * 
	 * @return the processService.
	 */
	public ProcessService getProcessService() {
		return this.processService ;
	}

	/**
	 * Setter of processService.
	 * 
	 * @param _processService
	 *            The processService to set.
	 */
	public void setProcessService(ProcessService _processService) {
		this.processService = _processService ;
	}
	

	/**
	 * Getter of projectService.
	 *
	 * @return the projectService.
	 */
	public ProjectService getProjectService() {
		return this.projectService ;
	}

	/**
	 * Setter of projectService.
	 *
	 * @param _projectService The projectService to set.
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService ;
	}

}
