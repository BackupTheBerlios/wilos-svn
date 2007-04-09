/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/
package wilos.presentation.web.process ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Map;
import java.util.ResourceBundle ;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;
import javax.faces.event.ActionEvent ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import wilos.business.services.misc.project.ProjectService ;
import wilos.business.services.misc.wilosuser.ProcessManagerService;
import wilos.business.services.spem2.process.ProcessManagementService ;
import wilos.business.services.spem2.process.ProcessService ;
import wilos.model.spem2.process.Process ;

public class ProcessBean {

	private ProcessService processService ;

	private ProjectService projectService ;
	
	private ProcessManagerService processManagerService;

	private List<HashMap<String, Object>> processesList ;

	private String processesListView ;

	private static final String VIEW_NULL = "processesManagement_null" ;

	private static final String VIEW_NOT_NULL = "processesManagementPanelGroup_not_null" ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	private ProcessManagementService processManagementService ;
	
	private boolean visiblePopup = false;
	
	private String processId;

	public ProcessBean() {
		//None.
	}
	
	/* Manage the popup. */

	public void confirmDelete(ActionEvent event) {

		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale()) ;
		FacesMessage message = new FacesMessage() ;
		message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
		FacesContext facesContext = FacesContext.getCurrentInstance() ;

		for(Process process : this.processService.getProcessesList()){
			if(process.getId().equals(processId)){
				if(this.processManagementService.hasBeenInstanciated(this.processId)){
					message.setSummary(bundle.getString("component.process.management.deletionforbidden")) ;
				}
				else{
					this.processManagementService.removeProcess(process) ;
					message.setSummary(bundle.getString("component.process.management.deletiondone")) ;
				}
			}
		}
		facesContext.addMessage(null, message) ;
		
		this.visiblePopup = false;
	}

	public void cancelDelete(ActionEvent event) {
		this.visiblePopup = false;
	}

	/* Others */

	/**
	 * 
	 * Deletes selected process from the database
	 *
	 * @param e event received when a user clicks on suppress button in the datatable
	 */
	public void deleteProcess(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		this.processId = (String) map.get("processId");
		
		this.visiblePopup = true;
	}

	/**
	 * Getter of processesList.
	 * 
	 * @return the processesList.
	 */
	public List<HashMap<String, Object>> getProcessesList() {
		if(this.processesList == null || this.processesList.size() != this.processService.getProcessesList().size()){
			this.processesList = new ArrayList<HashMap<String, Object>>() ;
			for(Process process : this.processService.getProcessesList()){
				HashMap<String, Object> processDescription = new HashMap<String, Object>() ;
				processDescription.put("presentationName", process.getPresentationName()) ;
				processDescription.put("id", process.getId()) ;
				processDescription.put("isEditable", new Boolean(false)) ;
				
				if (process.getProcessManager() != null)
				{
					process.setProcessManager(this.processManagerService.getProcessManager(process.getProcessManager().getWilosuser_id()));
					processDescription.put("owner", process.getProcessManager().getFirstname()+" "+process.getProcessManager().getName()) ;
				}
				
				this.processesList.add(processDescription) ;
			}
			return this.processesList ;
		}
		return this.processesList ;
	}

	/**
	 * 
	 * Editing process name
	 *
	 * @param e event received when a user clicks on edit button in the datatable
	 */
	public void editName(ActionEvent e) {
		String processId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("processEditId") ;
		for(HashMap<String, Object> processDescription : this.processesList){
			if( ((String) processDescription.get("id")).equals(processId)){
				processDescription.put("isEditable", new Boolean(true)) ;
			}
		}
	}

	/**
	 * 
	 * Saving new process name
	 *
	 * @param e event received when a user clicks on save button in the datatable
	 */
	@SuppressWarnings("unchecked")
	public void saveName(ActionEvent e) {
		String processId = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("processSaveId") ;
		Process process = this.processService.getProcessDao().getProcess(processId) ;
		for(Iterator iter = this.getProcessesList().iterator(); iter.hasNext();){
			HashMap<String, Object> processDescription = (HashMap<String, Object>) iter.next() ;
			if( ((String) processDescription.get("id")).equals(processId)){
				process.setPresentationName((String) processDescription.get("presentationName")) ;
				this.processService.getProcessDao().saveOrUpdateProcess(process) ;
				processDescription.put("isEditable", new Boolean(false)) ;
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
	 * @param _projectService
	 *            The projectService to set.
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService ;
	}

	public ProcessManagementService getProcessManagementService() {
		return processManagementService ;
	}

	public void setProcessManagementService(ProcessManagementService processManagementService) {
		this.processManagementService = processManagementService ;
	}

	public ProcessManagerService getProcessManagerService() {
		return processManagerService;
	}

	public void setProcessManagerService(ProcessManagerService processManagerService) {
		this.processManagerService = processManagerService;
	}

	/**
	 * @return the visiblePopup
	 */
	public boolean getVisiblePopup() {
		return this.visiblePopup;
	}

	/**
	 * @param visiblePopup the visiblePopup to set
	 */
	public void setVisiblePopup(boolean _visiblePopup) {
		this.visiblePopup = _visiblePopup;
	}

}
