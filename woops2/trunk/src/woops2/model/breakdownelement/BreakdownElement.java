package woops2.model.breakdownelement;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import woops2.model.activity.Activity;
import woops2.model.element.Element;

/**
 * @author deder.
 * 
 * Breakdown Element is an abstract generalization for any type of Element that
 * is part of a breakdown structure. It defines a set of properties available to
 * all of its specializations.
 * 
 */
public class BreakdownElement extends Element {

	private String prefix;

	private Boolean isPlanned;

	private Boolean hasMultipleOccurrences;

	private Boolean isOptional;

	private Set<Activity> superActivities;

	/**
	 * Constructor.
	 * 
	 */
	public BreakdownElement() {
		super();
		this.isOptional = false;
		this.isPlanned = true;
		this.hasMultipleOccurrences = false;

		this.superActivities = new HashSet<Activity>();
	}

	/**
	 * Add an Activity to the superActivities collection of a BreakdownElement
	 * 
	 * @param _activity
	 */
	public void addToActivity(Activity _activity) {
		this.getSuperActivities().add(_activity);
		_activity.getBreakDownElements().add(this);
	}

	/**
	 * Add an activity collection to the activity collection of a
	 * breakdownelement
	 * 
	 * @param _activities
	 */
	public void addToAllActivities(Set<Activity> _activities) {
		for (Activity activity : _activities) {
			// activity.addToBreakdownElement(this);
			this.addToActivity(activity);
		}
	}

	/**
	 * Remove from a breakdownelement one of these superActivities
	 * 
	 * @param _activity
	 */
	public void removeFromActivity(Activity _activity) {
		_activity.getBreakDownElements().remove(this);
		this.getSuperActivities().remove(_activity);
	}

	/**
	 * Remove from a breakdownelement all its superActivities.
	 * 
	 */
	public void removeFromAllActivities() {
		for (Activity activity : this.getSuperActivities())
			this.removeFromActivity(activity);
	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if (obj instanceof BreakdownElement == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		BreakdownElement breakdownElement = (BreakdownElement) obj;
		return new EqualsBuilder().appendSuper(super.equals(breakdownElement))
				.append(this.prefix, breakdownElement.prefix)
				.append(this.isPlanned, breakdownElement.isPlanned)
				.append(this.hasMultipleOccurrences, breakdownElement.hasMultipleOccurrences)
				.append(this.isOptional, breakdownElement.isOptional)
				.append(this.superActivities, breakdownElement.superActivities)
				.isEquals();
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.prefix).toHashCode();
	}

	/**
	 * Getter of hasMultipleOccurrences.
	 * 
	 * @return the hasMultipleOccurrences.
	 */
	public Boolean getHasMultipleOccurrences() {
		return this.hasMultipleOccurrences;
	}

	/**
	 * Setter of hasMultipleOccurrences.
	 * 
	 * @param _hasMultipleOccurrences
	 *            The hasMultipleOccurrences to set.
	 */
	public void setHasMultipleOccurrences(Boolean _hasMultipleOccurrences) {
		this.hasMultipleOccurrences = _hasMultipleOccurrences;
	}

	/**
	 * Getter of isOptional.
	 * 
	 * @return the isOptional.
	 */
	public Boolean getIsOptional() {
		return this.isOptional;
	}

	/**
	 * Setter of isOptional.
	 * 
	 * @param _isOptional
	 *            The isOptional to set.
	 */
	public void setIsOptional(Boolean _isOptional) {
		this.isOptional = _isOptional;
	}

	/**
	 * Getter of isPlanned.
	 * 
	 * @return the isPlanned.
	 */
	public Boolean getIsPlanned() {
		return this.isPlanned;
	}

	/**
	 * Setter of isPlanned.
	 * 
	 * @param _isPlanned
	 *            The isPlanned to set.
	 */
	public void setIsPlanned(Boolean _isPlanned) {
		this.isPlanned = _isPlanned;
	}

	/**
	 * Getter of prefix.
	 * 
	 * @return the prefix.
	 */
	public String getPrefix() {
		return this.prefix;
	}

	/**
	 * Setter of prefix.
	 * 
	 * @param _prefix
	 *            The prefix to set.
	 */
	public void setPrefix(String _prefix) {
		this.prefix = _prefix;
	}

	/**
	 * Getter of superActivities.
	 * 
	 * @return the superActivities.
	 */
	public Set<Activity> getSuperActivities() {
		return this.superActivities;
	}

	/**
	 * Setter of superActivities.
	 * 
	 * @param _superActivities
	 *            The superActivities to set.
	 */
	@SuppressWarnings("unused")
	private void setSuperActivities(Set<Activity> _superActivities) {
		this.superActivities = _superActivities;
	}
}
