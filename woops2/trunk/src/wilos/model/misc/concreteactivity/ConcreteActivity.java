
package wilos.model.misc.concreteactivity ;

import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.workbreakdownelement.WorkBreakdownElement;

/**
 *
 * Concrete instanciation of a SPEM2 Activity
 *
 * @author garwind
 *
 */
public class ConcreteActivity extends WorkBreakdownElement implements Cloneable {

	private Set<ConcreteBreakdownElement> concreteBreakdownElements ;

	private Activity activity;

	/**
	 * Constructor.
	 *
	 */
	public ConcreteActivity() {
		super() ;
		this.concreteBreakdownElements = new HashSet<ConcreteBreakdownElement>() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.workbreakdownelement.WorkBreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see woops2.model.workbreakdownelement.WorkBreakdownElement#hashCode()
	 */
	public int hashCode() {
		return super.hashCode() ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ConcreteActivity clone() throws CloneNotSupportedException {
		ConcreteActivity concreteActivity = new ConcreteActivity() ;
		concreteActivity.copy(this) ;
		return concreteActivity ;
	}

	/**
	 * Copy the object.
	 *
	 * @param _activity
	 *            The Activity to copy.
	 */
	protected void copy(final ConcreteActivity _concreteActivity) {
		super.copy(_concreteActivity) ;
		this.setConcreteBreakdownElements(_concreteActivity.getConcreteBreakdownElements());
	}

	/**
	 * Add a breakdownElement to the breakdownElement collection of an activity
	 *
	 * @param _breakdownElement
	 *            The BreakdownElement to add.
	 */
	public void addConcreteBreakdownElement(ConcreteBreakdownElement _concreteBeakdownElement) {
		//this.getConcreteBreakDownElements().add(_concreteBeakdownElement) ;
		this.getConcreteBreakdownElements().add(_concreteBeakdownElement);
		//FIXME superConreteactivities
		//_concreteBeakdownElement.getSuperActivities().add(this) ;
	}

	/**
	 * Add a breakdownElement collection to the breakdownElement collection of an activity
	 *
	 * @param _breakdownElements
	 *            The set of BreakdownElement to add.
	 */
	public void addAllConcreteBreakdownElements(Set<ConcreteBreakdownElement> _concreteBreakdownElements) {
		for(ConcreteBreakdownElement cbde : _concreteBreakdownElements){
			cbde.addSuperConcreteActivity(this) ;
			//FIXME addSuperConcreteActivity for Concretes
		}
	}

	/**
	 * Remove from an activity one of these breakdownElements
	 *
	 * @param _breakdownElement
	 *            The BreakdownElement to remove.
	 */
	public void removeConcreteBreakdownElement(ConcreteBreakdownElement _concreteBreakdownElement) {
		//FIXME _concreteBreakdownElement.getSuperConcreteActivities
		/*
		_concreteBreakdownElement.getSuperConcreteActivities().remove(this) ;
		this.getConcreteBreakDownElements().remove(_concreteBreakdownElement) ;
		*/
	}

	/**
	 * Remove from an activity all its breakdownElements
	 *
	 */
	public void removeAllConcreteBreakdownElements() {
		/*
		for(ConcreteBreakdownElement bde : this.getConcreteBreakDownElements())
			bde.getSuperConcreteActivities().remove(this) ;
		this.getConcreteBreakDownElements().clear() ;
		*/
//		FIXME cbde.getSuperConcreteActivities().remove
	}

	public void addActivity(Activity _activity){
		this.activity = _activity;
		_activity.addConcreteActivity(this);
	}

	public void removeActivity(Activity _activity){
		this.activity = null;
		_activity.removeConcreteActivity(this);
	}

	public Set<ConcreteBreakdownElement> getConcreteBreakdownElements() {
		return concreteBreakdownElements;
	}

	public void setConcreteBreakdownElements(
			Set<ConcreteBreakdownElement> concreteBreakdownElements) {
		this.concreteBreakdownElements = concreteBreakdownElements;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
