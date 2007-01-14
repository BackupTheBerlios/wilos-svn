
package wilos.hibernate.spem2.role ;

import java.util.ArrayList ;
import java.util.List ;

import org.hibernate.exception.ConstraintViolationException ;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException ;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport ;

import wilos.model.spem2.role.RoleDescriptor ;
import wilos.utils.ExceptionManager;

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
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateRoleDescriptor", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateRoleDescriptor", _ex);
		}
	}

	/**
	 * Return a set of RoleDescriptor
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<RoleDescriptor> getAllRoleDescriptors() {
		List<RoleDescriptor> loadAll = new ArrayList<RoleDescriptor>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(RoleDescriptor.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllRoleDescriptors", _e);
		}
		return loadAll ;
	}

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
		try{
			this.getHibernateTemplate().delete(_roleDescriptor) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteRoleDescriptor", _e);
		}
	}
}
