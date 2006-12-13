package wilos.business.services.role;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
public class RoleDescriptorService {
	
	private RoleDescriptorDao roleDescriptorDao ;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Return activities list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<RoleDescriptor> getRoleDescriptorsFromProcess(String _id) {
		return this.roleDescriptorDao.getRoleDescriptorsFromProcess(_id) ;
	}

	/**
	 * Getter of roleDescriptorDao.
	 *
	 * @return the roleDescriptorDao.
	 */
	public RoleDescriptorDao getRoleDescriptorDao() {
		return this.roleDescriptorDao ;
	}

	/**
	 * Setter of roleDescriptorDao.
	 *
	 * @param _roleDescriptorDao The roleDescriptorDao to set.
	 */
	public void setRoleDescriptorDao(RoleDescriptorDao _roleDescriptorDao) {
		this.roleDescriptorDao = _roleDescriptorDao ;
	}

}
