
package wilos.hibernate.spem2.process ;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wilos.model.spem2.process.Process;

/**
 * ProcessDao manage requests from the system to store Acitivties to the database
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
		try{
			this.getHibernateTemplate().saveOrUpdate(_process) ;
			//this.getHibernateTemplate().flush() ;

		}
		catch(DataIntegrityViolationException e){
			System.out.print("save in ProcessDao: The Exception is " + e.getClass().getName() + "\n") ;
		}
		catch(ConstraintViolationException ex){
			System.out.print("save in ProcessDao: The Exception is " + ex.getClass().getName() + "\n") ;
		}
	}

	/**
	 * Return a list of processes.
	 * 
	 * @return
	 */
	@ SuppressWarnings ("unchecked")
	public List<Process> getAllProcesses() {
		List<Process> loadAll = new ArrayList<Process>() ;
		try{
			loadAll.addAll(this.getHibernateTemplate().loadAll(Process.class)) ;

		}
		catch(Exception e){
			logger.error("###ProcessDao ### --> " + e) ;
		}
		return loadAll ;
	}

	/**
	 * Return the process which have the id _id
	 * 
	 * @param _id
	 * @return
	 */
	public Process getProcess(String _id) {
		return (Process) this.getHibernateTemplate().get(Process.class, _id) ;
	}

	/**
	 * Delete the process
	 * 
	 * @param _process
	 */
	public void deleteProcess(Process _process) {
		if(this.getProcess(_process.getId()) != null)
			this.getHibernateTemplate().delete(_process) ;
	}
}
