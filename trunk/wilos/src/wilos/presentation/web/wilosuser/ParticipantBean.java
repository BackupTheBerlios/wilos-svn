/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
 */

package wilos.presentation.web.wilosuser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.project.ProjectService;
import wilos.business.services.misc.wilosuser.LoginService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebCommonService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.presentation.web.tree.TreeBean;

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

	private HashMap<String, Boolean> concreteRoleDescriptorsMap;

	private ParticipantService participantService;

	private ProjectService projectService;

	private LoginService loginService;

	private Participant participant;

	private String passwordConfirmation;

	private List<Participant> participantsList;

	private List<HashMap<String, String>> affectedProjectsList;

	private List<HashMap<String, Object>> manageableProjectsList;

	private String selectManageableProjectView;

	private String selectAffectedProjectView;

	private SimpleDateFormat formatter;

	private String participantView;

	private WebSessionService webSessionService;
	
	private WebCommonService webCommonService;

	private String isSetParticipantFromSession;

	private String cleanBean;

	protected final Log logger = LogFactory.getLog(this.getClass());

	private boolean visiblePopup = false;

	private String selectedProjectId;

	/**
	 * Constructor.
	 * 
	 */
	public ParticipantBean() {
		this.participant = new Participant();
		this.affectedProjectsList = new ArrayList<HashMap<String, String>>();
		this.manageableProjectsList = new ArrayList<HashMap<String, Object>>();
		this.selectManageableProjectView = new String();
		this.formatter = new SimpleDateFormat("dd/MM/yyyy");
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
	public String saveParticipantAction() {

		String url = "";
		boolean error = false;
		
		// test if the fields are correctly completed
		if (this.participant.getName().trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.lastnameRequired"));
		}

		if (this.participant.getFirstname().trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.firstnameRequired"));
		}
		if (this.participant.getEmailAddress().trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.emailRequired"));
		}
		if (this.participant.getLogin().trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.loginRequired"));
		}
		if (this.participant.getPassword().trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.passwordRequired"));
		}
		if (this.passwordConfirmation.trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.confirmpasswordRequired"));
		}

		if (!error) {
			if (this.loginService
					.loginExist(this.participant.getLogin().trim())) {
				this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.loginalreadyexist"));
			} else {
				this.participantService.saveParticipant(this.participant);
				this.webCommonService.addInfoMessage(this.webCommonService.getStringFromBundle("component.forminscription.success"));
				url = "wilos"; // TODO :Passer eventuellement sur une page de
				// confirmation
				this.webCommonService.changeContentPage(url);
			}
		}
		this.participant = new Participant();

		return "";
	}

	/**
	 * Method for updating participant data from form
	 * 
	 * @return
	 */
	public String updateParticipantAction() {

		String url = "";
		boolean error = false;
		
		// test if the fields are correctly completed
		if (this.participant.getName().trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.lastnameRequired"));
		}

		if (this.participant.getFirstname().trim().length() == 0) {
			error = true;
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.firstnameRequired"));
		}
		if (this.participant.getEmailAddress().trim().length() == 0) {
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.emailRequired"));
			error = true;
		}
		if (this.participant.getLogin().trim().length() == 0) {
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.forminscription.err.loginRequired"));
			error = true;
		}

		if (!error) {
			if (this.participant.getPassword().length() == 0) {
				this.participant.setPassword(this.getParticipantFromSession()
						.getPassword());
				this.participantService
						.saveParticipantWithoutEncryption(this.participant);
			} else {
				this.participantService.saveParticipant(this.participant);
			}
			this.webCommonService.addInfoMessage(this.webCommonService.getStringFromBundle("component.participantUpdate.success"));
			url = "wilos"; // TODO :Passer eventuellement sur une page de
			// confirmation
			this.webCommonService.changeContentPage(url);
		}

		return "";
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
	public void emailValidation(FacesContext _context, UIComponent _toValidate,
			Object _value) throws ValidatorException {
		String enteredEmail = (String) _value;
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		// Match the given string with the pattern
		Matcher m = p.matcher(enteredEmail);
		// Check whether match is found
		boolean matchFound = m.matches();
		if (!matchFound) {
			FacesMessage message = new FacesMessage();
			message.setSummary(this.webCommonService.getStringFromBundle("component.forminscription.err.invalidemail"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	/**
	 * Getter of rolesList. TODO method description + test
	 * 
	 * @return the rolesList.
	 */
	public List<ConcreteRoleDescriptor> getConcreteRoleDescriptors() {
		this.concreteRoleDescriptors = new ArrayList<ConcreteRoleDescriptor>();
		// concreteRoleDescriptors.addAll(this.participantService.getConcreteRoleDescriptorsForAParticipantAndForAProject(_projectId,
		// _login);
		return this.concreteRoleDescriptors;
	}

	/**
	 * Setter of rolesList.
	 * 
	 * @param _rolesList
	 *            The rolesList to set.
	 */
	public void setConcreteRoleDescriptors(
			List<ConcreteRoleDescriptor> _concreteRoleDescriptors) {
		this.concreteRoleDescriptors = _concreteRoleDescriptors;
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
	public void passwordEqualValidation(FacesContext _context,
			UIComponent _toValidate, Object _value) throws ValidatorException {
		String passConfirm = (String) _value;
		// TODO : recuperer le nom de l autre champs de password via une f:param
		/*
		 * ExternalContext ec = (ExternalContext)_context.getExternalContext();
		 * HashMap hm = new HashMap(ec.getRequestParameterMap()); String
		 * passName = (String)hm.get("forPassword"); UIComponent passcomponent =
		 * _toValidate.findComponent(passName) ;
		 */

		UIComponent passcomponent = _toValidate.findComponent("equal1");
		String passValue = (String) passcomponent.getAttributes().get("value");

		if (!passConfirm.equals(passValue)) {
			FacesMessage message = new FacesMessage();
			message
					.setSummary(this.webCommonService.getStringFromBundle("component.forminscription.err.passwordnotequals"));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(message);
		}
	}

	/**
	 * Getter of participant.
	 * 
	 * @return the participant.
	 */
	public Participant getParticipant() {
		/*
		 * userID =
		 * (String)this.webSessionService.getAttribute(this.webSessionService.WILOS_USER_ID);
		 * if (userID != null) { this.participant =
		 * this.participantService.getParticipant(userID) ; }
		 */
		return this.participant;
	}

	/**
	 * Setter of participant.
	 * 
	 * @param _participant
	 *            The participant to set.
	 */
	public void setParticipant(Participant _participant) {
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
	 * Setter of participantService.
	 * 
	 * @param _participantService
	 *            The participantService to set.
	 */
	public void setParticipantService(ParticipantService _participantService) {
		this.participantService = _participantService;
	}

	/**
	 * Getter of projectService.
	 * 
	 * @return the projectService.
	 */
	public ProjectService getProjectService() {
		return this.projectService;
	}

	/**
	 * Setter of projectService.
	 * 
	 * @param _projectService
	 *            The projectService to set.
	 */
	public void setProjectService(ProjectService _projectService) {
		this.projectService = _projectService;
	}

	/**
	 * Getter of loginService.
	 * 
	 * @return the loginService.
	 */
	public LoginService getLoginService() {
		return this.loginService;
	}

	/**
	 * Setter of loginService.
	 * 
	 * @param _loginService
	 *            The loginService to set.
	 */
	public void setLoginService(LoginService _loginService) {
		this.loginService = _loginService;
	}

	/**
	 * Getter of passwordConfirmation.
	 * 
	 * @return the passwordConfirmation.
	 */
	public String getPasswordConfirmation() {
		return this.passwordConfirmation;
	}

	/**
	 * Setter of passwordConfirmation.
	 * 
	 * @param _passwordConfirmation
	 *            The passwordConfirmation to set.
	 */
	public void setPasswordConfirmation(String _passwordConfirmation) {
		this.passwordConfirmation = _passwordConfirmation;
	}

	/**
	 * Getter of participantsList.
	 * 
	 * @return the participantsList.
	 */
	public List<Participant> getParticipantsList() {
		this.participantsList = new ArrayList<Participant>();
		participantsList.addAll(this.participantService.getParticipants());
		return this.participantsList;
	}

	/**
	 * Setter of participantsList.
	 * 
	 * @param _participantsList
	 *            The participantsList to set.
	 */
	public void setParticipantsList(List<Participant> _participantsList) {
		this.participantsList = _participantsList;
	}

	public List<HashMap<String, String>> getAffectedProjectsList() {
		Participant user = getParticipantFromSession();

		if (user instanceof Participant) {
			this.affectedProjectsList = new ArrayList<HashMap<String, String>>();
			HashMap<Project, Boolean> plist = new HashMap<Project, Boolean>();
			plist = (HashMap<Project, Boolean>) this.participantService
					.getProjectsForAParticipant(user);
			Project currentProject = new Project();

			for (Iterator iter = plist.keySet().iterator(); iter.hasNext();) {

				HashMap<String, String> ligne = new HashMap<String, String>();

				currentProject = (Project) iter.next();

				ligne.put("project_id", currentProject.getId());
				ligne.put("affected", plist.get(currentProject).toString());
				ligne.put("name", currentProject.getConcreteName());
				ligne.put("creationDate", formatter.format(currentProject
						.getCreationDate()));
				ligne.put("launchingDate", formatter.format(currentProject
						.getLaunchingDate()));
				ligne.put("description", currentProject.getDescription());

				this.affectedProjectsList.add(ligne);
			}
		}
		return affectedProjectsList;
	}

	public void setAffectedProjectsList(
			List<HashMap<String, String>> affectedProjectsList) {
		this.affectedProjectsList = affectedProjectsList;
	}

	public void saveProjectsAffectation() {
		// getting the participant stored into the session
		Participant user = getParticipantFromSession();

		// creating a arraylist of hashmaps to represent the affected projects
		Map<String, Boolean> affectedProjects = new HashMap<String, Boolean>();

		// iterating onto the collection to create a collection of this form :
		// project_id/Boolean representing the affectation (true for
		// affectation, false for not
		for (HashMap ligne : this.affectedProjectsList) {
			Boolean testAffectation = (Boolean) ligne.get("affected");
			String project_id = (String) ligne.get("project_id");

			// si on se desaffecte du projet courement affiché on clean l'arbre
			if (this.webSessionService
					.getAttribute(WebSessionService.PROJECT_ID) != null) {
				if (this.webSessionService.getAttribute(
						WebSessionService.PROJECT_ID).equals(project_id)
						&& !testAffectation) {
					// display a message to inform the participant that the
					// current project won't have of project manager.
					this.visiblePopup = true;

					// clean the tree display.
					TreeBean treeBean = (TreeBean) this.webCommonService.getBean("TreeBean");
					treeBean.cleanTreeDisplay();
				}
			}

			affectedProjects.put(project_id, testAffectation);
		}
		// saving of the new project affectation
		this.participantService.saveProjectsForAParticipant(user,
				affectedProjects);

		// displaying a message to express the good validation
		this.webCommonService.addInfoMessage(this.webCommonService.getStringFromBundle("component.tableparticipantproject.success"));
}

	public void changeModeActionListener(ValueChangeEvent evt) {
		Boolean isForAssignment = (Boolean) evt.getNewValue();
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		this.selectedProjectId = (String) map.get("projectId");
		if (!isForAssignment) {
			// show the confirmation popup.
			this.visiblePopup = true;
		} else {
			this.participantService.saveProjectForAProjectManager(this
					.getParticipantFromSession().getWilosuser_id(),
					this.selectedProjectId, true);
		}
	}

	public void assignActionListener(ActionEvent event) {
		//get the participant id into the session.
		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		this.selectedProjectId = (String) map.get("projectId");
		
		//save.
		this.participantService.saveProjectForAProjectManager(this
				.getParticipantFromSession().getWilosuser_id(),
				this.selectedProjectId, true);
	}

	public void disAssignActionListener(ActionEvent event) {
		this.visiblePopup = true;
	}

	public void confirmDisassignment(ActionEvent _event) {
		this.participantService.saveProjectForAProjectManager(this
				.getParticipantFromSession().getWilosuser_id(),
				this.selectedProjectId, false);
		this.visiblePopup = false;
	}

	public void cancelDisassignment(ActionEvent _event) {
		this.visiblePopup = false;
	}

	/**
	 * 
	 * TODO return the arraylist to display it into a datatable the arraylist
	 * represent the projects list affected to the participant which have no
	 * projectManager
	 * 
	 * @return
	 */
	public List<HashMap<String, Object>> getManageableProjectsList() {

		Participant user = getParticipantFromSession();

		if (user instanceof Participant) {
			this.manageableProjectsList = new ArrayList<HashMap<String, Object>>();
			HashMap<Project, Participant> manageableProjects = (HashMap<Project, Participant>) this.participantService
					.getManageableProjectsForAParticipant(user);

			Project currentProject = new Project();
			for (Iterator iter = manageableProjects.keySet().iterator(); iter
					.hasNext();) {

				currentProject = (Project) iter.next();
				Participant projectManager = manageableProjects
						.get(currentProject);

				HashMap<String, Object> ligne = new HashMap<String, Object>();

				ligne.put("project_id", currentProject.getId());
				ligne.put("name", currentProject.getConcreteName());
				ligne.put("creationDate", formatter.format(currentProject
						.getCreationDate()));
				ligne.put("launchingDate", formatter.format(currentProject
						.getLaunchingDate()));
				ligne.put("description", currentProject.getDescription());

				if (projectManager == null) {

					ligne
							.put(
									"projectManagerName",
									this.webCommonService.getStringFromBundle("component.table1participantprojectManager.noAffectation"));
					ligne.put("projectManager_id", "");
					ligne.put("affected", (new Boolean(false)).toString());
					ligne.put("hasOtherManager", new Boolean(false));

					this.manageableProjectsList.add(ligne);
				} else {
					String projectManagerName = projectManager.getFirstname()
							.concat(" " + projectManager.getName());
					ligne.put("projectManager_id", projectManager
							.getWilosuser_id());
					ligne.put("projectManagerName", projectManagerName);
					if (projectManager.getWilosuser_id().equals(
							user.getWilosuser_id())) {
						ligne.put("affected", (new Boolean(true)).toString());
						ligne.put("hasOtherManager", new Boolean(false));
						this.manageableProjectsList.add(ligne);
					} else {
						ligne.put("affected", (new Boolean(true)).toString());
						ligne.put("hasOtherManager", new Boolean(true));
						this.manageableProjectsList.add(ligne);
					}
				}
			}
		}
		return this.manageableProjectsList;
	}

	public void setManageableProjectsList(
			List<HashMap<String, Object>> manageableProjectsList) {
		this.manageableProjectsList = manageableProjectsList;
	}

	/**
	 * 
	 * This method allows to save a project manager affectation
	 * 
	 */
	public void saveProjectManagerAffectation() {
		Participant user = getParticipantFromSession();
		Map<String, Boolean> affectedManagedProjects = new HashMap<String, Boolean>();
		for (HashMap ligne : this.manageableProjectsList) {
			// if the current project is not already managed by an other
			// participant
			if (!((Boolean) ligne.get("hasOtherManager"))) {
				Boolean testAffectation = (Boolean) ligne.get("affected");
				String project_id = (String) ligne.get("project_id");
				affectedManagedProjects.put(project_id, testAffectation);
			}
		}
		this.participantService.saveManagedProjectsForAParticipant(user,
				affectedManagedProjects);
		if (affectedManagedProjects.size() > 0) {
			this.webCommonService.addErrorMessage(this.webCommonService.getStringFromBundle("component.table1participantprojectManager.success"));
		}
	}

	/**
	 * method which permits the getting of the object Participant which is
	 * stored into the session
	 * 
	 * @return the participant stored into the session
	 */
	private Participant getParticipantFromSession() {
		String userId = (String) this.webSessionService
				.getAttribute(WebSessionService.WILOS_USER_ID);
		Participant user = this.participantService.getParticipant(userId);
		return user;
	}

	/**
	 * Set the participant of the participantBean to the participant which is
	 * stored into the session
	 * 
	 * @return
	 */
	public String getIsSetParticipantFromSession() {
		Participant user = this.getParticipantFromSession();
		if (user != null) {
			this.participant = user;
			this.participant.setPassword("");
			this.isSetParticipantFromSession = "ok";
		} else {
			this.isSetParticipantFromSession = "null";
		}
		return this.isSetParticipantFromSession;
	}

	public void setIsSetParticipantFromSession(String _msg) {
		this.isSetParticipantFromSession = _msg;
	}

	public String getSelectManageableProjectView() {
		if (this.getManageableProjectsList().size() == 0) {
			this.selectManageableProjectView = "manageable_no_records_view";
		} else {
			this.selectManageableProjectView = "manageable_records_view";
		}
		return selectManageableProjectView;
	}

	public void setSelectManageableProjectView(
			String selectManageableProjectView) {
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
		this.concreteRoleDescriptorHeaders.addAll(this
				.getConcreteRoleDescriptorsMap().keySet());
		return this.concreteRoleDescriptorHeaders;
	}

	/**
	 * Setter of concreteRoleDescriptorHeaders.
	 * 
	 * @param _concreteRoleDescriptorHeaders
	 *            The concreteRoleDescriptorHeaders to set.
	 */
	public void setConcreteRoleDescriptorHeaders(
			List<String> _concreteRoleDescriptorHeaders) {
		this.concreteRoleDescriptorHeaders = _concreteRoleDescriptorHeaders;
	}

	/**
	 * Getter of concreteRoleDescriptorsMap.
	 * 
	 * @return the concreteRoleDescriptorsMap.
	 */
	public HashMap<String, Boolean> getConcreteRoleDescriptorsMap() {
		// participantService.getConcreteRoleDescriptorsForAProject(String
		// _project_id, String
		// _participant_id);
		this.concreteRoleDescriptorsMap = new HashMap<String, Boolean>();
		List<ConcreteRoleDescriptor> concreteRoleDescriptorsForAParticipant = new ArrayList<ConcreteRoleDescriptor>();
		// concreteRoleDescriptorsForAParticipant.addAll(this.participantService.getConcreteRoleDescriptorsForAParticipant(this.getParticipantFromSession().getLogin()));
		// Ajouter les CRD ki ne sont pas affectés au participant
		// a recup direct ds le CRDService :D
		for (Iterator iter = concreteRoleDescriptorsForAParticipant.iterator(); iter
				.hasNext();) {
			ConcreteRoleDescriptor currentCRD = (ConcreteRoleDescriptor) iter
					.next();
			this.concreteRoleDescriptorsMap.put(currentCRD.getConcreteName(),
					true);

		}
		return this.concreteRoleDescriptorsMap;
	}

	/**
	 * Setter of concreteRoleDescriptorsMap.
	 * 
	 * @param _concreteRoleDescriptorsMap
	 *            The concreteRoleDescriptorsMap to set.
	 */
	public void setConcreteRoleDescriptorsMap(
			HashMap<String, Boolean> _concreteRoleDescriptorsMap) {
		this.concreteRoleDescriptorsMap = _concreteRoleDescriptorsMap;
	}

	/**
	 * Getter of selectAffectedProjectView.
	 * 
	 * @return the selectAffectedProjectView.
	 */
	public String getSelectAffectedProjectView() {
		if (this.getAffectedProjectsList().size() == 0) {
			this.selectAffectedProjectView = "affected_no_records_view";
		} else {
			this.selectAffectedProjectView = "affected_records_view";
		}
		return this.selectAffectedProjectView;
	}

	/**
	 * Setter of selectAffectedProjectView.
	 * 
	 * @param _selectAffectedProjectView
	 *            The selectAffectedProjectView to set.
	 */
	public void setSelectAffectedProjectView(String _selectAffectedProjectView) {
		this.selectAffectedProjectView = _selectAffectedProjectView;
	}

	/**
	 * Getter of selectAffectedProjectView.
	 * 
	 * @return the selectAffectedProjectView.
	 */
	public String getParticipantView() {
		if (this.getParticipantsList().size() == 0) {
			this.participantView = "participantView_null";
		} else {
			this.participantView = "participantView_not_null";
		}
		return this.participantView;
	}

	/**
	 * Setter of selectAffectedProjectView.
	 * 
	 * @param _ParticipantView
	 *            The selectAffectedProjectView to set.
	 */
	public void setParticipantView(String _participantView) {
		this.participantView = _participantView;
	}

	/**
	 * @return the webSessionService
	 */
	public WebSessionService getWebSessionService() {
		return this.webSessionService;
	}

	/**
	 * Setter of webSessionService.
	 * 
	 * @param _webSessionService
	 *            The webSessionService to set.
	 */
	public void setWebSessionService(WebSessionService _webSessionService) {
		this.webSessionService = _webSessionService;
	}

	/**
	 * Getter of cleanBean.
	 * 
	 * @return the cleanBean.
	 */
	public String getCleanBean() {
		this.participant = new Participant();
		this.cleanBean = "ok";
		return this.cleanBean;
	}

	/**
	 * Setter of cleanBean.
	 * 
	 * @param _cleanBean
	 *            The cleanBean to set.
	 */
	public void setCleanBean(String _cleanBean) {
		this.cleanBean = _cleanBean;
	}

	/**
	 * @return the visiblePopup
	 */
	public boolean isVisiblePopup() {
		return visiblePopup;
	}

	/**
	 * @param visiblePopup
	 *            the visiblePopup to set
	 */
	public void setVisiblePopup(boolean visiblePopup) {
		this.visiblePopup = visiblePopup;
	}

	/**
	 * @return the webPageService
	 */
	public WebCommonService getWebCommonService() {
		return webCommonService;
	}

	/**
	 * @param webPageService the webPageService to set
	 */
	public void setWebCommonService(WebCommonService webCommonService) {
		this.webCommonService = webCommonService;
	}
}
