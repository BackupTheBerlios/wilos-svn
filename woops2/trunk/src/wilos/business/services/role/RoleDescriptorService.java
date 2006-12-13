package wilos.business.services.role;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.role.RoleDescriptor;

/**
 * @author Sebastien
 *
 * This class represents ... TODO
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class RoleDescriptorService {
	
	private RoleDescriptorDao roleDescriptorDao ;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Return activities list
	 * 
	 * @return
	 */
	//@Transactional(readOnly = true)
	public List<RoleDescriptor> getRoleDescriptorsFromProcess(String _id) {
		List<RoleDescriptor> tempList =  this.roleDescriptorDao.getAllRoleDescriptor() ;
		List<RoleDescriptor> returnedList =  new ArrayList<RoleDescriptor>();
		boolean flag = false;
		
		for (RoleDescriptor rd : tempList) {
			flag = false;
			for (Activity a : rd.getActivities()) {
				if (a.getId().equals(_id)) {
					flag = true;
					break;
				}
			}
			if (flag){
				returnedList.add(rd);
				logger.debug("###RoleDescriptorDao ### added => "+rd);
			}	
		}
		return returnedList;
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
