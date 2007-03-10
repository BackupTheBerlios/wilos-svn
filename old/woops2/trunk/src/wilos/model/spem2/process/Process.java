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

package wilos.model.spem2.process;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.ProcessManager;
import wilos.model.spem2.activity.Activity;

/**
 * 
 * A Process is a special Activity that describes a structure for particular
 * types of development projects. To perform such a development project a
 * Processes would be 'instantiated' and adapted for the specific situation.
 * Process is an abstract class and this meta-model defines different special
 * types of Processes for different process management applications and
 * different situations of process reuse.
 * 
 * @author soosuske
 * @author deder
 * 
 * 
 */
public class Process extends Activity implements Cloneable {

	// The Project of Process
	private Set<Project> projects;
	
	private ProcessManager processManager;
	
	private String folderPath;

	/**
	 * Default constructor
	 * 
	 */
	public Process() {
		super();
		this.projects = new HashSet<Project>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Process clone() throws CloneNotSupportedException {
		Process process = new Process();
		process.copy(this);
		return process;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _process
	 *            The process to copy.
	 */
	protected void copy(final Process _process) {
		super.copy(_process);
		this.processManager = _process.getProcessManager();
		this.projects.addAll(_process.getProjects());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Process == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Process process = (Process) obj;
		return new EqualsBuilder().appendSuper(super.equals(process)).append(this.projects, process.projects).append(this.processManager, process.processManager)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.processManager)
				.toHashCode();
	}

	/**
	 * remove a project
	 * 
	 * @param _project
	 */
	public void removeProject(Project _project) {
		_project.setProcess(null);
		this.projects.remove(_project);
	}

	/**
	 * add a project
	 * 
	 * @param _project
	 */
	public void addProject(Project _project) {
		this.projects.add(_project);
		_project.setProcess(this);
	}

	/**
	 * remove all project
	 * 
	 */
	public void removeAllProjects() {
		for (Project project : this.projects) {
			project.setProcess(null);
		}
		this.projects.clear();
	}

	/**
	 * Add a RoleDescriptor collection to the RoleDescriptor collection of an
	 * RoleDefinition
	 * 
	 * @param _role
	 */
	public void addAllProjects(Set<Project> _project) {
		for (Project _proj1 : _project) {
			_proj1.addProcess(this);
		}
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> project) {
		this.projects = project;
	}
	
	/*
	 * relation between Process et Project Manager
	 */
	
	/**
	 * add a ProcessManager
	 * @param ProcessManager
	 */
	public void addProcessManager(ProcessManager _processManager){
		   this.processManager = _processManager;
		   _processManager.getProcessesManaged().add(this);
		}

	/**
	 * remove a ProcessManager
	 * @param ProcessManager
	 */
	public void removeFromProcessManager(ProcessManager _processManager){
		   _processManager.getProcessesManaged().remove(this);
		   this.processManager = null;
		  
		}

	public ProcessManager getProcessManager() {
		return processManager;
	}

	public void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	
}
