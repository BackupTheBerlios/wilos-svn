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

package wilos.model.misc.concreteiteration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.iteration.Iteration;

/**
 * @author Sebastien
 *
 */
public class ConcreteIteration extends ConcreteActivity implements Cloneable {

	private Iteration iteration;

	/**
	 * Default constructor
	 *
	 */
	public ConcreteIteration() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ConcreteIteration clone() throws CloneNotSupportedException {
		ConcreteIteration concreteIteration = new ConcreteIteration() ;
		concreteIteration.copy(this) ;
		return concreteIteration ;
	}

	/**
	 * Copy the object.
	 *
	 * @param _iteration
	 *            The iteration to copy.
	 */
	protected void copy(final ConcreteIteration _concreteIteration) {
		super.copy(_concreteIteration) ;
		this.iteration = _concreteIteration.getIteration();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof ConcreteIteration == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		ConcreteIteration concreteIteration = (ConcreteIteration) obj ;
		return new EqualsBuilder().appendSuper(super.equals(concreteIteration)).append(this.iteration, concreteIteration.iteration).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.activity.Activity#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.iteration).toHashCode() ;
	}

	/**
	 * Attach a iteration to a concreteIteration
	 *
	 * @param _iteration
	 */
	public void addIteration(Iteration _iteration) {
		this.iteration = _iteration ;
		_iteration.getConcreteIterations().add(this) ;
	}

	/**
	 * Detach a taskDescriptor to its taskDefinition
	 *
	 * @param _taskDefinition
	 */
	public void removeIteration(Iteration _iteration) {
		_iteration.getConcreteIterations().remove(this) ;
		this.iteration = null ;
	}

	/**
	 * Get the iteration rattached to the concreteIteration
	 * @return
	 */
	public Iteration getIteration() {
		return iteration;
	}

	/**
	 * Set the iteration rattached to the concreteIteration
	 * @param iteration
	 */
	public void setIteration(Iteration iteration) {
		this.iteration = iteration;
	}

}
