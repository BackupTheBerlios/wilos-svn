
package wilos.presentation.web.template ;

import java.util.Map ;

import javax.faces.context.FacesContext ;
import javax.faces.event.ActionEvent ;

/**
 * @author BlackMilk
 * 
 * This class represents ... TODO
 * 
 */
public class ActionBean {

	private String manageParticipants = "manageParticipants";
	private String manageProcessManagers = "manageProcessManagers";
	private String manageProjectDirectors = "manageProjectDirectors";
	
	private String affectProject = "affectProject";
	private String affectProjectAsManager = "affectProjectAsManager";
	private String updateParticipant = "updateParticipant" ;
	
	private String importProcessFile = "importProcessFile";
	private String manageProcesses = "manageProcesses";
	
	private String projectCreate="projectCreate";
	private String projectList = "projectList";
	
	private String adminMain = "admin_main";
	private String participantMain = "participant_main";
	private String projectDirectorMain = "project_director_main";
	private String processManagerMain = "process_manager_main";
	

	/**
	 * Getter of importProcessFile.
	 *
	 * @return the importProcessFile.
	 */
	public String getImportProcessFile() {
		return this.importProcessFile ;
	}

	/**
	 * Setter of importProcessFile.
	 *
	 * @param _importProcessFile The importProcessFile to set.
	 */
	public void setImportProcessFile(String _importProcessFile) {
		this.importProcessFile = _importProcessFile ;
	}

	/**
	 * Getter of manageParticipant.
	 * 
	 * @return the manageParticipant.
	 */
	public String getManageParticipants() {
		return this.manageParticipants ;
	}

	/**
	 * Setter of manageParticipant.
	 * 
	 * @param _manageParticipant
	 *            The manageParticipant to set.
	 */
	public void setManageParticipants(String _manageParticipant) {
		this.manageParticipants = _manageParticipant ;
	}

	public void selectNodeActionListener(ActionEvent _evt) {
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String mainPage = (String) map.get("mainPage");
		String pageToShow = (String) map.get("pageToShow");
		MenuBean menuBean = (MenuBean)context.getExternalContext().getSessionMap().get("menu");
		menuBean.getSelectedPanel().setTemplateName(mainPage);
		menuBean.getSelectedPanel().setTemplateNameForARole(pageToShow);
	}

	/**
	 * Getter of manageProcessManagers.
	 *
	 * @return the manageProcessManagers.
	 */
	public String getManageProcessManagers() {
		return this.manageProcessManagers ;
	}

	/**
	 * Setter of manageProcessManagers.
	 *
	 * @param _manageProcessManagers The manageProcessManagers to set.
	 */
	public void setManageProcessManagers(String _manageProcessManagers) {
		this.manageProcessManagers = _manageProcessManagers ;
	}

	/**
	 * Getter of manageProjectDirectors.
	 *
	 * @return the manageProjectDirectors.
	 */
	public String getManageProjectDirectors() {
		return this.manageProjectDirectors ;
	}

	/**
	 * Setter of manageProjectDirectors.
	 *
	 * @param _manageProjectDirectors The manageProjectDirectors to set.
	 */
	public void setManageProjectDirectors(String _manageProjectDirectors) {
		this.manageProjectDirectors = _manageProjectDirectors ;
	}

	/**
	 * Getter of affectProject.
	 *
	 * @return the affectProject.
	 */
	public String getAffectProject() {
		return this.affectProject ;
	}

	/**
	 * Setter of affectProject.
	 *
	 * @param _affectProject The affectProject to set.
	 */
	public void setAffectProject(String _affectProject) {
		this.affectProject = _affectProject ;
	}

	/**
	 * Getter of affectProjectAsManager.
	 *
	 * @return the affectProjectAsManager.
	 */
	public String getAffectProjectAsManager() {
		return this.affectProjectAsManager ;
	}

	/**
	 * Setter of affectProjectAsManager.
	 *
	 * @param _affectProjectAsManager The affectProjectAsManager to set.
	 */
	public void setAffectProjectAsManager(String _affectProjectAsManager) {
		this.affectProjectAsManager = _affectProjectAsManager ;
	}

	/**
	 * Getter of projectCreate.
	 *
	 * @return the projectCreate.
	 */
	public String getProjectCreate() {
		return this.projectCreate ;
	}

	/**
	 * Setter of projectCreate.
	 *
	 * @param _projectCreate The projectCreate to set.
	 */
	public void setProjectCreate(String _projectCreate) {
		this.projectCreate = _projectCreate ;
	}

	/**
	 * Getter of projectList.
	 *
	 * @return the projectList.
	 */
	public String getProjectList() {
		return this.projectList ;
	}

	/**
	 * Setter of projectList.
	 *
	 * @param _projectList The projectList to set.
	 */
	public void setProjectList(String _projectList) {
		this.projectList = _projectList ;
	}

	/**
	 * Getter of adminMain.
	 *
	 * @return the adminMain.
	 */
	public String getAdminMain() {
		return this.adminMain ;
	}

	/**
	 * Setter of adminMain.
	 *
	 * @param _adminMain The adminMain to set.
	 */
	public void setAdminMain(String _adminMain) {
		this.adminMain = _adminMain ;
	}

	/**
	 * Getter of participantMain.
	 *
	 * @return the participantMain.
	 */
	public String getParticipantMain() {
		return this.participantMain ;
	}

	/**
	 * Setter of participantMain.
	 *
	 * @param _participantMain The participantMain to set.
	 */
	public void setParticipantMain(String _participantMain) {
		this.participantMain = _participantMain ;
	}

	/**
	 * Getter of processManagerMain.
	 *
	 * @return the processManagerMain.
	 */
	public String getProcessManagerMain() {
		return this.processManagerMain ;
	}

	/**
	 * Setter of processManagerMain.
	 *
	 * @param _processManagerMain The processManagerMain to set.
	 */
	public void setProcessManagerMain(String _processManagerMain) {
		this.processManagerMain = _processManagerMain ;
	}

	/**
	 * Getter of projectDirectorMain.
	 *
	 * @return the projectDirectorMain.
	 */
	public String getProjectDirectorMain() {
		return this.projectDirectorMain ;
	}

	/**
	 * Setter of projectDirectorMain.
	 *
	 * @param _projectDirectorMain The projectDirectorMain to set.
	 */
	public void setProjectDirectorMain(String _projectDirectorMain) {
		this.projectDirectorMain = _projectDirectorMain ;
	}

	/**
	 * Getter of manageProcesses.
	 *
	 * @return the manageProcesses.
	 */
	public String getManageProcesses() {
		return this.manageProcesses ;
	}

	/**
	 * Setter of manageProcesses.
	 *
	 * @param _manageProcesses The manageProcesses to set.
	 */
	public void setManageProcesses(String _manageProcesses) {
		this.manageProcesses = _manageProcesses ;
	}

	/**
	 * Getter of updateParticipant.
	 *
	 * @return the updateParticipant.
	 */
	public String getUpdateParticipant() {
		return this.updateParticipant ;
	}

	/**
	 * Setter of updateParticipant.
	 *
	 * @param _updateParticipant The updateParticipant to set.
	 */
	public void setUpdateParticipant(String _updateParticipant) {
		this.updateParticipant = _updateParticipant ;
	}
}
