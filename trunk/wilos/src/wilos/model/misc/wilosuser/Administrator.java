/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 
 */

package wilos.model.misc.wilosuser ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * This class represents the administrator of Wilos. It hasn't been tested because it was too light. 
 * @author Yoann Lopes
 * @author MiKaMiKaZe
 */
public class Administrator extends WilosUser implements Cloneable {

	/**
	 * Default Constructor.
	 * 
	 */
	public Administrator() {
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
	public Administrator(String _name, String _fName, String _email, String _login, String _password) {
		super(_name, _fName, _email, _login, _password) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Administrator clone() throws CloneNotSupportedException {
		Administrator administrator = new Administrator() ;
		administrator.copy(this) ;
		return administrator ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _administrator
	 *            The administrator to copy.
	 */
	protected void copy(final Administrator _administrator) {
		super.copy(_administrator) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof Administrator == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		Administrator administrator = (Administrator) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(administrator)).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}
}
