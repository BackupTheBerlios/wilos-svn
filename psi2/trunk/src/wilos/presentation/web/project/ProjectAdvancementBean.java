
package wilos.presentation.web.project ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Map ;

import javax.faces.context.FacesContext ;
import javax.faces.event.ActionEvent ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import wilos.business.services.misc.concreteactivity.ConcreteActivityService;
import wilos.business.services.misc.project.ProjectService ;
import wilos.business.services.presentation.web.WebSessionService ;
import wilos.model.misc.concreteactivity.ConcreteActivity ;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement ;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor ;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project ;

/**
 * @author SaKaMaKaK
 *
 */
public class ProjectAdvancementBean {

	public static final String EXPAND_TABLE_ARROW = "images/expandableTable/expand.gif" ;

	public static final String CONTRACT_TABLE_ARROW = "images/expandableTable/contract.gif" ;

	public static final String TABLE_LEAF = "images/expandableTable/leaf.gif" ;

	private ProjectService projectService ;

	private WebSessionService webSessionService ;
	
	private ConcreteActivityService concreteActivityService;
	
	private Project project ;

	private String projectViewedId ;

	private ArrayList<HashMap<String,Object>> displayContent ;

	private HashMap<String, Double> advancementTimes ;

	private HashMap<String, String> indentationContent ;
	
	private boolean needIndentation = false;

	protected HashMap<String, Boolean> isExpanded = new HashMap<String, Boolean>() ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	private boolean selected_projectAdvancement_view ;

	/**
	 * Constructor.
	 * 
	 */
	public ProjectAdvancementBean() {
		this.project = new Project() ;
		this.displayContent = new ArrayList<HashMap<String,Object>>() ;
		this.indentationContent = new HashMap<String, String>() ;
		this.advancementTimes = new HashMap<String, Double>() ;
	}

	/**
	 * Toggles the expanded state of this ConcreteBreakDownElement.
	 *
	 * @param event
	 */
	public void toggleSubGroupAction(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance() ;
		Map map = context.getExternalContext().getRequestParameterMap() ;
		String elementId = (String) map.get("elementId") ;

		// toggle expanded state
		Boolean b = isExpanded.get(elementId) ;
		if(b == null){
			isExpanded.put(elementId, false) ;
			b = isExpanded.get(elementId) ;
		}
		b = !b ;
		isExpanded.put(elementId, b) ;

		// add sub elements to list
		if(b){
			expandNodeAction() ;
		}
		// remove items from list
		else{
			contractNodeAction() ;
		}
	}

	/**
	 * Utility method to add all child nodes to the parent dataTable list.
	 */
	private void expandNodeAction() {
		FacesContext context = FacesContext.getCurrentInstance() ;
		Map map = context.getExternalContext().getRequestParameterMap() ;
		String elementId = (String) map.get("elementId") ;

		ArrayList<Object> tmp = new ArrayList<Object>() ;
		tmp.addAll(this.displayContent) ;
		int index;
		
		for(Iterator iter = tmp.iterator(); iter.hasNext();)
		{
			HashMap<String,Object> hm = new HashMap<String,Object>();
			hm = (HashMap<String,Object>)iter.next();
			
			if(hm.get("id").equals(elementId)){
				hm.put("expansionImage", EXPAND_TABLE_ARROW);
				if(hm.get("nodeType").equals("node")){
					ConcreteActivity ca = this.concreteActivityService.getConcreteActivity((String)hm.get("id"));
					index = this.displayContent.indexOf(hm);
					this.displayContent.addAll(index+1,this.retrieveHierarchicalItems(ca));
				}
			}
		}
	}

	/**
	 * Return the advancement in percents of a Concrete breakdown element
	 * @param cbe
	 * @return
	 */
	public static double activityAdvancementCalculation(ConcreteBreakdownElement cbe) {
		double result = 0.0 ;
		double remainingTimes = 0.0 ;
		double accomplishedTimes = 0.0 ;
		HashMap<String, Double> couple = ProjectAdvancementBean.taskAdvancementCalculation(cbe) ;
		remainingTimes = couple.get("remainingTime") ;
		accomplishedTimes = couple.get("accomplishedTime") ;
		if(remainingTimes + accomplishedTimes > 0){
			result = remainingTimes / (remainingTimes + accomplishedTimes) ;
		}
		if(remainingTimes == 0 && accomplishedTimes > 0){
			result = 1;
		}

		return result*100 ;
	}

	/**
	 * Sub recursive method used for the Concrete breakdown element advancement calculation
	 * @param ctd
	 * @return
	 */
	private static HashMap<String, Double> taskAdvancementCalculation(ConcreteBreakdownElement cbe)
	{
		HashMap<String, Double> coupletmp = new HashMap<String, Double>() ;
		HashMap<String, Double> couple = new HashMap<String, Double>() ;
		couple.put("remainingTime", 0.0) ;
		couple.put("accomplishedTime", 0.0);
		
		//if the current element is an activity, parse the sub concrete breakdown elements
		if(cbe instanceof ConcreteActivity){
			ConcreteActivity ca = (ConcreteActivity) cbe ;
			for(Iterator iter = ca.getConcreteBreakdownElements().iterator(); iter.hasNext();){
				ConcreteBreakdownElement element = (ConcreteBreakdownElement) iter.next() ;
				coupletmp = ProjectAdvancementBean.taskAdvancementCalculation(element) ;
				couple.put("remainingTime", couple.get("remainingTime") + coupletmp.get("remainingTime")) ;
				couple.put("accomplishedTime", couple.get("accomplishedTime") + coupletmp.get("accomplishedTime")) ;
			}
		}
		//else if it's a concrete task get the values
		else{
			if(cbe instanceof ConcreteTaskDescriptor){
				ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) cbe ;
				couple.put("remainingTime", (double) ctd.getRemainingTime()) ;
				couple.put("accomplishedTime", (double) ctd.getAccomplishedTime()) ;
			}
		}
		return couple ;
	}

	/**
	 * Utility method to remove all child nodes from the parent dataTable list.
	 */
	private void contractNodeAction() {
		FacesContext context = FacesContext.getCurrentInstance() ;
		Map map = context.getExternalContext().getRequestParameterMap() ;
		String elementId = (String) map.get("elementId") ;
		
		ArrayList<HashMap<String,Object>> parentList = new ArrayList<HashMap<String,Object>>();		
		parentList.addAll(this.displayContent);
		
		/*Removes element which we want to contract from the parent list*/
		for(HashMap<String, Object> currentElement : this.displayContent){
			if(currentElement.get("id").equals(elementId)){
				currentElement.put("expansionImage", ProjectAdvancementBean.CONTRACT_TABLE_ARROW);
				parentList.remove(currentElement);
			}			
		}
		this.deleteChildren(elementId,parentList) ;
	}

	
	/**
	 * TODO Method description
	 *
	 * @param elementId
	 * @param tmp
	 */
	private void deleteChildren(String parentId, ArrayList<HashMap<String,Object>> parentList) {
		for(Iterator iter = parentList.iterator(); iter.hasNext();)
		{
			HashMap<String,Object> child = (HashMap<String,Object>) iter.next();
			if(child.get("parentId").equals(parentId))
			{
				this.displayContent.remove(child);
				deleteChildren((String)child.get("id"),parentList);
			}
			if(child.get("id").equals(parentId))
			{
				child.put("expansionImage", ProjectAdvancementBean.CONTRACT_TABLE_ARROW);
				this.isExpanded.put((String)child.get("id"),false);
			}
		}
	}


	/**
	 * TODO Method description
	 *
	 */
	private List<HashMap<String,Object>> retrieveHierarchicalItems(ConcreteActivity _concreteActivity) {
		double currentAdvancedTime = 0.0;
		String indentationString = "";
		List<HashMap<String,Object>> subConcretesContent = new ArrayList<HashMap<String,Object>>();
				
		for(ConcreteBreakdownElement concreteBreakdownElement : _concreteActivity.getConcreteBreakdownElements()){
			HashMap<String,Object> hm = new HashMap<String,Object>();
			if(concreteBreakdownElement instanceof ConcreteWorkBreakdownElement){
				if(concreteBreakdownElement instanceof ConcreteTaskDescriptor){
					hm.put("accomplishedTime",((ConcreteTaskDescriptor)concreteBreakdownElement).getAccomplishedTime());
					hm.put("remainingTime",((ConcreteTaskDescriptor)concreteBreakdownElement).getRemainingTime());
					hm.put("nodeType","leaf");
					hm.put("expansionImage",TABLE_LEAF);
				}
				else{
					hm.put("accomplishedTime",null);
					hm.put("remainingTime",null);
					hm.put("nodeType","node");
					hm.put("expansionImage",CONTRACT_TABLE_ARROW);
				}
				currentAdvancedTime = (double) Math.round(ProjectAdvancementBean.activityAdvancementCalculation(concreteBreakdownElement)) ;
				hm.put("advancementTime",currentAdvancedTime);
				hm.put("id", concreteBreakdownElement.getId());
				hm.put("concreteName",concreteBreakdownElement.getConcreteName());	
				hm.put("parentId",_concreteActivity.getId());
				subConcretesContent.add(hm);
				if(needIndentation){
					if (this.indentationContent.get(_concreteActivity.getId()) != null){
						indentationString = this.indentationContent.get(_concreteActivity.getId());
					}
					this.indentationContent.put((String)hm.get("id"),indentationString.concat("- - - "));
				}
			}
		}
			//currentAdvancedTime = (double) Math.round(ProjectAdvancementBean.activityAdvancementCalculation(concreteBreakdownElement)) ;
			//this.advancementTimes.put(concreteBreakdownElement.getId(), currentAdvancedTime) ;
		return subConcretesContent;
	}
	
	/**
	 * Getter of displayContent.
	 *
	 * @return the displayContent.
	 */
	public ArrayList<HashMap<String,Object>> getDisplayContent() {
		String projectId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID) ;
		this.project = this.projectService.getProject(projectId) ;
		if(this.projectViewedId == null || projectViewedId != projectId){
			projectViewedId = projectId ;
			this.displayContent.clear();
			this.needIndentation = false;
			this.displayContent.addAll(this.retrieveHierarchicalItems(this.project));
			this.needIndentation = true;
		}
		return this.displayContent ;
	}

	/**
	 * Setter of displayContent.
	 *
	 * @param _displayContent The displayContent to set.
	 */
	public void setDisplayContent(ArrayList<HashMap<String,Object>> _displayContent) {
		this.displayContent = _displayContent ;
	}

	/**
	 * @return the isExpanded
	 */
	public HashMap<String, Boolean> getIsExpanded() {
		return this.isExpanded ;
	}

	/**
	 * Setter of isExpanded.
	 *
	 * @param _isExpanded The isExpanded to set.
	 */
	public void setIsExpanded(HashMap<String, Boolean> _isExpanded) {
		this.isExpanded = _isExpanded ;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return this.project ;
	}

	/**
	 * Setter of project.
	 *
	 * @param _project The project to set.
	 */
	public void setProject(Project _project) {
		this.project = _project ;
	}

	/**
	 * @return the projectService
	 */
	public ProjectService getProjectService() {
		return this.projectService ;
	}

	/**
	 * Setter of projectService.
	 *
	 * @param _projectService The projectService to set.
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService ;
	}

	/**
	 * @return the projectViewedId
	 */
	public String getProjectViewedId() {
		return this.projectViewedId ;
	}

	/**
	 * Setter of projectViewedId.
	 *
	 * @param _projectViewedId The projectViewedId to set.
	 */
	public void setProjectViewedId(String _projectViewedId) {
		this.projectViewedId = _projectViewedId ;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return this.webSessionService ;
	}

	/**
	 * Setter of webSessionService.
	 *
	 * @param _webSessionService The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService ;
	}

	/**
	 * @return the indentationContent
	 */
	public HashMap<String, String> getIndentationContent() {
		return this.indentationContent ;
	}

	/**
	 * Setter of indentationContent.
	 *
	 * @param _indentationContent The indentationContent to set.
	 */
	public void setIndentationContent(HashMap<String, String> _indentationContent) {
		this.indentationContent = _indentationContent ;
	}

	/**
	 * @return the advancementTimes
	 */
	public HashMap<String, Double> getAdvancementTimes() {
		return this.advancementTimes ;
	}

	/**
	 * Setter of advancementTimes.
	 *
	 * @param _advancementTimes The advancementTimes to set.
	 */
	public void setAdvancementTimes(HashMap<String, Double> _advancementTimes) {
		this.advancementTimes = _advancementTimes ;
	}

	/**
	 * Getter of concreteActivityService.
	 *
	 * @return the concreteActivityService.
	 */
	public ConcreteActivityService getConcreteActivityService() {
		return this.concreteActivityService ;
	}

	/**
	 * Setter of concreteActivityService.
	 *
	 * @param _concreteActivityService The concreteActivityService to set.
	 */
	public void setConcreteActivityService(ConcreteActivityService _concreteActivityService) {
		this.concreteActivityService = _concreteActivityService ;
	}

	/**
	 * @return the selected_projectAdvancement_view
	 */
	public boolean getSelected_projectAdvancement_view() {
		String user_id = (String)this.webSessionService.getAttribute(this.webSessionService.WILOS_USER_ID);
		this.project = this.projectService.getProject((String)this.webSessionService.getAttribute(this.webSessionService.PROJECT_ID));
		this.selected_projectAdvancement_view  = false;
		if (this.project.getProjectManager() != null)
		{
			if (this.project.getProjectManager().getWilosuser_id().equals(user_id))
			{
				this.selected_projectAdvancement_view  = true;
			}
		}
		return this.selected_projectAdvancement_view ;
	}

	/**
	 * Setter of selected_projectAdvancement_view.
	 *
	 * @param _selected_projectAdvancement_view The selected_projectAdvancement_view to set.
	 */
	public void setSelected_projectAdvancement_view(boolean _selected_projectAdvancement_view) {
		this.selected_projectAdvancement_view = _selected_projectAdvancement_view ;
	}
}
