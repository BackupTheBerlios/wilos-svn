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

import wilos.business.services.misc.project.ProjectService ;
import wilos.business.services.presentation.web.WebSessionService ;
import wilos.model.misc.concreteactivity.ConcreteActivity ;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement ;
import wilos.model.misc.concreteiteration.ConcreteIteration ;
import wilos.model.misc.concretephase.ConcretePhase ;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor ;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor ;
import wilos.model.misc.project.Project ;

/**
 * @author SaKaMaKaK
 *
 */
public class ProjectAdvancementBean {

	private ProjectService projectService ;

	private WebSessionService webSessionService ;

	private Project project ;

	private String projectViewedId ;

	private ArrayList<Object> projectContent = new ArrayList<Object>() ; ;

	private ArrayList<Object> displayContent ;

	protected HashMap<String, Boolean> isExpanded = new HashMap<String, Boolean>() ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ProjectAdvancementBean() {
		this.project = new Project() ;
		this.projectContent = new ArrayList<Object>() ;
		this.displayContent = new ArrayList<Object>() ;
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
		for(Iterator iter = tmp.iterator(); iter.hasNext();){
			Object element = (Object) iter.next() ;
			if(element instanceof ConcreteActivity){
				if(elementId.equals( ((ConcreteActivity) element).getConcreteName())){
					this.logger.debug(this.displayContent.size()) ;
					int index = this.displayContent.indexOf(element) ;
					ConcreteActivity ca = (ConcreteActivity) element ;
					for(Iterator iterator = ca.getConcreteBreakdownElements().iterator(); iterator.hasNext();){
						ConcreteBreakdownElement element2 = (ConcreteBreakdownElement) iterator.next() ;
						if(! (element2 instanceof ConcreteRoleDescriptor))
							this.displayContent.add(index + 1, element2) ;
					}
				}

			}
		}
	}

	/**
	 * Utility method to remove all child nodes from the parent dataTable list.
	 */
	private void contractNodeAction() {
		ArrayList<Object> currentLevelElementsList = new ArrayList<Object>() ;
		ArrayList<Object> firstSubLevelElementsList = new ArrayList<Object>() ;
		ArrayList<Object> subLevelElementsList = new ArrayList<Object>() ;
		int i = 0 ;
		FacesContext context = FacesContext.getCurrentInstance() ;
		Map map = context.getExternalContext().getRequestParameterMap() ;
		String elementId = (String) map.get("elementId") ;

		for(Iterator iter = this.displayContent.iterator(); iter.hasNext();){
			Object element = (Object) iter.next() ;
			if(element instanceof ConcreteActivity){
				if(elementId.equals( ((ConcreteActivity) element).getConcreteName())){
					ConcreteActivity ca = (ConcreteActivity) element ;
					firstSubLevelElementsList.addAll(ca.getConcreteBreakdownElements()) ;
					while(i < firstSubLevelElementsList.size()){
						if(! (firstSubLevelElementsList.get(i) instanceof ConcreteTaskDescriptor)
								&& ! (firstSubLevelElementsList.get(i) instanceof ConcreteRoleDescriptor)){
							currentLevelElementsList.addAll(this.parseSubConcreteBreakdownElement(subLevelElementsList,
									(ConcreteActivity) firstSubLevelElementsList.get(i))) ;
						}
						currentLevelElementsList.add(firstSubLevelElementsList.get(i)) ;
						i++ ;
					}

				}
			}
		}
		this.displayContent.removeAll(currentLevelElementsList) ;
	}

	public List<Object> parseSubConcreteBreakdownElement(List<Object> result, ConcreteActivity ca) {
		int i = 0 ;
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>() ;
		if(ca.getConcreteBreakdownElements() != null){
			result.add(ca) ;
			this.isExpanded.put(ca.getConcreteName(), false) ;
			list.addAll(ca.getConcreteBreakdownElements()) ;
			while(i < list.size() && list.get(i) != null){
				if(! (list.get(i) instanceof ConcreteTaskDescriptor) && ! (list.get(i) instanceof ConcreteRoleDescriptor))
					result.addAll(parseSubConcreteBreakdownElement(result, (ConcreteActivity) list.get(i))) ;
				else
					result.add(list.get(i)) ;
				i++ ;
			}
		}
		return result ;
	}

	/**
	 * Getter of projectContent.
	 *
	 * @return the projectContent.
	 */
	public ArrayList<Object> getProjectContent() {
		this.projectContent.clear() ;
		String projectId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID) ;

		this.project = this.projectService.getProject(projectId) ;
		if(this.project.getProcess() != null){
			retrieveHierarchicalItems() ;
		}
		return this.projectContent ;
	}

	/**
	 * TODO Method description
	 *
	 */
	private void retrieveHierarchicalItems() {
		for(ConcreteBreakdownElement concreteBreakdownElement : this.project.getConcreteBreakdownElements()){
			if(concreteBreakdownElement instanceof ConcretePhase){
				this.projectContent.add(concreteBreakdownElement) ;
			}
			else if(concreteBreakdownElement instanceof ConcreteIteration){
				this.projectContent.add(concreteBreakdownElement) ;
			}
			else if(concreteBreakdownElement instanceof ConcreteActivity){
				this.projectContent.add(concreteBreakdownElement) ;
			}
			else if(concreteBreakdownElement instanceof ConcreteTaskDescriptor){
				this.projectContent.add((ConcreteTaskDescriptor) concreteBreakdownElement) ;
			}
		}
	}

	/**
	 * Getter of displayContent.
	 *
	 * @return the displayContent.
	 */
	public ArrayList<Object> getDisplayContent() {
		String projectId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID) ;
		if(this.projectViewedId == null || projectViewedId != projectId){
			projectViewedId = projectId ;
			this.displayContent.clear() ;
			this.displayContent.addAll(this.getProjectContent()) ;
		}
		return this.displayContent ;
	}

	/**
	 * Setter of projectContent.
	 *
	 * @param _projectContent The projectContent to set.
	 */
	public void setProjectContent(ArrayList<Object> _projectContent) {
		this.projectContent = _projectContent ;
	}

	/**
	 * Setter of displayContent.
	 *
	 * @param _displayContent The displayContent to set.
	 */
	public void setDisplayContent(ArrayList<Object> _displayContent) {
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

}
