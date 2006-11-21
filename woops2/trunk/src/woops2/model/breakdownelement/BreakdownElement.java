package woops2.model.breakdownelement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	private void setSuperActivities(Set<Activity> _superActivities) {
		this.superActivities = _superActivities;
	}

	/**
	 * Add the _activity to the superActivities collection.
	 * 
	 * @param _activity
	 */
	public void addToActivity(Activity _activity) {
		this.superActivities.add(_activity);
		_activity.getBreakDownElements().add(this); 
	}
	
	public void removeFromActivity(Activity _activity){
		this.superActivities.remove(_activity);
		_activity.getBreakDownElements().remove(this); 
	}
	
	public void removeAllActivities() {
		for (Activity activity : this.superActivities) 
			activity.removeFromBreakdownElement(this);
		this.superActivities.clear();
	}

	public void removeFromAllActivity() {
		for (Activity activity : this.superActivities) 
			this.removeFromActivity(activity);
	}
}
