
package wilos.hibernate.spem2.role ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.role.RoleDescriptor;

/**
 * RoleDefinitionDao manage requests from the system to store RoleDefinition to the database
 * 
 * @author soosuske
 */
public class RoleDescriptorDao extends HibernateDaoSupport {

	/**
	 * Save or update an RoleDescriptor
	 * 
	 * @param _RoleDescriptor
	 */
	public void saveOrUpdateRoleDescriptor(RoleDescriptor _RoleDescriptor) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_RoleDescriptor) ;
			// this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in RoleDescriptorDao: The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in RoleDescriptorDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
	}

	/**
	 * Return a set of RoleDescriptor
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<RoleDescriptor> getAllRoleDescriptor() {
		List<RoleDescriptor> loadAll = new ArrayList<RoleDescriptor>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(RoleDescriptor.class)) ;
		}
		catch(Exception e){
			logger.error("###RoleDescriptorDao ### --> " + e) ;
		}
		return loadAll ;
	}

	/*
	public List<RoleDescriptor> getRoleDescriptorsFromProcess(String _id) {
		logger.debug("###RoleDescriptorDao ### getRoleDescriptorsFromProcess id = "+_id);
		List<RoleDescriptor> loadAll = new ArrayList<RoleDescriptor>();
		boolean flag = false;
		List<RoleDescriptor> liste = this.getAllRoleDescriptor();
		logger.debug("###RoleDescriptorDao ### liste size = "+liste.size());
		for (RoleDescriptor rd : liste) {
			flag = false;
			for (Activity a : rd.getActivities()) {
				if (a.getId().equals(_id)) {
					flag = true;
					break;
				}
			}
			if (flag){
				loadAll.add(rd);
				logger.debug("###RoleDescriptorDao ### added => "+rd);
			}	
		}
		
		return loadAll ;
	}
	*/
	
	/**
	 * Return the RoleDescriptor which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public RoleDescriptor getRoleDescriptor(String _id) {
		return (RoleDescriptor) this.getHibernateTemplate().get(RoleDescriptor.class, _id) ;
	}

	/**
	 * Delete the RoleDescriptor
	 * 
	 * @param _RoleDescriptor
	 */
	public void deleteRoleDescriptor(RoleDescriptor _roleDescriptor) {
		if(this.getRoleDescriptor(_roleDescriptor.getId()) != null)
			this.getHibernateTemplate().delete(_roleDescriptor) ;
	}
}
