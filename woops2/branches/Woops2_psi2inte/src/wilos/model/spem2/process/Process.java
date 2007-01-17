package wilos.model.spem2.process;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.project.Project;
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
		return new EqualsBuilder().appendSuper(super.equals(process))
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
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
			project.removeFromProcess(this);
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
}
