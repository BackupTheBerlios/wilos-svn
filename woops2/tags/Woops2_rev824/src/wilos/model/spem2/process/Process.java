
package wilos.model.spem2.process ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.spem2.activity.Activity;

/**
 * 
 * A Process is a special Activity that describes a structure for particular types of development
 * projects. To perform such a development project a Processes would be 'instantiated' and adapted
 * for the specific situation. Process is an abstract class and this meta-model defines different
 * special types of Processes for different process management applications and different situations
 * of process reuse.
 * 
 * @author deder
 * 
 */
public class Process extends Activity implements Cloneable {

	/**
	 * Default constructor
	 * 
	 */
	public Process() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Process clone() throws CloneNotSupportedException {
		Process process = new Process() ;
		process.copy(this) ;
		return process ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _process
	 *            The process to copy.
	 */
	protected void copy(final Process _process) {
		super.copy(_process) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Process == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		Process process = (Process) obj ;
		return new EqualsBuilder().appendSuper(super.equals(process)).isEquals() ;
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
