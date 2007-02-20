
package wilos.presentation.web.process ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;

/**
 * @author BlackMilk
 * 
 * This class represents ... TODO
 * 
 */
public class ProcessBean {

	private ProcessService processService ;

	private List<HashMap<String, String>> processesList ;

	private String processesListView ;

	private static final String VIEW_NULL = "processesManagement_null" ;

	private static final String VIEW_NOT_NULL = "processesManagementPanelGroup_not_null" ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	private boolean isEditable = false;

	public ProcessBean() {

	}

	public void deleteProcess(ActionEvent e) {
		String processId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("processId");
		//Process process = this.processService.getPro
	}

	/**
	 * Getter of processesList.
	 * 
	 * @return the processesList.
	 */
	public List<HashMap<String, String>> getProcessesList() {
		 if(this.processesList == null){
			this.processesList = new ArrayList<HashMap<String, String>>() ;
			for(Process process : this.processService.getProcessesList()){
				HashMap<String, String> processDescription = new HashMap<String, String>() ;
				processDescription.put("presentationName", process.getPresentationName()) ;
				processDescription.put("id", process.getId()) ;
				this.processesList.add(processDescription) ;
			}
			return this.processesList ;
		 }
		return this.processesList ;
	}
	
	public void editName(ActionEvent e){
		this.isEditable = true;
	}
	
	public void saveName(ActionEvent e){		
		String processId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("processSaveId");
		Process process = this.processService.getProcessDao().getProcess(processId);
		for(Iterator iter = this.getProcessesList().iterator(); iter.hasNext();){
			HashMap<String,String> processDescription = (HashMap<String,String>) iter.next() ;
			if(((String)processDescription.get("id")).equals(processId)){
				process.setPresentationName(processDescription.get("presentationName"));
				this.processService.getProcessDao().saveOrUpdateProcess(process);
			}
		}
		this.isEditable = false;
	}

	/**
	 * Setter of processesList.
	 * 
	 * @param _processesList
	 *            The processesList to set.
	 */
	public void setProcessesList(List<HashMap<String, String>> _processesList) {
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
	 * Getter of isEditable.
	 *
	 * @return the isEditable.
	 */
	public boolean getIsEditable() {
		return this.isEditable ;
	}

	/**
	 * Setter of isEditable.
	 *
	 * @param _isEditable The isEditable to set.
	 */
	public void setIsEditable(boolean _isEditable) {
		this.isEditable = _isEditable ;
	}

}
