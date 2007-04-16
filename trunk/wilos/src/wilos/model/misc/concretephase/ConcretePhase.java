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

package wilos.model.misc.concretephase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.phase.Phase;

/**
 *
 * @author Soosuske
 *
 */
public class ConcretePhase extends ConcreteActivity implements Cloneable{

	private Phase phase;

	/**
	 * Default constructor
	 *
	 */
	public ConcretePhase() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ConcretePhase clone() throws CloneNotSupportedException {
		ConcretePhase concretePhase = new ConcretePhase() ;
		concretePhase.copy(this) ;
		return concretePhase ;
	}

	/**
	 * Copy the object.
	 *
	 * @param _cnocretePhase
	 *            The concretePhase to copy.
	 */
	protected void copy(final ConcretePhase _concretePhase) {
		super.copy(_concretePhase) ;
		this.phase = _concretePhase.getPhase();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.misc.concretephase.ConcretePhase#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof ConcretePhase == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		ConcretePhase concretePhase = (ConcretePhase) obj ;
		return new EqualsBuilder().appendSuper(super.equals(concretePhase)).append(this.phase, concretePhase.phase).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see wilos.model.misc.concretePhase.ConcretePhase#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.phase).toHashCode() ;
	}

	/**
	 * add a phase
	 * @param phase
	 */
	public void addPhase(Phase _phase){
		   this.phase = _phase;
		   _phase.getConcretePhases().add(this);
		}

	/**
	 * remove a phase
	 * @param phase
	 */
	public void removePhase(Phase _phase){
		  _phase.getConcretePhases().remove(this);
		   this.phase = null;

		}

	/**
	 * @return the phase
	 */
	public Phase getPhase() {
		return phase;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}



}

