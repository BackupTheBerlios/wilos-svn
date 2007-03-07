/**
 * 
 */
package wilos.presentation.web.expandabletable;

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

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.presentation.web.project.ProjectAdvancementBean;
import wilos.presentation.web.role.RolesInstanciationBean;
import wilos.presentation.web.tree.TreeBean;

/**
 * @author Sebastien
 * 
 */
public class ExpTableBean {

	public static final String EXPAND_TABLE_ARROW = "images/expandableTable/expand.gif";

	public static final String CONTRACT_TABLE_ARROW = "images/expandableTable/contract.gif";

	public static final String TABLE_LEAF = "images/expandableTable/leaf.gif";

	public static final String INDENTATION_STRING = "- - - ";

	private List<HashMap<String, Object>> expTableContent;

	protected HashMap<String, Boolean> isExpanded = new HashMap<String, Boolean>();

	private HashMap<String, String> indentationContent = new HashMap<String, String>();;

	private ProcessService processService;

	private ProjectService projectService;

	private ActivityService activityService;

	private boolean needIndentation = false;

	private WebSessionService webSessionService;

	private boolean isVisible = true;

	private boolean isInstanciedProject = false;

	private String viewedProcessId = "";

	private String selectedProcessGuid = "default";

	private String instanciationBtName;

	public ExpTableBean() {
		this.expTableContent = new ArrayList<HashMap<String, Object>>();
	}

	public void refreshExpTable() {
		this.isInstanciedProject = true;
	}

	public void saveProjectInstanciation() {

		String projectId = (String) this.webSessionService
				.getAttribute(WebSessionService.PROJECT_ID);
		if (projectId != null) {
			Project project = projectService.getProject(projectId);
			if (project != null) {
				Process process = processService.getProcessDao()
						.getProcessFromGuid(selectedProcessGuid);
				if (process != null) {
					processService.projectInstanciation(project, process,
							expTableContent, this.isInstanciedProject);
				}
			}
		}

		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean tb = (TreeBean) context.getApplication().getVariableResolver()
				.resolveVariable(context, "TreeBean");
		tb.rebuildProjectTree();

		// clear occurences number of each object of expTableContent
		for (HashMap<String, Object> map : this.expTableContent) {
			map.put("nbOccurences", new Integer(0));
		}

		// lock the combobox
		ProcessBean processBean = (ProcessBean) context.getApplication()
				.getVariableResolver().resolveVariable(context,
						"Woops2ProcessBean");
		processBean.setReadOnly(true);

		// refresh de la table des roles.
		RolesInstanciationBean rib = (RolesInstanciationBean) context
				.getApplication().getVariableResolver().resolveVariable(
						context, "RolesInstanciationBean");
		rib.refreshProcessTable();

		// refresh de la table d'avancement.
		ProjectAdvancementBean pab = (ProjectAdvancementBean) context
				.getApplication().getVariableResolver().resolveVariable(
						context, "ProjectAdvancementBean");
		pab.refreshProjectTable();
		
		/* Displays info message */
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale());
		FacesMessage message = new FacesMessage();
		message.setSummary(bundle
						.getString("component.instanciation.instanciatedMessage"));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		context.addMessage(null, message);
	}

	/**
	 * @return the expTableContent
	 */
	public List<HashMap<String, Object>> getExpTableContent() {

		if (!this.selectedProcessGuid.equals("default")) {
			Process process = this.processService
					.getProcessFromGuid(this.selectedProcessGuid);
			if (!this.viewedProcessId.equals(process.getId())) {
				this.viewedProcessId = process.getId();
				this.expTableContent.clear();
				this.indentationContent.clear();
				this.needIndentation = false;
				List<HashMap<String, Object>> lines = this
						.getExpTableLineContent(process);
				this.expTableContent.addAll(lines);
			}
		}
		return this.expTableContent;
	}

	/**
	 * @param _process
	 * @return
	 */
	private List<HashMap<String, Object>> getExpTableLineContent(Activity _act) {

		List<HashMap<String, Object>> lines = new ArrayList<HashMap<String, Object>>();
		String indentationString = "";
		Activity act = this.activityService.getActivity(_act.getId());
		SortedSet<BreakdownElement> set = this.activityService
				.getBreakdownElements(act);
		act.setBreakdownElements(set);
		for (BreakdownElement bde : act.getBreakdownElements()) {
			if (bde instanceof WorkBreakdownElement) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				if (bde instanceof TaskDescriptor) {
					hm.put("nodeType", "leaf");
					hm.put("expansionImage", TABLE_LEAF);
				} else {
					hm.put("nodeType", "node");
					hm.put("expansionImage", CONTRACT_TABLE_ARROW);
				}
				hm.put("id", bde.getId());
				hm.put("name", bde.getPresentationName());
				hm.put("isEditable", act.getHasMultipleOccurrences()
						|| act.getIsRepeatable());
				if (this.isInstanciedProject) {
					hm.put("nbOccurences", new Integer(0));
				} else {
					hm.put("nbOccurences", new Integer(1));
				}
				hm.put("parentId", act.getId());

				lines.add(hm);

				if (needIndentation) {
					if (this.indentationContent.get(act.getId()) != null) {
						indentationString = this.indentationContent.get(act
								.getId());
					}
					this.indentationContent.put((String) hm.get("id"),
							indentationString.concat(INDENTATION_STRING));
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
		String elementId = (String) map.get("elementId");

		this.needIndentation = true;

		ArrayList<Object> tmp = new ArrayList<Object>();
		tmp.addAll(this.expTableContent);
		int index;

		for (Iterator iter = tmp.iterator(); iter.hasNext();) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm = (HashMap<String, Object>) iter.next();

			if (hm.get("id").equals(elementId)) {
				if (hm.get("nodeType").equals("node")) {
					hm.put("expansionImage", EXPAND_TABLE_ARROW);
					Activity act = this.activityService.getActivityDao()
							.getActivity((String) hm.get("id"));
					index = this.expTableContent.indexOf(hm);
					this.expTableContent.addAll(index + 1, this
							.getExpTableLineContent(act));
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
		String elementId = (String) map.get("elementId");

		ArrayList<HashMap<String, Object>> parentList = new ArrayList<HashMap<String, Object>>();
		parentList.addAll(this.expTableContent);

		/* Removes element which we want to contract from the parent list */
		for (HashMap<String, Object> currentElement : this.expTableContent) {

			if (currentElement.get("id").equals(elementId)
					&& currentElement.get("nodeType").equals("node")) {
				currentElement.put("expansionImage", CONTRACT_TABLE_ARROW);
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
	private void deleteChildren(String parentId,
			ArrayList<HashMap<String, Object>> parentList) {
		for (Iterator iter = parentList.iterator(); iter.hasNext();) {
			HashMap<String, Object> child = (HashMap<String, Object>) iter
					.next();
			if (child.get("parentId").equals(parentId)) {
				this.expTableContent.remove(child);
				deleteChildren((String) child.get("id"), parentList);
			}
			if (child.get("id").equals(parentId)) {
				child.put("expansionImage", CONTRACT_TABLE_ARROW);
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
		String elementId = (String) map.get("elementId");

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
	 * @param _expTableContent
	 *            the expTableContent to set
	 */
	public void setExpTableContent(
			ArrayList<HashMap<String, Object>> _expTableContent) {
		this.expTableContent = _expTableContent;
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
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return this.webSessionService;
	}

	/**
	 * @param _webSessionService
	 *            the webSessionService to set
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService;
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
	 * @return the projectService
	 */
	public ProjectService getProjectService() {
		return this.projectService;
	}

	/**
	 * @param _projectService
	 *            the projectService to set
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService;
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
	 * @return the selectedProcessGuid
	 */
	public String getSelectedProcessGuid() {
		return this.selectedProcessGuid;
	}

	/**
	 * @param _selectedProcessGuid
	 *            the selectedProcessGuid to set
	 */
	public void setSelectedProcessGuid(String _selectedProcessGuid) {
		this.selectedProcessGuid = _selectedProcessGuid;
	}

	/**
	 * @return the isInstanciedProject
	 */
	public boolean getIsInstanciedProject() {
		return this.isInstanciedProject;
	}

	/**
	 * @param _isInstanciedProject
	 *            the isInstanciedProject to set
	 */
	public void setIsInstanciedProject(boolean _isInstanciedProject) {
		this.isInstanciedProject = _isInstanciedProject;
	}

	/**
	 * @return the indentationContent
	 */
	public HashMap<String, String> getIndentationContent() {
		return this.indentationContent;
	}

	/**
	 * @param _indentationContent
	 *            the indentationContent to set
	 */
	public void setIndentationContent(
			HashMap<String, String> _indentationContent) {
		this.indentationContent = _indentationContent;
	}

	/**
	 * @return the instanciationBtName
	 */
	public String getInstanciationBtName() {
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale());

		if (!this.isInstanciedProject) {
			this.instanciationBtName =  bundle.getString("component.instanciation.button.ins");
		} else {
			this.instanciationBtName =  bundle.getString("component.instanciation.button.up");
		}
		return this.instanciationBtName;
	}

	/**
	 * @param _instanciationBtName
	 *            the instanciationBtName to set
	 */
	public void setInstanciationBtName(String _instanciationBtName) {
		this.instanciationBtName = _instanciationBtName;
	}

}
