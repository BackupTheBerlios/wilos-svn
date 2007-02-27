/**
 * 
 */
package wilos.presentation.web.expandabletable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import wilos.business.services.spem2.activity.ActivityService;
import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.presentation.web.project.ProjectAdvancementBean;

/**
 * @author Sebastien
 * 
 */
public class ExpTableBean {

	public static final String EXPAND_TABLE_ARROW = "images/expandableTable/expand.gif";

	public static final String CONTRACT_TABLE_ARROW = "images/expandableTable/contract.gif";

	public static final String TABLE_LEAF = "images/expandableTable/leaf.gif";

	private List<HashMap<String, Object>> expTableContent;

	protected HashMap<String, Boolean> isExpanded = new HashMap<String, Boolean>();

	private ProcessService processService;

	private ActivityService activityService;

	private boolean isVisible = true;

	private String selectedProcessGuid = "default";
	
	public ExpTableBean() {
		this.expTableContent = new ArrayList<HashMap<String, Object>>();
	}

	/**
	 * @return the expTableContent
	 */
	public List<HashMap<String, Object>> getExpTableContent() {

		if (!this.selectedProcessGuid.equals("default")) {
			Process process = this.processService
					.getProcessFromGuid(this.selectedProcessGuid);
			this.expTableContent.clear() ;
			List<HashMap<String, Object>> lines = this.getExpTableLineContent(process);
			this.expTableContent.addAll(lines);
		}
		return this.expTableContent;
	}

	/**
	 * @param _process
	 * @return
	 */
	private List<HashMap<String, Object>> getExpTableLineContent(Activity _act) {

		List<HashMap<String, Object>> lines = new ArrayList<HashMap<String, Object>>();
		Activity act = this.activityService.getActivity(_act.getId()) ;
		Set<BreakdownElement> set = this.activityService.getBreakdownElements(act);
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
				hm.put("parentId", act.getId()) ;

				lines.add(hm);
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
				currentElement.put("expansionImage",
						ProjectAdvancementBean.CONTRACT_TABLE_ARROW);
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
				child.put("expansionImage",
						ProjectAdvancementBean.CONTRACT_TABLE_ARROW);
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
			isExpanded.put(elementId, false);
			b = isExpanded.get(elementId);
		}
		b = !b;
		isExpanded.put(elementId, b);

		// add sub elements to list
		if (b) {
			expandNodeAction();
		}
		// remove items from list
		else {
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

}
