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

package wilos.model.spem2.element;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * Every class defined in this specification is derived from Element. In other
 * words Element is the root generalization for all UMA classes and defines a
 * common set of attributes inherited by every other element type of this model.
 *
 * @author deder
 *
 */
public class Element implements Cloneable {

	/**
	 * The id for the database mapping.
	 */
	private String id;

	/**
	 * The global unique id for EPF.
	 */
	private String guid;

	/**
	 * The name of the element.
	 */
	private String name;

	/**
	 * The sentences describe this element.
	 */
	private String description;

	/**
	 * The main description of the element
	 */
	private String mainDescription;

	/**
	 * the key considerations of the element
	 */
	private String keyConsiderations;

	/**
	 * the insertion order of the element
	 */
	private int insertionOrder;

	/**
	 * Constructor.
	 *
	 */
	public Element() {
		this.guid = "";
		this.name = "";
		this.description = "";
		this.mainDescription = "";
		this.keyConsiderations = "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Element clone() throws CloneNotSupportedException {
		Element element = new Element();
		element.copy(this);
		return element;
	}

	/**
	 * Copy the object.
	 *
	 * @param _element
	 *            The element to copy.
	 */
	protected void copy(final Element _element) {
		this.guid = _element.guid;
		this.name = _element.name;
		this.description = _element.description;
		this.keyConsiderations = _element.keyConsiderations;
		this.mainDescription = _element.mainDescription;
		this.insertionOrder = _element.insertionOrder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if (_obj instanceof Element == false) {
			return false;
		}
		if (this == _obj) {
			return true;
		}
		Element element = (Element) _obj;
		return new EqualsBuilder().append(this.guid, element.guid).append(
				this.name, element.name).append(this.description,
				element.description).append(this.keyConsiderations,
				element.keyConsiderations).append(this.mainDescription,
				element.mainDescription).append(this.insertionOrder,
				element.insertionOrder).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.guid).append(this.name)
				.append(this.description).append(this.keyConsiderations)
				.append(this.mainDescription).append(this.insertionOrder)
				.toHashCode();
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
	@SuppressWarnings("unused")
	private void setId(String _id) {
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

	/**
	 * Getter of idEPF.
	 *
	 * @return the idEPF.
	 */
	public String getGuid() {
		return this.guid;
	}

	/**
	 * Setter of idEPF.
	 *
	 * @param _guid
	 *            The idEPF to set.
	 */
	public void setGuid(String _guid) {
		this.guid = _guid;
	}

	/**
	 * Getter of KeyConsiderations
	 *
	 * @return the KeyConsiderations
	 */
	public String getKeyConsiderations() {
		return keyConsiderations;
	}

	/**
	 * Setter of KeyConsideration
	 *
	 * @param _keyConsiderations
	 *            The KeyConsideration to set
	 */
	public void setKeyConsiderations(String _keyConsiderations) {
		keyConsiderations = _keyConsiderations;
	}

	/**
	 * Getter on MainDescription
	 *
	 * @return the MainDescription
	 */
	public String getMainDescription() {
		return mainDescription;
	}

	/**
	 * Setter of the MainDescription
	 *
	 * @param _mainDescription
	 *            The MainDescription to set
	 */
	public void setMainDescription(String _mainDescription) {
		mainDescription = _mainDescription;
	}

	/**
	 * @return the insertionOrder
	 */
	public int getInsertionOrder() {
		return this.insertionOrder;
	}

	/**
	 * @param _insertionOrder
	 *            the insertionOrder to set
	 */
	public void setInsertionOrder(int _insertionOrder) {
		this.insertionOrder = _insertionOrder;
	}

}
