
package woops2.model.breakdownelement ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.activity.Activity ;
import woops2.model.element.Element ;

/**
 * 
 * Breakdown Element is an abstract generalization for any type of Element that is part of a
 * breakdown structure. It defines a set of properties available to all of its specializations.
 * 
 * @author deder
 * 
 */
public class BreakdownElement extends Element implements Cloneable {

	private String presentationName;
	
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
		this.presentationName = "";
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
	 * 
	 * @param _breakdownElement
	 *            The BreakdownElement to copy.
	 */
	protected void copy(final BreakdownElement _breakdownElement) {
		super.copy(_breakdownElement) ;
		this.presentationName = _breakdownElement.presentationName;
		this.prefix = _breakdownElement.prefix ;
		this.hasMultipleOccurrences = _breakdownElement.hasMultipleOccurrences ;
		this.isPlanned = _breakdownElement.isPlanned ;
		this.isOptional = _breakdownElement.isOptional ;
		this.setActivities(_breakdownElement.getActivities()) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof BreakdownElement == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		BreakdownElement breakdownElement = (BreakdownElement) obj ;
		return new EqualsBuilder().appendSuper(super.equals(breakdownElement)).append(this.presentationName, breakdownElement.presentationName).append(this.prefix, breakdownElement.prefix).append(this.isPlanned,
				breakdownElement.isPlanned).append(this.hasMultipleOccurrences, breakdownElement.hasMultipleOccurrences).append(this.isOptional,
				breakdownElement.isOptional).append(this.activities, breakdownElement.activities).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.presentationName).append(this.prefix).append(this.hasMultipleOccurrences).append(this.isOptional)
				.append(this.isPlanned).toHashCode() ;
	}

	/**
	 * Add an Activity to the activities collection of a BreakdownElement.
	 * 
	 * @param _activity
	 *            The activity to add
	 */
	public void addActivity(Activity _activity) {
		this.getActivities().add(_activity) ;
		_activity.getBreakDownElements().add(this) ;
	}

	/**
	 * Add an activity collection to the activity collection of a breakdownelement.
	 * 
	 * @param _activities
	 *            The set of Activity to add.
	 */
	public void addAllActivities(Set<Activity> _activities) {
		for(Activity activity : _activities){
			activity.addBreakdownElement(this) ;
		}
	}

	/**
	 * Remove from a breakdownelement one of these activities.
	 * 
	 * @param _activity
	 *            The Activity to remove.
	 */
	public void removeActivity(Activity _activity) {
		_activity.getBreakDownElements().remove(this) ;
		this.getActivities().remove(_activity) ;
	}

	/**
	 * Remove from a breakdownelement all its activities.
	 * 
	 */
	public void removeAllActivities() {
		for(Activity activity : this.getActivities())
			activity.getBreakDownElements().remove(this) ;
		this.getActivities().clear() ;
	}

	/**
	 * @return the presentationName
	 */
	public String getPresentationName() {
		return this.presentationName ;
	}

	/**
	 * Setter of presentationName.
	 *
	 * @param _presentationName The presentationName to set.
	 */
	public void setPresentationName(String _presentationName) {
		this.presentationName = _presentationName ;
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
