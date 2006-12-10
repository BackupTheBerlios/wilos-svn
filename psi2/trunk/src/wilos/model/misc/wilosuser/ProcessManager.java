
package wilos.model.misc.wilosuser ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * This class represents a process manager using Wilos.
 * 
 * @author Yoann Lopes
 */
public class ProcessManager extends WilosUser implements Cloneable{

	/**
	 * Default Constructor.
	 * 
	 */
	public ProcessManager() {
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
	public ProcessManager(String _name, String _fName, String _email, String _login, String _password) {
		super(_name, _fName, _email, _login, _password) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof ProcessManager == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		ProcessManager processManager = (ProcessManager) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(processManager)).isEquals() ;
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
