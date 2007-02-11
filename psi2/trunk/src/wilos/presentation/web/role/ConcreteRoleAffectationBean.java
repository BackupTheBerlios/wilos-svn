package wilos.presentation.web.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.misc.concreterole.ConcreteRoleDescriptorService;
import wilos.business.services.misc.role.RoleService;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.presentation.web.project.ProjectAdvancementBean;

/**
 * Managed-Bean link to participant_logging.jsp
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class ConcreteRoleAffectationBean {

	private ProjectAdvancementBean projectAdvancementBean;
	
	/*private List<ConcreteRoleDescriptor> rolesList;

	private HashMap<String, Boolean> rolesParticipant = new HashMap<String, Boolean>();

	private List<String> keysRolesParticipant = new ArrayList<String>();

	private ConcreteRoleDescriptor roleDescriptor ;
	
	private RoleService roleService;
	
	private int project=1;*/
	
	private ConcreteRoleDescriptorService concreteRoleDescriptorService;
	
	private Hashtable<Object,Boolean> itemsToShow = new Hashtable<Object,Boolean>();
	
	private List<Object> itemsKeys = new ArrayList<Object>();

	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	private List<ConcreteRoleDescriptor> concreteRolesDescriptorsList;


	/**
	 * Constructor.
	 * 
	 */
	public ConcreteRoleAffectationBean() {
		this.logger.debug("--- ConcreteRoleAffectationBean --- == creating ..." + this);
		this.concreteRolesDescriptorsList = new ArrayList<ConcreteRoleDescriptor>();
	}

	/**
	 * Getter of projectAdvancementBean.
	 *
	 * @return the projectAdvancementBean.
	 */
	public ProjectAdvancementBean getProjectAdvancementBean() {
		return this.projectAdvancementBean ;
	}

	/**
	 * Setter of projectAdvancementBean.
	 *
	 * @param _projectAdvancementBean The projectAdvancementBean to set.
	 */
	public void setProjectAdvancementBean(ProjectAdvancementBean _projectAdvancementBean) {
		this.projectAdvancementBean = _projectAdvancementBean ;
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
	 * Getter of concreteRolesDescriptorsList.
	 *
	 * @return the concreteRolesDescriptorsList.
	 */
	public List<ConcreteRoleDescriptor> getConcreteRolesDescriptorsList() {
		this.concreteRolesDescriptorsList = this.concreteRoleDescriptorService.getAllConcreteRoleDescriptorsForProject(this.projectAdvancementBean.getProject().getId());
		return this.concreteRolesDescriptorsList ;
	}

	/**
	 * Setter of concreteRolesDescriptorsList.
	 *
	 * @param _concreteRolesDescriptorsList The concreteRolesDescriptorsList to set.
	 */
	public void setConcreteRolesDescriptorsList(List<ConcreteRoleDescriptor> _concreteRolesDescriptorsList) {
		this.concreteRolesDescriptorsList = _concreteRolesDescriptorsList ;
	}

	/**
	 * Getter of itemsToShow.
	 *
	 * @return the itemsToShow.
	 */
	public Hashtable<Object, Boolean> getItemsToShow() {
		this.itemsToShow.clear();
		for(Object obj : this.projectAdvancementBean.getDisplayContent()){
			if(!(obj instanceof ConcreteTaskDescriptor)){
				this.itemsToShow.put(((ConcreteActivity)obj).getConcreteName(),new Boolean(true));
			}
			else{
				this.itemsToShow.put(((ConcreteTaskDescriptor)obj).getConcreteName(), new Boolean(false));
			}
				
		}
		
		return this.itemsToShow ;
	}

	/**
	 * Setter of itemsToShow.
	 *
	 * @param _itemsToShow The itemsToShow to set.
	 */
	public void setItemsToShow(Hashtable<Object, Boolean> _itemsToShow) {
		this.itemsToShow = _itemsToShow ;
	}

	/**
	 * Getter of itemsKeys.
	 *
	 * @return the itemsKeys.
	 */
	public List<Object> getItemsKeys() {
		this.itemsKeys.clear();
		for(Object obj : this.projectAdvancementBean.getDisplayContent()){
			this.itemsKeys.add(obj);				
		}
		this.getItemsToShow();
		return this.itemsKeys;
	}

	/**
	 * Setter of itemsKeys.
	 *
	 * @param _itemsKeys The itemsKeys to set.
	 */
	public void setItemsKeys(List<Object> _itemsKeys) {
		this.itemsKeys = _itemsKeys ;
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
//		/*HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
//		HttpSession sess = req.getSession() ;
//		String userid = (String) sess.getAttribute("wilosUser") ;*/
//		//WilosUser user = this.
//		//String user_login = user.getLogin();
//		//TODO Attentio nrecuperation de l'id en session et non plus de l'objet WilosUser
//		//TODO ROLES : ATTENTION ENREGISTRER L ID AU LIEU DU LOGIN !!! 
//		//this.roleService.saveParticipantRoles(this.getRolesParticipant(),user_login);
//	}
//	
//	/**
//	 * Listener on the check action in the role checkboxes.
//	 * @param newRole
//	 */
//	public void addRoleChangeListener(ValueChangeEvent newRole){
//		String roleName = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("roleD");
//		this.rolesParticipant.put(roleName,(Boolean)newRole.getNewValue());
//	}
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
