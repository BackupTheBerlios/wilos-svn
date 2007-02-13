package wilos.presentation.web.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.wilosuser.ParticipantService;
import wilos.business.services.presentation.web.WebSessionService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;

/**
 * Managed-Bean link to participant_logging.jsp
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ConcreteRoleAffectationBean {

	private ConcreteRoleDescriptorService concreteRoleDescriptorService;
	
	private ParticipantService participantService;
	
	private WebSessionService webSessionService;
	
	private List<HashMap<String,Object>> concreteRolesDescriptorsList;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

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
	 * @param _participantService The participantService to set.
	 */
	public void setParticipantService(ParticipantService _participantService) {
		this.participantService = _participantService ;
	}

	/**
	 * Constructor.
	 * 
	 */
	public ConcreteRoleAffectationBean() {
		this.logger.debug("--- ConcreteRoleAffectationBean --- == creating ..." + this);
	}

	/**
	 * Getter of concreteRoleDescriptorService.
	 *
	 * @return the concreteRoleDescriptorService.
	 */
	public ConcreteRoleDescriptorService getConcreteRoleDescriptorService() {
		return this.concreteRoleDescriptorService ;
	}

	/**
	 * Setter of concreteRoleDescriptorService.
	 *
	 * @param _concreteRoleDescriptorService The concreteRoleDescriptorService to set.
	 */
	public void setConcreteRoleDescriptorService(ConcreteRoleDescriptorService _concreteRoleDescriptorService) {
		this.concreteRoleDescriptorService = _concreteRoleDescriptorService ;
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
//	public String saveRoleAction() {
//		String url = "participant";
//		//this.roleService.saveRoleDescriptor(this.roleDescriptor);
//		return url;
//	}
//
//	public void testTransactionActionListener(ActionEvent e) {
//		// this.participantManager.Test();
//	}
//
//	/**
//	 * Getter of rolesList.
//	 * 
//	 * @return the rolesList.
//	 */
//	public List<ConcreteRoleDescriptor> getRolesList() {
//		this.rolesList = new ArrayList<ConcreteRoleDescriptor>();
//		//rolesList.addAll(this.roleService.getRolesDescriptor());
//		this.logger.debug("roles list =" + this.rolesList);
//		return this.rolesList;
//	}
//
//	/**
//	 * Getter of roleDescriptor.
//	 * 
//	 * @return the roleDescriptor.
//	 */
//	public ConcreteRoleDescriptor getRoleDescriptor() {
//		return this.roleDescriptor;
//	}
//
//	/**
//	 * Setter of roleDescriptor.
//	 * 
//	 * @param _roleDescriptor
//	 *            The roleDescriptor to set.
//	 */
//	public void setRoleDescriptor(ConcreteRoleDescriptor _roleDescriptor) {
//		this.logger.debug("### Participant = " + _roleDescriptor + " ###");
//		this.roleDescriptor = _roleDescriptor;
//	}
//
//	/**
//	 * Getter of roleService.
//	 * 
//	 * @return the roleService.
//	 */
//	public RoleService getRoleService() {
//		return this.roleService;
//	}
//
//	/**
//	 * Setter of participantManager.
//	 * 
//	 * @param _participantManager
//	 *            The participantManager to set.
//	 */
//	public void setRoleService(RoleService _roleService) {
//		this.roleService = _roleService;
//	}
//
//	/**
//	 * Getter of project.
//	 * 
//	 * @return the project.
//	 */
//	public int getProject() {
//		return this.project;
//	}
//
//	/**
//	 * Setter of project.
//	 * 
//	 * @param _project
//	 *            The project to set.
//	 */
//	public void setProject(int _project) {
//		this.project = _project;
//	}
//
//	/**
//	 * Setter of rolesList.
//	 * 
//	 * @param _rolesList
//	 *            The rolesList to set.
//	 */
//	public void setRolesList(List<ConcreteRoleDescriptor> _rolesList) {
//		this.rolesList = _rolesList;
//	}
//
//	/**
//	 * Save roles of a participant
//	 * 
//	 */
//	public void saveParticipantRoles(){
//	FacesMessage message = new FacesMessage() ;
//	message.setSummary(bundle.getString("component.authentificationerror.loginError")) ;
//	message.setSeverity(FacesMessage.SEVERITY_ERROR) ;
//		/*HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
//		HttpSession sess = req.getSession() ;
//		String userid = (String) sess.getAttribute("wilosUser") ;*/
//		//WilosUser user = this.
//		//String user_login = user.getLogin();
//		//TODO Attentio nrecuperation de l'id en session et non plus de l'objet WilosUser
//		//TODO ROLES : ATTENTION ENREGISTRER L ID AU LIEU DU LOGIN !!! 
//		//this.roleService.saveParticipantRoles(this.getRolesParticipant(),user_login);
//	}
	
	/**
	 * Listener on the check action in the role checkboxes.
	 * @param newRole
	 */
	public void addConcreteRoleDescriptorChangeListener(ValueChangeEvent newRole){
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.getRemoteUser();
		String concreteRoleId = (String)((ConcreteRoleDescriptor)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("concreteRole")).getId();
		String concreteBDEId = (String)((ConcreteBreakdownElement)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("item")).getId();
		if( (Boolean)newRole.getOldValue() != (Boolean)newRole.getNewValue()){
			this.logger.debug("modif " + concreteBDEId + " : " + concreteRoleId + " = " + newRole.getNewValue());
		}
		/*this.concreteRolesDescriptorsForCurrentParticipant.put(concreteRoleId, (Boolean)newRole.getNewValue());
		this.concreteRolesDescriptorsForCBDE.put(concreteBDEId,concreteRoleId);*/
		
	}

	/**
	 * Getter of webSessionService.
	 *
	 * @return the webSessionService.
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

	/**
	 * Getter of concreteRolesDescriptorsList.
	 *
	 * @return the concreteRolesDescriptorsList.
	 */
	public List<HashMap<String, Object>> getConcreteRolesDescriptorsList() {
		String currentConcreteActivityId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("nodeId");
		ArrayList<ConcreteRoleDescriptor> globalCRD;
		
		if(this.concreteRolesDescriptorsList == null){
			globalCRD = (ArrayList<ConcreteRoleDescriptor>)this.concreteRoleDescriptorService.getAllConcreteRoleDescriptorsForProject((String)this.webSessionService.getAttribute(WebSessionService.PROJECT_ID));
			for(ConcreteRoleDescriptor concreteRD : globalCRD){
				Set<ConcreteActivity> globalCA = concreteRD.getSuperConcreteActivities();
				System.out.println("tarace");
				for(Iterator iter = globalCA.iterator(); iter.hasNext();){
					ConcreteActivity ca = (ConcreteActivity) iter.next() ;
					
				}
				
				
				/*for(ConcreteActivity currentActivity : globalCA){
					if(currentActivity.getId().equals("currentConcreteActivityId")){
						HashMap<String,Object> hm = new HashMap<String,Object>();
						hm.put("concreteId",concreteRD.getId());
						hm.put("concreteName",concreteRD.getConcreteName());
						hm.put("affected", new Boolean(true));
						hm.put("reference", currentActivity.getConcreteName());
						this.concreteRolesDescriptorsList.add(hm);
					}
				}*/
			}
			
		}
		return this.concreteRolesDescriptorsList ;
	}

	/**
	 * Setter of concreteRolesDescriptorsList.
	 *
	 * @param _concreteRolesDescriptorsList The concreteRolesDescriptorsList to set.
	 */
	public void setConcreteRolesDescriptorsList(List<HashMap<String, Object>> _concreteRolesDescriptorsList) {
		this.concreteRolesDescriptorsList = _concreteRolesDescriptorsList ;
	}
	
//
//	/**
//	 * Getter of rolesParticipant.
//	 * 
//	 * @return the rolesParticipant.
//	 */
//	public HashMap<String, Boolean> getRolesParticipant() {
//		ConcreteRoleDescriptor rd = null;
//		/*HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
//		HttpSession sess = req.getSession() ;
//		WilosUser user = (WilosUser) sess.getAttribute("wilosUser") ;
//		String user_login = user.getLogin();*/
//		
//		/*HashMap<ConcreteRoleDescriptor, Boolean> hashTemp = this.roleService
//				.getRolesForAParticipant(user_login);*/
//		/*for (Iterator iter = hashTemp.keySet().iterator(); iter.hasNext();) {
//			rd = (ConcreteRoleDescriptor) iter.next();
//			if(!this.rolesParticipant.containsKey(rd.getName()))
//				this.rolesParticipant.put(rd.getName(), hashTemp.get(rd));
//		}*/
//		return this.rolesParticipant;
//	}
//
//	/**
//	 * Setter of rolesParticipant.
//	 * 
//	 * @param _rolesParticipant
//	 *            The rolesParticipant to set.
//	 */
//	public void setRolesParticipant(HashMap<String, Boolean> _rolesParticipant) {
//		this.rolesParticipant = _rolesParticipant;
//	}
//
//	/**
//	 * Getter of keysRolesParticipant.
//	 * 
//	 * @return the keysRolesParticipant.
//	 */
//	//TODO Methode getKeysRolesParticipant a TESTER !!!
//	public List<String> getKeysRolesParticipant() {
//		HashSet<String> os = new HashSet<String>((Set<String>) this
//				.getRolesParticipant().keySet());
//		for (Iterator iter = os.iterator(); iter.hasNext();) {
//			String roleName = (String) iter.next();
//			if(!this.keysRolesParticipant.contains(roleName))
//				this.keysRolesParticipant.add(roleName);
//		}
//		return this.keysRolesParticipant;
//	}
//
//	/**
//	 * Setter of keysRolesParticipant.
//	 * 
//	 * @param _keysRolesParticipant
//	 *            The keysRolesParticipant to set.
//	 */
//	public void setKeysRolesParticipant(List<String> _keysRolesParticipant) {
//		this.keysRolesParticipant = _keysRolesParticipant;
//	}
}
