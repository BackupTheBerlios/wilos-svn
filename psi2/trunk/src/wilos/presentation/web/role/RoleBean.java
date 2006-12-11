package wilos.presentation.web.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.icesoft.faces.component.ext.HtmlDataTable;
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.HtmlSelectBooleanCheckbox;
import com.icesoft.faces.component.ext.UIColumns;

import wilos.business.services.role.RoleService;
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
		this.roleService.saveParticipantRoles(this.getRolesParticipant());
	}
	
	public void addRoleChangeListener(ValueChangeEvent newRole)
	{
		HtmlSelectBooleanCheckbox checkBox = (HtmlSelectBooleanCheckbox)newRole.getComponent();
		HtmlPanelGroup hpg = (HtmlPanelGroup)checkBox.getParent();
		UIColumns column = (UIColumns)hpg.getParent();
		String roleName = column.getVar();
		this.rolesParticipant.put(roleName,(Boolean)newRole.getNewValue());
		System.out.println(roleName);
	}

	/**
	 * Getter of rolesParticipant.
	 * 
	 * @return the rolesParticipant.
	 */
	public HashMap<String, Boolean> getRolesParticipant() {
		RoleDescriptor rd = null;
		HashMap<RoleDescriptor, Boolean> hashTemp = this.roleService
				.getRolesForAParticipant();
		for (Iterator iter = hashTemp.keySet().iterator(); iter.hasNext();) {
			rd = (RoleDescriptor) iter.next();
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
	public List<String> getKeysRolesParticipant() {
		HashSet<String> os = new HashSet<String>((Set<String>) this
				.getRolesParticipant().keySet());
		for (Iterator iter = os.iterator(); iter.hasNext();) {
			this.keysRolesParticipant.add((String) iter.next());
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
