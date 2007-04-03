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

package wilos.model.misc.concreteworkbreakdownelement;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

/**
 *
 * A Concrete Work Breakdown Element is a special Concrete Breakdown Element
 *
 * @author eperico
 *
 */
public class ConcreteWorkBreakdownElement extends ConcreteBreakdownElement
		implements Cloneable {

	private boolean isInUsed;

	private Date plannedStartingDate;

	private Date plannedFinishingDate;

	private float plannedTime;

	private WorkBreakdownElement workBreakdownElement;

	private Set<ConcreteWorkOrder> concretePredecessors;

	private Set<ConcreteWorkOrder> concreteSuccessors;

	public ConcreteWorkBreakdownElement() {
		super();
		this.isInUsed = true;
		this.concretePredecessors = new HashSet<ConcreteWorkOrder>();
		this.concreteSuccessors = new HashSet<ConcreteWorkOrder>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.misc.concretebreakdownelement.ConcreteBreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteWorkBreakdownElement == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteWorkBreakdownElement concreteworkBreakdownElement = (ConcreteWorkBreakdownElement) obj;
		return new EqualsBuilder().appendSuper(
				super.equals(concreteworkBreakdownElement)).append(
				this.isInUsed, concreteworkBreakdownElement.isInUsed).append(
				this.plannedFinishingDate,
				concreteworkBreakdownElement.plannedFinishingDate).append(
				this.plannedStartingDate,
				concreteworkBreakdownElement.plannedStartingDate).append(
				this.plannedTime, concreteworkBreakdownElement.plannedTime)
				.append(this.concretePredecessors,
						concreteworkBreakdownElement.concretePredecessors)
				.append(this.concreteSuccessors,
						concreteworkBreakdownElement.concreteSuccessors)
				.append(this.workBreakdownElement,
						concreteworkBreakdownElement.workBreakdownElement)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.misc.concretebreakdownelement.ConcreteBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.isInUsed).append(this.plannedFinishingDate)
				.append(this.plannedStartingDate).append(this.plannedTime)
				.append(this.workBreakdownElement).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConcreteWorkBreakdownElement clone()
			throws CloneNotSupportedException {
		ConcreteWorkBreakdownElement concreteworkBreakdownElement = new ConcreteWorkBreakdownElement();
		concreteworkBreakdownElement.copy(this);
		return concreteworkBreakdownElement;
	}

	/**
	 * Copy the _concreteworkBreakdownElement into this.
	 */
	protected void copy(
			final ConcreteWorkBreakdownElement _concreteWorkBreakdownElement) {
		super.copy(_concreteWorkBreakdownElement);
		this.isInUsed = _concreteWorkBreakdownElement.getIsInUsed();
		this.workBreakdownElement = _concreteWorkBreakdownElement
				.getWorkBreakdownElement();
		this.plannedFinishingDate = _concreteWorkBreakdownElement
				.getPlannedFinishingDate();
		this.plannedStartingDate = _concreteWorkBreakdownElement
				.getPlannedStartingDate();
		this.plannedTime = _concreteWorkBreakdownElement.getPlannedTime();
		this.concretePredecessors.addAll(_concreteWorkBreakdownElement
				.getConcretePredecessors());
		this.concreteSuccessors.addAll(_concreteWorkBreakdownElement
				.getConcreteSuccessors());
	}

	/*
	 * Relation between WorkBreakdownElement and ConcreteWorkBreakdownElement.
	 *
	 */

	public void addWorkBreakdownElement(
			WorkBreakdownElement _workbreakdownElement) {
		this.setWorkBreakdownElement(_workbreakdownElement);
		_workbreakdownElement.getConcreteWorkBreakdownElements().add(this);
	}

	public void removeWorkBreakdownElement(
			WorkBreakdownElement _workbreakdownElement) {
		_workbreakdownElement.getConcreteWorkBreakdownElements().remove(this);
		this.workBreakdownElement = null;
	}

	/*
	 * Relation between ConcreteWorkBreakdownElement and ConcreteWorkOrder.
	 *
	 */

	/**
	 * Add a concreteSuccesor to the concreteSuccessors collection of a
	 * concreteWorkOrder
	 *
	 * @param _concreteWorkOrder
	 */
	public void addConcreteSuccessor(ConcreteWorkOrder _concreteWorkOrder) {
		this.concreteSuccessors.add(_concreteWorkOrder);
		_concreteWorkOrder.setConcretePredecessor(this);
	}

	/**
	 * Remove from a ConcreteWbde one of these concreteSuccessor.
	 *
	 * @param _concreteWorkOrder
	 *            The concreteSuccessor to remove.
	 */
	public void removeConcreteSuccessor(ConcreteWorkOrder _concreteWorkOrder) {
		_concreteWorkOrder.setConcretePredecessor(null);
		this.concreteSuccessors.remove(_concreteWorkOrder);
	}

	/**
	 * Add a concreteSuccessors collection into the ConcreteWorkOrder successors
	 * collection.
	 *
	 * @param _concreteWorkOrder
	 *            The set of concreteSuccessors to add.
	 */
	public void addAllConcreteSuccessors(
			Set<ConcreteWorkOrder> _concreteWorkOrder) {
		for (ConcreteWorkOrder cwo : _concreteWorkOrder) {
			this.addConcreteSuccessor(cwo);
		}
	}

	/**
	 * Remove from a ConcreteWbde all its ConcreteWorkOrder successors.
	 *
	 */
	public void removeAllConcreteSuccessors() {
		for (ConcreteWorkOrder cwo : this.getConcreteSuccessors())
			cwo.setConcretePredecessor(null);
		this.getConcreteSuccessors().clear();
	}

	/**
	 * Add a concretePredecessor into the ConcreteWorkOrder predecessors
	 * collection.
	 *
	 * @param _concreteWorkOrder
	 *            The concretePredecessor to add.
	 */
	public void addConcretePredecessor(ConcreteWorkOrder _concreteWorkOrder) {
		this.concretePredecessors.add(_concreteWorkOrder);
		_concreteWorkOrder.setConcreteSuccessor(this);
	}

	/**
	 * Remove from a ConcreteWbde one of these concretePredecessor.
	 *
	 * @param _concreteWorkOrder
	 *            The concretePredecessor to remove.
	 */
	public void removeConcretePredecessor(ConcreteWorkOrder _concreteWorkOrder) {
		_concreteWorkOrder.setConcreteSuccessor(null);
		this.concretePredecessors.remove(_concreteWorkOrder);
	}

	/**
	 * Add a concretePredecessor collection into the ConcreteWorkOrder
	 * predecessors collection.
	 *
	 * @param _concreteWorkOrder
	 *            The set of concretePredecessors to add.
	 */
	public void addAllConcretePredecessors(
			Set<ConcreteWorkOrder> _concreteWorkOrder) {
		for (ConcreteWorkOrder cwo : _concreteWorkOrder) {
			this.addConcretePredecessor(cwo);
		}
	}

	/**
	 * Remove from an ConcreteWbde all its ConcreteWorkOrder successors.
	 *
	 */
	public void removeAllConcretePredecessors() {
		for (ConcreteWorkOrder cwo : this.getConcretePredecessors())
			cwo.setConcreteSuccessor(null);
		this.getConcretePredecessors().clear();
	}

	public WorkBreakdownElement getWorkBreakdownElement() {
		return workBreakdownElement;
	}

	public void setWorkBreakdownElement(
			WorkBreakdownElement _workbreakdownelement) {
		this.workBreakdownElement = _workbreakdownelement;
	}

	public Date getPlannedFinishingDate() {
		return plannedFinishingDate;
	}

	public void setPlannedFinishingDate(Date plannedFinishingDate) {
		this.plannedFinishingDate = plannedFinishingDate;
	}

	public Date getPlannedStartingDate() {
		return plannedStartingDate;
	}

	public void setPlannedStartingDate(Date plannedStartingDate) {
		this.plannedStartingDate = plannedStartingDate;
	}

	public float getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(float plannedTime) {
		this.plannedTime = plannedTime;
	}

	/**
	 * @return the concretePredecessors
	 */
	public Set<ConcreteWorkOrder> getConcretePredecessors() {
		return concretePredecessors;
	}

	/**
	 * @param concretePredecessors
	 *            the concretePredecessors to set
	 */
	public void setConcretePredecessors(
			Set<ConcreteWorkOrder> _concretePredecessors) {
		this.concretePredecessors = _concretePredecessors;
	}

	/**
	 * @return the concreteSuccessors
	 */
	public Set<ConcreteWorkOrder> getConcreteSuccessors() {
		return concreteSuccessors;
	}

	/**
	 * @param concreteSuccessors
	 *            the concreteSuccessors to set
	 */
	public void setConcreteSuccessors(Set<ConcreteWorkOrder> _concreteSuccessors) {
		this.concreteSuccessors = _concreteSuccessors;
	}

	/**
	 * @return the isInUsed
	 */
	public boolean getIsInUsed() {
		return isInUsed;
	}

	/**
	 * @param isInUsed
	 *            the isInUsed to set
	 */
	public void setIsInUsed(boolean _isInUsed) {
		this.isInUsed = _isInUsed;
	}
}
