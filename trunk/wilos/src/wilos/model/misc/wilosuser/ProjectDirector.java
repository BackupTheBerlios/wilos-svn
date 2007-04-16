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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.misc.project.Project;

/**
 * This class represents a project director using Wilos.
 * 
 * @author Yoann Lopes
 */
public class ProjectDirector extends WilosUser implements Cloneable {

	private Set<Project> projectMonitored;
	
	public ProjectDirector() {
		this.projectMonitored = new HashSet<Project>();
	}

	/**
	 * Constructor.
	 * 
	 * @param _name
	 * @param _fName
	 * @param _email
	 * @param _login
	 * @param _password
	 */
	public ProjectDirector(String _name, String _fName, String _email, String _login, String _password) {
		super(_name, _fName, _email, _login, _password) ;
		this.projectMonitored = new HashSet<Project>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ProjectDirector clone() throws CloneNotSupportedException {
		ProjectDirector projectDirector = new ProjectDirector() ;
		projectDirector.copy(this) ;
		return projectDirector ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _projectDirector
	 *            The ProjectDirector to copy.
	 */
	protected void copy(final ProjectDirector _projectDirector) {
		super.copy(_projectDirector) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof ProjectDirector == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		ProjectDirector projectDirector = (ProjectDirector) _obj ;
		return new EqualsBuilder().appendSuper(super.equals(projectDirector)).append(this.projectMonitored,projectDirector.projectMonitored).isEquals() ;
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
	 * Getter of projectMonitored.
	 *
	 * @return the projectMonitored.
	 */
	public Set<Project> getProjectMonitored() {
		return this.projectMonitored ;
	}

	/**
	 * Setter of projectMonitored.
	 *
	 * @param _projectMonitored The projectMonitored to set.
	 */
	public void setProjectMonitored(Set<Project> _projectMonitored) {
		this.projectMonitored = _projectMonitored ;
	}
	
	/**
	 * 
	 * Add a monitored project in a set of project
	 *
	 * @param project
	 */
	public void addMonitoredProject(Project _project) {
		this.projectMonitored.add(_project) ;
		_project.setProjectDirector(this) ;
	}

	/**
	 * 
	 * Remove a monitored project
	 *
	 * @param project
	 */
	public void removeMonitoredProject(Project _project) {
		_project.setProjectDirector(null);
		this.projectMonitored.remove(_project);
		
	}

	/**
	 * 
	 * Remove all monitored projects
	 *
	 */
	public void removeAllMonitoredProjects() {
		for(Project project : this.projectMonitored){
			project.removeProjectDirector(this) ;
		}
		this.projectMonitored.clear() ;
	}
}
