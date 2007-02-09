
package wilos.model.spem2.task ;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

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
	 * The corresponding concrete task descriptors.
	 */
	private Set<ConcreteTaskDescriptor> concreteTaskDescriptors;

	/**
	 * Default constructor.
	 */
	public TaskDescriptor() {
		super() ;
		this.additionalRoles = new HashSet<RoleDescriptor>() ;
		this.concreteTaskDescriptors = new HashSet<ConcreteTaskDescriptor>();
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
		this.setConcreteTaskDescriptors(_taskDescriptor.getConcreteTaskDescriptors());
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
				this.additionalRoles, taskDescriptor.additionalRoles).append(this.mainRole, taskDescriptor.mainRole).append(this.concreteTaskDescriptors, taskDescriptor.concreteTaskDescriptors).isEquals() ;
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
	 * relation between TaskDescriptor and ConcreteTaskDescriptor.
	 *
	 */

	/**
	 * @param _roleDescriptor
	 */
	public void addConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor) {
		this.concreteTaskDescriptors.add(_concreteTaskDescriptor) ;
		_concreteTaskDescriptor.addTaskDescriptor(this) ;
	}

	/**
	 * @param _roleDescriptor
	 */
	public void removeConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskDescriptor) {
		_concreteTaskDescriptor.removeTaskDescriptor(this) ;
		this.concreteTaskDescriptors.remove(_concreteTaskDescriptor) ;
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
	 *
	 * @param _ctd
	 */
	public void addAllConcreteTaskDescriptors(Set<ConcreteTaskDescriptor> _ctd) {
		for(ConcreteTaskDescriptor tmp : _ctd){
			tmp.addTaskDescriptor(this) ;
		}
	}

	/**
	 * Remove from a roleDescriptor all its taskDescriptors
	 */
	public void removeConcreteTaskDescriptors() {
		for(ConcreteTaskDescriptor tmp : this.getConcreteTaskDescriptors()){
			tmp.setTaskDescriptor(null) ;
		}
		this.getConcreteTaskDescriptors().clear() ;
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
	public void setTaskDefinition(TaskDefinition _taskDefinition) {
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

	public Set<ConcreteTaskDescriptor> getConcreteTaskDescriptors() {
		return concreteTaskDescriptors;
	}

	public void setConcreteTaskDescriptors(
			Set<ConcreteTaskDescriptor> concreteTaskDescriptors) {
		this.concreteTaskDescriptors = concreteTaskDescriptors;
	}
}
