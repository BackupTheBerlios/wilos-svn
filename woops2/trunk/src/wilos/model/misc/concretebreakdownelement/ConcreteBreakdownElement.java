package wilos.model.misc.concretebreakdownelement;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.task.TaskDescriptor;

public class ConcreteBreakdownElement implements Cloneable {

	private String id;

	private String concreteName;

	private Set<ConcreteActivity> superConcreteActivities;

	private BreakdownElement breakdownElement;

	public ConcreteBreakdownElement() {
		this.superConcreteActivities = new HashSet<ConcreteActivity>();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	@Override
	public ConcreteBreakdownElement clone() throws CloneNotSupportedException {
		ConcreteBreakdownElement concreteBreakdownElement = new ConcreteBreakdownElement();
		concreteBreakdownElement.copy(this);
		return concreteBreakdownElement;
	}

	/**
	 * Copy the object.
	 *
	 */
	protected void copy(final ConcreteBreakdownElement _concreteBreakdownElement) {
		this.setConcreteName(_concreteBreakdownElement.getConcreteName());
		this.setId(_concreteBreakdownElement.getId());
		this.setBreakdownElement(_concreteBreakdownElement.getBreakdownElement());
		this.setSuperConcreteActivities(_concreteBreakdownElement
				.getSuperConcreteActivities());
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteBreakdownElement == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteBreakdownElement concreteBreakdownElement = (ConcreteBreakdownElement) obj;
		return new EqualsBuilder().append(this.concreteName,
				concreteBreakdownElement.concreteName).append(this.breakdownElement,
						concreteBreakdownElement.breakdownElement).append(
				this.superConcreteActivities,
				concreteBreakdownElement.superConcreteActivities).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.concreteName).append(this.id).append(this.breakdownElement).append(
						this.superConcreteActivities).toHashCode();
	}

	/*
	 * Relation between BreakdownElement and ConcreteBreakdownElement.
	 *
	 */

	public void addBreakdownElement(BreakdownElement _breakdownElement) {
		this.breakdownElement = _breakdownElement;
		_breakdownElement.getConcreteBreakdownElements().add(this);
	}

	public void removeBreakdownElement(BreakdownElement _breakdownElement) {
		_breakdownElement.getConcreteBreakdownElements().remove(this);
		this.breakdownElement = null;
	}

	/*
	 * Getter & Setter.
	 *
	 */

	public String getConcreteName() {
		return concreteName;
	}

	public void setConcreteName(String concreteName) {
		this.concreteName = concreteName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<ConcreteActivity> getSuperConcreteActivities() {
		return superConcreteActivities;
	}

	public void setSuperConcreteActivities(
			Set<ConcreteActivity> superConcreteActivities) {
		this.superConcreteActivities = superConcreteActivities;
	}

	public BreakdownElement getBreakdownElement() {
		return breakdownElement;
	}

	public void setBreakdownElement(BreakdownElement breakdownElement) {
		this.breakdownElement = breakdownElement;
	}

}
