
package wilos.model.misc.wilosuser ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * This class represents the administrator of Wilos.
 * 
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

	/* (non-Javadoc)
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
	 * @param _administrator The administrator to copy.
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
