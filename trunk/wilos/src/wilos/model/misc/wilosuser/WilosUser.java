/*
Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
*/
package wilos.model.misc.wilosuser ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * This class represents a user of Wilos.
 * 
 * @author Yoann Lopes
 */
public abstract class WilosUser implements Cloneable {

	private String wilosuser_id ;
	
	private String name ;

	private String firstname ;

	private String emailAddress ;

	private String login ;

	private String password ;

	/**
	 * Default Constructor.
	 * 
	 */
	public WilosUser() {
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

	/**
	 * Copy the object.
	 * 
	 * @param _element
	 *            The element to copy.
	 */
	protected void copy(final WilosUser _wilosUser) {
		this.name = _wilosUser.name ;
		this.firstname = _wilosUser.firstname ;
		this.emailAddress = _wilosUser.emailAddress ;
		this.login = _wilosUser.login ;
		this.password = _wilosUser.password ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof WilosUser == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		WilosUser wilosUser = (WilosUser) obj ;
		return new EqualsBuilder()	.append(this.name, wilosUser.name)
									.append(this.firstname, wilosUser.firstname)
									.append(this.emailAddress, wilosUser.emailAddress)
									.append(this.login, wilosUser.login)
									.append(this.password, wilosUser.password)
									.isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37)	.append(this.name)
											.append(this.firstname)
											.append(this.emailAddress)
											.append(this.login)
											.append(this.password)
											.toHashCode() ;
	}
	
	public String getWilosuser_id() {
		return wilosuser_id;
	}

	public void setWilosuser_id(String wilosuser_id) {
		this.wilosuser_id = wilosuser_id;
	}

}
