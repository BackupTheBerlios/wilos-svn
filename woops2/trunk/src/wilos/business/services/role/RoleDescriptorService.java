package wilos.business.services.role;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wilos.hibernate.spem2.role.RoleDefinitionDao;
import wilos.hibernate.spem2.role.RoleDescriptorDao;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.role.RoleDefinition;
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
	
	private RoleDefinitionDao roleDefinitionDao; 
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Return activities list
	 * 
	 * @return
	 */
	//@Transactional(readOnly = true)
	public List<RoleDescriptor> getRoleDescriptorsFromProcess(String _id) {
		List<RoleDescriptor> tempList =  this.roleDescriptorDao.getAllRoleDescriptors() ;
		List<RoleDescriptor> returnedList =  new ArrayList<RoleDescriptor>();
		boolean flag = false;
		
		for (RoleDescriptor rd : tempList) {
			flag = false;
			for (Activity a : rd.getSuperActivities()) {
				if (a.getId().equals(_id)) {
					flag = true;
					break;
				}
			}
			if (flag){
				//rd.setRoleDefinition(this.getRoleDefinitionFromRoleDescriptor(rd.getId()));
				returnedList.add(rd);
				logger.debug("###RoleDescriptorDao ### added => "+rd);
			}	
		}
		return returnedList;
	}
	
	public RoleDefinition getRoleDefinitionFromRoleDescriptor(String _id){
		RoleDefinition rdf = null;
		boolean found = false;
		List<RoleDefinition> listRdf = this.roleDefinitionDao.getAllRoleDefinitions();
		for (RoleDefinition r : listRdf){
			for(RoleDescriptor rd : r.getRoleDescriptors()){
				if (rd.getId().equals(_id)){
					found = true;
					break;
				}
			}
			if (found) { rdf = r; break;}
		}
		return rdf;
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

	/**
	 * @return the roleDefinitionDao
	 */
	public RoleDefinitionDao getRoleDefinitionDao() {
		return this.roleDefinitionDao ;
	}

	/**
	 * Setter of roleDefinitionDao.
	 *
	 * @param _roleDefinitionDao The roleDefinitionDao to set.
	 */
	public void setRoleDefinitionDao(RoleDefinitionDao _roleDefinitionDao) {
		this.roleDefinitionDao = _roleDefinitionDao ;
	}

}
