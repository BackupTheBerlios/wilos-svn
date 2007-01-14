
package wilos.utils ;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * @author Sebastien
 * 
 * This class represents ... TODO
 * 
 */
public class ExceptionManager {

	private static ExceptionManager instance = null ;
	
	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	/**
	 * Default constructor
	 */
	private ExceptionManager() {
	}

	/**
	 * Get the current instance of the singleton
	 * 
	 * @return Exception manager instance
	 */
	public static ExceptionManager getInstance() {
		if(ExceptionManager.instance == null){
			ExceptionManager.instance = new ExceptionManager() ;
		}
		return ExceptionManager.instance ;
	}
	
	// DAO
	
	/**
	 * 
	 * Manage the DataIntegrityViolationException type
	 *
	 * @param _s Name of the class which raise the exception
	 * @param _e Exception
	 */
	public void manageDataIntegrityViolationException(String _s, String _fonction, DataIntegrityViolationException _e) {
		logger.error("### ExceptionManager -> "+ _s +" raises DataIntegrityViolationException in "+ _fonction +" ###");
		_e.printStackTrace();
	}
	
	/**
	 * 
	 * Manage the DataIntegrityViolationException type
	 *
	 * @param _s Name of the class which raise the exception
	 * @param _e Exception
	 */
	public void manageConstraintViolationException(String _s, String _fonction, ConstraintViolationException _e) {
		logger.error("### ExceptionManager -> "+ _s +" raises ConstraintViolationException in "+ _fonction +" ###");
		_e.printStackTrace();
	}
	
	/**
	 * 
	 * Manage the DataAccessException type
	 *
	 * @param _s Name of the class which raise the exception
	 * @param _e Exception
	 */
	public void manageDataAccessException(String _s, String _fonction, DataAccessException _e) {
		logger.fatal("### ExceptionManager -> "+ _s +" raises DataAccessException in "+ _fonction +" ###");
		_e.printStackTrace();
	}
}
