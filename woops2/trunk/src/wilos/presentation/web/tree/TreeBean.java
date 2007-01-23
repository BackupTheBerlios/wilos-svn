
package wilos.presentation.web.tree ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.project.ProjectService;
import wilos.model.misc.project.Project;
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

	private ProjectService projectService;
	
	private Project project;

	private String projectId = "";

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

	private void buildModel(boolean _mustBuildProject) {
		if (this.projectId != null && !this.projectId.equals("")) {
			if (_mustBuildProject) {
				this.project = this.projectService.getProject(this.projectId);
			}
			ProjectNode projectNode;
			if (this.affectedTaskFilter)
				projectNode = new ProjectNode(this.project, null);
			else
				projectNode = new ProjectNode(this.project, null);
			this.model = new DefaultTreeModel(projectNode);
		}
	}
	
	public DefaultTreeModel getModel() {
		return this.model;
	}

	public List<SelectItem> getProjects() {
		List<SelectItem> projectsList = new ArrayList<SelectItem>();
		
		for (Project project : this.projectService.getAllProjects()) {
			//Add the project if the wilosUser is in this project.
			projectsList.add(new SelectItem(project.getProject_id(), project.getName()));
		}
		projectsList.add(new SelectItem("", "Choose a project ..."));
		return projectsList;
	}
	
	public void changeTreeActionListener(ActionEvent evt) {
		this.buildModel(true);
	}
	
	public void filterTreeActionListener(ValueChangeEvent evt) {
		this.buildModel(false);
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
	public String getProjectId() {
		return this.projectId ;
	}

	/**
	 * Setter of processId.
	 * 
	 * @param _processId
	 *            The processId to set.
	 */
	public void setProjectId(String _processId) {
		this.projectId = _processId ;
	}

	public Boolean getAffectedTaskFilter() {
		return affectedTaskFilter;
	}

	public void setAffectedTaskFilter(Boolean _affectedTaskFilter) {
		this.affectedTaskFilter = _affectedTaskFilter;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}
