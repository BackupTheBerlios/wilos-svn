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

package wilos.model.spem2.phase;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.spem2.activity.Activity;

/**
 *
 * @author Soosuske
 *
 */
public class Phase extends Activity implements Cloneable{

	/**
	 * The ConcretePhases
	 */
	private Set<ConcretePhase> concretePhases;

	/**
	 * Default constructor
	 *
	 */
	public Phase() {
		super() ;
		this.concretePhases = new HashSet<ConcretePhase>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Phase clone() throws CloneNotSupportedException {
		Phase phase = new Phase() ;
		phase.copy(this) ;
		return phase ;
	}

	/**
	 * Copy the object.
	 *
	 * @param _phase
	 *            The iteration to copy.
	 */
	protected void copy(final Phase _phase) {
		super.copy(_phase) ;
		this.concretePhases.addAll(_phase.getConcretePhases());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Phase == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		Phase phase = (Phase) obj ;
		return new EqualsBuilder().appendSuper(super.equals(phase)).append(this.concretePhases,phase.concretePhases).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.activity.Activity#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/**
	 * relation between Phase and ConcretePhase
	 * @return
	 */
	public void removeConcretePhase(ConcretePhase _concretePhase) {
		_concretePhase.setPhase(null);
		this.concretePhases.remove(_concretePhase);
	}

	/**
	 * Add a concrete Phase
	 * @param _concretePhase
	 */

	public void addConcretePhase(ConcretePhase _concretePhase) {
		   this.concretePhases.add(_concretePhase);
		   _concretePhase.setPhase(this);
	}

	/**
	 * remove all concretePhase
	 *
	 */
	public void removeAllConcretePhase() {
		for (ConcretePhase tmp : this.concretePhases) {
			tmp.setPhase(null);
		}
		this.concretePhases.clear();
	}


	public void addAllConcretePhases(Set<ConcretePhase> _concretePhase) {
		for (ConcretePhase td : _concretePhase) {
			td.addPhase(this);
		}
	}

	public Set<ConcretePhase> getConcretePhases() {
		return concretePhases;
	}

	public void setConcretePhases(Set<ConcretePhase> concretePhases) {
		this.concretePhases = concretePhases;
	}

}
