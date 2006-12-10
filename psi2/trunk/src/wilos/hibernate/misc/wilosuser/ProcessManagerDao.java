package wilos.hibernate.misc.wilosuser;

import java.util.HashSet;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.misc.wilosuser.ProcessManager;

/**
 * ProcessManagerDao manage requests from the system to store ProcessManager
 * into the database.
 * 
 * @author Yoann Lopes
 * @author Martial Lapeyre
 */
public class ProcessManagerDao extends HibernateDaoSupport {

	/**
	 * Save or update an processmanager.
	 * 
	 * @param _processmanager
	 */
	public void saveOrUpdateProcessManager(ProcessManager _processmanager) {
		this.getHibernateTemplate().saveOrUpdate(_processmanager);
	}

	/**
	 * Return a list of processmanagers.
	 * 
	 * @return
	 */
	public Set<ProcessManager> getAllProcessManagers() {
		Set<ProcessManager> loadAll = new HashSet<ProcessManager>();
		loadAll.addAll(this.getHibernateTemplate()
				.loadAll(ProcessManager.class));
		return loadAll;
	}

	/**
	 * Return the processmanager which have the id _id.
	 * 
	 * @param _id
	 * @return
	 */
	public ProcessManager getProcessManager(String _id) {
		return (ProcessManager) this.getHibernateTemplate().get(
				ProcessManager.class, _id);
	}

	/**
	 * Delete the processmanager.
	 * 
	 * @param _processmanager
	 */
	public void deleteProcessManager(ProcessManager _processmanager) {
		try {
			this.getHibernateTemplate().delete(_processmanager);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting processmanager
			// into the db.
			logger
					.error("#### ERROR #### --- ProcessManagerDao => deleteProcessManager : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
