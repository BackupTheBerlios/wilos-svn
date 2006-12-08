
package woops2.business.wilosuser ;

import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import org.springframework.transaction.annotation.Propagation ;
import org.springframework.transaction.annotation.Transactional ;

import woops2.hibernate.wilosuser.ProcessManagerDao ;
import woops2.model.wilosuser.ProcessManager ;

/**
 * @author Marseyeah
 * 
 * This class represents ... TODO
 * 
 */
@ Transactional (readOnly = false, propagation = Propagation.REQUIRED)
public class ProcessManagerService {

	private ProcessManagerDao processManagerDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Save processManager
	 * 
	 * @param _processmanager
	 */
	public void saveProcessManager(ProcessManager _processManager) {
		_processManager.setPassword(Security.encode(_processManager.getPassword()));
		this.processManagerDao.saveOrUpdateProcessManager(_processManager) ;
	}

	/**
	 * Setter of processManagerDao.
	 * 
	 * @param _processManagerDao
	 *            The processManagerDao to set.
	 */
	public void setProcessManagerDao(ProcessManagerDao _processManagerDao) {
		this.processManagerDao = _processManagerDao ;
	}
	/**
	 * Getter of processManagerDao.
	 * 
	 *            The processManagerDao to get.
	 */
	public ProcessManagerDao getProcessManagerDao() {
		return this.processManagerDao ;
	}

}
