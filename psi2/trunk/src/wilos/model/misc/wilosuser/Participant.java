
package wilos.model.misc.wilosuser ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.misc.project.Project;
import wilos.model.spem2.role.RoleDescriptor ;

/**
 * This class represents a participant of a project This type of user can work on projects and
 * select a role from the process defined in relation with a project
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class Participant extends WilosUser implements Cloneable {

	private Set<RoleDescriptor> rolesListForAProject ;
	
	private Set<Project> affectedProjectList;

	public Participant() {
		super() ;
		rolesListForAProject = new HashSet<RoleDescriptor>() ;
		affectedProjectList = new HashSet<Project>() ;
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
		_roleDesc.getParticipants().add(this) ;
	}

	/**
	 * remove a RoleDescriptor
	 * 
	 * @param _roleDesc
	 */
	public void removeFromRoleDescriptor(RoleDescriptor _roleDesc) {
		this.rolesListForAProject.remove(_roleDesc) ;
		_roleDesc.getParticipants().remove(this) ;
	}

	/**
	 * Remove all the role descriptors
	 * 
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

	/**
	 * add participant to project
	 * 
	 * @param project
	 * 				the project to add to
	 */
	public void addToProject(Project project) {
		this.affectedProjectList.add(project) ;
		project.getParticipants().add(this) ;
	}
	
	/**
	 * remove a participant from a project 
	 * 
	 * @param project
	 * 			the project to remove from
	 */
	public void removeFromProject(Project project) {
		this.affectedProjectList.remove(project) ;
		project.getParticipants().remove(this) ;
	}

	/**
	 * remove the participant from all the project
	 * 
	 */
	public void removeAllProject() {
		for(Project project : this.affectedProjectList){
			project.removeFromParticipant(this) ;
		}
		this.affectedProjectList.clear() ;
	}
	
	/**
	 * remove all the project from the list
	 *
	 */
	public void removeFromAllProject() {
		for(Project project : this.affectedProjectList){
			this.removeFromProject(project) ;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Participant clone() throws CloneNotSupportedException {
		Participant participant = new Participant() ;
		participant.copy(this) ;
		return participant ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _administrator
	 *            The administrator to copy.
	 */
	protected void copy(final Participant _participant) {
		super.copy(_participant) ;
		this.setRolesListForAProject(_participant.getRolesListForAProject()) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof Participant == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		Participant participant = (Participant) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(participant)).append(this.rolesListForAProject, participant.rolesListForAProject).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.rolesListForAProject).toHashCode() ;
	}

	/**
	 * Getter of affectedProjectList.
	 *
	 * @return the affectedProjectList.
	 */
	public Set<Project> getAffectedProjectList() {
		return this.affectedProjectList ;
	}

	/**
	 * Setter of affectedProjectList.
	 *
	 * @param _affectedProjectList The affectedProjectList to set.
	 */
	public void setAffectedProjectList(Set<Project> _affectedProjectList) {
		this.affectedProjectList = _affectedProjectList ;
	}
	
}
