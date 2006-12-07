package wilos.hibernate.spem2.role;

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
			//this.getHibernateTemplate().flush() ;

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
	@SuppressWarnings("unchecked")
	public List<RoleDescriptor> getAllRoleDescriptor() {
		List<RoleDescriptor> loadAll = new ArrayList<RoleDescriptor>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(RoleDescriptor.class));
		} catch (Exception e) {
			logger.error("###RoleDescriptorDao ### --> "+e);
		}
		return loadAll;
	}

	/**
	 * Return the RoleDescriptor which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public RoleDescriptor getRoleDescriptor(String _id) {
		return (RoleDescriptor) this.getHibernateTemplate().get(
				RoleDescriptor.class, _id);
	}

	/**
	 * Delete the RoleDescriptor
	 * 
	 * @param _RoleDescriptor
	 */
	public void deleteRoleDescriptor(RoleDescriptor _RoleDescriptor) {
		try {
			this.getHibernateTemplate().delete(_RoleDescriptor);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting RoleDescriptor
			// into the db.
			logger.error("#### ERROR #### --- RoleDescriptorDao => deleteRoleDescriptor : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
