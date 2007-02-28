package wilos.hibernate.misc.concretetask;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.utils.ExceptionManager;

/**
 * ConcreteTaskDescriptorDao manage requests from the system to store
 * ConcreteTaskDescriptorDao to the database
 *
 * @author mat,seb
 *
 */
public class ConcreteTaskDescriptorDao extends HibernateDaoSupport {

	/**
	 * Save or update a ConcreteTaskDescriptorDao
	 *
	 * @param _concreteTaskdescriptor
	 */
	public void saveOrUpdateConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_concreteTaskDescriptor);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(),
							"saveOrUpdateConcreteTaskDescriptor", _e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(),
					"saveOrUpdateConcreteTaskDescriptor", _ex);
		}
	}

	/**
	 * Return a set of ConcreteTaskDescriptor
	 *
	 * @return set <ConcreteTaskDescriptor>
	 */
	@SuppressWarnings("unchecked")
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptors() {
		List<ConcreteTaskDescriptor> loadAll = new ArrayList<ConcreteTaskDescriptor>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(
					ConcreteTaskDescriptor.class));
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllConcreteTaskDescriptors",
					_e);
		}
		return loadAll;
	}

	/**
	 * Return the ConcreteTaskDescriptor which have the id _id
	 *
	 * @param _id
	 * @return ConcreteTaskDescriptor
	 */
	public ConcreteTaskDescriptor getConcreteTaskDescriptor(String _id) {
		return (ConcreteTaskDescriptor) this.getHibernateTemplate().get(
				ConcreteTaskDescriptor.class, _id);
	}

	/**
	 * Delete the ConcreteTaskDescriptor
	 *
	 * @param _concreteTaskdescriptor
	 */
	public void deleteConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskdescriptor) {
		try {
			this.getHibernateTemplate().delete(_concreteTaskdescriptor);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "deleteConcreteTaskDescriptor",
					_e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptorsForProject(
			String _projectId) {
		List ctds = this.getHibernateTemplate().find(
				"from ConcreteTaskDescriptor ctd where ctd.project.id=?",
				_projectId);
		return ctds;
	}
}
