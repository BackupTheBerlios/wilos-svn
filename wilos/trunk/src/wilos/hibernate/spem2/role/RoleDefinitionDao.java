
package wilos.hibernate.spem2.role ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.role.RoleDefinition;

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
			//this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in RoleDefinitionDao: The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in RoleDefinitionDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
	}

	/**
	 * Return a list of role
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RoleDefinition> getAllRole() {
		List<RoleDefinition> loadAll = new ArrayList<RoleDefinition>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(RoleDefinition.class));
		} catch (Exception e) {
			logger.error("###RoleDefinitionDao ### --> "+e);
		}
		return loadAll ;
	}

	/**
	 * Return the role which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public RoleDefinition getRole(String _id) {
		return (RoleDefinition) this.getHibernateTemplate().get(RoleDefinition.class, _id) ;
	}

	/**
	 * Delete the role
	 * 
	 * @param _roleDefinition
	 */
	public void deleteRole(RoleDefinition _roleDefinition) {
		try{
			this.getHibernateTemplate().delete(_roleDefinition) ;
		}
		catch(Exception e){
			// Catch normally errors when we delete an unexisting role into the
			// db.
			logger.error("#### ERROR #### --- RoleDefinitionDao => deleteRole : trying to delete unexisting object \n" + e) ;
		}
	}
}
