package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.presentation.web.tree.TreeBean;

public class ProjectViewerBean {

	private Project project;

	private ProjectService projectService;

	private WebSessionService webSessionService;

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	public boolean getChangeButtonIsDisabled() {
		String wilosUserId = (String) this.webSessionService
			.getAttribute(WebSessionService.WILOS_USER_ID);

		Project project = this.projectService
				.getProject((String) this.webSessionService
						.getAttribute(WebSessionService.PROJECT_ID));
		
		if ((project.getProjectManager() != null)
				&& (project.getProjectManager().getWilosuser_id()
						.equals(wilosUserId)))
			return false;
		else
			return true;
	}
	
	public void changeConcreteName() {
		this.projectService.saveProject(project);
		
		//	 Refresh the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}
	
	public boolean getIsInputNameReadOnly() {
		return (this.getChangeButtonIsDisabled()); 
	}

	//To have the mask available only for the ProjectManager.
	public boolean getTreeMaskIsAvailable() {
		// participant into session
		String wilosUserId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);
		if ((this.project.getProjectManager() != null)&&(this.project.getProjectManager().getWilosuser_id().equals(wilosUserId)))
			return true;
		else
			return false;
	}

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		list.addAll(this.project.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void saveProject() {
		this.concreteBreakdownElementService
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(this.project);

		// Reload the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

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