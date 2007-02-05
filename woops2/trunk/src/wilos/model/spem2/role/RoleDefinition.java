
package wilos.model.spem2.role ;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.element.Element;
import wilos.model.spem2.guide.Guidance;

/**
 * 
 * A RoleDefinition is a content element that defines a set of related skills, competencies, and
 * responsibilities. Roles are used by Tasks to define who performs them as well as define a set of
 * work products they are responsible for.
 * 
 * @author deder
 * @author soosuske
 * @author garwind 
 * 
 */
public class RoleDefinition extends Element implements Cloneable {

	/**
	 * Collection of TaskDescriptor
	 */
	private Set<RoleDescriptor> roleDescriptors ;
	
	private Set<Guidance> guidances;

	/**
	 * Constructor.
	 * 
	 */
	public RoleDefinition() {
		super() ;
		this.roleDescriptors = new HashSet<RoleDescriptor>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof RoleDefinition == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		RoleDefinition role = (RoleDefinition) obj ;
		return new EqualsBuilder().appendSuper(super.equals(role)).append(this.roleDescriptors, role.roleDescriptors).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public RoleDefinition clone() throws CloneNotSupportedException {
		RoleDefinition roleDefinition = new RoleDefinition() ;
		roleDefinition.copy(this) ;
		return roleDefinition ;
	}

	/**
	 * Copy the _roleDefinition into this.
	 */
	protected void copy(final RoleDefinition _roleDefinition) {
		super.copy(_roleDefinition) ;
		this.setRoleDescriptors(_roleDefinition.getRoleDescriptors()) ;
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
		this.roleDescriptors.add(_role) ;
		_role.setRoleDefinition(this) ;
	}

	/**
	 * remove a RoleDescriptor
	 * 
	 * @param _role
	 */
	public void removeRoleDescriptor(RoleDescriptor _role) {
		_role.setRoleDefinition(null) ;
		this.roleDescriptors.remove(_role) ;

	}

	/**
	 * Remove from an RoleDefinition all its RoleDescriptor
	 * 
	 */
	public void removeAllRoleDescriptors() {
		for(RoleDescriptor _role : this.roleDescriptors){
			_role.setRoleDefinition(null) ;
		}
		this.roleDescriptors.clear() ;
	}

	/**
	 * Add a RoleDescriptor collection to the RoleDescriptor collection of an RoleDefinition
	 * 
	 * @param _role
	 */
	public void addAllRoleDescriptors(Set<RoleDescriptor> _role) {
		for(RoleDescriptor _role1 : _role){
			_role1.addRoleDefinition(this) ;
		}
	}
	
	/*
	 * connection to guidances
	 */
	public void removeGuidance(Guidance _guidance) {
		_guidance.setRoledefinition(null);
		this.guidances.remove(_guidance);
	}

	
	public void addGuidance(Guidance _guidance) {
		this.guidances.add(_guidance);
		_guidance.setRoledefinition(this);
	}

	
	public void removeAllGuidances() {
		for (Guidance guidance : this.guidances) {
			guidance.setRoledefinition(null);
		}
		this.guidances.clear();
	}
	
	public void addAllGuidances(Set<Guidance> _guidances) {
		for (Guidance _guid1 : _guidances) {
			_guid1.addRoleDefinition(this);
		}
	}

	/**
	 * Getter of roleDescriptors.
	 * 
	 * @return the roleDescriptors.
	 */
	public Set<RoleDescriptor> getRoleDescriptors() {
		return this.roleDescriptors ;
	}

	/**
	 * Setter of roleDescriptors.
	 * 
	 * @param _roleDescriptors
	 *            The roleDescriptors to set.
	 */
	@ SuppressWarnings ("unused")
	private void setRoleDescriptors(Set<RoleDescriptor> _roleDescriptors) {
		this.roleDescriptors = _roleDescriptors ;
	}

	public Set<Guidance> getGuidances() {
		return guidances;
	}

	public void setGuidances(Set<Guidance> guidances) {
		this.guidances = guidances;
	}
}
