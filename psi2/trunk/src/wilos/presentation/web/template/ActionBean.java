
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
	
	private String affectRoles = "affectRoles";
	private String affectProject = "affectProject";
	private String affectProjectAsManager = "affectProjectAsManager";
	
	private String importProcessFile = "importProcessFile";
	
	private String projectCreate="projectCreate";
	private String projectList = "projectList";

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
		FacesContext context = FacesContext.getCurrentInstance() ;
		Map map = context.getExternalContext().getRequestParameterMap() ;
		String pageToShow = (String) map.get("pageToShow") ;
		Map map2 = context.getExternalContext().getApplicationMap();
		MenuBean menuBean = (MenuBean)context.getExternalContext().getSessionMap().get("menu");
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
	 * Getter of affectRoles.
	 *
	 * @return the affectRoles.
	 */
	public String getAffectRoles() {
		return this.affectRoles ;
	}

	/**
	 * Setter of affectRoles.
	 *
	 * @param _affectRoles The affectRoles to set.
	 */
	public void setAffectRoles(String _affectRoles) {
		this.affectRoles = _affectRoles ;
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
}
