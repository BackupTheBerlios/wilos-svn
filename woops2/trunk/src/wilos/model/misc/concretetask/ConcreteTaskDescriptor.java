package wilos.model.misc.concretetask;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptor {

	private String id;

	private String projectId;

	private String concreteName;

	private String state;

	private TaskDescriptor taskDescriptor;

	private Date plannedStartingDate;

	private Date plannedFinishingDate;

	private Date realStartingDate;

	private Date realFinishingDate;

	private float plannedTime;

	private float remainingTime;

	private float accomplishedTime;

	public ConcreteTaskDescriptor() {
		super();
		this.state = State.CREATED;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConcreteTaskDescriptor clone() throws CloneNotSupportedException {
		ConcreteTaskDescriptor concreteTaskDescriptor = new ConcreteTaskDescriptor();
		concreteTaskDescriptor.copy(this);
		return concreteTaskDescriptor;
	}

	/**
	 * Copy the _roleDescriptor into this.
	 */
	protected void copy(final ConcreteTaskDescriptor _concreteTaskDescriptor) {
		this.setAccomplishedTime(_concreteTaskDescriptor.getAccomplishedTime());
		this.setConcreteName(_concreteTaskDescriptor.getConcreteName());
		this.setId(_concreteTaskDescriptor.getId());
		this.setPlannedFinishingDate(_concreteTaskDescriptor
				.getPlannedFinishingDate());
		this.setPlannedStartingDate(_concreteTaskDescriptor
				.getPlannedStartingDate());
		this.setPlannedTime(_concreteTaskDescriptor.getPlannedTime());
		this.setProjectId(_concreteTaskDescriptor.getProjectId());
		this.setRealFinishingDate(_concreteTaskDescriptor
				.getRealFinishingDate());
		this.setRealStartingDate(_concreteTaskDescriptor.getRealStartingDate());
		this.setRemainingTime(_concreteTaskDescriptor.getRemainingTime());
		this.setState(_concreteTaskDescriptor.getState());
		this.setTaskDescriptor(_concreteTaskDescriptor.getTaskDescriptor());
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteTaskDescriptor == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteTaskDescriptor concreteTaskDescriptor = (ConcreteTaskDescriptor) obj;
		return new EqualsBuilder().append(this.accomplishedTime,
				concreteTaskDescriptor.accomplishedTime).append(
				this.concreteName, concreteTaskDescriptor.concreteName).append(
				this.plannedFinishingDate,
				concreteTaskDescriptor.plannedFinishingDate).append(
				this.plannedStartingDate,
				concreteTaskDescriptor.plannedStartingDate).append(
				this.plannedTime, concreteTaskDescriptor.plannedTime).append(
				this.projectId, concreteTaskDescriptor.projectId).append(
				this.realFinishingDate,
				concreteTaskDescriptor.realFinishingDate).append(
				this.realStartingDate, concreteTaskDescriptor.realStartingDate)
				.append(this.remainingTime,
						concreteTaskDescriptor.remainingTime).append(
						this.state, concreteTaskDescriptor.state).append(
						this.taskDescriptor,
						concreteTaskDescriptor.taskDescriptor).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.id).append(
				this.accomplishedTime).append(this.concreteName).append(
				this.plannedFinishingDate).append(this.plannedStartingDate)
				.append(this.plannedTime).append(this.projectId).append(
						this.realFinishingDate).append(this.realStartingDate)
				.append(this.remainingTime).append(this.taskDescriptor)
				.toHashCode();
	}

	public void addTaskDescriptor(TaskDescriptor _taskDescriptor) {
		this.taskDescriptor = _taskDescriptor;
		_taskDescriptor.getConcreteTaskDescriptors().add(this);
	}

	public void removeTaskDescriptor(TaskDescriptor _taskDescriptor) {
		_taskDescriptor.getConcreteTaskDescriptors().remove(this);
		this.taskDescriptor = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public TaskDescriptor getTaskDescriptor() {
		return taskDescriptor;
	}

	public void setTaskDescriptor(TaskDescriptor taskDescriptor) {
		this.taskDescriptor = taskDescriptor;
	}

	public float getAccomplishedTime() {
		return accomplishedTime;
	}

	public void setAccomplishedTime(float accomplishedTime) {
		this.accomplishedTime = accomplishedTime;
	}

	public String getConcreteName() {
		return concreteName;
	}

	public void setConcreteName(String concreteName) {
		this.concreteName = concreteName;
	}

	public Date getPlannedFinishingDate() {
		return plannedFinishingDate;
	}

	public void setPlannedFinishingDate(Date plannedFinishingDate) {
		this.plannedFinishingDate = plannedFinishingDate;
	}

	public Date getPlannedStartingDate() {
		return plannedStartingDate;
	}

	public void setPlannedStartingDate(Date plannedStartingDate) {
		this.plannedStartingDate = plannedStartingDate;
	}

	public float getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(float plannedTime) {
		this.plannedTime = plannedTime;
	}

	public Date getRealFinishingDate() {
		return realFinishingDate;
	}

	public void setRealFinishingDate(Date realFinishingDate) {
		this.realFinishingDate = realFinishingDate;
	}

	public Date getRealStartingDate() {
		return realStartingDate;
	}

	public void setRealStartingDate(Date realStartingDate) {
		this.realStartingDate = realStartingDate;
	}

	public float getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(float remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
