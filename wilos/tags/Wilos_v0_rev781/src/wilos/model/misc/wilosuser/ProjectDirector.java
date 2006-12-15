
package wilos.model.misc.wilosuser ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * This class represents a project director using Wilos.
 * 
 * @author Yoann Lopes
 */
public class ProjectDirector extends WilosUser implements Cloneable {

	public ProjectDirector() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ProjectDirector clone() throws CloneNotSupportedException {
		ProjectDirector projectDirector = new ProjectDirector() ;
		projectDirector.copy(this) ;
		return projectDirector ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _projectDirector
	 *            The ProjectDirector to copy.
	 */
	protected void copy(final ProjectDirector _projectDirector) {
		super.copy(_projectDirector) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof ProjectDirector == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		ProjectDirector projectDirector = (ProjectDirector) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(projectDirector)).isEquals() ;
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
