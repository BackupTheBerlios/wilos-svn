package woops2.model.task;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import woops2.model.role.RoleDescriptor;
import woops2.model.workbreakdownelement.WorkBreakdownElement;

/**
 * @author Sebastien BALARD
 * @author eperico
 * 
 * This class represents ... TODO
 * 
 */
public class TaskDescriptor extends WorkBreakdownElement {

	/**
	 * the attached taskDefinition
	 */
	private TaskDefinition taskDefinition;

	/**
	 * The additional roles of the role
	 */
	private Set<RoleDescriptor> additionalRoles;

	/**
	 * The main role of the taskDefinition
	 */
	private RoleDescriptor mainRole;

	/**
	 * Default constructor.
	 */
	public TaskDescriptor() {
		super();
	}

	/**
	 * Getter of taskDefinition.
	 * 
	 * @return the taskDefinition.
	 */
	public TaskDefinition getTaskDefinition() {
		return this.taskDefinition;
	}

	/**
	 * Setter of taskDefinition.
	 * 
	 * @param _taskDefinition
	 *            The taskDefinition to set.
	 */
	protected void setTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = _taskDefinition;
	}

	/**
	 * Getter of additionalRoles.
	 * 
	 * @return the additionalRoles.
	 */
	public Set<RoleDescriptor> getAdditionalRoles() {
		return this.additionalRoles;
	}

	/**
	 * Setter of additionalRoles.
	 * 
	 * @param _additionalRoles
	 *            The additionalRoles to set.
	 */
	@SuppressWarnings("unused")
	private void setAdditionalRoles(Set<RoleDescriptor> _additionalRoles) {
		this.additionalRoles = _additionalRoles;
	}

	/**
	 * Getter of mainRole.
	 * 
	 * @return the mainRole.
	 */
	public RoleDescriptor getMainRole() {
		return this.mainRole;
	}

	/**
	 * Setter of mainRole.
	 * 
	 * @param _mainRole
	 *            The mainRole to set.
	 */
	public void setMainRole(RoleDescriptor _mainRole) {
		this.mainRole = _mainRole;
	}
	
	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if (obj instanceof TaskDescriptor == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		TaskDescriptor taskDescriptor = (TaskDescriptor) obj;
		return new EqualsBuilder().appendSuper(super.equals(taskDescriptor)).append(
				this.taskDefinition, taskDescriptor.taskDefinition).isEquals();
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.getName()).toHashCode();
	}

	/**
	 * Attach a taskDescriptor to a taskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void addToTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = _taskDefinition;
		_taskDefinition.getTaskDescriptors().add(this);
	}

	/**
	 * Detach a taskDescriptor to its taskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void removeFromTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = null;
		_taskDefinition.getTaskDescriptors().remove(this);
	}

	/**
	 * Attach a taskDescriptor to a roleDescriptor
	 * 
	 * @param _roleDescriptor
	 */
	public void addToMainRole(RoleDescriptor _roleDescriptor) {
		this.mainRole = _roleDescriptor;
		_roleDescriptor.getAdditionalTasks().add(this);
	}

	/**
	 * Detach a taskDescriptor to its roleDescriptor
	 * 
	 * @param _roleDescriptor
	 */
	public void removeFromMainRole(RoleDescriptor _roleDescriptor) {
		this.mainRole = null;
		_roleDescriptor.getAdditionalTasks().remove(this);
	}

	/**
	 * @param _roleDescriptor
	 */
	public void addToRoleDescriptor(RoleDescriptor _roleDescriptor) {
		this.additionalRoles.add(_roleDescriptor);
		_roleDescriptor.getAdditionalTasks().add(this);
	}

	/**
	 * @param _roleDescriptor
	 */
	public void removeFromRoleDescriptor(RoleDescriptor _roleDescriptor) {
		_roleDescriptor.getAdditionalTasks().remove(this);
		this.additionalRoles.remove(_roleDescriptor);
	}

//	/**
//	 * Remove from a roleDescriptor all its taskDescriptors
//	 */
//	public void removeAllRoleDescriptor() {
//		for (RoleDescriptor tmp : this.additionalRoles) {
//			tmp.removeFromTaskDescriptor(this);
//		}
//		this.additionalRoles.clear();
//	}

	/**
	 * Remove from a taskDescriptor all its roleDescriptors
	 */
	public void removeFromAllRoleDescriptor() {
		for (RoleDescriptor tmp : this.getAdditionalRoles()) {
			this.removeFromRoleDescriptor(tmp);
		}
	}

}
