package woops2.business.process ;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import woops2.hibernate.process.ProcessDao;
import woops2.model.process.Process;

/**
 * ProcessService is a transactional class, that manage operations about process, requested by web pages (?)
 * 
 * @author deder.
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessService {

	private ProcessDao processDao ;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	/**
	 * Return processes list
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Process> getProcessesList() {
		return this.processDao.getAllProcesses() ;
	}
	
	/**
	 * Save process
	 * 
	 * @param _process
	 */
	public void saveActivity(Process _process) {
		this.processDao.saveOrUpdateProcess(_process) ;
	}
	
	/**
	 * Getter of processDao.
	 * 
	 * @return the processDao.
	 */
	public ProcessDao getprocessDao() {
		return this.processDao ;
	}

	/**
	 * Setter of processDao.
	 * 
	 * @param _processDao
	 *            The processDao to set.
	 */
	public void setProcessDao(ProcessDao _processDao) {
		this.processDao = _processDao ;
	}
}

