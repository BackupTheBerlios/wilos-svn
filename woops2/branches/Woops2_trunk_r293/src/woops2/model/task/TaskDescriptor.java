
package woops2.model.task ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.role.RoleDescriptor ;
import woops2.model.workbreakdownelement.WorkBreakdownElement ;

/**
 * 
 * A Task Descriptor is a Descriptor and Work Breakdown Element that represents a proxy for a Task
 * in the context of one specific Activity. Every breakdown structure can define different
 * relationships of Task Descriptors to Work Product Descriptors and Role Descriptors. Therefore one
 * Task can be represented by many Task Descriptors each within the context of an Activity with its
 * own set of relationships.
 * 
 * @author Sebastien BALARD
 * @author eperico
 * 
 */
public class TaskDescriptor extends WorkBreakdownElement implements Cloneable {

	/**
	 * the attached taskDefinition
	 */
	private TaskDefinition taskDefinition ;

	/**
	 * The additional roles of the role
	 */
	private Set<RoleDescriptor> additionalRoles ;

	/**
	 * The main role of the taskDefinition
	 */
	private RoleDescriptor mainRole ;

	/**
	 * Default constructor.
	 */
	public TaskDescriptor() {
		super() ;
		this.additionalRoles = new HashSet<RoleDescriptor>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public TaskDescriptor clone() throws CloneNotSupportedException {
		TaskDescriptor taskDescriptor = new TaskDescriptor() ;
		taskDescriptor.copy(this) ;
		return taskDescriptor ;
	}

	/**
	 * Copy the _roleDescriptor into this.
	 */
	protected void copy(final TaskDescriptor _taskDescriptor) {
		super.copy(_taskDescriptor) ;
		this.setAdditionalRoles(_taskDescriptor.getAdditionalRoles()) ;
		this.setTaskDefinition(_taskDescriptor.getTaskDefinition()) ;
		this.setMainRole(_taskDescriptor.getMainRole()) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.workbreakdownelement.WorkBreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof TaskDescriptor == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		TaskDescriptor taskDescriptor = (TaskDescriptor) obj ;
		return new EqualsBuilder().appendSuper(super.equals(taskDescriptor)).append(this.taskDefinition, taskDescriptor.taskDefinition).append(
				this.additionalRoles, taskDescriptor.additionalRoles).append(this.mainRole, taskDescriptor.mainRole).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.workbreakdownelement.WorkBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.taskDefinition).append(this.mainRole).toHashCode() ;
	}

	/*
	 * relation between TaskDescriptor and TaskDefinition
	 * 
	 */

	/**
	 * Attach a taskDescriptor to a taskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void addTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = _taskDefinition ;
		_taskDefinition.getTaskDescriptors().add(this) ;
	}

	/**
	 * Detach a taskDescriptor to its taskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void removeTaskDefinition(TaskDefinition _taskDefinition) {
		_taskDefinition.getTaskDescriptors().remove(this) ;
		this.taskDefinition = null ;
	}

	/*
	 * relation primaryTask between TaskDescriptor and RoleDescriptor
	 */

	/**
	 * Attach a taskDescriptor to a roleDescriptor
	 * 
	 * @param _roleDescriptor
	 */
	public void addMainRole(RoleDescriptor _roleDescriptor) {
		this.mainRole = _roleDescriptor ;
		_roleDescriptor.getPrimaryTasks().add(this) ;
	}

	/**
	 * Detach a taskDescriptor to its roleDescriptor
	 * 
	 * @param _roleDescriptor
	 */
	public void removeMainRole(RoleDescriptor _roleDescriptor) {
		_roleDescriptor.getPrimaryTasks().remove(this) ;
		this.mainRole = null ;
	}

	/*
	 * relation additionalTasks between TaskDescriptor and RoleDescriptor
	 */

	/**
	 * @param _roleDescriptor
	 */
	public void addAdditionalRole(RoleDescriptor _roleDescriptor) {
		this.additionalRoles.add(_roleDescriptor) ;
		_roleDescriptor.getAdditionalTasks().add(this) ;
	}

	/**
	 * @param _roleDescriptor
	 */
	public void removeAdditionalRole(RoleDescriptor _roleDescriptor) {
		_roleDescriptor.getAdditionalTasks().remove(this) ;
		this.additionalRoles.remove(_roleDescriptor) ;
	}

	/**
	 * 
	 * @param _role
	 */
	public void addAllAdditionalRoles(Set<RoleDescriptor> _role) {
		for(RoleDescriptor role : _role){
			role.addAdditionalTask(this) ;
		}
	}

	/**
	 * Remove from a roleDescriptor all its taskDescriptors
	 */
	public void removeAllAdditionalRoles() {
		for(RoleDescriptor tmp : this.getAdditionalRoles()){
			tmp.getAdditionalTasks().remove(this) ;
		}
		this.getAdditionalRoles().clear() ;
	}

	/**
	 * Getter of taskDefinition.
	 * 
	 * @return the taskDefinition.
	 */
	public TaskDefinition getTaskDefinition() {
		return this.taskDefinition ;
	}

	/**
	 * Setter of taskDefinition.
	 * 
	 * @param _taskDefinition
	 *            The taskDefinition to set.
	 */
	protected void setTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = _taskDefinition ;
	}

	/**
	 * Getter of additionalRoles.
	 * 
	 * @return the additionalRoles.
	 */
	public Set<RoleDescriptor> getAdditionalRoles() {
		return this.additionalRoles ;
	}

	/**
	 * Setter of additionalRoles.
	 * 
	 * @param _additionalRoles
	 *            The additionalRoles to set.
	 */
	@ SuppressWarnings ("unused")
	private void setAdditionalRoles(Set<RoleDescriptor> _additionalRoles) {
		this.additionalRoles = _additionalRoles ;
	}

	/**
	 * Getter of mainRole.
	 * 
	 * @return the mainRole.
	 */
	public RoleDescriptor getMainRole() {
		return this.mainRole ;
	}

	/**
	 * Setter of mainRole.
	 * 
	 * @param _mainRole
	 *            The mainRole to set.
	 */
	public void setMainRole(RoleDescriptor _mainRole) {
		this.mainRole = _mainRole ;
	}
}
