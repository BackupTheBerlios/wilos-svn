
package woops2.model.wilosuser ;

/**
 * @author Yoann Lopes
 * 
 * This class represents the administrator of Wilos.
 * 
 */
public class Administrator extends WilosUser {

	/**
	 * Constructor.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 */
	public Administrator(String _name, String _fName, String _email, String _login, String _password) {
		super(_name, _fName, _email, _login, _password) ;
	}

	/**
	 * Create a new ProcessManager.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 * @return
	 */
	public ProcessManager createProcessManager(String _name, String _fName, String _email, String _login, String _password) {
		ProcessManager pm = new ProcessManager(_name, _fName, _email, _login, _password) ;
		return pm ;
	}

	/**
	 * Create a new ProjectDirector.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 * @return
	 */
	public ProjectDirector createProjectDirector(String _name, String _fName, String _email, String _login, String _password) {
		ProjectDirector pd = new ProjectDirector(_name, _fName, _email, _login, _password) ;
		return pd ;
	}
}
