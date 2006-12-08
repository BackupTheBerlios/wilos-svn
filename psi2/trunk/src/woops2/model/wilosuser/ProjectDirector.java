
package woops2.model.wilosuser ;

/**
 * @author Yoann Lopes
 * 
 * This class represents a project director using Wilos.
 * 
 */
public class ProjectDirector extends WilosUser {

	public ProjectDirector() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 */
	public ProjectDirector(String _name, String _fName, String _email, String _login, String _password) {
		super(_name, _fName, _email, _login, _password) ;
	}
}
