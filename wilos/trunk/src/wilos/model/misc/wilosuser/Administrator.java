
package wilos.model.misc.wilosuser ;

/**
 * @author Yoann Lopes
 * 
 * This class represents the administrator of Wilos.
 * 
 */
public class Administrator extends WilosUser {

	/**
	 * Default Constructor.
	 *
	 */
	public Administrator() {}
	
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

}
