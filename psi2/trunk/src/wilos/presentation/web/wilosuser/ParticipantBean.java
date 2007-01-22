
package wilos.presentation.web.wilosuser ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.Map ;

import java.util.List ;
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;

import javax.faces.application.FacesMessage ;
import javax.faces.component.UIComponent ;
import javax.faces.context.FacesContext ;
import javax.faces.validator.ValidatorException ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpSession ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import wilos.business.services.project.ProjectService ;
import wilos.business.services.wilosuser.LoginService ;
import wilos.business.services.wilosuser.ParticipantService ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.misc.project.Project ;
import wilos.model.misc.wilosuser.Participant ;
import wilos.presentation.web.template.MenuBean ;

/**
 * Managed-Bean link to participantSubscribe.jspx
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ParticipantBean {

	private List<RoleDescriptor> rolesList ;

	private ParticipantService participantService ;

	private ProjectService projectService ;

	private LoginService loginService ;

	private Participant participant ;

	private String passwordConfirmation ;

	private List<Participant> participantsList ;

	private List<HashMap<String, String>> affectedProjectsList ;

	private List<HashMap<String, String>> manageableProjectsList ;

	private List<HashMap<String, String>> notManageableProjectsList ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ParticipantBean() {
		this.logger.debug("--- ParticipantBean --- == creating ..." + this) ;
		this.participant = new Participant() ;
		this.affectedProjectsList = new ArrayList() ;
		this.manageableProjectsList = new ArrayList() ;
		this.notManageableProjectsList = new ArrayList() ;
	}

	/**
	 * Method designed to change main page content after participant subscription
	 * 
	 * @param url
	 */
	public void changeContentPage(String url) {
		// TODO factoriser cette fonction pr eviter de l'avoir
		// dans chaque Bean ;)
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		Object menuObject = facesContext.getApplication().createValueBinding("#{menu}").getValue(facesContext) ;
		if(menuObject != null && menuObject instanceof MenuBean){

			MenuBean menuBean = (MenuBean) menuObject ;
			menuBean.changePage(url) ;
		}
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
	public String saveParticipantAction() {
		String url = "" ;
		boolean error = false ;
		FacesMessage message = new FacesMessage() ;
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		// test if the fields are correctly completed
		if(this.participant.getName().trim().length() == 0){
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary("Le champ nom est obligatoire") ;
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errName) ;
		}

		if(this.participant.getFirstname().trim().length() == 0){
			FacesMessage errFirstName = new FacesMessage() ;
			errFirstName.setSummary("Le champ prénom est obligatoire") ;
			errFirstName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errFirstName) ;
		}
		if(this.participant.getEmailAddress().trim().length() == 0){
			FacesMessage errMail = new FacesMessage() ;
			errMail.setSummary("Le champ adrese e-mail est obligatoire") ;
			errMail.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errMail) ;
		}
		if(this.participant.getLogin().trim().length() == 0){
			FacesMessage errLogin = new FacesMessage() ;
			errLogin.setSummary("Le champ login est obligatoire") ;
			errLogin.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errLogin) ;
		}
		if(this.participant.getPassword().trim().length() == 0){
			FacesMessage errpasswd = new FacesMessage() ;
			errpasswd.setSummary("Le champ password est obligatoire") ;
			errpasswd.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errpasswd) ;
		}
		if(this.passwordConfirmation.trim().length() == 0){
			FacesMessage errConfirmation = new FacesMessage() ;
			errConfirmation.setSummary("Le champ de confirmation du password est obligatoire") ;
			errConfirmation.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errConfirmation) ;
		}

		if(!error){
			if(this.loginService.loginExist(this.participant.getLogin())){
				message.setSummary("Ce Login existe deja") ;
				message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
				facesContext.addMessage(null, message) ;
			}
			else{
				this.participantService.saveParticipant(this.participant) ;
				message.setSummary("Participant bien enregistré") ;
				message.setSeverity(FacesMessage.SEVERITY_INFO) ;
				facesContext.addMessage(null, message) ;
				url = "wilos" ; // TODO :Passer eventuellement sur une page de confirmation
				changeContentPage(url) ;
			}
		}
		this.participant = new Participant() ;

		return "" ;
	}

	/**
	 * 
	 * methode qui controle que les deux mots de passes sont identiques
	 * 
	 * @param _context
	 * @param _toValidate
	 * @param _value
	 * @throws ValidatorException
	 */
	public void emailValidation(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException {
		String enteredEmail = (String) _value ;
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+") ;
		// Match the given string with the pattern
		Matcher m = p.matcher(enteredEmail) ;
		// Check whether match is found
		boolean matchFound = m.matches() ;
		if(!matchFound){
			FacesMessage message = new FacesMessage() ;
			message.setSummary("L'email est invalide") ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			throw new ValidatorException(message) ;
		}
	}

	/**
	 * Getter of rolesList.
	 * 
	 * @return the rolesList.
	 */
	public List<RoleDescriptor> getRolesList() {
		this.rolesList = new ArrayList<RoleDescriptor>() ;
		rolesList.addAll(this.participantService.getRolesListForAParticipant(this.participant.getLogin())) ;
		this.logger.debug("roles list =" + this.rolesList) ;
		return this.rolesList ;
	}

	/**
	 * Setter of rolesList.
	 * 
	 * @param _rolesList
	 *            The rolesList to set.
	 */
	public void setRolesList(List<RoleDescriptor> _rolesList) {
		this.rolesList = _rolesList ;
	}

	/**
	 * 
	 * methode qui controle que les deux mots de passes sont identiques
	 * 
	 * @param _context
	 * @param _toValidate
	 * @param _value
	 * @throws ValidatorException
	 */
	public void passwordEqualValidation(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException {
		String passConfirm = (String) _value ;

		// TODO : recuperer le nom de l autre champs de password via une f:param
		/*
		 * ExternalContext ec = (ExternalContext)_context.getExternalContext(); HashMap hm = new
		 * HashMap(ec.getRequestParameterMap()); String passName = (String)hm.get("forPassword");
		 * UIComponent passcomponent = _toValidate.findComponent(passName) ;
		 */

		UIComponent passcomponent = _toValidate.findComponent("equal1") ;
		String passValue = (String) passcomponent.getAttributes().get("value") ;

		if(!passConfirm.equals(passValue)){
			FacesMessage message = new FacesMessage() ;
			message.setSummary("Les 2 mots de passe ne sont pas identiques") ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			throw new ValidatorException(message) ;
		}
	}

	/**
	 * Getter of participant.
	 * 
	 * @return the participant.
	 */
	public Participant getParticipant() {
		return this.participant ;
	}

	/**
	 * Setter of participant.
	 * 
	 * @param _participant
	 *            The participant to set.
	 */
	public void setParticipant(Participant _participant) {
		this.logger.debug("### Participant = " + _participant + " ###") ;
		this.participant = _participant ;
	}

	/**
	 * Getter of participantService.
	 * 
	 * @return the participantService.
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
	 * Getter of loginService.
	 * 
	 * @return the loginService.
	 */
	public LoginService getLoginService() {
		return this.loginService ;
	}

	/**
	 * Setter of loginService.
	 * 
	 * @param _loginService
	 *            The loginService to set.
	 */
	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService ;
	}

	/**
	 * Getter of passwordConfirmation.
	 * 
	 * @return the passwordConfirmation.
	 */
	public String getPasswordConfirmation() {
		return this.passwordConfirmation ;
	}

	/**
	 * Setter of passwordConfirmation.
	 * 
	 * @param _passwordConfirmation
	 *            The passwordConfirmation to set.
	 */
	public void setPasswordConfirmation(String _passwordConfirmation) {
		this.passwordConfirmation = _passwordConfirmation ;
	}

	/**
	 * Getter of participantsList.
	 * 
	 * @return the participantsList.
	 */
	public List<Participant> getParticipantsList() {
		this.participantsList = new ArrayList<Participant>() ;
		participantsList.addAll(this.participantService.getParticipants()) ;
		return this.participantsList ;
	}

	/**
	 * Setter of participantsList.
	 * 
	 * @param _participantsList
	 *            The participantsList to set.
	 */
	public void setParticipantsList(List<Participant> _participantsList) {
		this.participantsList = _participantsList ;
	}

	public List<HashMap<String, String>> getAffectedProjectsList() {
		Participant user = getParticipantFromSession() ;

		if(user instanceof Participant){
			this.affectedProjectsList = new ArrayList<HashMap<String, String>>() ;
			HashMap<Project, Boolean> plist = new HashMap<Project, Boolean>() ;
			plist = (HashMap<Project, Boolean>) this.participantService.getProjectsForAParticipant(user) ;
			Project currentProject = new Project() ;

			for(Iterator iter = plist.keySet().iterator(); iter.hasNext();){

				HashMap<String, String> ligne = new HashMap<String, String>() ;

				currentProject = (Project) iter.next() ;

				ligne.put("project_id", currentProject.getProject_id()) ;
				ligne.put("affected", plist.get(currentProject).toString()) ;
				ligne.put("name", currentProject.getName()) ;
				ligne.put("creationDate", currentProject.getCreationDate().toString()) ;
				ligne.put("launchingDate", currentProject.getLaunchingDate().toString()) ;
				ligne.put("description", currentProject.getDescription()) ;

				this.affectedProjectsList.add(ligne) ;
			}
		}
		return affectedProjectsList ;
	}

	public void setAffectedProjectsList(List<HashMap<String, String>> affectedProjectsList) {
		this.affectedProjectsList = affectedProjectsList ;
	}

	public void saveProjectsAffectation() {
		// getting the participant stored into the session
		Participant user = getParticipantFromSession() ;

		// creating a arraylist of hashmaps to represent the affected projects
		Map<String, Boolean> affectedProjects = new HashMap<String, Boolean>() ;

		// iterating onto the collection to create a collection of this form :
		// project_id/Boolean representing the affectation (true for affectation, false for not
		for(HashMap ligne : this.affectedProjectsList){
			Boolean testAffectation = (Boolean) ligne.get("affected") ;
			String project_id = (String) ligne.get("project_id") ;
			affectedProjects.put(project_id, testAffectation) ;
		}
		// saving of the new project affectation
		this.participantService.saveProjectsForAParticipant(user, affectedProjects) ;

		// displaying a message to express the good validation
		FacesMessage saveAffectationMessage = new FacesMessage() ;
		saveAffectationMessage.setSummary("Votre affectation à ces projets à bien été enregistrée") ;
		saveAffectationMessage.setSeverity(FacesMessage.SEVERITY_ERROR) ;
		FacesContext.getCurrentInstance().addMessage(null, saveAffectationMessage) ;
	}

	/**
	 * 
	 * TODO return the arraylist to display it into a datatable the arraylist represent the projects
	 * list affected to the participant which have no projectManager
	 * 
	 * @return
	 */
	public List<HashMap<String, String>> getManageableProjectsList() {
		Participant user = getParticipantFromSession() ;

		if(user instanceof Participant){

			this.manageableProjectsList = new ArrayList<HashMap<String, String>>() ;
			HashMap<Project, Participant> manageableProjects = (HashMap<Project, Participant>) this.participantService
					.getManageableProjectsForAParticipant(user) ;

			Project currentProject = new Project() ;
			for(Iterator iter = manageableProjects.keySet().iterator(); iter.hasNext();){
				currentProject = (Project) iter.next() ;
				this.logger.debug("### Projet : " + currentProject.getName() + "/ Affecte : " + manageableProjects.get(currentProject) + "###") ;
				if(manageableProjects.get(currentProject) == null){
					HashMap<String, String> ligne = new HashMap<String, String>() ;
					ligne.put("project_id", currentProject.getProject_id()) ;
					ligne.put("affected", "") ;
					ligne.put("name", currentProject.getName()) ;
					ligne.put("creationDate", currentProject.getCreationDate().toString()) ;
					ligne.put("launchingDate", currentProject.getLaunchingDate().toString()) ;
					ligne.put("description", currentProject.getDescription()) ;
					this.manageableProjectsList.add(ligne) ;
				}
			}
		}
		return this.manageableProjectsList ;
	}

	public void setManageableProjectsList(List<HashMap<String, String>> manageableProjectsList) {
		this.manageableProjectsList = manageableProjectsList ;
	}

	/**
	 * Getter of notManageableProjectsList.
	 * 
	 * @return the notManageableProjectsList.
	 */
	public List<HashMap<String, String>> getNotManageableProjectsList() {
		Participant user = getParticipantFromSession() ;

		if(user instanceof Participant){

			this.notManageableProjectsList = new ArrayList<HashMap<String, String>>() ;
			HashMap<Project, Participant> notManageableProjects = (HashMap<Project, Participant>) this.participantService
					.getManageableProjectsForAParticipant(user) ;

			Project currentProject = new Project() ;
			for(Iterator iter = notManageableProjects.keySet().iterator(); iter.hasNext();){
				currentProject = (Project) iter.next() ;
				this.logger.debug("### Projet : " + currentProject.getName() + "/ Affecte : " + notManageableProjects.get(currentProject) + "###") ;
				if(notManageableProjects.get(currentProject) != null){
					// projectManager Name construction
					String projectManagerName = ((Participant) notManageableProjects.get(currentProject)).getName().concat(
							" " + ((Participant) notManageableProjects.get(currentProject)).getFirstname()) ;

					HashMap<String, String> ligne = new HashMap<String, String>() ;
					ligne.put("project_id", currentProject.getProject_id()) ;
					ligne.put("projectManager_id", ((Participant) notManageableProjects.get(currentProject)).getWilosuser_id()) ;
					ligne.put("projectManagerName", projectManagerName) ;
					ligne.put("name", currentProject.getName()) ;
					ligne.put("creationDate", currentProject.getCreationDate().toString()) ;
					ligne.put("launchingDate", currentProject.getLaunchingDate().toString()) ;
					ligne.put("description", currentProject.getDescription()) ;
					this.notManageableProjectsList.add(ligne) ;
				}
			}
		}
		return this.notManageableProjectsList ;
	}

	/**
	 * Setter of notManageableProjectsList.
	 * 
	 * @param _notManageableProjectsList
	 *            The notManageableProjectsList to set.
	 */
	public void setNotManageableProjectsList(List<HashMap<String, String>> _notManageableProjectsList) {
		this.notManageableProjectsList = _notManageableProjectsList ;
	}

	/**
	 * 
	 * TODO Method description
	 *
	 */
	public void saveProjectManagerAffectation() {
		
		Participant user = getParticipantFromSession() ;
		Map<String, Boolean> affectedManagedProjects = new HashMap<String, Boolean>() ;
		for(HashMap ligne : this.manageableProjectsList){
			Boolean testAffectation = (Boolean) ligne.get("affected") ;
			String project_id = (String) ligne.get("project_id") ;
			affectedManagedProjects.put(project_id, testAffectation) ;
		}
		this.participantService.saveManagedProjectsForAParticipant(user, affectedManagedProjects) ;
		
		FacesMessage saveAffectationMessage = new FacesMessage() ;
		saveAffectationMessage.setSummary("Votre affectation en tant que chef de projet à ces projets à bien été enregistrée") ;
		saveAffectationMessage.setSeverity(FacesMessage.SEVERITY_ERROR) ;
		FacesContext.getCurrentInstance().addMessage(null, saveAffectationMessage) ;
	}

	/**
	 * method which permits the getting of the object Participant 
	 * 	which is stored into the session
	 *
	 * @return the participant stored into the session 
	 */
	private Participant getParticipantFromSession() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		Participant user = (Participant) sess.getAttribute("wilosUser") ;
		return user ;
	}
}
