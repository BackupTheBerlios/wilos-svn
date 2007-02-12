
package wilos.model.spem2.activity ;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

/**
 *
 * An Activity is a Work Breakdown Element and Work Definition which supports the nesting and
 * logical grouping of related Breakdown Elements forming breakdown structures. Although Activity is
 * a concrete meta-class, other classes which represent breakdown structures derive from it; such as
 * Phase, Iteration, Delivery Process, or Capability Pattern.
 *
 * @author deder.
 * @author morpheus
 * @author garwind
 */
public class Activity extends WorkBreakdownElement implements Cloneable {

	private Set<BreakdownElement> breakdownElements ;

	private Set<ConcreteActivity> concreteActivities;

	private Set<Guidance> guidances;

	/**
	 * Constructor.
	 *
	 */
	public Activity() {
		super() ;
		this.breakdownElements = new HashSet<BreakdownElement>() ;
		this.concreteActivities = new HashSet<ConcreteActivity>();
		this.guidances = new HashSet<Guidance>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.workbreakdownelement.WorkBreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Activity == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		Activity activity = (Activity) obj ;
		return new EqualsBuilder().appendSuper(super.equals(activity)).append(this.breakdownElements, activity.breakdownElements).append(this.concreteActivities,activity.concreteActivities).append(this.guidances, activity.guidances).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.workbreakdownelement.WorkBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Activity clone() throws CloneNotSupportedException {
		Activity activity = new Activity() ;
		activity.copy(this) ;
		return activity ;
	}

	/**
	 * Copy the object.
	 *
	 * @param _activity
	 *            The Activity to copy.
	 */
	protected void copy(final Activity _activity) {
		super.copy(_activity) ;
		this.setBreakdownElements(_activity.getBreakdownElements()) ;
	}

	/**
	 * Add a breakdownElement to the breakdownElement collection of an activity
	 *
	 * @param _breakdownElement
	 *            The BreakdownElement to add.
	 */
	public void addBreakdownElement(BreakdownElement _breakdownElement) {
		this.getBreakdownElements().add(_breakdownElement) ;
		_breakdownElement.getSuperActivities().add(this) ;
	}

	/**
	 * Add a breakdownElement collection to the breakdownElement collection of an activity
	 *
	 * @param _breakdownElements
	 *            The set of BreakdownElement to add.
	 */
	public void addAllBreakdownElements(Set<BreakdownElement> _breakdownElements) {
		for(BreakdownElement bde : _breakdownElements){
			bde.addSuperActivity(this) ;
		}
	}

	/**
	 * Remove from an activity one of these breakdownElements
	 *
	 * @param _breakdownElement
	 *            The BreakdownElement to remove.
	 */
	public void removeBreakdownElement(BreakdownElement _breakdownElement) {
		_breakdownElement.getSuperActivities().remove(this) ;
		this.getBreakdownElements().remove(_breakdownElement) ;
	}

	public void addConcreteActivity(ConcreteActivity _concreteActivity){
		this.concreteActivities.add(_concreteActivity);
		_concreteActivity.setActivity(this);
	}

	public void addAllConcreteActivity(Set<ConcreteActivity> _concreteActivities){
		for (ConcreteActivity ca : _concreteActivities){
			this.addConcreteActivity(ca);
		}
	}

	public void removeConcreteActivity(ConcreteActivity _concreteActivity){
		this.concreteActivities.remove(_concreteActivity);
		_concreteActivity.setActivity(null);
	}

	public void removeAllConcreteActivities(){

		for (ConcreteActivity tmp : this.concreteActivities) {
			tmp.setActivity(null);
		}
		this.concreteActivities.clear();
	}


	/**
	 * Remove from an activity all its breakdownElements
	 *
	 */
	public void removeAllBreakdownElements() {
		for(BreakdownElement bde : this.getBreakdownElements())
			bde.getSuperActivities().remove(this) ;
		this.getBreakdownElements().clear() ;
	}

	/**
	 * Getter of breakDownElements.
	 *
	 * @return the breakDownElements.
	 */
	public Set<BreakdownElement> getBreakdownElements() {
		return this.breakdownElements ;
	}


	/*
	 * connection to guidances
	 */
	public void removeGuidance(Guidance _guidance) {
		_guidance.getActivities().remove(this);
		this.guidances.remove(_guidance);
	}


	public void addGuidance(Guidance _guidance) {
		this.guidances.add(_guidance);
		_guidance.getActivities().add(this);
	}


	public void removeAllGuidances() {
		for (Guidance guidance : this.guidances) {
			guidance.getActivities().remove(this);
		}
		// not necessary ...
		this.guidances.clear();
	}

	public void addAllGuidances(Set<Guidance> _guidances) {
		for (Guidance _guid1 : _guidances) {
			this.addGuidance(_guid1);
		}
	}

	/**
	 * Setter of breakDownElements.
	 *
	 * @param _breakDownElements
	 *            The breakDownElements to set.
	 */
	@ SuppressWarnings ("unused")
	private void setBreakdownElements(Set<BreakdownElement> _breakDownElements) {
		this.breakdownElements.addAll(_breakDownElements);
	}

	public Set<ConcreteActivity> getConcreteActivities() {
		return concreteActivities;
	}

	public void setConcreteActivities(Set<ConcreteActivity> concreteActivities) {
		this.concreteActivities = concreteActivities;
	}

	public Set<Guidance> getGuidances() {
		return guidances;
	}

	public void setGuidances(Set<Guidance> guidances) {
		this.guidances = guidances;
	}
}
