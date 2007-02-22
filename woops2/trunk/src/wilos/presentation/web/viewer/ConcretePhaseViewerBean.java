package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import wilos.business.services.misc.concretebreakdownelement.ConcreteBreakdownElementService;
import wilos.business.services.misc.concretephase.ConcretePhaseService;
import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;
import wilos.presentation.web.tree.TreeBean;

public class ConcretePhaseViewerBean {

	private ConcretePhase concretePhase;

	private WebSessionService webSessionService;

	private ProjectService projectService;

	private ConcretePhaseService concretePhaseService;

	private ConcreteBreakdownElementService concreteBreakdownElementService;

	/* Manage the table for the visible elements in the tree. */

	// To have the mask available only for the ProjectManager.
	public boolean getTreeMaskIsAvailable() {
		// participant into session
		String wilosUserId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);

		Project project = this.projectService
				.getProject((String) this.webSessionService
						.getAttribute(WebSessionService.PROJECT_ID));

		if ((project.getProjectManager() != null)
				&& (project.getProjectManager().getWilosuser_id()
						.equals(wilosUserId)))
			return true;
		else
			return false;
	}

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		list.addAll(this.concretePhase.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void savePhase() {
		this.concreteBreakdownElementService
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(this.concretePhase);

		// Reload the treebean.
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean treeBean = (TreeBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, "TreeBean");
		treeBean.refreshProjectTree();
	}

	/* Manage the concretename field editable. */

	public boolean getIsInputNameReadOnly() {
		return this.getChangeButtonIsDisabled();
	}

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
		this.concretePhaseService.saveConcretePhase(this.concretePhase);
	}

	/* Getters & Setters */

	public ConcretePhase getConcretePhase() {
		return concretePhase;
	}

	public void setConcretePhase(ConcretePhase concretePhase) {
		this.concretePhase = concretePhase;
	}

	/**
	 * @return the concretePhaseService
	 */
	public ConcretePhaseService getConcretePhaseService() {
		return concretePhaseService;
	}

	/**
	 * @param concretePhaseService
	 *            the concretePhaseService to set
	 */
	public void setConcretePhaseService(
			ConcretePhaseService concretePhaseService) {
		this.concretePhaseService = concretePhaseService;
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

}