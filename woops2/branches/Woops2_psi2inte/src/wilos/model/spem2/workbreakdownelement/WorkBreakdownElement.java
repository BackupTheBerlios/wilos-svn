
package wilos.model.spem2.workbreakdownelement ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.spem2.breakdownelement.BreakdownElement;

/**
 * 
 * A Work Breakdown Element is a special Breakdown Element that provides specific properties for
 * Breakdown Elements that represent or refer to Work Definitions.
 * 
 * @author deder
 * 
 */
public class WorkBreakdownElement extends BreakdownElement implements Cloneable {

	private Boolean isRepeatable ;

	private Boolean isOngoing ;

	private Boolean isEvenDriven ;

	public WorkBreakdownElement() {
		super() ;
		this.isEvenDriven = false ;
		this.isOngoing = false ;
		this.isRepeatable = false ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof BreakdownElement == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		WorkBreakdownElement workBreakdownElement = (WorkBreakdownElement) obj ;
		return new EqualsBuilder().appendSuper(super.equals(workBreakdownElement)).append(this.isEvenDriven, workBreakdownElement.isEvenDriven).append(
				this.isOngoing, workBreakdownElement.isOngoing).append(this.isRepeatable, workBreakdownElement.isRepeatable).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.isEvenDriven).append(this.isOngoing).append(this.isRepeatable)
				.toHashCode() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public WorkBreakdownElement clone() throws CloneNotSupportedException {
		WorkBreakdownElement workBreakdownElement = new WorkBreakdownElement() ;
		workBreakdownElement.copy(this) ;
		return workBreakdownElement ;
	}

	/**
	 * Copy the _workBreakdownElement into this.
	 */
	protected void copy(final WorkBreakdownElement _workBreakdownElement) {
		super.copy(_workBreakdownElement) ;
		this.setIsEvenDriven(_workBreakdownElement.getIsEvenDriven()) ;
		this.setIsOngoing(_workBreakdownElement.getIsOngoing()) ;
		this.setIsRepeatable(_workBreakdownElement.getIsRepeatable()) ;
	}

	/**
	 * Getter of isEvenDriven.
	 * 
	 * @return the isEvenDriven.
	 */
	public Boolean getIsEvenDriven() {
		return this.isEvenDriven ;
	}

	/**
	 * Setter of isEvenDriven.
	 * 
	 * @param _isEvenDriven
	 *            The isEvenDriven to set.
	 */
	public void setIsEvenDriven(Boolean _isEvenDriven) {
		this.isEvenDriven = _isEvenDriven ;
	}

	/**
	 * Getter of isOngoing.
	 * 
	 * @return the isOngoing.
	 */
	public Boolean getIsOngoing() {
		return this.isOngoing ;
	}

	/**
	 * Setter of isOngoing.
	 * 
	 * @param _isOngoing
	 *            The isOngoing to set.
	 */
	public void setIsOngoing(Boolean _isOngoing) {
		this.isOngoing = _isOngoing ;
	}

	/**
	 * Getter of isRepeatable.
	 * 
	 * @return the isRepeatable.
	 */
	public Boolean getIsRepeatable() {
		return this.isRepeatable ;
	}

	/**
	 * Setter of isRepeatable.
	 * 
	 * @param _isRepeatable
	 *            The isRepeatable to set.
	 */
	public void setIsRepeatable(Boolean _isRepeatable) {
		this.isRepeatable = _isRepeatable ;
	}
}