package wilos.model.spem2.iteration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.activity.Activity;

/**
 * 
 * @author Soosuske
 *
 */
public class Iteration extends Activity implements Cloneable{
	

	/**
	 * Default constructor
	 * 
	 */
	public Iteration() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Iteration clone() throws CloneNotSupportedException {
		Iteration iteration = new Iteration() ;
		iteration.copy(this) ;
		return iteration ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _iteration
	 *            The iteration to copy.
	 */
	protected void copy(final Iteration _iteration) {
		super.copy(_iteration) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Iteration == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		Iteration iteration = (Iteration) obj ;
		return new EqualsBuilder().appendSuper(super.equals(iteration)).isEquals() ;
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
