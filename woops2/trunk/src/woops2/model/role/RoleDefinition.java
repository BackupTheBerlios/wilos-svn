package woops2.model.role;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import woops2.model.element.Element;

/**
 * @author deder
 * @author soosuske
 * 
 * A RoleDefinition is a content element that defines a set of related skills,
 * competencies, and responsibilities. Roles are used by Tasks to define who
 * performs them as well as define a set of work products they are responsible
 * for.
 * 
 */
public class RoleDefinition extends Element implements Cloneable {

	/**
	 * Collection of TaskDescriptor
	 */
	private Set<RoleDescriptor> roleDescriptors;

	/**
	 * Constructor.
	 * 
	 */
	public RoleDefinition() {
		super();
		this.roleDescriptors = new HashSet<RoleDescriptor>();
	}

	

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if (obj instanceof RoleDefinition == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		RoleDefinition role = (RoleDefinition) obj;
		return new EqualsBuilder().appendSuper(super.equals(role)).append(
				this.roleDescriptors, role.roleDescriptors).isEquals();
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RoleDefinition clone() throws CloneNotSupportedException {
		RoleDefinition roleDefinition = new RoleDefinition();
		roleDefinition.copy(this);
		return roleDefinition;
	}

	/**
	 * Copy the _roleDefinition into this.
	 */
	protected void copy(final RoleDefinition _roleDefinition) {
		super.copy(_roleDefinition);
		this.setRoleDescriptors(_roleDefinition.getRoleDescriptors());
	}

	/**
	 * relation between RoleDefinition and RoleDescriptor
	 */
	/**
	 * add a roleDescriptor to the set
	 * 
	 * @param _role
	 */
	public void addRoleDescriptor(RoleDescriptor _role) {
		this.roleDescriptors.add(_role);
		_role.setRoleDefinition(this);
	}

	/**
	 * remove a RoleDescriptor
	 * 
	 * @param _role
	 */
	public void removeRoleDescriptor(RoleDescriptor _role) {
		_role.setRoleDefinition(null);
		this.roleDescriptors.remove(_role);

	}

	/**
	 * Remove from an RoleDefinition all its RoleDescriptor
	 * 
	 */
	public void removeAllRoleDescriptors() {
		for (RoleDescriptor _role : this.roleDescriptors) {
			_role.setRoleDefinition(null);
		}
		this.roleDescriptors.clear();
	}

	/**
	 * Add a RoleDescriptor collection to the RoleDescriptor collection of an
	 * RoleDefinition
	 * 
	 * @param _role
	 */
	public void addAllRoleDescriptors(Set<RoleDescriptor> _role) {
		for (RoleDescriptor _role1 : _role) {
			_role1.addRoleDefinition(this);
		}
	}
	
	/**
	 * Getter of roleDescriptors.
	 * 
	 * @return the roleDescriptors.
	 */
	public Set<RoleDescriptor> getRoleDescriptors() {
		return this.roleDescriptors;
	}

	/**
	 * Setter of roleDescriptors.
	 * 
	 * @param _roleDescriptors
	 *            The roleDescriptors to set.
	 */
	@SuppressWarnings("unused")
	private void setRoleDescriptors(Set<RoleDescriptor> _roleDescriptors) {
		this.roleDescriptors = _roleDescriptors;
	}
}
