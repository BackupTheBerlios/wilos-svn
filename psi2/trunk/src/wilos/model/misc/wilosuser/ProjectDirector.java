
package wilos.model.misc.wilosuser ;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.misc.project.Project;
import wilos.model.spem2.process.Process;

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
	 * TODO Method description
	 *
	 * @param project
	 */
	public void addMonitoredProject(Project _project) {
		this.projectMonitored.add(_project) ;
		_project.setProjectDirector(this) ;
	}

	/**
	 * 
	 * TODO Method description
	 *
	 * @param project
	 */
	public void removeMonitoredProject(Project _project) {
		_project.setProjectDirector(null);
		this.projectMonitored.remove(_project);
		
	}

	/**
	 * 
	 * TODO Method description
	 *
	 */
	public void removeAllMonitoredProjects() {
		for(Project project : this.projectMonitored){
			project.removeProjectDirector(this) ;
		}
		this.projectMonitored.clear() ;
	}
}
