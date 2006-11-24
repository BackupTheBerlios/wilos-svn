
package woops2.model.role ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.breakdownelement.BreakdownElement ;
import woops2.model.task.TaskDescriptor ;

/**
 * @author deder
 * 
 * A RoleDefinition Descriptor represents a RoleDefinition in the context of one specific Activity.
 * Every breakdown structure can define different relationships of RoleDefinition Descriptors to
 * TaskDefinition Descriptors and Work Product Descriptors. Therefore one RoleDefinition can be
 * represented by many RoleDefinition Descriptors each within the context of an Activity with its
 * own set of relationships.
 * 
 */
public class RoleDescriptor extends BreakdownElement implements Cloneable {

	private RoleDefinition roleDefinition ;

	/**
	 * The main tasks of the roleDefinition
	 */
	private Set<TaskDescriptor> primaryTasks ;

	/**
	 * The additional tasks of the roleDefinition
	 */
	private Set<TaskDescriptor> additionalTasks ;

	/**
	 * The participants of the Participant
	 */
	// private Set<Participant> participants ;TODO
	/**
	 * Getter of participant.
	 * 
	 * @return the participants.
	 */
	// public Set<Participant> getParticipant() {
	// return this.participants ;TODO
	// }
	/**
	 * Setter of participants.
	 * 
	 * @param _participants
	 *            The participants to set.
	 */
	@ SuppressWarnings ("unused")
	// private void setParticipant(Set<Participant> _participants) {
	// this.participants = _participants ;TODO
	// }
	public RoleDescriptor() {
		super() ;
		this.primaryTasks = new HashSet<TaskDescriptor>() ;
		this.additionalTasks = new HashSet<TaskDescriptor>() ;
		// this.participants = new HashSet<Participant>();TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public RoleDescriptor clone() throws CloneNotSupportedException {
		RoleDescriptor roleDescriptor = new RoleDescriptor() ;
		roleDescriptor.copy(this) ;
		return roleDescriptor ;
	}

	/**
	 * Copy the _roleDescriptor into this.
	 */
	protected void copy(final RoleDescriptor _roleDescriptor) {
		super.copy(_roleDescriptor) ;
		this.setAdditionalTasks(_roleDescriptor.getAdditionalTasks()) ;
		this.setPrimaryTasks(_roleDescriptor.getPrimaryTasks()) ;
		this.setRoleDefinition(_roleDescriptor.getRoleDefinition()) ;
		// this.setParticipants(_roleDescriptor.getParticipants());TODO
	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if(obj instanceof RoleDescriptor == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}

		RoleDescriptor roleDescriptor = (RoleDescriptor) obj ;
		return new EqualsBuilder().appendSuper(super.equals(roleDescriptor)).append(this.roleDefinition, roleDescriptor.roleDefinition).isEquals() ;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.roleDefinition).toHashCode() ;
	}

	/**
	 * relation between RoleDescriptor and RoleDefinition
	 */

	/**
	 * add a RoleDefinition to the Set
	 * 
	 * @param _role
	 */
	public void addRoleDefinition(RoleDefinition _role) {
		this.roleDefinition = _role ;
		_role.getRoleDescriptors().add(this) ;
	}

	/**
	 * remove a RoleDefinition
	 * 
	 * @param _role
	 */
	public void removeRoleDefinition(RoleDefinition _role) {
		_role.getRoleDescriptors().remove(this) ;
		this.roleDefinition = null ;
	}

	/**
	 * relation mainrole between RoleDescriptor and TaskDescriptor
	 */

	/**
	 * add a taskDescritor to the Set
	 * 
	 * @param _task
	 */
	public void addPrimaryTask(TaskDescriptor _task) {
		this.primaryTasks.add(_task) ;
		_task.setMainRole(this) ;
	}

	/**
	 * remove a taskDescriptor
	 * 
	 * @param _task
	 */
	public void removePrimaryTask(TaskDescriptor _task) {
		_task.setMainRole(null) ;
		this.primaryTasks.remove(_task) ;
	}

	/**
	 * 
	 * @param _role
	 */
	public void addAllPrimaryTasks(Set<TaskDescriptor> _role) {
		for(TaskDescriptor _role1 : _role){
			_role1.addMainRole(this) ;
		}
	}

	/**
	 * Remove from an RoleDescriptor all its primaryTask
	 * 
	 */
	public void removeAllPrimaryTasks() {
		for(TaskDescriptor _task : this.primaryTasks){
			_task.setMainRole(null);
		}
		this.primaryTasks.clear() ;
	}

	/**
	 * relation additionnalRoles between RoleDescriptor and TaskDescriptor
	 */

	/**
	 * add a TaskDescriptor to the Set
	 * 
	 * @param _task
	 */
	public void addAdditionalTask(TaskDescriptor _task) {
		this.additionalTasks.add(_task) ;
		_task.getAdditionalRoles().add(this) ;
	}

	/**
	 * remove a TaskDescriptor
	 * 
	 * @param _task
	 */
	public void removeAdditionalTask(TaskDescriptor _task) {
		_task.getAdditionalRoles().remove(this) ;
		this.additionalTasks.remove(_task) ;
	}

	/**
	 * 
	 * @param _role
	 */
	public void addAllAdditionalTasks(Set<TaskDescriptor> _task) {
		for(TaskDescriptor task : _task){
			task.addAdditionalRole(this) ;
		}
	}

	/**
	 * remove all the roleDescriptors
	 * 
	 */
	public void removeAllAdditionalTasks() {
		for(TaskDescriptor _task : this.additionalTasks){
			_task.removeAdditionalRole(this) ;
		}
		this.additionalTasks.clear() ;
	}

	// public void addToParticpant(Participant _participant) {TODO
	// this.participants.add(_participant);
	// _participant.getRolesListForAProject().add(this);
	// }
	//
	//
	// public void removeFromParticipant(Participant _participant) {
	// _participant.getRolesListForAProject().remove(this);
	// this.additionalTasks.remove(_participant);
	// }
	//
	//
	// public void removeAllParticipant() {
	// for (Participant _participant : this.participants) {
	// _participant.removeFromRoleDescriptor(this);
	// }
	// this.participants.clear();
	// }
	//
	//
	// public void removeFromAllParticipant() {
	// for (Participant _participant : this.participants) {
	// this.removeFromParticipant(_participant);
	// }
	// }

	/**
	 * Getter of roleDefinition.
	 * 
	 * @return the roleDefinition.
	 */
	public RoleDefinition getRoleDefinition() {
		return this.roleDefinition ;
	}

	/**
	 * Setter of roleDefinition.
	 * 
	 * @param _roleDefinition
	 *            The roleDefinition to set.
	 */
	protected void setRoleDefinition(RoleDefinition _roleDefinition) {
		this.roleDefinition = _roleDefinition ;
	}

	/**
	 * Getter of primaryTasks.
	 * 
	 * @return the primaryTasks.
	 */
	@ SuppressWarnings ("unused")
	public Set<TaskDescriptor> getPrimaryTasks() {
		return this.primaryTasks ;
	}

	/**
	 * Setter of primaryTasks.
	 * 
	 * @param _primaryTasks
	 *            The primaryTasks to set.
	 */
	@ SuppressWarnings ("unused")
	private void setPrimaryTasks(Set<TaskDescriptor> _primaryTasks) {
		this.primaryTasks = _primaryTasks ;
	}

	/**
	 * Getter of additionalTasks.
	 * 
	 * @return the additionalTasks.
	 */
	public Set<TaskDescriptor> getAdditionalTasks() {
		return this.additionalTasks ;
	}

	/**
	 * Setter of additionalTasks.
	 * 
	 * @param _additionalTasks
	 *            The additionalTasks to set.
	 */
	@ SuppressWarnings ("unused")
	private void setAdditionalTasks(Set<TaskDescriptor> _additionalTasks) {
		this.additionalTasks = _additionalTasks ;
	}

}
