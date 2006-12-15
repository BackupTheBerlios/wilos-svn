
package wilos.utils ;

/**
 * @author Sebastien
 * 
 * This class represents ... TODO
 * 
 */
public class ExceptionManager {

	private static ExceptionManager instance = null ;

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

	public void manageDaoException(Exception _e) {
				
	}
}
