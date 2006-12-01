
package woops2.model.wilosuser ;

import java.util.Set ;

import woops2.model.role.RoleDescriptor ;

/**
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 * 
 * This class represents a participant of a project This type of user can work on projects and
 * select a role from the process defined in relation with a project
 * 
 */
public class Participant extends WilosUser {

	private Set<RoleDescriptor> rolesListForAProject ;

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
	 * @param _rolesListForAProject
	 *            The rolesListForAProject to set.
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
		this.rolesListForAProject.add(_roleDesc) ;
		// _roleDesc.getParticipant().add(this);
	}

	/**
	 * remove a RoleDescriptor
	 * 
	 * @param _roleDesc
	 */
	public void removeFromRoleDescriptor(RoleDescriptor _roleDesc) {
		this.rolesListForAProject.remove(_roleDesc) ;
		// _roleDesc..getParticipant().remove(this);
	}

	/**
	 * Remove all the role descriptors
	 * 
	 */
	public void removeAllRoleDescriptors() {
		for(RoleDescriptor _roleD : this.rolesListForAProject){
			// _roleD.removeFromParticipant(this);
		}
		this.rolesListForAProject.clear() ;
	}

	/**
	 * Removes all Participants from a RoleDescriptor
	 * 
	 */
	public void removeFromAllRoleDescriptor() {
		for(RoleDescriptor _roleD : this.rolesListForAProject){
			this.removeFromRoleDescriptor(_roleD) ;
		}
	}
}
