package wilos.hibernate.misc.concreterole;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
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

	@SuppressWarnings("unchecked")
	public List<ConcreteTaskDescriptor> getAllConcreteTaskDescriptorsForConcreteRoleDescriptor(
			String _concreteRoleDescriptorId) {
		List crds = this
				.getHibernateTemplate()
				.find(
						"from ConcreteTaskDescriptor ctd where ctd.concreteRoleDescriptor.id=?",
						_concreteRoleDescriptorId);
		return crds;
	}

	@SuppressWarnings("unchecked")
	public List<ConcreteRoleDescriptor> getAllConcreteRoleDescriptorsForARoleDescriptor(
			String _roleId) {
		List ctds = this
				.getHibernateTemplate()
				.find(
						"from ConcreteRoleDescriptor ctd where ctd.roleDescriptor.id=?",
						_roleId);
		return ctds;
	}

	public List<ConcreteTaskDescriptor> getPrimaryConcreteTaskDescriptors(
			String _roleId) {
		List ctds = this
				.getHibernateTemplate()
				.find(
						"from ConcreteTaskDescriptor ctd join ctd.concreteRoleDescriptors crds where crds.id=?",
						_roleId);
		List<ConcreteTaskDescriptor> listCtds = new ArrayList<ConcreteTaskDescriptor>();
		if (ctds.get(0) instanceof List) {
			for (Object o : (ArrayList) ctds.get(0)) {
				if (o instanceof ConcreteTaskDescriptor) {
					ConcreteTaskDescriptor bde = (ConcreteTaskDescriptor) o;
					listCtds.add(bde);
				}
			}
		}
		return listCtds;
	}
}
