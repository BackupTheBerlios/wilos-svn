package woops2.hibernate.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * ProcessDao manage requests from the system to store Acitivties to the
 * database
 * 
 * @author garwind
 * @author deder
 */
public class ProcessDao extends HibernateDaoSupport {

	/**
	 * Save or update an process
	 * 
	 * @param _process
	 */
	public void saveOrUpdateProcess(Process _process) {
		this.getHibernateTemplate().saveOrUpdate(_process);
	}

	/**
	 * Return a list of processes.
	 * 
	 * @return
	 */
	public List<Process> getAllProcesses() {
		List<Process> loadAll = new ArrayList<Process>();
		loadAll.addAll(this.getHibernateTemplate().loadAll(Process.class));
		return loadAll;
	}

	/**
	 * Return the process which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Process getProcess(String _id) {
		return (Process) this.getHibernateTemplate().get(Process.class, _id);
	}

	/**
	 * Delete the process
	 * 
	 * @param _process
	 */
	public void deleteProcess(Process _process) {
		try {
			this.getHibernateTemplate().delete(_process);
		} catch (Exception sose) {
			// Catch normally errors when we delete an unexisting process into
			// the db.
			logger
					.error("#### ERROR #### --- ProcessDao => deleteProcess : trying to delete unexisting object \n"
							+ sose);
		}
	}
}
