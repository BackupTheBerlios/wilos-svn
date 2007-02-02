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

	private WorkBreakdownElement workbreakdownelement;

	public ConcreteWorkBreakdownElement() {
		super();
		this.workbreakdownelement = new WorkBreakdownElement();
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
				.append(this.workbreakdownelement,concreteworkBreakdownElement.workbreakdownelement).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.misc.concretebreakdownelement.ConcreteBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.workbreakdownelement).toHashCode();
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
		this.setWorkbreakdownelement(_concreteWorkBreakdownElement.getWorkbreakdownelement());
	}

	public WorkBreakdownElement getWorkbreakdownelement() {
		return workbreakdownelement;
	}

	public void setWorkbreakdownelement(WorkBreakdownElement _workbreakdownelement) {
		this.workbreakdownelement = _workbreakdownelement;
	}
}
