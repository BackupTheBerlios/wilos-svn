package woops2.model.process;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import woops2.model.activity.Activity;

/**
 * @author deder
 * 
 * A Process is a special Activity that describes a structure for particular
 * types of development projects. To perform such a development project a
 * Processes would be 'instantiated' and adapted for the specific situation.
 * Process is an abstract class and this meta-model defines different special
 * types of Processes for different process management applications and
 * different situations of process reuse.
 * 
 */
public class Process extends Activity implements Cloneable {

	/**
	 * Default constructor
	 * 
	 */
	public Process() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Activity clone() throws CloneNotSupportedException {
		Process process = new Process();
		process.copy(this);
		return process;
	}
	
	/**
	 * Copy the _process into this.
	 */
	protected void copy(final Process _process) {
		super.copy(_process) ;
	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Process == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Process process = (Process) obj;
		return new EqualsBuilder().appendSuper(super.equals(process))
				.isEquals();
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.toHashCode();
	}
}
