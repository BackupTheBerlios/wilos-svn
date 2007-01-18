package wilos.presentation.web.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import wilos.business.services.role.RoleService;
import wilos.model.misc.wilosuser.WilosUser;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * Managed-Bean link to participant_logging.jsp
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class RoleBean {

	private List<RoleDescriptor> rolesList;

	private HashMap<String, Boolean> rolesParticipant = new HashMap<String, Boolean>();

	private List<String> keysRolesParticipant = new ArrayList<String>();

	private RoleDescriptor roleDescriptor ;
	
	private RoleService roleService;
	
	private int project=1;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Constructor.
	 * 
	 */
	public RoleBean() {
		this.logger.debug("--- RoleBean --- == creating ..." + this);
		this.roleDescriptor = new RoleDescriptor();
	}

	/**
	 * Method for saving participant data from form
	 * 
	 * @return
	 */
	public String saveRoleAction() {
		String url = "participant";
		this.roleService.saveRoleDescriptor(this.roleDescriptor);
		return url;
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
		rolesList.addAll(this.roleService.getRolesDescriptor());
		this.logger.debug("roles list =" + this.rolesList);
		return this.rolesList;
	}

	/**
	 * Getter of roleDescriptor.
	 * 
	 * @return the roleDescriptor.
	 */
	public RoleDescriptor getRoleDescriptor() {
		return this.roleDescriptor;
	}

	/**
	 * Setter of roleDescriptor.
	 * 
	 * @param _roleDescriptor
	 *            The roleDescriptor to set.
	 */
	public void setRoleDescriptor(RoleDescriptor _roleDescriptor) {
		this.logger.debug("### Participant = " + _roleDescriptor + " ###");
		this.roleDescriptor = _roleDescriptor;
	}

	/**
	 * Getter of roleService.
	 * 
	 * @return the roleService.
	 */
	public RoleService getRoleService() {
		return this.roleService;
	}

	/**
	 * Setter of participantManager.
	 * 
	 * @param _participantManager
	 *            The participantManager to set.
	 */
	public void setRoleService(RoleService _roleService) {
		this.roleService = _roleService;
	}

	/**
	 * Getter of project.
	 * 
	 * @return the project.
	 */
	public int getProject() {
		return this.project;
	}

	/**
	 * Setter of project.
	 * 
	 * @param _project
	 *            The project to set.
	 */
	public void setProject(int _project) {
		this.project = _project;
	}

	/**
	 * Setter of rolesList.
	 * 
	 * @param _rolesList
	 *            The rolesList to set.
	 */
	public void setRolesList(List<RoleDescriptor> _rolesList) {
		this.rolesList = _rolesList;
	}

	/**
	 * Save roles of a participant
	 * 
	 */
	public void saveParticipantRoles(){
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		WilosUser user = (WilosUser) sess.getAttribute("wilosUser") ;
		String user_login = user.getLogin();
		//TODO ROLES : ATTENTION ENREGISTRER L ID AU LIEU DU LOGIN !!! 
		this.roleService.saveParticipantRoles(this.getRolesParticipant(),user_login);
	}
	
	/**
	 * Listener on the check action in the role checkboxes.
	 * @param newRole
	 */
	public void addRoleChangeListener(ValueChangeEvent newRole){
		String roleName = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("roleD");
		this.rolesParticipant.put(roleName,(Boolean)newRole.getNewValue());
	}

	/**
	 * Getter of rolesParticipant.
	 * 
	 * @return the rolesParticipant.
	 */
	public HashMap<String, Boolean> getRolesParticipant() {
		RoleDescriptor rd = null;
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest() ;
		HttpSession sess = req.getSession() ;
		WilosUser user = (WilosUser) sess.getAttribute("wilosUser") ;
		String user_login = user.getLogin();
		
		HashMap<RoleDescriptor, Boolean> hashTemp = this.roleService
				.getRolesForAParticipant(user_login);
		for (Iterator iter = hashTemp.keySet().iterator(); iter.hasNext();) {
			rd = (RoleDescriptor) iter.next();
			if(!this.rolesParticipant.containsKey(rd.getName()))
				this.rolesParticipant.put(rd.getName(), hashTemp.get(rd));
		}
		return this.rolesParticipant;
	}

	/**
	 * Setter of rolesParticipant.
	 * 
	 * @param _rolesParticipant
	 *            The rolesParticipant to set.
	 */
	public void setRolesParticipant(HashMap<String, Boolean> _rolesParticipant) {
		this.rolesParticipant = _rolesParticipant;
	}

	/**
	 * Getter of keysRolesParticipant.
	 * 
	 * @return the keysRolesParticipant.
	 */
	//TODO Methode getKeysRolesParticipant a TESTER !!!
	public List<String> getKeysRolesParticipant() {
		HashSet<String> os = new HashSet<String>((Set<String>) this
				.getRolesParticipant().keySet());
		for (Iterator iter = os.iterator(); iter.hasNext();) {
			String roleName = (String) iter.next();
			if(!this.keysRolesParticipant.contains(roleName))
				this.keysRolesParticipant.add(roleName);
		}
		return this.keysRolesParticipant;
	}

	/**
	 * Setter of keysRolesParticipant.
	 * 
	 * @param _keysRolesParticipant
	 *            The keysRolesParticipant to set.
	 */
	public void setKeysRolesParticipant(List<String> _keysRolesParticipant) {
		this.keysRolesParticipant = _keysRolesParticipant;
	}
}
