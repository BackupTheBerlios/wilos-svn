package wilos.model.misc.project ;

import java.util.Date ;
import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.wilosuser.Participant ;
import wilos.model.misc.wilosuser.ProjectDirector;

/**
 * This class represents a project.
 * 
 * @author martial
 */
public class Project extends ConcreteActivity implements Cloneable {

	private String description ;

	private Date creationDate ;

	private Date launchingDate ;

	private Boolean isFinished ;

	private wilos.model.spem2.process.Process process ;

	private Set<Participant> participants ;
	
	private Participant projectManager;
	
	private ProjectDirector projectDirector;
	
	public Project() {
		this.creationDate = new Date() ;
		this.launchingDate = new Date() ;
		this.participants = new HashSet<Participant>() ;
		this.isFinished = false ;
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
	public void addProcess(wilos.model.spem2.process.Process _process) {
		this.process = _process ;
		_process.getProjects().add(this) ;
	}

	/**
	 * @param _process
	 *            the process to be unlinked to
	 */
	public void removeFromProcess(wilos.model.spem2.process.Process _process) {
		this.process = null ;
		_process.getProjects().remove(this) ;
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
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
											.append(this.description)
											.append(this.creationDate)
											.append(this.launchingDate)
											.append(this.isFinished)
											.append(this.process)
											.append(this.projectManager)
											.toHashCode() ;
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
		return new EqualsBuilder().appendSuper(super.equals(project))
									.append(this.description, project.description)
									.append(this.creationDate, project.creationDate)
									.append(this.launchingDate, project.launchingDate)
									.append(this.isFinished, project.isFinished)
									.append(this.process, project.process)
									.append(this.participants, project.participants)
									.append(this.projectManager, project.projectManager)
									.isEquals() ;
	
	}

	/**
	 * Getter of process.
	 * 
	 * @return the process.
	 */
	public wilos.model.spem2.process.Process getProcess() {
		return this.process ;
	}

	/**
	 * Setter of process.
	 * 
	 * @param _process
	 *            The process to set.
	 */
	public void setProcess(wilos.model.spem2.process.Process _process) {
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
	 * @param _participants
	 *            The participants to set.
	 */
	public void setParticipants(Set<Participant> _participants) {
		this.participants = _participants ;
	}

	public Participant getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(Participant projectManager) {
		this.projectManager = projectManager;
	}

	/**
	 * add participant to this project
	 * 
	 * @param participant
	 *            the participant to add
	 */
	public void addToParticipant(Participant participant) {
		this.participants.add(participant) ;
		participant.getAffectedProjectList().add(this) ;
	}

	/**
	 * remove a project from a participant
	 * 
	 * @param participant
	 *            the participant to remove from
	 */
	public void removeFromParticipant(Participant participant) {		
		participant.getAffectedProjectList().remove(this) ;
		this.participants.remove(participant) ;
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

	/**
	 * Getter of isFinished.
	 * 
	 * @return the isFinished.
	 */
	public Boolean getIsFinished() {
		return this.isFinished ;
	}

	/**
	 * Setter of isFinished.
	 * 
	 * @param _isFinished
	 *            The isFinished to set.
	 */
	public void setIsFinished(Boolean _isFinished) {
		this.isFinished = _isFinished ;
	}
	
	/**
	 * 
	 * TODO Method : A voir très bizarre (methode non utilise?)
	 *
	 * @param projectManager
	 */
	public void addToProjectManager(Participant projectManager){
		   this.projectManager = projectManager;
		   projectManager.getManagedProjects().add(this);
	}
	
	/**
	 * 
	 * TODO Method : A voir très bizarre (methode non utilise?)
	 *
	 * @param projectManager
	 */
	public void removeFromProjectManager(Participant projectManager){
		   this.projectManager = null;
		   projectManager.getManagedProjects().remove(this);
		}

	/**
	 * Getter of projectDirector.
	 *
	 * @return the projectDirector.
	 */
	public ProjectDirector getProjectDirector() {
		return this.projectDirector ;
	}

	/**
	 * Setter of projectDirector.
	 *
	 * @param _projectDirector The projectDirector to set.
	 */
	public void setProjectDirector(ProjectDirector _projectDirector) {
		this.projectDirector = _projectDirector ;
	}
	
	/**
	 * 
	 * Add a project director 
	 *
	 * @param project
	 */
	public void addProjectDirector(ProjectDirector _projectDirector) {
		this.projectDirector = _projectDirector;
		_projectDirector.getProjectMonitored().add(this) ;
	}

	/**
	 * 
	 * Remove a project director
	 *
	 * @param project
	 */
	public void removeProjectDirector(ProjectDirector _projectDirector) {
		_projectDirector.getProjectMonitored().remove(this);
		this.projectDirector  = null;
	}

}
