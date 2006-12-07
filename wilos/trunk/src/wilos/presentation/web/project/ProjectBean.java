package wilos.presentation.web.project;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.project.ProjectService;
import wilos.model.misc.project.Project;


/**
 * @author martial
 *
 * This class represents ... TODO
 *
 */
public class ProjectBean {

	private ProjectService projectService;
	
	private Project project;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	
	public ProjectBean() {
		this.logger.debug("--- Project --- == creating ..." + this) ;
		this.project = new Project() ;
	}
	
	/**
	 * Method for saving project data from form
	 * 
	 * @return
	 */
	public String saveProjectAction() {
		String url = "welcome" ;
		this.logger.debug("--------------------saveProjectAction------------\n") ;
		if(this.projectService.projectExist(this.project.getName())){
			this.logger.debug("if\n") ;
			FacesMessage message = new FacesMessage() ;
			message.setDetail("Ce Projet existe deja") ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			FacesContext facesContext = FacesContext.getCurrentInstance() ;
			facesContext.addMessage(null, message) ;
			url = "project_create" ;
		}
		else{
			this.logger.debug("else\n") ;
			this.projectService.saveProject(this.project) ;
			url = "welcome" ;
		}
		return url ;
	}

	/**
	 * Getter of project.
	 *
	 * @return the project.
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
	 * Getter of projectService.
	 *
	 * @return the projectService.
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

}

