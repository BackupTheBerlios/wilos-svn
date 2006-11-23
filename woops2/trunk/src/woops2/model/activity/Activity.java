
package woops2.model.activity ;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import woops2.model.breakdownelement.BreakdownElement;
import woops2.model.workbreakdownelement.WorkBreakdownElement;

/**
 * @author deder.
 * @author morpheus
 * 
 * An Activity is a Work Breakdown Element and Work Definition which supports the nesting and
 * logical grouping of related Breakdown Elements forming breakdown structures. Although Activity is
 * a concrete meta-class, other classes which represent breakdown structures derive from it; such as
 * Phase, Iteration, Delivery Process, or Capability Pattern.
 * 
 */
public class Activity extends WorkBreakdownElement implements Cloneable {

	private Set<BreakdownElement> breakdownElements ;

	/**
	 * Constructor.
	 * 
	 */
	public Activity() {
		super() ;
		this.breakdownElements = new HashSet<BreakdownElement>() ;
	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Activity == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		Activity activity = (Activity) obj ;
		return new EqualsBuilder().appendSuper(super.equals(activity)).append(this.breakdownElements, activity.breakdownElements).isEquals() ;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())/* .append(this.breakdownElements) */.toHashCode() ; // TODO
																															// a
																															// decider
																															// si
																															// on
																															// l'inclue
																															// ou
																															// pas
																															// (reunion)
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
	 * Copy the _activity into this.
	 */
	protected void copy(final Activity _activity) {
		super.copy(_activity) ;
		this.setBreakDownElements(_activity.getBreakDownElements()) ;
	}

	/**
	 * Add a breakdownElement to the breakdownElement collection of an activity
	 * 
	 * @param _breakdownElement
	 */
	public void addToBreakdownElement(BreakdownElement _breakdownElement) {
		this.getBreakDownElements().add(_breakdownElement) ;
		_breakdownElement.getSuperActivities().add(this) ;
	}

	/**
	 * Add a breakdownElement collection to the breakdownElement collection of an activity
	 * 
	 * @param _breakdownElement
	 */
	public void addToAllBreakdownElements(Set<BreakdownElement> _breakdownElements) {
		for(BreakdownElement bde : _breakdownElements){
			bde.addToActivity(this) ;
		}
	}

	/**
	 * Remove from an activity one of these breakdownElements
	 * 
	 * @param _breakdownElement
	 */
	public void removeFromBreakdownElement(BreakdownElement _breakdownElement) {
		_breakdownElement.getSuperActivities().remove(this) ;
		this.getBreakDownElements().remove(_breakdownElement) ;
	}

	/**
	 * Remove from an activity all its breakdownElements
	 * 
	 */
	public void removeFromAllBreakdownElements() {
		/*for(BreakdownElement bde : this.getBreakDownElements()){
			this.removeFromBreakdownElement(bde) ;
		}*/
		/*Iterator<BreakdownElement> iterator = this.getBreakDownElements().iterator();
        while(iterator.hasNext()){
        	this.removeFromBreakdownElement(iterator.next()) ;
        }*/
		for(BreakdownElement bde : this.getBreakDownElements())
			bde.getSuperActivities().remove(this);
		this.getBreakDownElements().clear();
	}

	/**
	 * Getter of breakDownElements.
	 * 
	 * @return the breakDownElements.
	 */
	public Set<BreakdownElement> getBreakDownElements() {
		return this.breakdownElements ;
	}

	/**
	 * Setter of breakDownElements.
	 * 
	 * @param _breakDownElements
	 *            The breakDownElements to set.
	 */
	@ SuppressWarnings ("unused")
	private void setBreakDownElements(Set<BreakdownElement> _breakDownElements) {
		this.breakdownElements.addAll(_breakDownElements) ;
	}
}
