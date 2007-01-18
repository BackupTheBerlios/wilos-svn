package wilos.presentation.web.wilosuser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.project.ProjectService;
import wilos.business.services.wilosuser.LoginService;
import wilos.business.services.wilosuser.ParticipantService;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.presentation.web.template.MenuBean;

/**
 * Managed-Bean link to participantSubscribe.jspx
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ParticipantBean {

	private List<RoleDescriptor> rolesList;

	private ParticipantService participantService;
	
	private ProjectService projectService;

	private LoginService loginService;

	private Participant participant;

	private String passwordConfirmation;
	
	private List<Participant> participantsList;
	
	private List<HashMap<String,String>> affectedProjectsList;

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor.
	 * 
	 */
	public ParticipantBean() {
		this.logger.debug("--- ParticipantBean --- == creating ..." + this);
		this.participant = new Participant();
		this.affectedProjectsList = new ArrayList();
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
	public String saveParticipantAction() {
		String url = "";
		boolean error=false;
		FacesMessage message = new FacesMessage();
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		//		test if the fields are correctly completed 
		if (this.participant.getName().trim().length()==0)
		{
			FacesMessage errName = new FacesMessage() ;
			errName.setSummary("Le champ nom est obligatoire");
			errName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errName) ;
		}
		
		if (this.participant.getFirstname().trim().length()==0)
		{
			FacesMessage errFirstName = new FacesMessage() ;
			errFirstName.setSummary("Le champ prénom est obligatoire");
			errFirstName.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errFirstName) ;
		}
		if (this.participant.getEmailAddress().trim().length()==0)
		{
			FacesMessage errMail = new FacesMessage() ;
			errMail.setSummary("Le champ adrese e-mail est obligatoire");
			errMail.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errMail) ;
		}
		if (this.participant.getLogin().trim().length()==0)
		{
			FacesMessage errLogin = new FacesMessage() ;
			errLogin.setSummary("Le champ login est obligatoire");
			errLogin.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errLogin) ;
		}
		if (this.participant.getPassword().trim().length()==0)
		{
			FacesMessage errpasswd = new FacesMessage() ;
			errpasswd.setSummary("Le champ password est obligatoire");
			errpasswd.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errpasswd) ;
		}
		if (this.passwordConfirmation.trim().length()==0)
		{
			FacesMessage errConfirmation = new FacesMessage() ;
			errConfirmation.setSummary("Le champ de confirmation du password est obligatoire");
			errConfirmation.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			error=true;
			facesContext.addMessage(null, errConfirmation) ;
		}
		
		if (!error)
		{
			if (this.loginService.loginExist(this.participant.getLogin())) {
				message.setSummary("Ce Login existe deja");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				facesContext.addMessage(null, message);
			} else {
				this.participantService.saveParticipant(this.participant);
				message.setSummary("Participant bien enregistré");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
				facesContext.addMessage(null, message);
				url="wilos"; //TODO :Passer eventuellement sur une page de confirmation
				changeContentPage(url);
			}
		}
		this.participant = new Participant();
		
		return "";
	}

	/**
	 * Method designed to change main page content after
	 * participant subscription
	 * @param url
	 */
	public void changeContentPage(String url) {
		// TODO factoriser cette fonction pr eviter de l'avoir
		// dans chaque Bean ;)
		FacesContext facesContext = FacesContext.getCurrentInstance() ;
		Object menuObject =
            facesContext.getApplication()
                    .createValueBinding("#{menu}")
                    .getValue(facesContext);
		if (menuObject != null &&
                menuObject instanceof MenuBean) {
			
                MenuBean menuBean = (MenuBean) menuObject;
                menuBean.changePage(url);
		}
	}

	public void testTransactionActionListener(ActionEvent e) {
		// this.participantManager.Test();
	}

	/**
	 * Getter of rolesList.
	 * 
	 * @return the rolesList.
	 */
	public List<RoleDescriptor> getRolesList() {
		this.rolesList = new ArrayList<RoleDescriptor>();
		rolesList.addAll(this.participantService.getRolesList());
		this.logger.debug("roles list =" + this.rolesList);
		return this.rolesList;
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
	public void emailValidation(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException
	{
		String enteredEmail = (String) _value ;
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+") ;
		// Match the given string with the pattern
		Matcher m = p.matcher(enteredEmail) ;
		// Check whether match is found
		boolean matchFound = m.matches() ;
		if(!matchFound){
			FacesMessage message = new FacesMessage() ;
			message.setSummary("L'email est invalide");
			message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
			throw new ValidatorException(message) ;
		}
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
	public void passwordEqualValidation(FacesContext _context, UIComponent _toValidate, Object _value) throws ValidatorException
	{
		String passConfirm = (String) _value;

		//TODO : recuperer le nom de l autre champs de password via une f:param
		/*ExternalContext ec = (ExternalContext)_context.getExternalContext();
		HashMap hm = new HashMap(ec.getRequestParameterMap());
		String passName = (String)hm.get("forPassword");
		UIComponent passcomponent = _toValidate.findComponent(passName) ;*/
		
		UIComponent passcomponent = _toValidate.findComponent("equal1") ;
		String passValue = (String) passcomponent.getAttributes().get("value");
		
		if(!passConfirm.equals(passValue))
		{
			FacesMessage message = new FacesMessage();
			message.setSummary("Les 2 mots de passe ne sont pas identiques");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message) ;
		}
	}
	
	/**
	 * Getter of participant.
	 * 
	 * @return the participant.
	 */
	public Participant getParticipant() {
		return this.participant;
	}

	/**
	 * Setter of participant.
	 * 
	 * @param _participant
	 *            The participant to set.
	 */
	public void setParticipant(Participant _participant) {
		this.logger.debug("### Participant = " + _participant + " ###");
		this.participant = _participant;
	}

	/**
	 * Getter of participantService.
	 * 
	 * @return the participantService.
	 */
	public ParticipantService getParticipantService() {
		return this.participantService;
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
	 * @param _loginService The loginService to set.
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
	 * @param _passwordConfirmation The passwordConfirmation to set.
	 */
	public void setPasswordConfirmation(String _passwordConfirmation) {
		this.passwordConfirmation = _passwordConfirmation ;
	}

	/**
	 * Setter of rolesList.
	 *
	 * @param _rolesList The rolesList to set.
	 */
	public void setRolesList(List<RoleDescriptor> _rolesList) {
		this.rolesList = _rolesList ;
	}

	/**
	 * Setter of participantService.
	 * 
	 * @param _participantService
	 *            The participantService to set.
	 */
	public void setParticipantService(ParticipantService _participantService) {
		this.participantService = _participantService;
	}

	/**
	 * Getter of participantsList.
	 *
	 * @return the participantsList.
	 */
	public List<Participant> getParticipantsList() {
		this.participantsList = new ArrayList<Participant>();
		participantsList.addAll(this.participantService.getParticipants());
		return this.participantsList ;
	}

	/**
	 * Setter of participantsList.
	 *
	 * @param _participantsList The participantsList to set.
	 */
	public void setParticipantsList(List<Participant> _participantsList) {
		this.participantsList = _participantsList ;
	}

	public List<HashMap<String,String>> getAffectedProjectsList() {
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		Participant user = (Participant) sess.getAttribute("wilosUser") ;
		
		if (user instanceof Participant)
		{
			this.affectedProjectsList = new ArrayList<HashMap<String,String>>();
			HashMap<Project, Boolean> plist = new HashMap<Project, Boolean>();
			this.logger.debug("### FIN INIT RACE ###");
			plist = (HashMap<Project, Boolean>)this.participantService.getProjectsForAParticipant(user);
			this.logger.debug("### TAILLE RACE :"+plist.size()+" ###");
			Project currentProject = new Project();
			
			for (Iterator iter = plist.keySet().iterator(); iter.hasNext();) {
				
				HashMap<String,String> ligne = new HashMap<String,String>();
				
				currentProject = (Project)iter.next();
				this.logger.debug("### DATA :"+currentProject.getName()+" ###");
				
				ligne.put("project_id", currentProject.getProject_id());
				ligne.put("affected", plist.get(currentProject).toString());
				ligne.put("name", currentProject.getName());
				ligne.put("creationDate", currentProject.getCreationDate().toString());
				ligne.put("launchingDate", currentProject.getLaunchingDate().toString());
				ligne.put("description", currentProject.getDescription());
		
				this.affectedProjectsList.add(ligne);
				this.logger.debug("### TAILLE RACE :"+ligne.get("name")+" ###");
			}
		}		
		return affectedProjectsList;
	}

	public String test(){
		this.getAffectedProjectsList();
		return "";
	}
	public void setAffectedProjectsList(List<HashMap<String,String>> affectedProjectsList) {
		this.affectedProjectsList = affectedProjectsList;
	}
	
	public void saveProjectsAffectation()
	{
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		Participant user = (Participant) sess.getAttribute("wilosUser") ;
		Map<String,Boolean> affectedProjects = new HashMap<String, Boolean>();
		for (HashMap ligne : this.affectedProjectsList) {
			Boolean testAffectation = (Boolean)ligne.get("affected");
			String project_id = (String)ligne.get("project_id");
			affectedProjects.put(project_id,testAffectation);
		}
		this.participantService.saveProjectsForAParticipant(user, affectedProjects);
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
