package woops2.model.role;

import java.util.ArrayList;
import java.util.List;

import woops2.model.element.Element;

/**
 * @author deder
 * 
 * A RoleDefinition is a content element that defines a set of related skills,
 * competencies, and responsibilities. Roles are used by Tasks to define who
 * performs them as well as define a set of work products they are responsible
 * for.
 * 
 */
public class RoleDefinition extends Element {

	/**
	 * Collection of TaskDescriptor
	 */
	private List<RoleDescriptor> roleDescriptors;

	/**
	 * Constructor.
	 * 
	 */
	public RoleDefinition() {
		super();
		this.roleDescriptors = new ArrayList<RoleDescriptor>();
	}

	/**
	 * Getter of roleDescriptors.
	 * 
	 * @return the roleDescriptors.
	 */
	public List<RoleDescriptor> getRoleDescriptors() {
		return this.roleDescriptors;
	}

	/**
	 * Setter of roleDescriptors.
	 * 
	 * @param _roleDescriptors
	 *            The roleDescriptors to set.
	 */
	public void setRoleDescriptors(List<RoleDescriptor> _roleDescriptors) {
		this.roleDescriptors = _roleDescriptors;
	}
}
