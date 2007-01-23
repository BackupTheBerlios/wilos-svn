package wilos.presentation.web.task;

import wilos.business.services.spem2.task.TaskDescriptorService;
import wilos.model.spem2.task.TaskDescriptor;
import java.util.Date;

public class ConcreteTaskViewerBean {
	private String id ;
	
	private String projectId ;
	
	private String state ;
	
	private String concreteName ;
	
	private Date plannedStartingDate ;
	
	private Date plannedFinishingDate ;
	
	private Date realStartingDate ;
	
	private Date realFinishingDate ;
	
	private float plannedTime ;
	
	private float accomplishedTime ;
	
	private float remainingTime ;

	/**
	 * @return the accomplishedTime
	 */
	public float getAccomplishedTime() {
		return this.accomplishedTime;
	}

	/**
	 * @param _accomplishedTime the accomplishedTime to set
	 */
	public void setAccomplishedTime(float _accomplishedTime) {
		this.accomplishedTime = _accomplishedTime;
	}

	/**
	 * @return the concreteName
	 */
	public String getConcreteName() {
		return this.concreteName;
	}

	/**
	 * @param _concreteName the concreteName to set
	 */
	public void setConcreteName(String _concreteName) {
		this.concreteName = _concreteName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param _id the id to set
	 */
	public void setId(String _id) {
		this.id = _id;
	}

	/**
	 * @return the plannedFinishingDate
	 */
	public Date getPlannedFinishingDate() {
		return this.plannedFinishingDate;
	}

	/**
	 * @param _plannedFinishingDate the plannedFinishingDate to set
	 */
	public void setPlannedFinishingDate(Date _plannedFinishingDate) {
		this.plannedFinishingDate = _plannedFinishingDate;
	}

	/**
	 * @return the plannedStartingDate
	 */
	public Date getPlannedStartingDate() {
		return this.plannedStartingDate;
	}

	/**
	 * @param _plannedStartingDate the plannedStartingDate to set
	 */
	public void setPlannedStartingDate(Date _plannedStartingDate) {
		this.plannedStartingDate = _plannedStartingDate;
	}

	/**
	 * @return the plannedTime
	 */
	public float getPlannedTime() {
		return this.plannedTime;
	}

	/**
	 * @param _plannedTime the plannedTime to set
	 */
	public void setPlannedTime(float _plannedTime) {
		this.plannedTime = _plannedTime;
	}

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return this.projectId;
	}

	/**
	 * @param _projectId the projectId to set
	 */
	public void setProjectId(String _projectId) {
		this.projectId = _projectId;
	}

	/**
	 * @return the realFinishingDate
	 */
	public Date getRealFinishingDate() {
		return this.realFinishingDate;
	}

	/**
	 * @param _realFinishingDate the realFinishingDate to set
	 */
	public void setRealFinishingDate(Date _realFinishingDate) {
		this.realFinishingDate = _realFinishingDate;
	}

	/**
	 * @return the realStartingDate
	 */
	public Date getRealStartingDate() {
		return this.realStartingDate;
	}

	/**
	 * @param _realStartingDate the realStartingDate to set
	 */
	public void setRealStartingDate(Date _realStartingDate) {
		this.realStartingDate = _realStartingDate;
	}

	/**
	 * @return the remainingTime
	 */
	public float getRemainingTime() {
		return this.remainingTime;
	}

	/**
	 * @param _remainingTime the remainingTime to set
	 */
	public void setRemainingTime(float _remainingTime) {
		this.remainingTime = _remainingTime;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * @param _state the state to set
	 */
	public void setState(String _state) {
		this.state = _state;
	}
}