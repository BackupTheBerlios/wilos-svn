/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.web.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.presentation.web.expandabletable.ExpTableBean;
import wilos.presentation.web.expandabletable.ProcessBean;
import wilos.presentation.web.template.MenuBean;
import wilos.presentation.web.viewer.ConcreteActivityViewerBean;
import wilos.presentation.web.viewer.ConcreteIterationViewerBean;
import wilos.presentation.web.viewer.ConcretePhaseViewerBean;
import wilos.presentation.web.viewer.ConcreteRoleViewerBean;
import wilos.presentation.web.viewer.ConcreteTaskViewerBean;
import wilos.presentation.web.viewer.ProjectViewerBean;

/**
 * @author deder
 * @author eperico
 * @author garwind <p/> A basic backing bean for a ice:tree component. The only
 *         instance variable needed is a DefaultTreeModel Object which is bound
 *         to the icefaces tree component in the jspx code.
 *         </p>
 *         <p/> The tree created by this backing bean is used to control the
 *         selected panel in a ice:panelStack.
 *         </p>
 */
public class TreeBean {

	/* Services */

	private WebSessionService webSessionService;

	private ProjectService projectService;

	private LoginService loginService;

	private ParticipantService participantService;

	private ProcessService processService;

	/* Simple fields */

	private Project project;

	private static final String DEFAULT_PROJECT_ID = "default";

	private String projectId = DEFAULT_PROJECT_ID;

	private boolean loadTree;

	private boolean hideRadio;

	private String selectedMode = TASKS_MODE;

	public static final String TASKS_MODE = "tasksMode";

	public static final String ROLES_MODE = "rolesMode";

	// tree default model, used as a value for the tree component
	private DefaultTreeModel model = null;

	protected final Log logger = LogFactory.getLog(this.getClass());

	// HashMap which contains the object and his id
	private HashMap<String, Object> treeMap = new HashMap<String, Object>();

	public TreeBean() {
		this.model = new DefaultTreeModel(this.getDefaultTree());
	}

	/* Manage the tree. */

	/**
	 * Sets PROJECT_ID attribute to DEFAULT_PROJECT_ID and cleans tree and node
	 * to show Must be called at participant logout
	 */

	public DefaultMutableTreeNode getDefaultTree() {
		DefaultMutableTreeNode defaultTree = new DefaultMutableTreeNode();
		WilosObjectNode iceUserObject = new WilosObjectNode(defaultTree);

		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance()
				.getApplication().getDefaultLocale());
		iceUserObject.setText(bundle.getString("navigation.tree.defaulttreenodetext"));
		defaultTree.setUserObject(iceUserObject);
		return defaultTree;
	}

	public void refreshProjectTree() {
		this.buildTreeModel();
	}

	public void rebuildProjectTree() {
		String prId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID);
		this.project = this.projectService.getProject(prId);
		this.buildTreeModel();
	}

	public void cleanTreeDisplay() {
		this.webSessionService.setAttribute(WebSessionService.PROJECT_ID, DEFAULT_PROJECT_ID);
		this.projectId = DEFAULT_PROJECT_ID;
		this.buildTreeModel();
	}

	private void buildTreeModel() {
		if (this.projectId != null && !this.projectId.equals(DEFAULT_PROJECT_ID)) {
			ProjectNode projectNode;
			if (this.selectedMode.equals(TASKS_MODE)) {
				projectNode = new ProjectNode(this.project, true, treeMap);
				this.webSessionService.setAttribute(WebSessionService.TREE_MODE, TASKS_MODE);
			} else {
				projectNode = new ProjectNode(this.project, false, treeMap);
				this.webSessionService.setAttribute(WebSessionService.TREE_MODE, ROLES_MODE);
			}
			this.model = new DefaultTreeModel(projectNode);
		} else {
			// Build the default tree.
			this.model = new DefaultTreeModel(this.getDefaultTree());
		}
	}

	public void changeTreeActionListener(ValueChangeEvent evt) {
		String nodeTypeToShow = DEFAULT_PROJECT_ID;

		this.projectId = (String) evt.getNewValue();
		// Put into the session the current project used.
		this.webSessionService.setAttribute(WebSessionService.PROJECT_ID, this.projectId);

		if (!this.projectId.equals(DEFAULT_PROJECT_ID)) {

			// Retrieve the entire project.
			this.project = this.projectService.getProject(this.projectId);

			nodeTypeToShow = WilosObjectNode.PROJECTNODE;

			// masquage de la exptable d'instanciation
			String projectId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID);
			Project project = this.projectService.getProject(projectId);

			FacesContext context = FacesContext.getCurrentInstance();

			ProcessBean processBean = (ProcessBean) context.getApplication().getVariableResolver().resolveVariable(
					context, "Woops2ProcessBean");

			ExpTableBean expTableBean = (ExpTableBean) context.getApplication().getVariableResolver().resolveVariable(
					context, "ExpTableBean");

			if (project.getProcess() == null) {
				processBean.setSelectedProcessGuid("default");
				expTableBean.setSelectedProcessGuid("default");
				processBean.setIsVisibleExpTable(false);
				expTableBean.setIsInstanciedProject(false);
				expTableBean.getExpTableContent().clear();
			} else {
				processBean.setSelectedProcessGuid(project.getProcess().getGuid());
				expTableBean.setSelectedProcessGuid(project.getProcess().getGuid());
				processBean.setIsVisibleExpTable(true);
				expTableBean.setIsInstanciedProject(true);
			}
		}

		this.buildTreeModel();

		if (this.projectId.length() > 0)
			this.selectNodeToShow(this.projectId, nodeTypeToShow);

	}

	public void selectNodeActionListener(ActionEvent evt) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();

		String nodeId = (String) map.get("nodeId");
		String pageId = (String) map.get("pageId");

		this.selectNodeToShow(nodeId, pageId);
		/*
		 * logger.debug("### TreeBean ### HIBERNATE STATS :: \n" +
		 * this.getProcessService().getActivityDao().getSessionFactory()
		 * .getStatistics());
		 */
	}

	/* Manage the select one radio */

	public void changeModeActionListener(ValueChangeEvent evt) {
		this.selectedMode = (String) evt.getNewValue();
		this.buildTreeModel();
	}

	public List<SelectItem> getModesList() {
		ArrayList<SelectItem> modesList = new ArrayList<SelectItem>();

		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance()
				.getApplication().getDefaultLocale());

		modesList.add(new SelectItem(TASKS_MODE, bundle.getString("navigation.tree.checkboxlabel.tasks")));
		modesList.add(new SelectItem(ROLES_MODE, bundle.getString("navigation.tree.checkboxlabel.roles")));

		return modesList;
	}

	/* Manage the combobox. */

	public List<SelectItem> getProjects() {
		List<SelectItem> projectsList = new ArrayList<SelectItem>();

		String wilosUserId = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant participant = this.participantService.getParticipant(wilosUserId);

		if (participant != null) {
			HashMap<Project, Boolean> projects = this.participantService.getProjectsForAParticipant(participant);
			for (Project project : projects.keySet()) {
				if (projects.get(project)) {
					this.addSelectItemToList(projectsList, new SelectItem(project.getId(), project.getConcreteName()));
				}
			}
		}

		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance()
				.getApplication().getDefaultLocale());
		projectsList
				.add(0, new SelectItem(DEFAULT_PROJECT_ID, bundle.getString("navigation.tree.defaulttreenodetext")));
		return projectsList;
	}

	/* Private methods. */

	/**
	 * Inserts the SelectItem _si representing a project into the list used by
	 * the combo
	 * 
	 * @param _projectsList
	 *            the projectsList
	 * @param _si
	 *            the SelectItem
	 */
	private void addSelectItemToList(List<SelectItem> _projectsList, SelectItem _si) {
		if (_projectsList.size() == 0)
			_projectsList.add(_si);
		else {
			int i;
			// inserting the project in an alphabetically ordered list
			for (i = 0; i < _projectsList.size() && _si.getLabel().compareTo(_projectsList.get(i).getLabel()) > 0; i++) {
			}
			_projectsList.add(i, _si);
		}
	}

	/**
	 * 
	 * @param _objectId
	 * @param _pageId
	 *            node selection function
	 */
	private void selectNodeToShow(String _objectId, String _pageId) {
		FacesContext context = FacesContext.getCurrentInstance();
		MenuBean mb = (MenuBean) context.getApplication().getVariableResolver().resolveVariable(context, "menu");
		if (_objectId != null && _pageId != null) {
			if (_pageId.equals(WilosObjectNode.ACTIVITYNODE)) {
				ConcreteActivityViewerBean av = (ConcreteActivityViewerBean) context.getApplication()
						.getVariableResolver().resolveVariable(context, WilosObjectNode.ACTIVITYNODE + "Bean");

				// recover the object in the HashMap for the viewer
				ConcreteActivity ca = (ConcreteActivity) treeMap.get(_objectId);
				av.setConcreteActivity(ca);

				mb.changePage(_pageId);
			} else if (_pageId.equals(WilosObjectNode.CONCRETETASKNODE)) {
				ConcreteTaskViewerBean ctv = (ConcreteTaskViewerBean) context.getApplication().getVariableResolver()
						.resolveVariable(context, WilosObjectNode.CONCRETETASKNODE + "Bean");

				// recover the object in the HashMap for the viewer
				ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) treeMap.get(_objectId);
				ctv.setConcreteTaskDescriptor(ctd);

				mb.changePage(_pageId);
			} else if (_pageId.equals(WilosObjectNode.CONCRETEROLENODE)) {
				ConcreteRoleViewerBean crv = (ConcreteRoleViewerBean) context.getApplication().getVariableResolver()
						.resolveVariable(context, WilosObjectNode.CONCRETEROLENODE + "Bean");

				// recover the object in the HashMap for the viewer
				ConcreteRoleDescriptor crd = (ConcreteRoleDescriptor) treeMap.get(_objectId);
				crv.setConcreteRoleDescriptor(crd);

				mb.changePage(_pageId);
			} else if (_pageId.equals(WilosObjectNode.ITERATIONNODE)) {
				ConcreteIterationViewerBean iv = (ConcreteIterationViewerBean) context.getApplication()
						.getVariableResolver().resolveVariable(context, WilosObjectNode.ITERATIONNODE + "Bean");

				// recover the object in the HashMap for the viewer
				ConcreteIteration ci = (ConcreteIteration) treeMap.get(_objectId);
				iv.setConcreteIteration(ci);

				mb.changePage(_pageId);
			} else if (_pageId.equals(WilosObjectNode.PHASENODE)) {
				ConcretePhaseViewerBean pb = (ConcretePhaseViewerBean) context.getApplication().getVariableResolver()
						.resolveVariable(context, WilosObjectNode.PHASENODE + "Bean");

				// recover the object in the HashMap for the viewer
				ConcretePhase cp = (ConcretePhase) treeMap.get(_objectId);
				pb.setConcretePhase(cp);

				mb.changePage(_pageId);
			} else if (_pageId.equals(WilosObjectNode.PROJECTNODE)) {
				ProjectViewerBean p = (ProjectViewerBean) context.getApplication().getVariableResolver()
						.resolveVariable(context, WilosObjectNode.PROJECTNODE + "Bean");

				// recover the object in the HashMap for the viewer
				Project proj = (Project) treeMap.get(_objectId);
				p.setProject(proj);

				mb.changePage(_pageId);
			} else {
				// displays blank page
				mb.changePage("wilos");
			}
		}
	}

	/* Getters & Setters */

	public DefaultTreeModel getModel() {
		return this.model;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String _processId) {
		this.projectId = _processId;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public Boolean getLoadTree() {
		if (this.projectId != null && !this.projectId.equals(DEFAULT_PROJECT_ID))
			this.loadTree = false;
		else
			this.loadTree = true;
		return this.loadTree;
	}

	public void setLoadTree(Boolean loadTree) {
		this.loadTree = loadTree;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public ParticipantService getParticipantService() {
		return participantService;
	}

	public void setParticipantService(ParticipantService participantService) {
		this.participantService = participantService;
	}

	public WebSessionService getWebSessionService() {
		return webSessionService;
	}

	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public HashMap<String, Object> getTreeMap() {
		return treeMap;
	}

	public void setTreeMap(HashMap<String, Object> _treeMap) {
		this.treeMap = _treeMap;
	}

	/**
	 * @return the selectedMode
	 */
	public String getSelectedMode() {
		return this.selectedMode;
	}

	/**
	 * @param selectedMode
	 *            the selectedMode to set
	 */
	public void setSelectedMode(String _selectedMode) {
		this.selectedMode = _selectedMode;
	}

	public boolean getHideRadio() {
		if (this.projectId != null && !this.projectId.equals(DEFAULT_PROJECT_ID))
			this.hideRadio = true;
		else
			this.hideRadio = false;
		return this.hideRadio;
	}

	public void setHideRadio(boolean _hideRadio) {
		this.hideRadio = _hideRadio;
	}
}