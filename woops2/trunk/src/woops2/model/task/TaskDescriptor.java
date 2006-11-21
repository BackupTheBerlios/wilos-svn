
package woops2.model.task ;

import java.util.List;

import woops2.model.role.RoleDescriptor;
import woops2.model.workbreakdownelement.WorkBreakdownElement;

/**
 * @author Sebastien BALARD
 * 
 * This class represents ... TODO
 * 
 */
public class TaskDescriptor extends WorkBreakdownElement {

	/**
	 * the attached taskDefinition
	 */
	private TaskDefinition taskDefinition ;

	/**
	 * The additional roles of the role
	 */
	private List<RoleDescriptor> additionalRoles ;

	/**
	 * The main role of the taskDefinition
	 */
	private RoleDescriptor mainRole ;

	/**
	 * Default constructor.
	 */
	public TaskDescriptor() {
		super() ;
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
	public List<RoleDescriptor> getAdditionalRoles() {
		return this.additionalRoles ;
	}

	/**
	 * Setter of additionalRoles.
	 * 
	 * @param _additionalRoles
	 *            The additionalRoles to set.
	 */
	public void setAdditionalRoles(List<RoleDescriptor> _additionalRoles) {
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
