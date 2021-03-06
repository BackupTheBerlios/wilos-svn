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

package wilos.model.misc.wilosuser ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.misc.project.Project ;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;

/**
 * This class represents a participant of a project This type of user can work on projects and
 * select a role from the process defined in relation with a project
 * 
 * @author BlackMilk
 * @author Mikamikaze
 * @author Sakamakak
 */
public class Participant extends WilosUser implements Cloneable {

	private Set<ConcreteRoleDescriptor> concreteRoleDescriptors ;

	private Set<Project> affectedProjectList ;

	private Set<Project> managedProjects ;

	public Participant() {
		super() ;
		concreteRoleDescriptors = new HashSet<ConcreteRoleDescriptor>() ;
		affectedProjectList = new HashSet<Project>() ;
		managedProjects = new HashSet<Project>() ;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Participant clone() throws CloneNotSupportedException {
		Participant participant = new Participant() ;
		participant.copy(this) ;
		return participant ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _administrator
	 *            The administrator to copy.
	 */
	protected void copy(final Participant _participant) {
		super.copy(_participant) ;
		this.setConcreteRoleDescriptors(_participant.getConcreteRoleDescriptors()) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof Participant == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		Participant participant = (Participant) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(participant)).append(this.concreteRoleDescriptors, participant.concreteRoleDescriptors).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/**
	 * adds a role descriptor to the Set
	 * @param _roleDescriptor
	 */
	public void addConcreteRoleDescriptor(ConcreteRoleDescriptor _roleDesc) {
		this.concreteRoleDescriptors.add(_roleDesc) ;
		_roleDesc.setParticipant(this) ;
	}

	/**
	 * remove a RoleDescriptor
	 * @param _roleDesc
	 */
	public void removeConcreteRoleDescriptor(ConcreteRoleDescriptor _roleDesc) {
		_roleDesc.setParticipant(null) ;
		this.concreteRoleDescriptors.remove(_roleDesc) ;		
	}

	/**
	 * Remove all the role descriptors
	 */
	public void removeAllConcreteRoleDescriptors() {
		for(ConcreteRoleDescriptor _roleD : this.concreteRoleDescriptors){
			_roleD.removeParticipant(this) ;
		}
		this.concreteRoleDescriptors.clear() ;
	}

	/**
	 * add participant to project
	 * 
	 * @param project
	 * 				the project to add to
	 */
	public void addAffectedProject(Project project) {
		this.affectedProjectList.add(project) ;
		project.getParticipants().add(this) ;
	}

	/**
	 * remove a participant from a project 
	 * 
	 * @param project
	 * 			the project to remove from
	 */
	public void removeAffectedProject(Project project) {
		this.affectedProjectList.remove(project) ;
		project.getParticipants().remove(this) ;
	}

	/**
	 * remove the participant from all the project
	 * 
	 */
	public void removeAllAffectedProjects() {
		for(Project project : this.affectedProjectList){
			project.removeParticipant(this) ;
		}
		this.affectedProjectList.clear() ;
	}
	
	/**
	 * 
	 * Add a managed project
	 *
	 * @param project
	 */
	public void addManagedProject(Project project) {
		this.managedProjects.add(project) ;
		project.setProjectManager(this) ;
	}

	/**
	 * 
	 * Remove a managed project
	 *
	 * @param project
	 */
	public void removeManagedProject(Project project) {
		this.managedProjects.remove(project) ;
		project.setProjectManager(null) ;
	}

	/**
	 * 
	 * Remove all managed projects
	 *
	 */
	public void removeAllManagedProjects() {
		for(Project project : this.managedProjects){
			project.removeFromProjectManager(this) ;
		}
		this.managedProjects.clear() ;
	}
	
	/* Getters & Setters */

	/**
	 * Getter of affectedProjectList.
	 *
	 * @return the affectedProjectList.
	 */
	public Set<Project> getAffectedProjectList() {
		return this.affectedProjectList ;
	}

	/**
	 * Setter of affectedProjectList.
	 *
	 * @param _affectedProjectList The affectedProjectList to set.
	 */
	public void setAffectedProjectList(Set<Project> _affectedProjectList) {
		this.affectedProjectList = _affectedProjectList ;
	}

	/**
	 * Getter of managedProjects.
	 *
	 * @return the managedProjects.
	 */
	public Set<Project> getManagedProjects() {
		return this.managedProjects ;
	}

	/**
	 * Setter of managedProjects.
	 *
	 * @param _managedProjects The managedProjects to set.
	 */
	public void setManagedProjects(Set<Project> _managedProjects) {
		this.managedProjects = _managedProjects ;
	}

	/**
	 * Getter of rolesListForAProject.
	 * @return the rolesListForAProject.
	 */
	public Set<ConcreteRoleDescriptor> getConcreteRoleDescriptors() {
		return this.concreteRoleDescriptors ;
	}

	/**
	 * Setter of rolesListForAProject.
	 * @param _rolesListForAProject
	 *            The rolesListForAProject to set.
	 */
	public void setConcreteRoleDescriptors(Set<ConcreteRoleDescriptor> _concreteRoleDescriptors) {
		this.concreteRoleDescriptors = _concreteRoleDescriptors ;
	}
}
