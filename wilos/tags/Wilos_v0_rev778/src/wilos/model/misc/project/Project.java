
package wilos.model.misc.project ;

import java.util.Date ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

/**
 * This class represents a project.
 * 
 * @author martial
 */
public class Project implements Cloneable {

	private String name ;

	private String description ;

	private Date creationDate ;

	private Date launchingDate ;
	
	public Project()
	{
		this.creationDate = new Date();
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate ;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate ;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description ;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description ;
	}

	/**
	 * @return the launchingDate
	 */
	public Date getLaunchingDate() {
		return launchingDate ;
	}

	/**
	 * @param launchingDate
	 *            the launchingDate to set
	 */
	public void setLaunchingDate(Date launchingDate) {
		this.launchingDate = launchingDate ;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name ;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name ;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 * @Override
	 */
	public Project clone() throws CloneNotSupportedException {
		Project project = new Project() ;
		project.copy(this) ;
		return project ;
	}

	
	/**
	 * Copy the object.
	 *
	 * @param _project The project to copy.
	 */
	protected void copy(final Project _project) {
		this.name = _project.name ;
		this.description = _project.description ;
		this.creationDate = _project.creationDate ;
		this.launchingDate = _project.launchingDate ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.name).append(this.description).append(this.creationDate).append(this.launchingDate).toHashCode() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object _obj) {
		if(_obj instanceof Project == false){
			return false ;
		}
		if(this == _obj){
			return true ;
		}
		Project project = (Project) _obj ;
		return new EqualsBuilder().append(this.name, project.name).append(this.description, project.description)
				.append(this.creationDate, project.creationDate).append(this.launchingDate, project.launchingDate).isEquals() ;
	}

}
