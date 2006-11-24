package woops2.model.participant;

import java.util.Set;

import woops2.model.role.RoleDescriptor;

/**
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 *
 * This class represents a participant of a project
 * This type of user can work on projects and  
 * select a role from the process defined in relation
 * with a project
 *
 */
public class Participant {
	
	private String name;
	private String firstname;
	private String emailAddress;
	private String login;
	private String password;
	
	private Set<RoleDescriptor> rolesListForAProject;
	
	
	/**
	 * Getter of emailAddress.
	 *
	 * @return the emailAddress.
	 */
	public String getEmailAddress() {
		return this.emailAddress ;
	}

	/**
	 * Setter of emailAddress.
	 *
	 * @param _emailAddress The emailAddress to set.
	 */
	public void setEmailAddress(String _emailAddress) {
		this.emailAddress = _emailAddress ;
	}

	/**
	 * Getter of firstname.
	 *
	 * @return the firstname.
	 */
	public String getFirstname() {
		return this.firstname ;
	}

	/**
	 * Setter of firstname.
	 *
	 * @param _firstname The firstname to set.
	 */
	public void setFirstname(String _firstname) {
		this.firstname = _firstname ;
	}

	/**
	 * Getter of login.
	 *
	 * @return the login.
	 */
	public String getLogin() {
		return this.login ;
	}

	/**
	 * Setter of login.
	 *
	 * @param _login The login to set.
	 */
	public void setLogin(String _login) {
		this.login = _login ;
	}

	/**
	 * Getter of name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return this.name ;
	}

	/**
	 * Setter of name.
	 *
	 * @param _name The name to set.
	 */
	public void setName(String _name) {
		this.name = _name ;
	}

	/**
	 * Getter of password.
	 *
	 * @return the password.
	 */
	public String getPassword() {
		return this.password ;
	}

	/**
	 * Setter of password.
	 *
	 * @param _password The password to set.
	 */
	public void setPassword(String _password) {
		this.password = _password ;
	}

	/**
	 * Getter of rolesListForAProject.
	 *
	 * @return the rolesListForAProject.
	 */
	public Set<RoleDescriptor> getRolesListForAProject() {
		return this.rolesListForAProject ;
	}
	
	/**
	 * Setter of rolesListForAProject.
	 *
	 * @param _rolesListForAProject The rolesListForAProject to set.
	 */
	public void setRolesListForAProject(Set<RoleDescriptor> _rolesListForAProject) {
		this.rolesListForAProject = _rolesListForAProject ;
	}
	
	/**
	 * adds a role descriptor to the Set
	 *
	 * @param _roleDescriptor
	 */
	public void addToRoleDescriptor(RoleDescriptor _roleDesc) {
		this.rolesListForAProject.add(_roleDesc);
		//_roleDesc.getParticipant().add(this);
	}
	/**
	 * remove a RoleDescriptor
	 *
	 * @param _roleDesc
	 */
	public void removeFromRoleDescriptor(RoleDescriptor _roleDesc){
		this.rolesListForAProject.remove(_roleDesc);
		//_roleDesc..getParticipant().remove(this);
	}
	
	
	/**
	 * Empty the set of RoleDescriptor
	 *
	 * @param _roleDesc
	 */
	public void removeAllRoleDescriptors(){
		for (RoleDescriptor _roleD : this.rolesListForAProject) {
			
//PAS BON _roleD.removeFromParticipant(this);
		}
		this.rolesListForAProject.clear();
	}
	
	
	/**
	 * Removes all Participants from a RoleDescriptor
	 *
	 */
	public void removeFromAllRoleDescriptor(){
		for(RoleDescriptor _roleD : this.rolesListForAProject){
			this.removeRoleDescriptor(_roleD);
		}
	}
}
