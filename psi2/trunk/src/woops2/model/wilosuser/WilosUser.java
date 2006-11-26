
package woops2.model.wilosuser ;

/**
 * @author Yoann Lopes
 * 
 * This class represents a user of Wilos.
 * 
 */
public abstract class WilosUser {

	private String name ;

	private String firstname ;

	private String emailAddress ;

	private String login ;

	private String password ;

	/**
	 * Default Constructor.
	 *
	 */
	public WilosUser() {}
	
	/**
	 * Constructor.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 */
	public WilosUser(String _name, String _fName, String _email, String _login, String _password) {
		this.name = _name ;
		this.firstname = _fName ;
		this.emailAddress = _email ;
		this.login = _login ;
		this.password = _password ;
	}

	/**
	 * Getter of emailAddress.
	 * 
	 * @return the emailAddress.
	 */
	public String getEmailAddress() {
		return this.emailAddress ;
	}

	/**
	 * Setter of emailAddress.
	 * 
	 * @param _emailAddress
	 *            The emailAddress to set.
	 */
	public void setEmailAddress(String _emailAddress) {
		this.emailAddress = _emailAddress ;
	}

	/**
	 * Getter of firstname.
	 * 
	 * @return the firstname.
	 */
	public String getFirstname() {
		return this.firstname ;
	}

	/**
	 * Setter of firstname.
	 * 
	 * @param _firstname
	 *            The firstname to set.
	 */
	public void setFirstname(String _firstname) {
		this.firstname = _firstname ;
	}

	/**
	 * Getter of login.
	 * 
	 * @return the login.
	 */
	public String getLogin() {
		return this.login ;
	}

	/**
	 * Setter of login.
	 * 
	 * @param _login
	 *            The login to set.
	 */
	public void setLogin(String _login) {
		this.login = _login ;
	}

	/**
	 * Getter of name.
	 * 
	 * @return the name.
	 */
	public String getName() {
		return this.name ;
	}

	/**
	 * Setter of name.
	 * 
	 * @param _name
	 *            The name to set.
	 */
	public void setName(String _name) {
		this.name = _name ;
	}

	/**
	 * Getter of password.
	 * 
	 * @return the password.
	 */
	public String getPassword() {
		return this.password ;
	}

	/**
	 * Setter of password.
	 * 
	 * @param _password
	 *            The password to set.
	 */
	public void setPassword(String _password) {
		this.password = _password ;
	}

}
