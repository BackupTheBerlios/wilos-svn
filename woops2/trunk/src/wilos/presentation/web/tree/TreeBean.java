
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
import wilos.presentation.web.template.MenuBean;

/**
 * <p/> A basic backing bean for a ice:tree component. The only instance
 * variable needed is a DefaultTreeModel Object which is bound to the icefaces
 * tree component in the jspx code.
 * </p>
 * <p/> The tree created by this backing bean is used to control the selected
 * panel in a ice:panelStack.
 * </p>
 */
public class TreeBean {

	private ProcessService processService;

	private Process process;

	String processId = "";

	public Boolean affectedTaskFilter = false;

	// tree default model, used as a value for the tree component
	private DefaultTreeModel model = null;

	protected final Log logger = LogFactory.getLog(this.getClass());

	public TreeBean() {
		DefaultMutableTreeNode defaultTree = new DefaultMutableTreeNode();
		WilosObjectNode iceUserObject = new WilosObjectNode(defaultTree);
		iceUserObject.setText("Choose a project ...");
		defaultTree.setUserObject(iceUserObject);
		this.model = new DefaultTreeModel(defaultTree);
	}

	/**
	 * Gets the tree's default model.
	 * 
	 * @return tree model.
	 */

	private void buildModel(boolean _mustBuildProcess) {
		if (this.processId != null && !this.processId.equals("")) {
			if (_mustBuildProcess) {
				this.process = this.processService
						.getEntireProcess(this.processId);
			}
			ProcessNode processNode;
			if (this.affectedTaskFilter)
				processNode = new ProcessNode(process, null);
			else
				processNode = new ProcessNode(process, null);
			this.model = new DefaultTreeModel(processNode);
		}
	}
	
	public DefaultTreeModel getModel() {
		return this.model;
	}

	public List<SelectItem> getProjects() {
		List<SelectItem> projectsList = new ArrayList<SelectItem>();
		for (Process process : this.processService.getProcessesList()) {
			projectsList
					.add(new SelectItem(process.getId(), process.getName()));
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
	}

	private void selectNodeToShow(String _objectId, String _pageId) {
		logger.debug("### TreeBean ### selectNodeToShow id="+_objectId+" page="+_pageId);
		FacesContext context = FacesContext.getCurrentInstance();
		MenuBean mb = (MenuBean) context.getApplication()
		.getVariableResolver().resolveVariable(context,"menu");
		
		if (_pageId.equals(WilosObjectNode.ACTIVITYNODE)){
			// TODO faire selectNodeToShow WilosObjectNode.ACTIVITYNODE
		}
		else if (_pageId.equals(WilosObjectNode.CONCRETETASKNODE)){
			// TODO faire selectNodeToShow WilosObjectNode.CONCRETETASKNODE
		}
		else if (_pageId.equals(WilosObjectNode.ITERATIONNODE)){
			// TODO faire selectNodeToShow WilosObjectNode.ITERATIONNODE
		}
		else if (_pageId.equals(WilosObjectNode.PHASENODE)){
			// TODO faire selectNodeToShow WilosObjectNode.PHASENODE
		}
		else if (_pageId.equals(WilosObjectNode.PROCESSNODE)){
			// TODO faire selectNodeToShow WilosObjectNode.PROCESSNODE
		}
		else if (_pageId.equals(WilosObjectNode.PROJECTNODE)){
			// TODO faire selectNodeToShow WilosObjectNode.PROJECTNODE
		}
		else if (_pageId.equals(WilosObjectNode.TASKNODE)){
			TaskViewerBean tv = (TaskViewerBean) context.getApplication()
			.getVariableResolver().resolveVariable(context,"TaskViewerBean");
			tv.setTaskId(_objectId);
			// model building
			tv.buildTaskDescriptor();
			mb.changePage(_pageId);
		}
		else {
			// didnt found the node's class
			new ClassNotFoundException("coulnd't found the node class");
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
