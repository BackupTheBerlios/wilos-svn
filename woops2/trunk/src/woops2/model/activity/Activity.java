
package woops2.model.activity ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.breakdownelement.BreakdownElement ;
import woops2.model.workbreakdownelement.WorkBreakdownElement ;

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
public class Activity extends WorkBreakdownElement {

	private Set<BreakdownElement> breakDownElements ;

	/**
	 * Constructor.
	 * 
	 */
	public Activity() {
		super() ;
		this.breakDownElements = new HashSet<BreakdownElement>() ;
	}

	/**
	 * Getter of breakDownElements.
	 * 
	 * @return the breakDownElements.
	 */
	public Set<BreakdownElement> getBreakDownElements() {
		return this.breakDownElements ;
	}

	/**
	 * Setter of breakDownElements.
	 * 
	 * @param _breakDownElements
	 *            The breakDownElements to set.
	 */
	private void setBreakDownElements(Set<BreakdownElement> _breakDownElements) {
		this.breakDownElements = _breakDownElements ;
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
		return new EqualsBuilder().appendSuper(super.equals(activity)).append(this.breakDownElements, activity.breakDownElements).isEquals() ;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.breakDownElements).toHashCode() ;
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
	 * Remove from an activity one of these breakdownElements
	 * 
	 * @param _breakdownElement
	 */
	public void removeFromBreakdownElement(BreakdownElement _breakdownElement) {
		this.getBreakDownElements().remove(_breakdownElement) ;
		_breakdownElement.getSuperActivities().remove(this) ;
	}

	/**
	 * Remove from an activity all its breakdownElements
	 * 
	 */
	public void removeFromAllBreakdownElements() {
		for(BreakdownElement temp : this.getBreakDownElements()){
			this.removeFromBreakdownElement(temp) ;
		}
	}

}
