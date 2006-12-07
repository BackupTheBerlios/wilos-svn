
package wilos.model.spem2.methodelement ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.spem2.element.Element;

/**
 * A MethodElement is a special Element that shall be maintained in a MethodLibrary as a separate
 * unit of control.
 * 
 * @author deder
 * 
 */
public class MethodElement extends Element {

	/**
	 * Every MethodElement is being created and owned by an author or authoring team.
	 */
	private String authors ;

	/**
	 * Every Package has a version number used to track changes.
	 */
	private String version ;

	/**
	 * The date the last change that resulted into this version has been made.
	 */
	private String changeDate ;

	/**
	 * The description of the last change that resulted into this version.
	 */
	private String changeDescription ;

	/**
	 * Default Constructor.
	 */
	public MethodElement() {
		super() ;
		this.authors = "" ;
		this.version = "" ;
		this.changeDate = "" ;
		this.changeDescription = "" ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#clone()
	 */
	@ Override
	public MethodElement clone() throws CloneNotSupportedException {
		MethodElement methodElement = new MethodElement() ;
		methodElement.copy(this) ;
		return methodElement ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#copy(woops2.model.element.Element)
	 */
	protected void copy(MethodElement _methodElement) {
		super.copy(_methodElement) ;
		this.authors = _methodElement.getAuthors() ;
		this.changeDate = _methodElement.getChangeDate() ;
		this.changeDescription = _methodElement.getChangeDescription() ;
		this.version = _methodElement.getVersion() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#equals(java.lang.Object)
	 */
	@ Override
	public boolean equals(Object _obj) {
		if(_obj instanceof MethodElement == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		MethodElement methodElement = (MethodElement) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(methodElement)).append(this.authors, methodElement.getAuthors()).append(this.changeDescription,
				methodElement.getChangeDescription()).append(this.version, methodElement.getVersion()).append(this.changeDate, methodElement.getChangeDate())
				.isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#hashCode()
	 */
	@ Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.authors).append(this.changeDate).append(this.changeDescription).append(
				this.version).toHashCode() ;
	}

	/**
	 * @return the authors
	 */
	public String getAuthors() {
		return this.authors ;
	}

	/**
	 * Setter of authors.
	 * 
	 * @param _authors
	 *            The authors to set.
	 */
	public void setAuthors(String _authors) {
		this.authors = _authors ;
	}

	/**
	 * @return the changeDate
	 */
	public String getChangeDate() {
		return this.changeDate ;
	}

	/**
	 * Setter of changeDate.
	 * 
	 * @param _changeDate
	 *            The changeDate to set.
	 */
	public void setChangeDate(String _changeDate) {
		this.changeDate = _changeDate ;
	}

	/**
	 * @return the changeDescription
	 */
	public String getChangeDescription() {
		return this.changeDescription ;
	}

	/**
	 * Setter of changeDescription.
	 * 
	 * @param _changeDescription
	 *            The changeDescription to set.
	 */
	public void setChangeDescription(String _changeDescription) {
		this.changeDescription = _changeDescription ;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version ;
	}

	/**
	 * Setter of version.
	 * 
	 * @param _version
	 *            The version to set.
	 */
	public void setVersion(String _version) {
		this.version = _version ;
	}
}
