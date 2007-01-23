
package wilos.presentation.web.project ;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.spem2.process.ProcessService;
import wilos.business.services.project.ProjectService;
import wilos.model.misc.project.Project;


/**
 * Managed-Bean link to project_create.jspx
 * 
 * @author martial
 */
public class ProjectBean {

	private ProjectService projectService ;

	private ProcessService processService ;
	
	private Project project ;
	
	private String selectedProcessGuid ;
	
	private ArrayList<SelectItem> processNamesList ;
	
	private List<Project> projectList ;
	
	private List<Project> projectListWithoutProcess = new ArrayList<Project>();

	private List<Project> projectListWithProcess = new ArrayList<Project>();
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ProjectBean() {
		this.logger.debug("--- Project --- == creating ..." + this) ;
		this.project = new Project() ;
		this.selectedProcessGuid="";
		/*
		 * this.projectList = (HashSet<Project>)this.projectService.getAllProjects(); for(Project
		 * projectTmp : this.projectList){ this.logger.debug("### Project"+projectTmp.getName()+"
		 * ###") ; }
		 */
	}

	/**
	 * Method for saving project data from form
	 * 
	 * @return
	 */
	public String saveProjectAction() {
		String url = "" ;
		boolean error=false;
		FacesMessage message = new FacesMessage();
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
	// test if the fields are correctly completed
		if (this.project.getName().trim().length()==0)
		{
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary("Le champ nom est obligatoire");
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errName) ;
		}
		
		if (this.project.getLaunchingDate()==null)
		{
			FacesMessage errDate = new FacesMessage() ;
			errDate.setSummary("La date de lancement est obligatoire");
			errDate.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errDate) ;
		}
		if(!error)
		{
			if(this.projectService.projectExist(this.project.getName())){
			
				message.setSummary("Ce Projet existe déjà");
				message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			}
			else{
				this.projectService.saveProject(this.project) ;
				message.setSummary("Le Projet a bien été créé");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
		
		facesContext.addMessage(null, message) ;
		}
		this.project = new Project() ;
	
		return url ;
	}

	
	/**
	 * Method for saving project/process association from radio buttons
	 *
	 * @return nothing
	 */
	public String saveProjectProcessAffectation(){
		Project projTmp = projectService.getProject("2c90a1b2104ad70601104ad906f90001");
		this.logger.debug("Project: "+projTmp) ;
		wilos.model.spem2.process.Process procTmp = processService.getProcessDao().getProcessFromGuid(selectedProcessGuid);
		this.logger.debug("Process selectionne: "+procTmp) ;
		projectService.saveProcessProjectAffectation(procTmp, projTmp);
		return "";
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
	 * @param _project
	 *            The project to set.
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
	 * @param _projectService
	 *            The projectService to set.
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService ;
	}
	

	/**
	 * Return all the Projects
	 * 
	 * @return A set of Project
	 */
	public Set<Project> getAllProjects(){
		return this.projectService.getAllProjects();
	}

	/**
	 * Getter of projectList.
	 * 
	 * @return the projectList.
	 */
	public List<Project> getProjectList() {
		this.projectList = new ArrayList<Project>();
		projectList.addAll(this.projectService.getAllProjects());		
		return this.projectList ;
	}

	/**
	 * Setter of projectList.
	 * 
	 * @param _projectList
	 *            The projectList to set.
	 */
	public void setProjectList(List<Project> _projectList) {
		this.projectList = _projectList ;
	}

	public List<Project> getProjectListWithoutProcess() {
		this.projectListWithoutProcess = new ArrayList<Project>();
		this.projectListWithoutProcess.addAll(this.projectService.getAllProjectsWithNoProcess());
		return projectListWithoutProcess;
	}

	public void setProjectListWithoutProcess(List<Project> projectListWithoutProcess) {
		this.projectListWithoutProcess = projectListWithoutProcess;
	}

	public List<Project> getProjectListWithProcess() {
		this.projectListWithProcess = new ArrayList<Project>();
		this.projectListWithProcess.addAll(this.projectService.getAllProjectsWithProcess());
		return projectListWithProcess;
	}

	public void setProjectListWithProcess(List<Project> projectListWithProcess) {
		this.projectListWithProcess = projectListWithProcess;
	}

	/**
	 * Getter of processNamesList.
	 *
	 * @return the processNamesList.
	 */
	public ArrayList<SelectItem> getProcessNamesList() {
		ArrayList<SelectItem> tmpListNames = new ArrayList<SelectItem>();
		ArrayList<wilos.model.spem2.process.Process> tmpListProcess= (ArrayList<wilos.model.spem2.process.Process>) this.processService.getProcessesList();
		
		for(int i = 0; i < tmpListProcess.size(); i++ ){
			tmpListNames.add(new SelectItem(tmpListProcess.get(i).getGuid(),tmpListProcess.get(i).getName()));
			this.logger.debug("NEW PROCESS DETECTED !!!!!") ;
		}
		this.logger.debug("tmpListNames:"+tmpListNames) ;
		processNamesList = tmpListNames;
		this.logger.debug("processNamesList:"+processNamesList) ;

		return this.processNamesList ;
	}

	/**
	 * Setter of processNamesList.
	 *
	 * @param _processNamesList The processNamesList to set.
	 */
	public void setProcessNamesList(ArrayList<SelectItem> _processNamesList) {
		this.processNamesList = _processNamesList ;
	}
	
	/**
	 * Getter of processService.
	 *
	 * @return the processService.
	 */
	public ProcessService getProcessService() {
		return this.processService ;
	}

	/**
	 * Setter of processService.
	 *
	 * @param _processService The processService to set.
	 */
	public void setProcessService(ProcessService _processService) {
		this.processService = _processService ;
	}

	/**
	 * Getter of selectedProcessGuid.
	 *
	 * @return the selectedProcessGuid.
	 */
	public String getSelectedProcessGuid() {
		return this.selectedProcessGuid ;
	}

	/**
	 * Setter of selectedProcessGuid.
	 *
	 * @param _selectedProcessGuid The selectedProcessGuid to set.
	 */
	public void setSelectedProcessGuid(String _selectedProcessGuid) {
		this.logger.debug("Process selectionne "+this.selectedProcessGuid) ;
		this.selectedProcessGuid = _selectedProcessGuid ;
	}

}
