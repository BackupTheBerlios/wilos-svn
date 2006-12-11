package wilos.presentation.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.role.RoleDescriptorService;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
public class RoleDescriptorBean {

	private List<RoleDescriptor> roleDescriptorList ;
	
	private RoleDescriptor roleDescriptor;
	
	private RoleDescriptorService roleDescriptorService;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Constructor.
	 * 
	 */
	public RoleDescriptorBean() {
		this.logger.debug("--- RoleDescriptor --- == creating ..." + this) ;
		this.roleDescriptor = new RoleDescriptor() ;
	}
	
	/**
	 * Getter of activitiesList.
	 * 
	 * @return the activitiesList.
	 */
	public List<RoleDescriptor> getRoleDescriptorList(String _id) {
		this.roleDescriptorList = new ArrayList<RoleDescriptor>();
		try {
			roleDescriptorList.addAll(this.roleDescriptorService.getRoleDescriptorsFromProcess(_id));
		} catch (Exception e) {
			logger.error("### RoleDescriptorBean ### "+e);
		}
		this.logger.debug("roleDescriptorList =" + this.roleDescriptorList) ;
		return this.roleDescriptorList ;
	}

	/**
	 * Getter of roleDescriptor.
	 *
	 * @return the roleDescriptor.
	 */
	public RoleDescriptor getRoleDescriptor() {
		return this.roleDescriptor ;
	}

	/**
	 * Setter of roleDescriptor.
	 *
	 * @param _roleDescriptor The roleDescriptor to set.
	 */
	public void setRoleDescriptor(RoleDescriptor _roleDescriptor) {
		this.roleDescriptor = _roleDescriptor ;
	}

	/**
	 * Getter of roleDescriptorList.
	 *
	 * @return the roleDescriptorList.
	 */
	/*public List<RoleDescriptor> getRoleDescriptorList() {
		return this.roleDescriptorList ;
	}*/

	/**
	 * Setter of roleDescriptorList.
	 *
	 * @param _roleDescriptorList The roleDescriptorList to set.
	 */
	public void setRoleDescriptorList(List<RoleDescriptor> _roleDescriptorList) {
		this.roleDescriptorList = _roleDescriptorList ;
	}

	/**
	 * Getter of roleDescriptorService.
	 *
	 * @return the roleDescriptorService.
	 */
	public RoleDescriptorService getRoleDescriptorService() {
		return this.roleDescriptorService ;
	}

	/**
	 * Setter of roleDescriptorService.
	 *
	 * @param _roleDescriptorService The roleDescriptorService to set.
	 */
	public void setRoleDescriptorService(RoleDescriptorService _roleDescriptorService) {
		this.roleDescriptorService = _roleDescriptorService ;
	}
	
}
