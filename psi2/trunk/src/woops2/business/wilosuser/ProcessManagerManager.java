
package woops2.business.wilosuser ;

import java.util.Set ;

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
public class ProcessManagerManager {

	private ProcessManagerDao processManagerDao ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Save processManager
	 * 
	 * @param _processmanager
	 */
	public void saveProcessManager(ProcessManager _processmanager) {
		this.processManagerDao.saveOrUpdateProcessManager(_processmanager) ;
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

}
