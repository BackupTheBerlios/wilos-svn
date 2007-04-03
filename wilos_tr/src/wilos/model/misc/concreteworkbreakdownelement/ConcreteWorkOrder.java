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

/**
 * 
 * @author Sebastien
 *
 */
public class ConcreteWorkOrder {

	private String concreteLinkType;
	
	private ConcreteWorkBreakdownElement concretePredecessor;
	
	private ConcreteWorkBreakdownElement concreteSuccessor;
	
	public ConcreteWorkOrder(){
		//None.
	}

	/**
	 * @return the concretePredecessor
	 */
	public ConcreteWorkBreakdownElement getConcretePredecessor() {
		return this.concretePredecessor;
	}

	/**
	 * @param concretePredecessor the concretePredecessor to set
	 */
	public void setConcretePredecessor(
			ConcreteWorkBreakdownElement _concretePredecessor) {
		this.concretePredecessor = _concretePredecessor;
	}

	/**
	 * @return the concreteSuccessor
	 */
	public ConcreteWorkBreakdownElement getConcreteSuccessor() {
		return this.concreteSuccessor;
	}

	/**
	 * @param concreteSuccessor the concreteSuccessor to set
	 */
	public void setConcreteSuccessor(ConcreteWorkBreakdownElement concreteSuccessor) {
		this.concreteSuccessor = concreteSuccessor;
	}

	public String getConcreteLinkType() {
		return this.concreteLinkType;
	}

	public void setConcreteLinkType(String _concreteLinkType) {
		this.concreteLinkType = _concreteLinkType;
	}
	
}
