/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
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

package wilos.presentation.web.viewer;

import javax.faces.context.FacesContext;

import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.project.Project;
import wilos.presentation.web.project.ProjectAdvancementBean;
import wilos.presentation.web.tree.TreeBean;

public class ViewerBean {

	private WebSessionService webSessionService;

	private ProjectService projectService;

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	protected void refreshProjectTree() {
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}
	
	protected void rebuildProjectTree() {
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.rebuildProjectTree();
	}

	protected void refreshProjectTable() {
		FacesContext context = FacesContext.getCurrentInstance();
		ProjectAdvancementBean pab = (ProjectAdvancementBean) context
				.getApplication().getVariableResolver().resolveVariable(
						context, "ProjectAdvancementBean");
		pab.refreshProjectTable();
	}

	public boolean getIsInputNameReadOnly() {
		return (this.getChangeButtonIsDisabled());
	}

	public boolean getChangeButtonIsDisabled() {
		String wilosUserId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);

		Project project = this.projectService
				.getProject((String) this.webSessionService
						.getAttribute(WebSessionService.PROJECT_ID));

		if ((project != null)&&((project.getProjectManager() != null)
				&& (project.getProjectManager().getWilosuser_id()
						.equals(wilosUserId))))
			return false;
		else
			return true;
	}

	// To have the mask available only for the ProjectManager.
	// And Only for the TasksTreeMode (not for RolesTreeMode).
	public boolean getTreeMaskIsAvailable() {
		// Get the treemode.
		String treeMode = (String) this.webSessionService
				.getAttribute(WebSessionService.TREE_MODE);

		if (treeMode.equals(TreeBean.TASKS_MODE)) {
			// participant into session
			String wilosUserId = (String) this.webSessionService
					.getAttribute(WebSessionService.WILOS_USER_ID);

			Project project = this.projectService.getProject((String) this
					.getWebSessionService().getAttribute(
							WebSessionService.PROJECT_ID));

			if ((project.getProjectManager() != null)
					&& (project.getProjectManager().getWilosuser_id()
							.equals(wilosUserId)))
				return true;
		}

		return false;
	}

	/* Getters & Setters */

	/**
	 * @return the concreteBreakdownElementService
	 */
	public ConcreteBreakdownElementService getConcreteBreakdownElementService() {
		return concreteBreakdownElementService;
	}

	/**
	 * @param concreteBreakdownElementService
	 *            the concreteBreakdownElementService to set
	 */
	public void setConcreteBreakdownElementService(
			ConcreteBreakdownElementService concreteBreakdownElementService) {
		this.concreteBreakdownElementService = concreteBreakdownElementService;
	}

	/**
	 * @return the projectService
	 */
	public ProjectService getProjectService() {
		return projectService;
	}

	/**
	 * @param projectService
	 *            the projectService to set
	 */
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return webSessionService;
	}

	/**
	 * @param webSessionService
	 *            the webSessionService to set
	 */
	public void setWebSessionService(WebSessionService webSessionService) {
		this.webSessionService = webSessionService;
	}

}
