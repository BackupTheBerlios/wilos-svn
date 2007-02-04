package wilos.model.misc.concreteworkbreakdownelement;

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
public class ConcreteWorkBreakdownElement extends ConcreteBreakdownElement implements Cloneable {

	private WorkBreakdownElement workBreakdownElement;

	public ConcreteWorkBreakdownElement() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.misc.concretebreakdownelement.ConcreteBreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteBreakdownElement == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteWorkBreakdownElement concreteworkBreakdownElement = (ConcreteWorkBreakdownElement) obj;
		return new EqualsBuilder().appendSuper(
				super.equals(concreteworkBreakdownElement))
				.append(this.workBreakdownElement,concreteworkBreakdownElement.workBreakdownElement).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.misc.concretebreakdownelement.ConcreteBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.workBreakdownElement).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConcreteWorkBreakdownElement clone() throws CloneNotSupportedException {
		ConcreteWorkBreakdownElement concreteworkBreakdownElement = new ConcreteWorkBreakdownElement();
		concreteworkBreakdownElement.copy(this);
		return concreteworkBreakdownElement;
	}

	/**
	 * Copy the _concreteworkBreakdownElement into this.
	 */
	protected void copy(final ConcreteWorkBreakdownElement _concreteWorkBreakdownElement) {
		super.copy(_concreteWorkBreakdownElement);
		this.setWorkBreakdownElement(_concreteWorkBreakdownElement.getWorkBreakdownElement());
	}

	/*
	 * Relation between WorkBreakdownElement and ConcreteWorkBreakdownElement.
	 *
	 */

	public void addWorkBreakdownElement(WorkBreakdownElement _workbreakdownElement) {
		this.workBreakdownElement = _workbreakdownElement;
		_workbreakdownElement.getConcreteBreakdownElements().add(this);
	}

	public void removeWorkBreakdownElement(WorkBreakdownElement _workbreakdownElement) {
		_workbreakdownElement.getConcreteWorkBreakdownElements().remove(this);
		this.workBreakdownElement = null;
	}

	public WorkBreakdownElement getWorkBreakdownElement() {
		return workBreakdownElement;
	}

	public void setWorkBreakdownElement(WorkBreakdownElement _workbreakdownelement) {
		this.workBreakdownElement = _workbreakdownelement;
	}
}
