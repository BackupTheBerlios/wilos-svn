package wilos.model.spem2.phase;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.activity.Activity;

/**
 * 
 * @author Soosuske
 *
 */
public class Phase extends Activity implements Cloneable{
	

	/**
	 * Default constructor
	 * 
	 */
	public Phase() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Phase clone() throws CloneNotSupportedException {
		Phase phase = new Phase() ;
		phase.copy(this) ;
		return phase ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _phase
	 *            The iteration to copy.
	 */
	protected void copy(final Phase _phase) {
		super.copy(_phase) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Phase == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		Phase phase = (Phase) obj ;
		return new EqualsBuilder().appendSuper(super.equals(phase)).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

}
