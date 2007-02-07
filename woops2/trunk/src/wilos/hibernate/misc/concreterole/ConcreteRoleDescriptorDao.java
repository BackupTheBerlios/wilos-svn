package wilos.hibernate.misc.concreterole;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.utils.ExceptionManager;

/**
 * ConcreteRoleDescriptorDao manage requests from the system to store
 * ConcreteRoleDescriptorDao to the database
 *
 * @author nanawel
 *
 */
public class ConcreteRoleDescriptorDao extends HibernateDaoSupport {

	/**
	 * Save or update a ConcreteRoleDescriptorDao
	 *
	 * @param _concreteRoledescriptor
	 */
	public void saveOrUpdateConcreteRoleDescriptor(
			ConcreteRoleDescriptor _concreteRoledescriptor) {
		try {
			this.getHibernateTemplate().saveOrUpdate(_concreteRoledescriptor);
		} catch (DataIntegrityViolationException _e) {
			ExceptionManager.getInstance()
					.manageDataIntegrityViolationException(
							this.getClass().getName(),
							"saveOrUpdateConcreteRoleDescriptor", _e);
		} catch (ConstraintViolationException _ex) {
			ExceptionManager.getInstance().manageConstraintViolationException(
					this.getClass().getName(),
					"saveOrUpdateConcreteRoleDescriptor", _ex);
		}
	}

	/**
	 * Return a set of ConcreteRoleDescriptor
	 *
	 * @return set <ConcreteRoleDescriptor>
	 */
	@SuppressWarnings("unchecked")
	public List<ConcreteRoleDescriptor> getAllConcreteRoleDescriptors() {
		List<ConcreteRoleDescriptor> loadAll = new ArrayList<ConcreteRoleDescriptor>();
		try {
			loadAll.addAll(this.getHibernateTemplate().loadAll(
					ConcreteRoleDescriptor.class));
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "getAllConcreteRoleDescriptors",
					_e);
		}
		return loadAll;
	}

	/**
	 * Return the ConcreteRoleDescriptor which have the id _id
	 *
	 * @param _id
	 * @return ConcreteRoleDescriptor
	 */
	public ConcreteRoleDescriptor getConcreteRoleDescriptor(String _id) {
		return (ConcreteRoleDescriptor) this.getHibernateTemplate().get(
				ConcreteRoleDescriptor.class, _id);
	}

	/**
	 * Delete the ConcreteRoleDescriptor
	 *
	 * @param _concreteRoledescriptor
	 */
	public void deleteConcreteRoleDescriptor(
			ConcreteRoleDescriptor _concreteRoledescriptor) {
		try {
			this.getHibernateTemplate().delete(_concreteRoledescriptor);
		} catch (DataAccessException _e) {
			ExceptionManager.getInstance().manageDataAccessException(
					this.getClass().getName(), "deleteConcreteRoleDescriptor",
					_e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConcreteRoleDescriptor> getAllConcreteRoleDescriptorsForProject(
			String _projectId) {
		List crds = this.getHibernateTemplate().find(
				"from ConcreteRoleDescriptor ctd where ctd.project.id=?",
				_projectId);
		return crds;
	}
}
