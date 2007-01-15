
package wilos.hibernate.spem2.role ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.role.RoleDefinition;
import wilos.utils.ExceptionManager;

/**
 * RoleDefinitionDao manage requests from the system to store RoleDefinition to the database
 * 
 * @author soosuske
 */
public class RoleDefinitionDao extends HibernateDaoSupport {

	/**
	 * Save or update an role
	 * 
	 * @param _roleDefinition
	 */
	public void saveOrUpdateRoleDefinition(RoleDefinition _roleDefinition) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_roleDefinition) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateRoleDefinition", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateRoleDefinition", _ex);
		}
	}

	/**
	 * Return a list of role
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RoleDefinition> getAllRoleDefinitions() {
		List<RoleDefinition> loadAll = new ArrayList<RoleDefinition>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(RoleDefinition.class));
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllRoleDefinitions", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the role which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public RoleDefinition getRoleDefinition(String _id) {
		return (RoleDefinition) this.getHibernateTemplate().get(RoleDefinition.class, _id) ;
	}

	/**
	 * Delete the role
	 * 
	 * @param _roleDefinition
	 */
	public void deleteRoleDefinition(RoleDefinition _roleDefinition) {
		try{
			this.getHibernateTemplate().delete(_roleDefinition) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteRoleDefinition", _e);
		}
	}
}
