
package wilos.model.misc.project ;

import java.util.Date ;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

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

	private Process process ;
	
	private Set<Participant> participants;

	public Project() {
		this.creationDate = new Date() ;
		this.launchingDate = new Date() ;
		this.participants = new HashSet<Participant>() ;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate ;
	}

	/**
	 * @param _process
	 *            the process to be linked to
	 */
	public void addToProcess(Process _process) {
		this.process = _process ;
		// _process.getProjects().add(this);
	}

	/**
	 * @param _process
	 *            the process to be unlinked to
	 */
	public void removeFromProcess(Process _process) {
		this.process = null ;
		// _process.getProjects().remove(this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone() @Override
	 */
	public Project clone() throws CloneNotSupportedException {
		Project project = new Project() ;
		project.copy(this) ;
		return project ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _project
	 *            The project to copy.
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

	/**
	 * Getter of process.
	 *
	 * @return the process.
	 */
	public Process getProcess() {
		return this.process ;
	}

	/**
	 * Setter of process.
	 *
	 * @param _process The process to set.
	 */
	public void setProcess(Process _process) {
		this.process = _process ;
	}

	/**
	 * Getter of participants.
	 *
	 * @return the participants.
	 */
	public Set<Participant> getParticipants() {
		return this.participants ;
	}

	/**
	 * Setter of participants.
	 *
	 * @param _participants The participants to set.
	 */
	public void setParticipants(Set<Participant> _participants) {
		this.participants = _participants ;
	}

	/**
	 * add participant to this project
	 * 
	 * @param participant
	 * 				the participant to add
	 */
	public void addToParticipant(Participant participant) {
		this.participants.add(participant) ;
		participant.getAffectedProjectList().add(this) ;
	}

	/**
	 * remove a project from a participant
	 * 
	 * @param participant
	 * 			the participant to remove from
	 */
	public void removeFromParticipant(Participant participant) {
		this.participants.remove(participant) ;
		participant.getAffectedProjectList().remove(this) ;
	}

	/**
	 * remove the project from all the participants
	 * 
	 */
	public void removeAllParticipant() {
		for(Participant participant : this.participants){
			participant.removeFromProject(this) ;
		}
		this.participants.clear() ;
	}

	/**
	 * remove all the participants from the list
	 *
	 */
	public void removeFromAllParticipant() {
		for(Participant participant : this.participants){
			this.removeFromParticipant(participant) ;
		}
	}
}
