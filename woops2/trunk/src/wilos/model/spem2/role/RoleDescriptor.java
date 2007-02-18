package wilos.model.spem2.role;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.task.TaskDescriptor;

/**
 *
 * A RoleDefinition Descriptor represents a RoleDefinition in the context of one
 * specific Activity. Every breakdown structure can define different
 * relationships of RoleDefinition Descriptors to TaskDefinition Descriptors and
 * Work Product Descriptors. Therefore one RoleDefinition can be represented by
 * many RoleDefinition Descriptors each within the context of an Activity with
 * its own set of relationships.
 *
 * @author deder
 *
 */
public class RoleDescriptor extends BreakdownElement implements Cloneable {

	private RoleDefinition roleDefinition;

	/**
	 * The main tasks of the roleDefinition
	 */
	private Set<TaskDescriptor> primaryTasks;

	/**
	 * The additional tasks of the roleDefinition
	 */
	private Set<TaskDescriptor> additionalTasks;

	/**
	 * Associated ConcreteRoleDescriptors.
	 */
	private Set<ConcreteRoleDescriptor> concreteRoleDescriptors;

	public RoleDescriptor() {
		super();
		this.primaryTasks = new HashSet<TaskDescriptor>();
		this.additionalTasks = new HashSet<TaskDescriptor>();
		this.concreteRoleDescriptors = new HashSet<ConcreteRoleDescriptor>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RoleDescriptor clone() throws CloneNotSupportedException {
		RoleDescriptor roleDescriptor = new RoleDescriptor();
		roleDescriptor.copy(this);
		return roleDescriptor;
	}

	/**
	 * Copy the object.
	 *
	 * @param _roleDescriptor
	 *            The RoleDescriptor to copy.
	 */
	protected void copy(final RoleDescriptor _roleDescriptor) {
		super.copy(_roleDescriptor);
		this.additionalTasks.addAll(_roleDescriptor.getAdditionalTasks());
		this.primaryTasks.addAll(_roleDescriptor.getPrimaryTasks());
		this.roleDefinition = _roleDescriptor.getRoleDefinition();
		this.concreteRoleDescriptors.addAll(_roleDescriptor
				.getConcreteRoleDescriptors());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.breakdownelement.BreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof RoleDescriptor == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		RoleDescriptor roleDescriptor = (RoleDescriptor) obj;
		return new EqualsBuilder().appendSuper(super.equals(roleDescriptor))
				.append(this.roleDefinition, roleDescriptor.roleDefinition)
				.append(this.primaryTasks, roleDescriptor.primaryTasks).append(
						this.additionalTasks, roleDescriptor.additionalTasks)
				.append(this.concreteRoleDescriptors,
						roleDescriptor.concreteRoleDescriptors).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.breakdownelement.BreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.roleDefinition).toHashCode();
	}

	/*
	 * relation between RoleDescriptor and ConcreteRoleDescriptor.
	 *
	 */

	/**
	 * @param _roleDescriptor
	 */
	public void addConcreteRoleDescriptor(
			ConcreteRoleDescriptor _concreteRoleDescriptor) {
		this.concreteRoleDescriptors.add(_concreteRoleDescriptor);
		_concreteRoleDescriptor.addRoleDescriptor(this);
	}

	/**
	 * @param _roleDescriptor
	 */
	public void removeConcreteRoleDescriptor(
			ConcreteRoleDescriptor _concreteRoleDescriptor) {
		_concreteRoleDescriptor.removeRoleDescriptor(this);
		this.concreteRoleDescriptors.remove(_concreteRoleDescriptor);
	}

	public void addAllConcreteRoleDescriptors(
			Set<ConcreteRoleDescriptor> _concreteRoleDescriptors) {
		for (ConcreteRoleDescriptor crd : _concreteRoleDescriptors) {
			crd.addRoleDescriptor(this);
		}
	}

	public void removeAllConcreteRoleDescriptors() {
		for (ConcreteRoleDescriptor crd : this.getConcreteRoleDescriptors())
			crd.setRoleDescriptor(null);
		this.getConcreteRoleDescriptors().clear();
	}

	/*
	 * relation between RoleDescriptor and RoleDefinition
	 */

	/**
	 * Add a RoleDefinition to the Set
	 *
	 * @param _role
	 */
	public void addRoleDefinition(RoleDefinition _role) {
		this.roleDefinition = _role;
		_role.getRoleDescriptors().add(this);
	}

	/**
	 * remove a RoleDefinition
	 *
	 * @param _role
	 */
	public void removeRoleDefinition(RoleDefinition _role) {
		_role.getRoleDescriptors().remove(this);
		this.roleDefinition = null;
	}

	/*
	 * relation mainrole between RoleDescriptor and TaskDescriptor
	 */

	/**
	 * add a taskDescritor to the Set
	 *
	 * @param _task
	 */
	public void addPrimaryTask(TaskDescriptor _task) {
		this.primaryTasks.add(_task);
		_task.setMainRole(this);
	}

	/**
	 * remove a taskDescriptor
	 *
	 * @param _task
	 */
	public void removePrimaryTask(TaskDescriptor _task) {
		_task.setMainRole(null);
		this.primaryTasks.remove(_task);
	}

	/**
	 *
	 * @param _role
	 */
	public void addAllPrimaryTasks(Set<TaskDescriptor> _role) {
		for (TaskDescriptor _role1 : _role) {
			_role1.addMainRole(this);
		}
	}

	/**
	 * Remove from an RoleDescriptor all its primaryTask
	 *
	 */
	public void removeAllPrimaryTasks() {
		for (TaskDescriptor _task : this.primaryTasks) {
			_task.setMainRole(null);
		}
		this.primaryTasks.clear();
	}

	/*
	 * relation additionnalRoles between RoleDescriptor and TaskDescriptor
	 */

	/**
	 * add a TaskDescriptor to the Set
	 *
	 * @param _task
	 */
	public void addAdditionalTask(TaskDescriptor _task) {
		this.additionalTasks.add(_task);
		_task.getAdditionalRoles().add(this);
	}

	/**
	 * remove a TaskDescriptor
	 *
	 * @param _task
	 */
	public void removeAdditionalTask(TaskDescriptor _task) {
		_task.getAdditionalRoles().remove(this);
		this.additionalTasks.remove(_task);
	}

	/**
	 *
	 * @param _role
	 */
	public void addAllAdditionalTasks(Set<TaskDescriptor> _task) {
		for (TaskDescriptor task : _task) {
			task.addAdditionalRole(this);
		}
	}

	/**
	 * remove all the roleDescriptors
	 *
	 */
	public void removeAllAdditionalTasks() {
		for (TaskDescriptor _task : this.additionalTasks) {
			_task.getAdditionalRoles().remove(this);
		}
		this.additionalTasks.clear();
	}

	/*
	 * Getter and Setter.
	 */

	/**
	 * Getter of roleDefinition.
	 *
	 * @return the roleDefinition.
	 */
	public RoleDefinition getRoleDefinition() {
		return this.roleDefinition;
	}

	/**
	 * Setter of roleDefinition.
	 *
	 * @param _roleDefinition
	 *            The roleDefinition to set.
	 */
	public void setRoleDefinition(RoleDefinition _roleDefinition) {
		this.roleDefinition = _roleDefinition;
	}

	/**
	 * Getter of primaryTasks.
	 *
	 * @return the primaryTasks.
	 */
	@SuppressWarnings("unused")
	public Set<TaskDescriptor> getPrimaryTasks() {
		return this.primaryTasks;
	}

	/**
	 * Setter of primaryTasks.
	 *
	 * @param _primaryTasks
	 *            The primaryTasks to set.
	 */
	@SuppressWarnings("unused")
	private void setPrimaryTasks(Set<TaskDescriptor> _primaryTasks) {
		this.primaryTasks = _primaryTasks;
	}

	/**
	 * Getter of additionalTasks.
	 *
	 * @return the additionalTasks.
	 */
	public Set<TaskDescriptor> getAdditionalTasks() {
		return this.additionalTasks;
	}

	/**
	 * Setter of additionalTasks.
	 *
	 * @param _additionalTasks
	 *            The additionalTasks to set.
	 */
	@SuppressWarnings("unused")
	private void setAdditionalTasks(Set<TaskDescriptor> _additionalTasks) {
		this.additionalTasks = _additionalTasks;
	}

	public Set<ConcreteRoleDescriptor> getConcreteRoleDescriptors() {
		return concreteRoleDescriptors;
	}

	public void setConcreteRoleDescriptors(
			Set<ConcreteRoleDescriptor> concreteRoleDescriptors) {
		this.concreteRoleDescriptors = concreteRoleDescriptors;
	}
}
