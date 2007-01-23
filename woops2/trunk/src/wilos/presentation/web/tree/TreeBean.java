
package wilos.presentation.web.tree ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;
import wilos.presentation.web.task.TaskViewerBean;

/**
 * <p/> A basic backing bean for a ice:tree component. The only instance variable needed is a
 * DefaultTreeModel Object which is bound to the icefaces tree component in the jspx code.
 * </p>
 * <p/> The tree created by this backing bean is used to control the selected panel in a
 * ice:panelStack.
 * </p>
 */
public class TreeBean {

	private ProcessService processService ;

	String processId = "" ;
	
	public Boolean affectedTaskFilter = false;
	
	// tree default model, used as a value for the tree component
	private DefaultTreeModel model = null ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public TreeBean() {
		DefaultMutableTreeNode defaultTree = new DefaultMutableTreeNode();
		WilosObjectNode iceUserObject = new WilosObjectNode(defaultTree) ;
		iceUserObject.setText("Choose a project ...") ;
		defaultTree.setUserObject(iceUserObject) ;
		this.model = new DefaultTreeModel(defaultTree) ;
	}

	/**
	 * Gets the tree's default model.
	 * 
	 * @return tree model.
	 */
	
	private void buildModel(){
		// construire les cas avec la selection des filtres
		// creer la methode getConcreteTaskDescriptorByProcess
		// creer les methodes getAffectedConcreteTaskDescriptorsByProcess
		if(this.processId != null && !this.processId.equals("") ){
			Process process = this.processService.getEntireProcess(this.processId) ;
			ProcessNode processNode = new ProcessNode(process);
			this.model = new DefaultTreeModel(processNode) ;
		}
	}
	
	public DefaultTreeModel getModel() {
		return this.model ;
	}
	
	public List<SelectItem> getProjects(){
		List<SelectItem> projectsList = new ArrayList<SelectItem>();
		for (Process process : this.processService.getProcessesList()) {
			projectsList.add(new SelectItem(process.getId(), process.getName()));
		}
		projectsList.add(new SelectItem("", ""));
		return projectsList;
	}
	
	public void changeTreeActionListener(ActionEvent evt) {
		this.buildModel();
	}
	
	public void selectNodeActionListener(ActionEvent evt) {
		logger.debug("### TreeBean ### selectNodeActionListener");
		FacesContext context = FacesContext.getCurrentInstance(); 
		Map map = context.getExternalContext().getRequestParameterMap();
		
		String nodeId = (String) map.get("nodeId");
		logger.debug("### TreeBean ### selectNodeActionListener - nodeId ="+nodeId);
		String pageId = (String) map.get("pageId");
		logger.debug("### TreeBean ### selectNodeActionListener - pageId ="+pageId);
		// 
		this.selectNodeToShow(nodeId,pageId);

		/*
		TaskViewerBean tv = (TaskViewerBean) context.getApplication()
		.getVariableResolver().resolveVariable(context,"TaskViewerBean");
		tv.setTaskId(basicNodeId);
		tv.buildTaskDescriptor();

		MenuBean mb = (MenuBean) context.getApplication()
		.getVariableResolver().resolveVariable(context,"menu");
		mb.changePage("taskviewer");
		*/
	}

	private void selectNodeToShow(String _objectId, String _pageId) {
		logger.debug("### TreeBean ### selectNodeToShow id="+_objectId+" page="+_pageId);
		FacesContext context = FacesContext.getCurrentInstance();
		if (_pageId.equals(WilosObjectNode.ACTIVITYNODE)){
			TaskViewerBean tv = (TaskViewerBean) context.getApplication()
			.getVariableResolver().resolveVariable(context,"TaskViewerBean");
			tv.setTaskId(_objectId);
		}
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return this.processId ;
	}

	/**
	 * Setter of processId.
	 * 
	 * @param _processId
	 *            The processId to set.
	 */
	public void setProcessId(String _processId) {
		this.processId = _processId ;
	}

	/**
	 * @return the processService
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

	public Boolean getAffectedTaskFilter() {
		return affectedTaskFilter;
	}

	public void setAffectedTaskFilter(Boolean _affectedTaskFilter) {
		this.affectedTaskFilter = _affectedTaskFilter;
	}
}
