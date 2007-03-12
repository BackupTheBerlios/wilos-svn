/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
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

package wilos.model.misc.concreteactivity;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.activity.Activity;

/**
 *
 * Concrete instanciation of a SPEM2 Activity
 *
 * @author garwind
 *
 */
public class ConcreteActivity extends ConcreteWorkBreakdownElement implements
		Cloneable {

	private Set<ConcreteBreakdownElement> concreteBreakdownElements;

	private Activity activity;

	/**
	 * Constructor.
	 *
	 */
	public ConcreteActivity() {
		super();
		this.concreteBreakdownElements = new HashSet<ConcreteBreakdownElement>();
	}

	public boolean equals(Object obj) {
		if (obj instanceof ConcreteActivity == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteActivity concreteActivity = (ConcreteActivity) obj;
		return new EqualsBuilder().appendSuper(super.equals(concreteActivity))
				.append(this.activity, concreteActivity.activity).append(
						this.concreteBreakdownElements,
						concreteActivity.concreteBreakdownElements).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.activity).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConcreteActivity clone() throws CloneNotSupportedException {
		ConcreteActivity concreteActivity = new ConcreteActivity();
		concreteActivity.copy(this);
		return concreteActivity;
	}

	/**
	 * Copy the object.
	 *
	 * @param _activity
	 *            The Activity to copy.
	 */
	protected void copy(final ConcreteActivity _concreteActivity) {
		super.copy(_concreteActivity);
		this.activity = _concreteActivity.getActivity();
		this.concreteBreakdownElements.addAll(_concreteActivity
				.getConcreteBreakdownElements());
	}

	/*
	 * Relation between ConcreteActivity and ConcreteBreakdownElement.
	 *
	 */

	public void addConcreteBreakdownElement(
			ConcreteBreakdownElement _concreteBeakdownElement) {
		this.getConcreteBreakdownElements().add(_concreteBeakdownElement);
		_concreteBeakdownElement.getSuperConcreteActivities().add(this);
	}

	public void addAllConcreteBreakdownElements(
			Set<ConcreteBreakdownElement> _concreteBreakdownElements) {
		for (ConcreteBreakdownElement cbde : _concreteBreakdownElements) {
			cbde.addSuperConcreteActivity(this);
		}
	}

	public void removeConcreteBreakdownElement(
			ConcreteBreakdownElement _concreteBreakdownElement) {
		_concreteBreakdownElement.getSuperConcreteActivities().remove(this);
		this.getConcreteBreakdownElements().remove(_concreteBreakdownElement);
	}

	public void removeAllConcreteBreakdownElements() {
		for (ConcreteBreakdownElement bde : this.getConcreteBreakdownElements())
			bde.getSuperConcreteActivities().remove(this);
		this.getConcreteBreakdownElements().clear();
	}

	/*
	 * Relation between ConcreteActivity and Activity.
	 *
	 */

	public void addActivity(Activity _activity) {
		this.activity = _activity;
		_activity.addConcreteActivity(this);
	}

	public void removeActivity(Activity _activity) {
		this.activity = null;
		_activity.removeConcreteActivity(this);
	}

	/*
	 * Getter & Setter.
	 *
	 */

	public Set<ConcreteBreakdownElement> getConcreteBreakdownElements() {
		return concreteBreakdownElements;
	}

	public void setConcreteBreakdownElements(
			Set<ConcreteBreakdownElement> concreteBreakdownElements) {
		this.concreteBreakdownElements = concreteBreakdownElements;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
