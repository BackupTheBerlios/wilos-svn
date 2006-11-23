
package woops2.model.wilosuser ;

/**
 * @author Yoann Lopes
 * 
 * This class represents a user of Wilos.
 * 
 */
public abstract class WilosUser {

	private String name ;

	private String firstName ;

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
		this.firstName = _fName ;
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
	 * Getter of firstName.
	 * 
	 * @return the firstName.
	 */
	public String getFirstName() {
		return this.firstName ;
	}

	/**
	 * Setter of firstName.
	 * 
	 * @param _firstName
	 *            The firstName to set.
	 */
	public void setFirstName(String _firstName) {
		this.firstName = _firstName ;
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
