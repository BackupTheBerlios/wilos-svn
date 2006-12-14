
package wilos.model.spem2.element ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * 
 * Every class defined in this specification is derived from Element. In other words Element is the
 * root generalization for all UMA classes and defines a common set of attributes inherited by every
 * other element type of this model.
 * 
 * @author deder
 * 
 */
public class Element implements Cloneable {

	/**
	 * The id for the database mapping.
	 */
	private String id ;

	/**
	 * The global unique id for EPF.
	 */
	private String guid ;

	/**
	 * The name of the element.
	 */
	private String name ;

	/**
	 * The sentences describe this element.
	 */
	private String description ;

	/**
	 * Constructor.
	 * 
	 */
	public Element() {
		this.guid = "" ;
		this.name = "" ;
		this.description = "" ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Element clone() throws CloneNotSupportedException {
		Element element = new Element() ;
		element.copy(this) ;
		return element ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _element
	 *            The element to copy.
	 */
	protected void copy(final Element _element) {
		this.guid = _element.guid ;
		this.name = _element.name ;
		this.description = _element.description ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof Element == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		Element element = (Element) _obj ;
		return new EqualsBuilder().append(this.guid, element.guid).append(this.name, element.name).append(this.description, element.description).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.guid).append(this.name).append(this.description).toHashCode() ;
	}

	/**
	 * Getter of description.
	 * 
	 * @return the description.
	 */
	public String getDescription() {
		return this.description ;
	}

	/**
	 * Setter of description.
	 * 
	 * @param _description
	 *            The description to set.
	 */
	public void setDescription(String _description) {
		this.description = _description ;
	}

	/**
	 * Getter of id.
	 * 
	 * @return the id.
	 */
	public String getId() {
		return this.id ;
	}

	/**
	 * Setter of id.
	 * 
	 * @param _id
	 *            The id to set.
	 */
	@ SuppressWarnings ("unused")
	private void setId(String _id) {
		this.id = _id ;
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
	 * Getter of idEPF.
	 * 
	 * @return the idEPF.
	 */
	public String getGuid() {
		return this.guid ;
	}

	/**
	 * Setter of idEPF.
	 * 
	 * @param _guid
	 *            The idEPF to set.
	 */
	public void setGuid(String _guid) {
		this.guid = _guid ;
	}

}
