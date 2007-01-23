
package wilos.presentation.web.tree ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.project.ProjectService;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.presentation.web.task.ActivityViewerBean;
import wilos.presentation.web.task.ConcreteTaskViewerBean;
import wilos.presentation.web.task.IterationViewerBean;
import wilos.presentation.web.task.PhaseViewerBean;
import wilos.presentation.web.task.ProjectViewerBean;
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
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession httpSession = httpServletRequest.getSession() ;
		WilosUser wilosUser = (WilosUser) httpSession.getAttribute("wilosUser") ; 
		
		for (Project project : this.projectService.getAllProjects()) {
			//if(loginService.isParticipant(wilosUser))
			//if(project.getParticipants().contains((Participant)wilosUser))
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
			ActivityViewerBean av = (ActivityViewerBean) context.getApplication()
			.getVariableResolver().resolveVariable(context,"ActivityViewerBean");
			av.setActivityId(_objectId);
			// model building
			av.buildActivity();
			mb.changePage(_pageId);
		}
		else if (_pageId.equals(WilosObjectNode.CONCRETETASKNODE)){
			ConcreteTaskViewerBean ctv = (ConcreteTaskViewerBean) context.getApplication()
			.getVariableResolver().resolveVariable(context,"ConcreteTaskViewerBean");
			ctv.setConcreteTaskDescriptorId(_objectId);
			// model building
			ctv.buildConcreteTaskDescriptor();
			mb.changePage(_pageId);
		}
		else if (_pageId.equals(WilosObjectNode.ITERATIONNODE)){
			IterationViewerBean iv = (IterationViewerBean) context.getApplication()
			.getVariableResolver().resolveVariable(context,"IterationViewerBean");
			iv.setIterationId(_objectId);
			// model building
			iv.buildIteration();
			mb.changePage(_pageId);
		}
		else if (_pageId.equals(WilosObjectNode.PHASENODE)){
			PhaseViewerBean pb = (PhaseViewerBean) context.getApplication()
			.getVariableResolver().resolveVariable(context,"PhaseViewerBean");
			pb.setPhaseId(_objectId);
			// model building
			pb.buildPhaseModel();
			mb.changePage(_pageId);
		}
		else if (_pageId.equals(WilosObjectNode.PROJECTNODE)){
			ProjectViewerBean p = (ProjectViewerBean) context.getApplication()
			.getVariableResolver().resolveVariable(context,"ProjectViewerBean");
			p.setProjectId(_objectId);
			// model building
			p.buildProjectModel();
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
