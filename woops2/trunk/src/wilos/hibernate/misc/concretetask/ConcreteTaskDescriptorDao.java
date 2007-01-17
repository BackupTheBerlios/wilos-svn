
package wilos.hibernate.misc.concretetask ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.utils.ExceptionManager;

/**
 * TaskDescriptorDao manage requests from the system to store TaskDescriptor to the database
 * 
 * @author eperico
 * 
 */
public class ConcreteTaskDescriptorDao extends HibernateDaoSupport {

	/**
	 * Save or update a TaskDescriptor
	 * 
	 * @param _taskdescriptor
	 */
	public void saveOrUpdateConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskdescriptor) {
		try{
			this.getHibernateTemplate().saveOrUpdate(_concreteTaskdescriptor) ;
		}
		catch(DataIntegrityViolationException _e){
			ExceptionManager.getInstance().manageDataIntegrityViolationException(this.getClass().getName(), "saveOrUpdateConcreteTaskDescriptor", _e);
		}
		catch(ConstraintViolationException _ex){
			ExceptionManager.getInstance().manageConstraintViolationException(this.getClass().getName(), "saveOrUpdateConcreteTaskDescriptor", _ex);
		}
	}

	/**
	 * Return a set of TaskDescriptor
	 * 
	 * @return set <TaskDescriptor>
	 */
	@ SuppressWarnings ("unchecked")
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptors() {
		List<ConcreteTaskDescriptor> loadAll = new ArrayList<ConcreteTaskDescriptor>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(ConcreteTaskDescriptor.class)) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "getAllConcreteTaskDescriptors", _e);
		}
		return loadAll ;
	}

	/**
	 * Return the TaskDescriptor which have the id _id
	 * 
	 * @param _id
	 * @return TaskDescriptor
	 */
	public ConcreteTaskDescriptor getConcreteTaskDescriptor(String _id) {
		return (ConcreteTaskDescriptor) this.getHibernateTemplate().get(ConcreteTaskDescriptor.class, _id) ;
	}

	/**
	 * Delete the TaskDescriptor
	 * 
	 * @param _taskdescriptor
	 */
	public void deleteConcreteTaskDescriptor(ConcreteTaskDescriptor _concreteTaskdescriptor) {
		try{
			this.getHibernateTemplate().delete(_concreteTaskdescriptor) ;
		}
		catch(DataAccessException _e){
			ExceptionManager.getInstance().manageDataAccessException(this.getClass().getName(), "deleteConcreteTaskDescriptor", _e);
		}
	}
}
