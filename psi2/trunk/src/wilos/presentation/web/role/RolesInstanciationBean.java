package wilos.presentation.web.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedSet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.presentation.web.project.ProjectAdvancementBean;
import wilos.presentation.web.tree.TreeBean;
import wilos.business.services.misc.role.ConcreteRoleInstanciationService;

/**
 * @author Sakamakak
 * 
 */
public class RolesInstanciationBean {

	public static final String EXPAND_TABLE_ARROW = "images/expandableTable/expand.gif";

	public static final String CONTRACT_TABLE_ARROW = "images/expandableTable/contract.gif";

	public static final String TABLE_LEAF = "images/expandableTable/leaf.gif";

	public static final String INDENTATION_STRING = "- - - ";

	private HashMap<String, String> indentationContent;

	private boolean needIndentation = false;

	private List<HashMap<String, Object>> displayContent;

	private String processViewedId = null;

	private String projectViewedId = null;

	protected HashMap<String, Boolean> isExpanded = new HashMap<String, Boolean>();

	private ProcessService processService;

	private ActivityService activityService;

	private ConcreteRoleInstanciationService concreteRoleInstanciationService;

	private WebSessionService webSessionService;

	private ProjectService projectService;

	private boolean isVisible = true;

	protected final Log logger = LogFactory.getLog(this.getClass());

	public RolesInstanciationBean() {
		this.displayContent = new ArrayList<HashMap<String, Object>>();
		this.indentationContent = new HashMap<String, String>();
		this.concreteRoleInstanciationService = new ConcreteRoleInstanciationService();
	}

	/**
	 * @return the displayContent
	 */
	@SuppressWarnings( { "unchecked", "static-access" })
	public List<HashMap<String, Object>> getDisplayContent() {

		String projectId = (String) this.webSessionService.getAttribute(this.webSessionService.PROJECT_ID);
		Project project = this.projectService.getProject(projectId);

		if (this.processViewedId == null || this.projectViewedId != projectId) {
			this.projectViewedId = projectId;
			if (project.getProcess() != null) {
				Process process = project.getProcess();
				process = this.processService.getProcessFromGuid(process.getGuid());

				processViewedId = process.getId();
				this.displayContent.clear();
				this.indentationContent.clear();
				this.isExpanded.clear();
				this.needIndentation = false;
				List<HashMap<String, Object>> lines = this.retrieveHierarchicalItems(process);
				this.displayContent.addAll(lines);
			}
		}
		return this.displayContent;
	}

	/**
	 * methode called when the instanciation is required from the role instanciation page
	 *
	 */
	public void instanciateConcreteRole() {
		List<HashMap<String, Object>> tmp = this.displayContent;
		List<HashMap<String, Object>> resultat = new ArrayList<HashMap<String, Object>>();

		String projectId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID);

		for (Iterator iter = this.displayContent.iterator(); iter.hasNext();) {
			HashMap<String, Object> hm = (HashMap<String, Object>) iter.next();
			if (hm.get("nodeType").equals("leaf")) {
				HashMap<String, Object> tmpHm = new HashMap<String, Object>();
				tmpHm.put("id", hm.get("id"));
				tmpHm.put("nbOccurences", hm.get("nbOccurences"));
				tmpHm.put("parentId", hm.get("parentId"));
				resultat.add(tmpHm);
			}
		}
		this.concreteRoleInstanciationService.saveInstanciateConcreteRole(resultat, projectId);

		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean tb = (TreeBean) context.getApplication().getVariableResolver().resolveVariable(context, "TreeBean");
		tb.rebuildProjectTree();
		tb.refreshProjectTree();

		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		FacesMessage message = new FacesMessage();
		message.setSummary(bundle.getString("component.project.rolesinstanciation.validationMessage"));
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(null, message);

		//remise a 0 des nombres d'occurences
		for (HashMap<String, Object> map : this.displayContent) {
			map.put("nbOccurences", new Integer(0));
		}
	}

	/**
	 * @param _process
	 * @return
	 */
	private List<HashMap<String, Object>> retrieveHierarchicalItems(Activity _act) {

		List<HashMap<String, Object>> lines = new ArrayList<HashMap<String, Object>>();
		String indentationString = "";
		Activity act = this.activityService.getActivity(_act.getId());
		SortedSet<BreakdownElement> set = this.activityService.getBreakdownElements(act);
		act.setBreakdownElements(set);
		for (BreakdownElement bde : act.getBreakdownElements()) {

			HashMap<String, Object> hm = new HashMap<String, Object>();
			if (!(bde instanceof TaskDescriptor)) {
				if (bde instanceof WorkBreakdownElement) {
					hm.put("nodeType", "node");
					hm.put("expansionImage", CONTRACT_TABLE_ARROW);
					hm.put("isVisible", false);
				} else {
					if (bde instanceof RoleDescriptor) {
						hm.put("nodeType", "leaf");
						hm.put("expansionImage", TABLE_LEAF);
						hm.put("isVisible", true);
					}
				}
				hm.put("id", bde.getId());
				hm.put("name", bde.getPresentationName());
				hm.put("isEditable", act.getHasMultipleOccurrences());
				hm.put("nbOccurences", new Integer(0));
				hm.put("parentId", act.getId());
				lines.add(hm);

				// if this is not the root node -> needIndentation == true
				if (needIndentation) {
					if (this.indentationContent.get(act.getId()) != null) {
						indentationString = this.indentationContent.get(act.getId());
					}
					this.indentationContent.put((String) hm.get("id"), indentationString.concat(RolesInstanciationBean.INDENTATION_STRING));
				}
			}
		}
		return lines;
	}

	/**
	 * Utility method to add all child nodes to the parent dataTable list.
	 */
	@SuppressWarnings("unchecked")
	private void expandNodeAction() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String elementId = (String) map.get("roleId");

		ArrayList<HashMap<String, Object>> tmp = new ArrayList<HashMap<String, Object>>();
		tmp.addAll(this.displayContent);
		int index;

		this.needIndentation = true;

		for (Iterator iter = tmp.iterator(); iter.hasNext();) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm = (HashMap<String, Object>) iter.next();

			if (hm.get("id").equals(elementId)) {
				if (hm.get("nodeType").equals("node")) {
					hm.put("expansionImage", EXPAND_TABLE_ARROW);
					Activity act = this.activityService.getActivityDao().getActivity((String) hm.get("id"));
					index = this.displayContent.indexOf(hm);
					this.displayContent.addAll(index + 1, this.retrieveHierarchicalItems(act));
					return;
				}
			}
		}
	}

	/**
	 * Utility method to remove all child nodes from the parent dataTable list.
	 */
	private void contractNodeAction() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String elementId = (String) map.get("roleId");

		ArrayList<HashMap<String, Object>> parentList = new ArrayList<HashMap<String, Object>>();
		parentList.addAll(this.displayContent);

		/* Removes element which we want to contract from the parent list */
		for (HashMap<String, Object> currentElement : this.displayContent) {

			if (currentElement.get("id").equals(elementId) && currentElement.get("nodeType").equals("node")) {
				currentElement.put("expansionImage", ProjectAdvancementBean.CONTRACT_TABLE_ARROW);
				parentList.remove(currentElement);
			}
		}
		this.deleteChildren(elementId, parentList);
	}

	/**
	 * TODO Method description
	 * 
	 * @param elementId
	 * @param tmp
	 */
	@SuppressWarnings("unchecked")
	private void deleteChildren(String parentId, ArrayList<HashMap<String, Object>> parentList) {
		for (Iterator iter = parentList.iterator(); iter.hasNext();) {
			HashMap<String, Object> child = (HashMap<String, Object>) iter.next();
			if (child.get("parentId").equals(parentId)) {
				this.displayContent.remove(child);
				deleteChildren((String) child.get("id"), parentList);
			}
			if (child.get("id").equals(parentId)) {
				child.put("expansionImage", ProjectAdvancementBean.CONTRACT_TABLE_ARROW);
				this.isExpanded.put((String) child.get("id"), false);
			}
		}
	}

	/**
	 * Toggles the expanded state of this ConcreteBreakDownElement.
	 * 
	 * @param event
	 */
	public void toggleSubGroupAction(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String elementId = (String) map.get("roleId");

		// toggle expanded state
		Boolean b = isExpanded.get(elementId);
		if (b == null) {
			isExpanded.put(elementId, true);
			b = isExpanded.get(elementId);
		} else {
			if (b) {
				b = false;
			} else {
				b = true;
			}
			isExpanded.put(elementId, b);
		}

		if (b) {
			expandNodeAction();
		} else {
			contractNodeAction();
		}

	}

	/**
	 * @param displayContent the displayContent to set
	 */
	public void setDisplayContent(List<HashMap<String, Object>> displayContent) {
		this.displayContent = displayContent;
	}

	/**
	 * @param _expTableContent
	 *            the expTableContent to set
	 */
	public void setExpTableContent(ArrayList<HashMap<String, Object>> _expTableContent) {
		this.displayContent = _expTableContent;
	}

	/**
	 * @return the isExpanded
	 */
	public HashMap<String, Boolean> getIsExpanded() {
		return this.isExpanded;
	}

	/**
	 * @param _isExpanded
	 *            the isExpanded to set
	 */
	public void setIsExpanded(HashMap<String, Boolean> _isExpanded) {
		this.isExpanded = _isExpanded;
	}

	/**
	 * @return the activityService
	 */
	public ActivityService getActivityService() {
		return this.activityService;
	}

	/**
	 * @param _activityService
	 *            the activityService to set
	 */
	public void setActivityService(ActivityService _activityService) {
		this.activityService = _activityService;
	}

	/**
	 * @return the processService
	 */
	public ProcessService getProcessService() {
		return this.processService;
	}

	/**
	 * @param _processService
	 *            the processService to set
	 */
	public void setProcessService(ProcessService _processService) {
		this.processService = _processService;
	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return this.isVisible;
	}

	/**
	 * @param _isVisible
	 *            the isVisible to set
	 */
	public void setVisible(boolean _isVisible) {
		this.isVisible = _isVisible;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return webSessionService;
	}

	/**
	 * @param webSessionService the webSessionService to set
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
	 * @param projectService the projectService to set
	 */
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * @return the indentationContent
	 */
	public HashMap<String, String> getIndentationContent() {
		return indentationContent;
	}

	/**
	 * @param indentationContent the indentationContent to set
	 */
	public void setIndentationContent(HashMap<String, String> indentationContent) {
		this.indentationContent = indentationContent;
	}

	/**
	 * @return the concreteRoleInstanciationService
	 */
	public ConcreteRoleInstanciationService getConcreteRoleInstanciationService() {
		return concreteRoleInstanciationService;
	}

	/**
	 * @param concreteRoleInstanciationService the concreteRoleInstanciationService to set
	 */
	public void setConcreteRoleInstanciationService(ConcreteRoleInstanciationService concreteRoleInstanciationService) {
		this.concreteRoleInstanciationService = concreteRoleInstanciationService;
	}

}
