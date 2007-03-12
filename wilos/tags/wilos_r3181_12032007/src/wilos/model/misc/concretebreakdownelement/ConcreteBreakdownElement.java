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

package wilos.model.misc.concretebreakdownelement;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.project.Project;
import wilos.model.spem2.breakdownelement.BreakdownElement;

public class ConcreteBreakdownElement implements Cloneable {

	private String id;

	private Project project;

	private String concreteName;

	private Set<ConcreteActivity> superConcreteActivities;

	private BreakdownElement breakdownElement;

	public ConcreteBreakdownElement() {
		this.superConcreteActivities = new HashSet<ConcreteActivity>();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	@Override
	public ConcreteBreakdownElement clone() throws CloneNotSupportedException {
		ConcreteBreakdownElement concreteBreakdownElement = new ConcreteBreakdownElement();
		concreteBreakdownElement.copy(this);
		return concreteBreakdownElement;
	}

	/**
	 * Copy the object.
	 *
	 */
	protected void copy(final ConcreteBreakdownElement _concreteBreakdownElement) {
		this.concreteName = _concreteBreakdownElement.getConcreteName();
		this.breakdownElement = _concreteBreakdownElement
				.getBreakdownElement();
		this.superConcreteActivities.addAll(_concreteBreakdownElement
				.getSuperConcreteActivities());
		this.project = _concreteBreakdownElement.getProject();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteBreakdownElement == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteBreakdownElement concreteBreakdownElement = (ConcreteBreakdownElement) obj;
		return new EqualsBuilder().append(this.concreteName,
				concreteBreakdownElement.concreteName).append(
				this.breakdownElement,
				concreteBreakdownElement.breakdownElement).append(
				this.superConcreteActivities,
				concreteBreakdownElement.superConcreteActivities).append(
				this.project, concreteBreakdownElement.project).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.concreteName).append(
				this.breakdownElement).append(this.project).toHashCode();
	}

	/*
	 * Relation between BreakdownElement and ConcreteBreakdownElement.
	 *
	 */

	public void addBreakdownElement(BreakdownElement _breakdownElement) {
		this.breakdownElement = _breakdownElement;
		_breakdownElement.getConcreteBreakdownElements().add(this);
	}

	public void removeBreakdownElement(BreakdownElement _breakdownElement) {
		_breakdownElement.getConcreteBreakdownElements().remove(this);
		this.breakdownElement = null;
	}

	/*
	 * Relation between ConcreteActivity and ConcreteBreakdownElement.
	 *
	 */

	public void addSuperConcreteActivity(ConcreteActivity _superConcreteActivity) {
		this.getSuperConcreteActivities().add(_superConcreteActivity);
		_superConcreteActivity.getConcreteBreakdownElements().add(this);
	}

	public void addAllSuperActivities(
			Set<ConcreteActivity> _superConcreteActivities) {
		for (ConcreteActivity concreteActivity : _superConcreteActivities) {
			concreteActivity.addConcreteBreakdownElement(this);
		}
	}

	public void removeSuperConcreteActivity(
			ConcreteActivity _superConcreteActivity) {
		_superConcreteActivity.getConcreteBreakdownElements().remove(this);
		this.getSuperConcreteActivities().remove(_superConcreteActivity);
	}

	public void removeAllSuperConcreteActivities() {
		for (ConcreteActivity concreteActivity : this
				.getSuperConcreteActivities())
			concreteActivity.getConcreteBreakdownElements().remove(this);
		this.getSuperConcreteActivities().clear();
	}

	/*
	 * Getter & Setter.
	 *
	 */

	public String getConcreteName() {
		return concreteName;
	}

	public void setConcreteName(String concreteName) {
		this.concreteName = concreteName;
	}

	public String getId() {
		return this.id;
	}

	@SuppressWarnings("unused")
	protected void setId(String _id) {
		this.id = _id;
	}

	public Set<ConcreteActivity> getSuperConcreteActivities() {
		return superConcreteActivities;
	}

	public void setSuperConcreteActivities(
			Set<ConcreteActivity> superConcreteActivities) {
		this.superConcreteActivities = superConcreteActivities;
	}

	public BreakdownElement getBreakdownElement() {
		return breakdownElement;
	}

	public void setBreakdownElement(BreakdownElement breakdownElement) {
		this.breakdownElement = breakdownElement;
	}

	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

}
