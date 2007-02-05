package wilos.model.misc.concreteworkbreakdownelement;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

/**
 *
 * A Concrete Work Breakdown Element is a special Concrete Breakdown Element
 *
 * @author eperico
 *
 */
public class ConcreteWorkBreakdownElement extends ConcreteBreakdownElement
		implements Cloneable {

	private Date plannedStartingDate;

	private Date plannedFinishingDate;

	private float plannedTime;

	private WorkBreakdownElement workBreakdownElement;

	private Set<ConcreteWorkBreakdownElement> concretePredecessors;

	private Set<ConcreteWorkBreakdownElement> concreteSuccessors;

	public ConcreteWorkBreakdownElement() {
		super();
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
				this.plannedFinishingDate,
				concreteworkBreakdownElement.plannedFinishingDate).append(
				this.plannedStartingDate,
				concreteworkBreakdownElement.plannedStartingDate).append(
				this.plannedTime, concreteworkBreakdownElement.plannedTime)
				.append(this.workBreakdownElement,
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
				.append(this.plannedFinishingDate).append(
						this.plannedStartingDate).append(this.plannedTime)
				.append(this.workBreakdownElement)
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
		this.setPlannedFinishingDate(_concreteWorkBreakdownElement
				.getPlannedFinishingDate());
		this.setPlannedStartingDate(_concreteWorkBreakdownElement
				.getPlannedStartingDate());
		this.setPlannedTime(_concreteWorkBreakdownElement.getPlannedTime());
	}

	/*
	 * Relation between WorkBreakdownElement and ConcreteWorkBreakdownElement.
	 *
	 */

	public void addWorkBreakdownElement(
			WorkBreakdownElement _workbreakdownElement) {
		this.setWorkBreakdownElement(_workbreakdownElement);
		_workbreakdownElement.getConcreteWorkBreakdownElements().add(this);
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

	public Set<ConcreteWorkBreakdownElement> getConcretePredecessors() {
		return concretePredecessors;
	}

	public void setConcretePredecessors(
			Set<ConcreteWorkBreakdownElement> concretePredecessors) {
		this.concretePredecessors = concretePredecessors;
	}

	public Set<ConcreteWorkBreakdownElement> getConcreteSuccessors() {
		return concreteSuccessors;
	}

	public void setConcreteSuccessors(
			Set<ConcreteWorkBreakdownElement> concreteSuccessors) {
		this.concreteSuccessors = concreteSuccessors;
	}
}
