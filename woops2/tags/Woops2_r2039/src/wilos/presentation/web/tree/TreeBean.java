
package wilos.presentation.web.tree ;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.web.template.MenuBean;
import wilos.presentation.web.viewer.ConcreteActivityViewerBean;
import wilos.presentation.web.viewer.ConcreteIterationViewerBean;
import wilos.presentation.web.viewer.ConcretePhaseViewerBean;
import wilos.presentation.web.viewer.ConcreteTaskViewerBean;
import wilos.presentation.web.viewer.ProjectViewerBean;

/**
 * @author deder
 * @author eperico
 * @author garwind <p/> A basic backing bean for a ice:tree component. The only instance variable
 *         needed is a DefaultTreeModel Object which is bound to the icefaces tree component in the
 *         jspx code.
 *         </p>
 *         <p/> The tree created by this backing bean is used to control the selected panel in a
 *         ice:panelStack.
 *         </p>
 */
public class TreeBean {

	/* Services */

	private static final String ACTIVITY_VIEWER_BEAN = "ConcreteActivityViewerBean";

	private static final String PROJECT_VIEWER_BEAN = "ProjectViewerBean";

	private static final String PHASE_VIEWER_BEAN = "ConcretePhaseViewerBean";

	private static final String ITERATION_VIEWER_BEAN = "ConcreteIterationViewerBean";

	private static final String CONCRETE_TASK_VIEWER_BEAN = "ConcreteTaskViewerBean";

	private WebSessionService webSessionService ;

	private ProjectService projectService ;

	private LoginService loginService ;

	private ParticipantService participantService ;

	private ProcessService processService;

	/* Simple fields */

	private Project project ;

	private String projectId = "default" ;

	private boolean affectedTaskFilter = false ;

	private boolean loadTree = true ;

	private boolean loadCheckBox = false ;

	// tree default model, used as a value for the tree component
	private DefaultTreeModel model = null ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public TreeBean() {
		this.model = new DefaultTreeModel(this.getDefaultTree()) ;
	}

	public DefaultMutableTreeNode getDefaultTree() {
		DefaultMutableTreeNode defaultTree = new DefaultMutableTreeNode() ;
		WilosObjectNode iceUserObject = new WilosObjectNode(defaultTree) ;
		iceUserObject.setText("Choose a project ...") ;
		defaultTree.setUserObject(iceUserObject) ;
		return defaultTree ;
	}

	/**
	 * Gets the tree's default model.
	 *
	 * @return tree model.
	 */
	private void buildModel(boolean _mustBuildProject) {
		if(this.projectId != null && !this.projectId.equals("default")){
			if(_mustBuildProject){
				// Put into the session the current project used.
				this.webSessionService.setAttribute(WebSessionService.PROJECT_ID, this.projectId) ;

				// Retrieve the entire project.
				this.project = this.projectService.getProject(this.projectId) ;
				//this.project = this.processService.getEntireProject(this.projectId) ;
			}
			ProjectNode projectNode = null ; Project p = new Project();

			if(this.affectedTaskFilter){
				// participant into session
				String wilosUserId = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID) ;
				Participant participant = this.participantService.getParticipant(wilosUserId);

				if(participant != null){
					Set<RoleDescriptor> roleDescriptorsList = new HashSet<RoleDescriptor>() ;
					projectNode = new ProjectNode(this.project, roleDescriptorsList) ;
				}
			}
			else{
				projectNode = new ProjectNode(this.project, null) ;
			}
			this.model = new DefaultTreeModel(projectNode) ;
		}
		else{
			// Build the default tree.
			this.model = new DefaultTreeModel(this.getDefaultTree()) ;

			// hide tree.
			this.loadTree = true ;
		}
	}

	public DefaultTreeModel getModel() {
		return this.model ;
	}

	public List<SelectItem> getProjects() {
		List<SelectItem> projectsList = new ArrayList<SelectItem>() ;

		String wilosUserId = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID) ;
		Participant participant = this.participantService.getParticipant(wilosUserId);

		if(participant != null){
			/*HashMap<Project, Boolean> projects = this.participantService.getProjectsForAParticipant(participant) ;
			for(Project project : projects.keySet()){
				if(projects.get(project)){
					projectsList.add(new SelectItem(project.getId(), project.getConcreteName())) ;
				}
			}*/

			for(Project project : this.projectService.getAllProjects()){
				projectsList.add(new SelectItem(project.getId(), project.getConcreteName())) ;
			}
		}

		projectsList.add(new SelectItem("default", "Choose a project ...")) ;
		return projectsList ;
	}

	public void changeTreeActionListener(ValueChangeEvent evt) {
		this.projectId = (String) evt.getNewValue() ;
		this.loadCheckBox = true ;
		this.loadTree = false ;
		this.buildModel(true) ;

		// TODO changeTreeActionListener not verify
		if (this.projectId.length() > 0) this.selectNodeToShow(this.projectId, WilosObjectNode.PROJECTNODE) ;
		logger.debug("### TreeBean ### changeTreeActionListener projectId="+this.projectId) ;
	}

	public void filterTreeActionListener(ValueChangeEvent evt) {
		this.buildModel(false) ;
	}

	public void selectNodeActionListener(ActionEvent evt) {
		logger.debug("### TreeBean ### selectNodeActionListener") ;
		FacesContext context = FacesContext.getCurrentInstance() ;
		Map map = context.getExternalContext().getRequestParameterMap() ;

		String nodeId = (String) map.get("nodeId") ;
		logger.debug("### TreeBean ### selectNodeActionListener - nodeId =" + nodeId) ;
		String pageId = (String) map.get("pageId") ;

		logger.debug("### TreeBean ### selectNodeActionListener - pageId =" + pageId) ;
		//
		this.selectNodeToShow(nodeId, pageId) ;
	}

	/**
	 *
	 * @param _objectId
	 * @param _pageId
	 *            node selection function
	 */
	private void selectNodeToShow(String _objectId, String _pageId) {
		logger.debug("### TreeBean ### selectNodeToShow id=" + _objectId + " page=" + _pageId) ;
		FacesContext context = FacesContext.getCurrentInstance() ;
		MenuBean mb = (MenuBean) context.getApplication().getVariableResolver().resolveVariable(context, "menu") ;
		if(_objectId != null && _pageId != null){
			if(_pageId.equals(WilosObjectNode.ACTIVITYNODE)){
				ConcreteActivityViewerBean av = (ConcreteActivityViewerBean) context.getApplication().getVariableResolver().resolveVariable(context,
						ACTIVITY_VIEWER_BEAN) ;
				av.setConcreteActivityId(_objectId) ;
				// model building
				av.buildConcreteActivity() ;
				mb.changePage(_pageId) ;
			}
			else if(_pageId.equals(WilosObjectNode.CONCRETETASKNODE)){
				ConcreteTaskViewerBean ctv = (ConcreteTaskViewerBean) context.getApplication().getVariableResolver().resolveVariable(context,
						CONCRETE_TASK_VIEWER_BEAN) ;
				ctv.setConcreteTaskDescriptorId(_objectId) ;
				// model building
				ctv.buildConcreteTaskDescriptor() ;
				mb.changePage(_pageId) ;
			}
			else if(_pageId.equals(WilosObjectNode.ITERATIONNODE)){
				ConcreteIterationViewerBean iv = (ConcreteIterationViewerBean) context.getApplication().getVariableResolver().resolveVariable(context,
						ITERATION_VIEWER_BEAN) ;
				iv.setConcreteIterationId(_objectId) ;
				// model building
				iv.buildConcreteIteration() ;
				mb.changePage(_pageId) ;
			}
			else if(_pageId.equals(WilosObjectNode.PHASENODE)){
				ConcretePhaseViewerBean pb = (ConcretePhaseViewerBean) context.getApplication().getVariableResolver().resolveVariable(context,
						PHASE_VIEWER_BEAN) ;
				pb.setConcretePhaseId(_objectId) ;
				// model building
				pb.buildConcretePhaseModel() ;
				mb.changePage(_pageId) ;
			}
			else if(_pageId.equals(WilosObjectNode.PROJECTNODE)){
				ProjectViewerBean p = (ProjectViewerBean) context.getApplication().getVariableResolver().resolveVariable(context, PROJECT_VIEWER_BEAN) ;
				p.setProjectId(_objectId) ;
				// model building
				p.buildProjectModel() ;
				mb.changePage(_pageId) ;
			}
			else{
				// didnt found the node's class
				new ClassNotFoundException("coulnd't found the node class") ;
			}
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
		return affectedTaskFilter ;
	}

	public void setAffectedTaskFilter(Boolean _affectedTaskFilter) {
		this.affectedTaskFilter = _affectedTaskFilter ;
	}

	public ProjectService getProjectService() {
		return projectService ;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService ;
	}

	public Boolean getLoadTree() {
		return loadTree ;
	}

	public void setLoadTree(Boolean loadTree) {
		this.loadTree = loadTree ;
	}

	public Boolean getLoadCheckBox() {
		return loadCheckBox ;
	}

	public void setLoadCheckBox(Boolean loadCheckBox) {
		this.loadCheckBox = loadCheckBox ;
	}

	public LoginService getLoginService() {
		return loginService ;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService ;
	}

	public ParticipantService getParticipantService() {
		return participantService ;
	}

	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService ;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return webSessionService ;
	}

	/**
	 * Setter of webSessionService.
	 *
	 * @param webSessionService
	 *            The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService ;
	}

	/**
	 * @return the processService
	 */
	public ProcessService getProcessService() {
		return processService ;
	}

	/**
	 * Setter of processService.
	 *
	 * @param processService The processService to set.
	 */
	public void setProcessService(ProcessService processService) {
		this.processService = processService ;
	}
}