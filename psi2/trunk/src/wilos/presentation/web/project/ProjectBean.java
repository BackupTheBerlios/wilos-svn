
package wilos.presentation.web.project ;

import java.text.SimpleDateFormat ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.ResourceBundle ;
import java.util.Set ;

import javax.faces.application.FacesMessage ;
import javax.faces.context.FacesContext ;
import javax.faces.model.SelectItem ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;

import wilos.business.services.misc.project.ProjectService ;
import wilos.business.services.misc.wilosuser.ParticipantService ;
import wilos.business.services.presentation.web.WebSessionService ;
import wilos.business.services.spem2.process.ProcessService ;
import wilos.model.misc.project.Project ;
import wilos.model.misc.wilosuser.Participant ;
import wilos.model.spem2.process.Process ;
import wilos.presentation.web.tree.TreeBean;

/**
 * Managed-Bean link to project_create.jspx
 * 
 * @author martial
 */
public class ProjectBean {

	private ProjectService projectService ;

	private ProcessService processService ;

	private WebSessionService webSessionService ;

	private ParticipantService participantService ;

	private Project project ;
	
	private TreeBean treeBean;

	private String selectedProcessGuid ;

	private ArrayList<SelectItem> processNamesList ;

	private List<Project> projectList ;

	private List<Project> projectListWithoutProcess = new ArrayList<Project>() ;

	private List<Project> projectListWithProcess = new ArrayList<Project>() ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	@SuppressWarnings("unused")
	private SimpleDateFormat formatter ;

	private String selectProcessAffectation ;

	private String processName ;

	private String projectListView ;

	/**
	 * Constructor.
	 * 
	 */
	public ProjectBean() {
		this.project = new Project() ;
		this.selectedProcessGuid = "" ;
		this.processNamesList = new ArrayList<SelectItem>() ;
		this.formatter = new SimpleDateFormat("dd/MM/yyyy") ;
	}

	/**
	 * Method for saving project data from form
	 * 
	 * @return
	 */
	public String saveProjectAction() {
		ResourceBundle bundle = ResourceBundle.getBundle("wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale()) ;
		String url = "" ;
		boolean error = false ;
		FacesMessage message = new FacesMessage() ;
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		// test if the fields are correctly completed
		if(this.project.getConcreteName().trim().length() == 0){
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary(bundle.getString("component.projectcreate.err.namerequired")) ;
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errName) ;
		}

		if(this.project.getLaunchingDate() == null){
			FacesMessage errDate = new FacesMessage() ;
			errDate.setSummary(bundle.getString("component.projectcreate.err.launchingdaterequired")) ;
			errDate.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errDate) ;
		}
		if(!error){
			if(this.projectService.projectExist(this.project.getConcreteName().trim())){

				message.setSummary(bundle.getString("component.projectcreate.err.projectalreadyexists")) ;
				message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			}
			else{
				this.projectService.saveProject(this.project) ;
				message.setSummary(bundle.getString("component.projectcreate.success")) ;
				message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
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
	public String saveProjectProcessAffectation() {
		String tmpProjId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID) ;
		if(tmpProjId != null){
			Project projTmp = projectService.getProject(tmpProjId) ;
			if(projTmp != null){
				Process procTmp = processService.getProcessDao().getProcessFromGuid(selectedProcessGuid) ;
				if(procTmp != null){
					projectService.saveProcessProjectAffectation(procTmp, projTmp) ;
				}
			}
		}
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean tb = (TreeBean) context.getApplication().getVariableResolver().resolveVariable(context, "TreeBean");
		tb.buildModel(true);
		return "" ;
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
	public Set<Project> getAllProjects() {
		return this.projectService.getAllProjects() ;
	}

	/**
	 * Getter of projectList.
	 * 
	 * @return the projectList.
	 */
	public List<Project> getProjectList() {
		this.projectList = new ArrayList<Project>() ;
		projectList.addAll(this.projectService.getAllProjects()) ;
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
		this.projectListWithoutProcess = new ArrayList<Project>() ;
		this.projectListWithoutProcess.addAll(this.projectService.getAllProjectsWithNoProcess()) ;
		return projectListWithoutProcess ;
	}

	public void setProjectListWithoutProcess(List<Project> projectListWithoutProcess) {
		this.projectListWithoutProcess = projectListWithoutProcess ;
	}

	public List<Project> getProjectListWithProcess() {
		this.projectListWithProcess = new ArrayList<Project>() ;
		this.projectListWithProcess.addAll(this.projectService.getAllProjectsWithProcess()) ;
		return projectListWithProcess ;
	}

	public void setProjectListWithProcess(List<Project> projectListWithProcess) {
		this.projectListWithProcess = projectListWithProcess ;
	}

	/**
	 * Getter of processNamesList.
	 * 
	 * @return the processNamesList.
	 */
	public ArrayList<SelectItem> getProcessNamesList() {
		ArrayList<SelectItem> tmpListNames = new ArrayList<SelectItem>() ;
		ArrayList<wilos.model.spem2.process.Process> tmpListProcess = (ArrayList<wilos.model.spem2.process.Process>) this.processService.getProcessesList() ;

		for(int i = 0; i < tmpListProcess.size(); i++ ){
			tmpListNames.add(new SelectItem(tmpListProcess.get(i).getGuid(), tmpListProcess.get(i).getPresentationName())) ;
		}
		processNamesList = tmpListNames ;
		return this.processNamesList ;
	}

	/**
	 * Setter of processNamesList.
	 * 
	 * @param _processNamesList
	 *            The processNamesList to set.
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
	 * @param _processService
	 *            The processService to set.
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
		// Getting the current projet id from cession
		String tmpProjId = (String) webSessionService.getAttribute(WebSessionService.PROJECT_ID) ;
		if(tmpProjId != null){
			Project projTmp = projectService.getProject(tmpProjId) ;
			if(projTmp != null){
				Process procTmp = projTmp.getProcess() ;
				if(procTmp != null){
					this.selectedProcessGuid = projTmp.getProcess().getGuid() ;
				}
			}
		}
		return this.selectedProcessGuid ;
	}

	/**
	 * Setter of selectedProcessGuid.
	 * 
	 * @param _selectedProcessGuid
	 *            The selectedProcessGuid to set.
	 */
	public void setSelectedProcessGuid(String _selectedProcessGuid) {
		this.selectedProcessGuid = _selectedProcessGuid ;
	}

	/**
	 * @return the selectProcessAffectation
	 */
	public String getSelectProcessAffectation() {
		String tmpProjId = (String) this.webSessionService.getAttribute(WebSessionService.PROJECT_ID) ;
		Project currentProject = this.projectService.getProject(tmpProjId) ;

		String participantId = (String) this.webSessionService.getAttribute(WebSessionService.WILOS_USER_ID) ;
		Participant participant = this.participantService.getParticipant(participantId) ;
		if(currentProject.getProcess() == null){
			if(currentProject.getProjectManager() != null){
				if(currentProject.getProjectManager().getWilosuser_id().equals(participant.getWilosuser_id())){
					this.selectProcessAffectation = "process_affectation_view" ;
				}
				else{
					this.selectProcessAffectation = "no_process_affectation_view" ;
				}
			}
			else{
				this.selectProcessAffectation = "no_process_affectation_view" ;
			}
		}
		else{
			this.setProcessName(currentProject.getProcess().getPresentationName()) ;
			this.selectProcessAffectation = "selected_process_view" ;
		}
		return this.selectProcessAffectation ;
	}

	/**
	 * @param selectProcessAffectation
	 *            the selectProcessAffectation to set
	 */
	public void setSelectProcessAffectation(String selectProcessAffectation) {
		this.selectProcessAffectation = selectProcessAffectation ;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName ;
	}

	/**
	 * @param processName
	 *            the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName ;
	}

	/**
	 * Getter of projectListView
	 * 
	 * @return the projectListView.
	 */
	public String getprojectListView() {
		if(this.getProjectList().size() == 0){
			this.projectListView = "projectsListPanelGroup_null" ;
		}
		else{
			this.projectListView = "projectsListPanelGroup_not_null" ;
		}
		return this.projectListView ;
	}

	/**
	 * Setter of projectListView.
	 * 
	 * @param _projectListView
	 *            The projectListView to set.
	 */
	public void setSelectAffectedProjectView(String _projectListView) {
		this.projectListView = _projectListView ;
	}

	/**
	 * @return the participantService
	 */
	public ParticipantService getParticipantService() {
		return this.participantService ;
	}

	/**
	 * Setter of participantService.
	 * 
	 * @param _participantService
	 *            The participantService to set.
	 */
	public void setParticipantService(ParticipantService _participantService) {
		this.participantService = _participantService ;
	}

	/**
	 * @return the projectListView
	 */
	public String getProjectListView() {
		return this.projectListView ;
	}

	/**
	 * Setter of projectListView.
	 * 
	 * @param _projectListView
	 *            The projectListView to set.
	 */
	public void setProjectListView(String _projectListView) {
		this.projectListView = _projectListView ;
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
	 * @param _webSessionService
	 *            The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService ;
	}

	/**
	 * @return the treeBean
	 */
	public TreeBean getTreeBean() {
		return this.treeBean ;
	}

	/**
	 * Setter of treeBean.
	 *
	 * @param _treeBean The treeBean to set.
	 */
	public void setTreeBean(TreeBean _treeBean) {
		this.treeBean = _treeBean ;
	}

}
