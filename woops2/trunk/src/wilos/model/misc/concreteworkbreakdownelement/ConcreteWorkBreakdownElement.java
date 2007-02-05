package wilos.model.misc.concreteworkbreakdownelement;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;
import wilos.utils.Constantes.State;

/**
 *
 * A Concrete Work Breakdown Element is a special Concrete Breakdown Element
 *
 * @author eperico
 *
 */
public class ConcreteWorkBreakdownElement extends ConcreteBreakdownElement
		implements Cloneable {

	private String state;

	private Date plannedStartingDate;

	private Date plannedFinishingDate;

	private Date realStartingDate;

	private Date realFinishingDate;

	private float plannedTime;

	private float remainingTime;

	private float accomplishedTime;

	private WorkBreakdownElement workBreakdownElement;

	public ConcreteWorkBreakdownElement() {
		super();
		this.state = State.CREATED;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.misc.concretebreakdownelement.ConcreteBreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteWorkBreakdownElement == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteWorkBreakdownElement concreteworkBreakdownElement = (ConcreteWorkBreakdownElement) obj;
		return new EqualsBuilder().appendSuper(
				super.equals(concreteworkBreakdownElement)).append(
				this.accomplishedTime,
				concreteworkBreakdownElement.accomplishedTime).append(
				this.plannedFinishingDate,
				concreteworkBreakdownElement.plannedFinishingDate).append(
				this.plannedStartingDate,
				concreteworkBreakdownElement.plannedStartingDate).append(
				this.plannedTime, concreteworkBreakdownElement.plannedTime)
				.append(this.realFinishingDate,
						concreteworkBreakdownElement.realFinishingDate).append(
						this.realStartingDate,
						concreteworkBreakdownElement.realStartingDate).append(
						this.remainingTime,
						concreteworkBreakdownElement.remainingTime).append(
						this.state, concreteworkBreakdownElement.state).append(
						this.workBreakdownElement,
						concreteworkBreakdownElement.workBreakdownElement)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.misc.concretebreakdownelement.ConcreteBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.accomplishedTime)
				.append(this.plannedFinishingDate).append(
						this.plannedStartingDate).append(this.plannedTime)
				.append(this.realFinishingDate).append(this.realStartingDate)
				.append(this.remainingTime).append(this.workBreakdownElement)
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConcreteWorkBreakdownElement clone()
			throws CloneNotSupportedException {
		ConcreteWorkBreakdownElement concreteworkBreakdownElement = new ConcreteWorkBreakdownElement();
		concreteworkBreakdownElement.copy(this);
		return concreteworkBreakdownElement;
	}

	/**
	 * Copy the _concreteworkBreakdownElement into this.
	 */
	protected void copy(
			final ConcreteWorkBreakdownElement _concreteWorkBreakdownElement) {
		super.copy(_concreteWorkBreakdownElement);
		this.setWorkBreakdownElement(_concreteWorkBreakdownElement
				.getWorkBreakdownElement());
		this.setAccomplishedTime(_concreteWorkBreakdownElement
				.getAccomplishedTime());
		this.setPlannedFinishingDate(_concreteWorkBreakdownElement
				.getPlannedFinishingDate());
		this.setPlannedStartingDate(_concreteWorkBreakdownElement
				.getPlannedStartingDate());
		this.setPlannedTime(_concreteWorkBreakdownElement.getPlannedTime());
		this.setRealFinishingDate(_concreteWorkBreakdownElement
				.getRealFinishingDate());
		this.setRealStartingDate(_concreteWorkBreakdownElement
				.getRealStartingDate());
		this.setRemainingTime(_concreteWorkBreakdownElement.getRemainingTime());
		this.setState(_concreteWorkBreakdownElement.getState());
	}

	/*
	 * Relation between WorkBreakdownElement and ConcreteWorkBreakdownElement.
	 *
	 */

	public void addWorkBreakdownElement(
			WorkBreakdownElement _workbreakdownElement) {
		this.workBreakdownElement = _workbreakdownElement;
		_workbreakdownElement.getConcreteBreakdownElements().add(this);
	}

	public void removeWorkBreakdownElement(
			WorkBreakdownElement _workbreakdownElement) {
		_workbreakdownElement.getConcreteWorkBreakdownElements().remove(this);
		this.workBreakdownElement = null;
	}

	public WorkBreakdownElement getWorkBreakdownElement() {
		return workBreakdownElement;
	}

	public void setWorkBreakdownElement(
			WorkBreakdownElement _workbreakdownelement) {
		this.workBreakdownElement = _workbreakdownelement;
	}

	public float getAccomplishedTime() {
		return accomplishedTime;
	}

	public void setAccomplishedTime(float accomplishedTime) {
		this.accomplishedTime = accomplishedTime;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
