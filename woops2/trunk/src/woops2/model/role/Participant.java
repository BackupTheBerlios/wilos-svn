package woops2.model.role;

import java.util.HashSet;
import java.util.Set;

import woops2.model.element.Element;

public class Participant extends Element {
	
	private Set<RoleDescriptor> rolesListForAProject ;
	
	public Participant()
	{
		this.rolesListForAProject = new HashSet<RoleDescriptor>();
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
            _roleDesc.getParticipants().add(this);
}

    /**
     * remove a RoleDescriptor
     * 
     * @param _roleDesc
     */
    public void removeFromRoleDescriptor(RoleDescriptor _roleDesc) {
            this.rolesListForAProject.remove(_roleDesc) ;
            _roleDesc.getParticipants().remove(this);
}

    /**
     * Empty the set of RoleDescriptor
     * 
     * @param _roleDesc
     */
    public void removeAllRoleDescriptors() {
            for(RoleDescriptor _roleD : this.rolesListForAProject){
            	_roleD.removeParticipant(this);
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
