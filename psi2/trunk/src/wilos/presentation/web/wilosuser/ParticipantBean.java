
package wilos.presentation.web.wilosuser ;

import java.text.SimpleDateFormat;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Iterator ;
import java.util.Map ;
import java.util.ResourceBundle;

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
import wilos.model.misc.concreterole.ConcreteRoleDescriptor ;
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

	private List<String> concreteRoleDescriptorHeaders;
	
	private List<ConcreteRoleDescriptor> concreteRoleDescriptors;
	
	private HashMap<String,Boolean> concreteRoleDescriptorsMap;

	private ParticipantService participantService ;

	private ProjectService projectService ;

	private LoginService loginService ;

	private Participant participant ;

	private String passwordConfirmation ;

	private List<Participant> participantsList ;

	private List<HashMap<String, String>> affectedProjectsList ;

	private List<HashMap<String, Object>> manageableProjectsList ;

	private String selectManageableProjectView ;
	
	private String selectAffectedProjectView;
	
	private SimpleDateFormat formatter ; 
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public ParticipantBean() {
		this.participant = new Participant() ;
		this.affectedProjectsList = new ArrayList() ;
		this.manageableProjectsList = new ArrayList() ;
		this.selectManageableProjectView = new String();
		this.formatter = new SimpleDateFormat("dd/MM/yyyy");
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
		
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		ResourceBundle bundle = ResourceBundle.getBundle(
		"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		
		String url = "" ;
		boolean error = false ;
		FacesMessage message = new FacesMessage() ;
		
		// test if the fields are correctly completed
		if(this.participant.getName().trim().length() == 0){
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary(bundle.getString("component.forminscription.err.lastnameRequired")) ;
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errName) ;
		}

		if(this.participant.getFirstname().trim().length() == 0){
			FacesMessage errFirstName = new FacesMessage() ;
			errFirstName.setSummary(bundle.getString("component.forminscription.err.firstnameRequired")) ;
			errFirstName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errFirstName) ;
		}
		if(this.participant.getEmailAddress().trim().length() == 0){
			FacesMessage errMail = new FacesMessage() ;
			errMail.setSummary(bundle.getString("component.forminscription.err.emailRequired")) ;
			errMail.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errMail) ;
		}
		if(this.participant.getLogin().trim().length() == 0){
			FacesMessage errLogin = new FacesMessage() ;
			errLogin.setSummary(bundle.getString("component.forminscription.err.loginRequired")) ;
			errLogin.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errLogin) ;
		}
		if(this.participant.getPassword().trim().length() == 0){
			FacesMessage errpasswd = new FacesMessage() ;
			errpasswd.setSummary(bundle.getString("component.forminscription.err.passwordRequired")) ;
			errpasswd.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errpasswd) ;
		}
		if(this.passwordConfirmation.trim().length() == 0){
			FacesMessage errConfirmation = new FacesMessage() ;
			errConfirmation.setSummary(bundle.getString("component.forminscription.err.confirmpasswordRequired")) ;
			errConfirmation.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error = true ;
			facesContext.addMessage(null, errConfirmation) ;
		}

		if(!error){
			if(this.loginService.loginExist(this.participant.getLogin())){
				message.setSummary(bundle.getString("component.forminscription.err.loginalreadyexist")) ;
				message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
				facesContext.addMessage(null, message) ;
			}
			else{
				this.participantService.saveParticipant(this.participant) ;
				message.setSummary(bundle.getString("component.forminscription.success")) ;
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
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		String enteredEmail = (String) _value ;
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+") ;
		// Match the given string with the pattern
		Matcher m = p.matcher(enteredEmail) ;
		// Check whether match is found
		boolean matchFound = m.matches() ;
		if(!matchFound){
			FacesMessage message = new FacesMessage() ;
			message.setSummary(bundle.getString("component.forminscription.err.invalidemail")) ;
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			throw new ValidatorException(message) ;
		}
	}

	/**
	 * Getter of rolesList.
	 * TODO method description + test
	 * @return the rolesList.
	 */
	public List<ConcreteRoleDescriptor> getConcreteRoleDescriptors() {
		this.concreteRoleDescriptors = new ArrayList<ConcreteRoleDescriptor>() ;
		//concreteRoleDescriptors.addAll(this.participantService.getConcreteRoleDescriptorsForAParticipantAndForAProject(_projectId, _login);
		return this.concreteRoleDescriptors ;
	}

	/**
	 * Setter of rolesList.
	 * TODO method description
	 * @param _rolesList
	 *            The rolesList to set.
	 */
	public void setConcreteRoleDescriptors(List<ConcreteRoleDescriptor> _concreteRoleDescriptors) {
		this.concreteRoleDescriptors = _concreteRoleDescriptors ;
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
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
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
			message.setSummary(bundle.getString("component.forminscription.err.passwordnotequals")) ;
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
				ligne.put("creationDate", formatter.format(currentProject.getCreationDate())) ;
				ligne.put("launchingDate", formatter.format(currentProject.getLaunchingDate())) ;
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
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
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
		saveAffectationMessage.setSummary(bundle.getString("component.tableparticipantproject.success")) ;
		saveAffectationMessage.setSeverity(FacesMessage.SEVERITY_INFO) ;
		FacesContext.getCurrentInstance().addMessage(null, saveAffectationMessage) ;
	}

	/**
	 * 
	 * TODO return the arraylist to display it into a datatable the arraylist represent the projects
	 * list affected to the participant which have no projectManager
	 * 
	 * @return
	 */
	public List<HashMap<String, Object>> getManageableProjectsList() {
		
		Participant user = getParticipantFromSession() ;
		
		ResourceBundle bundle = ResourceBundle.getBundle(
		"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		
		if(user instanceof Participant)
		{
			this.manageableProjectsList = new ArrayList<HashMap<String, Object>>() ;
			HashMap<Project, Participant> manageableProjects = (HashMap<Project, Participant>) this.participantService
					.getManageableProjectsForAParticipant(user) ;
				
				Project currentProject = new Project() ;
				for(Iterator iter = manageableProjects.keySet().iterator(); iter.hasNext();){
					
					currentProject = (Project) iter.next() ;
					Participant projectManager = manageableProjects.get(currentProject);
	
					HashMap<String, Object> ligne = new HashMap<String, Object>() ;
					
					ligne.put("project_id", currentProject.getProject_id()) ;
					ligne.put("name", currentProject.getName()) ;
					ligne.put("creationDate", formatter.format(currentProject.getCreationDate())) ;
					ligne.put("launchingDate", formatter.format(currentProject.getLaunchingDate())) ;
					ligne.put("description", currentProject.getDescription()) ;
					
					if(projectManager == null){

						ligne.put("projectManagerName", bundle.getString("component.table1participantprojectManager.noAffectation")) ;
						ligne.put("projectManager_id", "") ;
						ligne.put("affected", (new Boolean(false)).toString()) ;
						ligne.put("hasOtherManager", new Boolean(false)) ;
						
						this.manageableProjectsList.add(ligne) ;
					}
					else
					{
						String projectManagerName = projectManager.getName().concat(" " +projectManager.getFirstname()) ;
						ligne.put("projectManager_id", projectManager.getWilosuser_id()) ;
						ligne.put("projectManagerName", projectManagerName) ;
						if(projectManager.getWilosuser_id().equals(user.getWilosuser_id()))
						{
							ligne.put("affected", (new Boolean(true)).toString()) ;
							ligne.put("hasOtherManager", new Boolean(false)) ;
							this.manageableProjectsList.add(ligne) ;
						}
						else
						{
							ligne.put("affected", (new Boolean(true)).toString()) ;
							ligne.put("hasOtherManager", new Boolean(true)) ;
							this.manageableProjectsList.add(ligne) ;
						}
					}
				}
		}
		return this.manageableProjectsList ;
	}

	public void setManageableProjectsList(List<HashMap<String, Object>> manageableProjectsList) {
		this.manageableProjectsList = manageableProjectsList ;
	}


	/**
	 * 
	 * TODO Method description
	 *
	 */
	public void saveProjectManagerAffectation() {
		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
		Participant user = getParticipantFromSession() ;
		Map<String, Boolean> affectedManagedProjects = new HashMap<String, Boolean>() ;
		for(HashMap ligne : this.manageableProjectsList){
			//if the current project is not already managed by an other participant
			if (! ((Boolean) ligne.get("hasOtherManager")) )
			{
				Boolean testAffectation = (Boolean) ligne.get("affected") ;
				String project_id = (String) ligne.get("project_id") ;
				affectedManagedProjects.put(project_id, testAffectation) ;
			}
		}
		this.participantService.saveManagedProjectsForAParticipant(user, affectedManagedProjects) ;
		if (affectedManagedProjects.size() > 0)
		{
			FacesMessage saveAffectationMessage = new FacesMessage() ;
			saveAffectationMessage.setSummary(bundle.getString("component.table1participantprojectManager.success")) ;
			saveAffectationMessage.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			FacesContext.getCurrentInstance().addMessage(null, saveAffectationMessage) ;
		}
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

	public String getSelectManageableProjectView() {
		if (this.getManageableProjectsList().size()==0 )
		{
			this.selectManageableProjectView  = "manageable_no_records_view";
		}
		else
		{
			this.selectManageableProjectView ="manageable_records_view";
		}
		return selectManageableProjectView;
	}

	public void setSelectManageableProjectView(String selectManageableProjectView) {
		this.selectManageableProjectView = selectManageableProjectView;
	}

	public SimpleDateFormat getFormatter() {
		return formatter;
	}

	public void setFormatter(SimpleDateFormat formatter) {
		this.formatter = formatter;
	}

	/**
	 * Getter of concreteRoleDescriptorHeaders.
	 *
	 * @return the concreteRoleDescriptorHeaders.
	 */
	public List<String> getConcreteRoleDescriptorHeaders() {
		this.concreteRoleDescriptorHeaders.addAll(this.getConcreteRoleDescriptorsMap().keySet());
		return this.concreteRoleDescriptorHeaders ;
	}
	

	/**
	 * Setter of concreteRoleDescriptorHeaders.
	 *
	 * @param _concreteRoleDescriptorHeaders The concreteRoleDescriptorHeaders to set.
	 */
	public void setConcreteRoleDescriptorHeaders(List<String> _concreteRoleDescriptorHeaders) {
		this.concreteRoleDescriptorHeaders = _concreteRoleDescriptorHeaders ;
	}
	
	

	/**
	 * Getter of concreteRoleDescriptorsMap.
	 *
	 * @return the concreteRoleDescriptorsMap.
	 */
	public HashMap<String, Boolean> getConcreteRoleDescriptorsMap() {
		//participantService.getConcreteRoleDescriptorsForAProject(String _project_id, String _participant_id);
		this.concreteRoleDescriptorsMap = new HashMap<String, Boolean>();
		List<ConcreteRoleDescriptor> concreteRoleDescriptorsForAParticipant  = new ArrayList<ConcreteRoleDescriptor>();
		//concreteRoleDescriptorsForAParticipant.addAll(this.participantService.getConcreteRoleDescriptorsForAParticipant(this.getParticipantFromSession().getLogin()));
		//Ajouter les CRD ki ne sont pas affectés au participant
		//a recup direct ds le CRDService :D
		for(Iterator iter = concreteRoleDescriptorsForAParticipant.iterator(); iter.hasNext();){
			ConcreteRoleDescriptor currentCRD = (ConcreteRoleDescriptor) iter.next() ;
			this.concreteRoleDescriptorsMap.put(currentCRD.getConcreteName(), true);
			
		}
		return this.concreteRoleDescriptorsMap ;
	}
	

	/**
	 * Setter of concreteRoleDescriptorsMap.
	 *
	 * @param _concreteRoleDescriptorsMap The concreteRoleDescriptorsMap to set.
	 */
	public void setConcreteRoleDescriptorsMap(HashMap<String, Boolean> _concreteRoleDescriptorsMap) {
		this.concreteRoleDescriptorsMap = _concreteRoleDescriptorsMap ;
	}

	/**
	 * Getter of selectAffectedProjectView.
	 *
	 * @return the selectAffectedProjectView.
	 */
	public String getSelectAffectedProjectView() {
		if (this.getAffectedProjectsList().size()==0 )
		{
			this.selectAffectedProjectView  = "affected_no_records_view";
		}
		else
		{
			this.selectAffectedProjectView ="affected_records_view";
		}
		return this.selectAffectedProjectView;
	}

	/**
	 * Setter of selectAffectedProjectView.
	 *
	 * @param _selectAffectedProjectView The selectAffectedProjectView to set.
	 */
	public void setSelectAffectedProjectView(String _selectAffectedProjectView) {
		this.selectAffectedProjectView = _selectAffectedProjectView ;
	}

}
