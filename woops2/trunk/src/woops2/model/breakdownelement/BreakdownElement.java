
package woops2.model.breakdownelement ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.activity.Activity ;
import woops2.model.element.Element ;

/**
 * @author deder.
 * 
 * Breakdown Element is an abstract generalization for any type of Element that is part of a
 * breakdown structure. It defines a set of properties available to all of its specializations.
 * 
 */
public class BreakdownElement extends Element implements Cloneable {

	private String prefix ;

	private Boolean isPlanned ;

	private Boolean hasMultipleOccurrences ;

	private Boolean isOptional ;

	private Set<Activity> activities ;

	/**
	 * Constructor.
	 * 
	 */
	public BreakdownElement() {
		super() ;
		this.prefix = "" ;
		this.isOptional = false ;
		this.isPlanned = true ;
		this.hasMultipleOccurrences = false ;
		this.activities = new HashSet<Activity>() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#clone()
	 */
	@ Override
	public BreakdownElement clone() throws CloneNotSupportedException {
		BreakdownElement breakdownElement = new BreakdownElement() ;
		breakdownElement.copy(this) ;
		return breakdownElement ;
	}

	/**
	 * Copy the object.
	 */
	protected void copy(final BreakdownElement _breakdownElement) {
		super.copy(_breakdownElement) ;
		this.prefix = _breakdownElement.prefix ;
		this.hasMultipleOccurrences = _breakdownElement.hasMultipleOccurrences ;
		this.isPlanned = _breakdownElement.isPlanned ;
		this.isOptional = _breakdownElement.isOptional ;
		this.setActivities(_breakdownElement.getActivities()) ;

	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if(obj instanceof BreakdownElement == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		BreakdownElement breakdownElement = (BreakdownElement) obj ;
		return new EqualsBuilder().appendSuper(super.equals(breakdownElement)).append(this.prefix, breakdownElement.prefix).append(this.isPlanned,
				breakdownElement.isPlanned).append(this.hasMultipleOccurrences, breakdownElement.hasMultipleOccurrences).append(this.isOptional,
				breakdownElement.isOptional).append(this.activities, breakdownElement.activities).isEquals() ;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.prefix).append(this.hasMultipleOccurrences).append(this.isOptional)
				.append(this.isPlanned).toHashCode() ;
	}

	/**
	 * Add an Activity to the superActivities collection of a BreakdownElement
	 * 
	 * @param _activity
	 */
	public void addActivity(Activity _activity) {
		this.getActivities().add(_activity) ;
		_activity.getBreakDownElements().add(this) ;
	}

	/**
	 * Add an activity collection to the activity collection of a breakdownelement
	 * 
	 * @param _activities
	 */
	public void addAllActivities(Set<Activity> _activities) {
		for(Activity activity : _activities){
			activity.addBreakdownElement(this) ;
		}
	}

	/**
	 * Remove from a breakdownelement one of these superActivities
	 * 
	 * @param _activity
	 */
	public void removeActivity(Activity _activity) {
		_activity.getBreakDownElements().remove(this) ;
		this.getActivities().remove(_activity) ;
	}

	/**
	 * Remove from a breakdownelement all its superActivities.
	 * 
	 */
	public void removeAllActivities() {
		for(Activity activity : this.getActivities())
			activity.getBreakDownElements().remove(this) ;
		this.getActivities().clear() ;
	}

	/**
	 * Getter of hasMultipleOccurrences.
	 * 
	 * @return the hasMultipleOccurrences.
	 */
	public Boolean getHasMultipleOccurrences() {
		return this.hasMultipleOccurrences ;
	}

	/**
	 * Setter of hasMultipleOccurrences.
	 * 
	 * @param _hasMultipleOccurrences
	 *            The hasMultipleOccurrences to set.
	 */
	public void setHasMultipleOccurrences(Boolean _hasMultipleOccurrences) {
		this.hasMultipleOccurrences = _hasMultipleOccurrences ;
	}

	/**
	 * Getter of isOptional.
	 * 
	 * @return the isOptional.
	 */
	public Boolean getIsOptional() {
		return this.isOptional ;
	}

	/**
	 * Setter of isOptional.
	 * 
	 * @param _isOptional
	 *            The isOptional to set.
	 */
	public void setIsOptional(Boolean _isOptional) {
		this.isOptional = _isOptional ;
	}

	/**
	 * Getter of isPlanned.
	 * 
	 * @return the isPlanned.
	 */
	public Boolean getIsPlanned() {
		return this.isPlanned ;
	}

	/**
	 * Setter of isPlanned.
	 * 
	 * @param _isPlanned
	 *            The isPlanned to set.
	 */
	public void setIsPlanned(Boolean _isPlanned) {
		this.isPlanned = _isPlanned ;
	}

	/**
	 * Getter of prefix.
	 * 
	 * @return the prefix.
	 */
	public String getPrefix() {
		return this.prefix ;
	}

	/**
	 * Setter of prefix.
	 * 
	 * @param _prefix
	 *            The prefix to set.
	 */
	public void setPrefix(String _prefix) {
		this.prefix = _prefix ;
	}

	/**
	 * Getter of activities.
	 * 
	 * @return the activities.
	 */
	public Set<Activity> getActivities() {
		return this.activities ;
	}

	/**
	 * Setter of activities.
	 * 
	 * @param _superActivities
	 *            The activities to set.
	 */
	@ SuppressWarnings ("unused")
	private void setActivities(Set<Activity> _activities) {
		this.activities = _activities ;
	}
}
