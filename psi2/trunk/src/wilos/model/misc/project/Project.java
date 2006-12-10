
package wilos.model.misc.project ;

import java.util.Date ;

/**
 * @author martial
 * 
 * This class represents a project.
 * 
 */
public class Project {

	private String name ;

	private String description ;

	private Date creationDate ;

	private Date launchingDate ;

	/**
	 * Constructor.
	 *
	 */
	public Project() {
		// Project's Creation Date is current date time
		this.creationDate = new Date() ;
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

}
