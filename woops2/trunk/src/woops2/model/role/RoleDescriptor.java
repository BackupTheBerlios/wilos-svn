
package woops2.model.role ;

import java.util.List;

import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.task.TaskDescriptor;

/**
 * @author deder
 * 
 * A RoleDefinition Descriptor represents a RoleDefinition in the context of one specific Activity. Every breakdown
 * structure can define different relationships of RoleDefinition Descriptors to TaskDefinition Descriptors and Work
 * Product Descriptors. Therefore one RoleDefinition can be represented by many RoleDefinition Descriptors each within
 * the context of an Activity with its own set of relationships.
 * 
 */
public class RoleDescriptor extends BreakdownElement {

	private RoleDefinition roleDefinition ;

	public RoleDescriptor() {
		super() ;
	}

	/**
	 * The main tasks of the roleDefinition
	 */
	private List<TaskDescriptor> primaryTasks ;

	/**
	 * The additional tasks of the roleDefinition
	 */
	private List<TaskDescriptor> additionalTasks ;

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
	public void setRoleDefinition(RoleDefinition _roleDefinition) {
		this.roleDefinition = _roleDefinition ;
	}

	/**
	 * Getter of primaryTasks.
	 * 
	 * @return the primaryTasks.
	 */
	public List<TaskDescriptor> getPrimaryTasks() {
		return this.primaryTasks ;
	}

	/**
	 * Setter of primaryTasks.
	 * 
	 * @param _primaryTasks
	 *            The primaryTasks to set.
	 */
	public void setPrimaryTasks(List<TaskDescriptor> _primaryTasks) {
		this.primaryTasks = _primaryTasks ;
	}

	/**
	 * Getter of additionalTasks.
	 * 
	 * @return the additionalTasks.
	 */
	public List<TaskDescriptor> getAdditionalTasks() {
		return this.additionalTasks ;
	}

	/**
	 * Setter of additionalTasks.
	 * 
	 * @param _additionalTasks
	 *            The additionalTasks to set.
	 */
	public void setAdditionalTasks(List<TaskDescriptor> _additionalTasks) {
		this.additionalTasks = _additionalTasks ;
	}
}
