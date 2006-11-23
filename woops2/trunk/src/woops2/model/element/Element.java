package woops2.model.element;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author deder
 * 
 * Every class defined in this specification is derived from Element. In other
 * words Element is the root generalization for all UMA classes and defines a
 * common set of attributes inherited by every other element type of this model.
 * 
 */
public class Element implements Cloneable {

	private String id;

	private String name;

	private String description;

	/**
	 * Constructor.
	 * 
	 */
	public Element() {
		// None
	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object _obj) {
		if (_obj instanceof Element == false) {
			return false;
		}
		if (this == _obj) {
			return true;
		}
		Element element = (Element) _obj;
		return new EqualsBuilder().append(this.name, element.name).append(
				this.description, element.description).isEquals();
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.name).append(
				this.description).toHashCode();
	}

	/**
	 * Copy the element into this.
	 */
	protected void copy(final Element _element) {
		this.setDescription(_element.getDescription());
		this.setName(_element.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Element element = new Element();
		element.copy(this);
		return element;
	}

	/**
	 * Getter of description.
	 * 
	 * @return the description.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Setter of description.
	 * 
	 * @param _description
	 *            The description to set.
	 */
	public void setDescription(String _description) {
		this.description = _description;
	}

	/**
	 * Getter of id.
	 * 
	 * @return the id.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Setter of id.
	 * 
	 * @param _id
	 *            The id to set.
	 */
	public void setId(String _id) {
		this.id = _id;
	}

	/**
	 * Getter of name.
	 * 
	 * @return the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter of name.
	 * 
	 * @param _name
	 *            The name to set.
	 */
	public void setName(String _name) {
		this.name = _name;
	}

}
