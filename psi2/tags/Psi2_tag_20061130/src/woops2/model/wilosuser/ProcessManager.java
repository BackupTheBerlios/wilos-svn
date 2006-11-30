
package woops2.model.wilosuser ;

/**
 * @author Yoann Lopes
 * 
 * This class represents a process manager using Wilos.
 * 
 */
public class ProcessManager extends WilosUser {

	/**
	 * Default Constructor.
	 *
	 */
	public ProcessManager() {}
	
	/**
	 * Constructor.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 */
	public ProcessManager(String _name, String _fName, String _email, String _login, String _password) {
		super(_name, _fName, _email, _login, _password) ;
	}
}
