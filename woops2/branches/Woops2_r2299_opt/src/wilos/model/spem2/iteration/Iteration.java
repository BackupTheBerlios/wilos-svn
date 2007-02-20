package wilos.model.spem2.iteration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.spem2.activity.Activity;

/**
 * Iteration is a special Activity, which prescribes pre-defined values for its instances for the attributes prefix ('Iteration') and isRepeatable ('True').
 * It has been included into the meta-model for convenience and to provide a special stereotype, because it represents a very commonly used Activity type.
 * Iteration groups a set of nested Activities that are repeated more than once. It represents an important structuring element to organize work in repetitive cycles

 * @author Soosuske
 *
 */
public class Iteration extends Activity implements Cloneable{

	private Set<ConcreteIteration> concreteIterations;

	/**
	 * Default constructor
	 *
	 */
	public Iteration() {
		super() ;
		this.concreteIterations = new HashSet<ConcreteIteration>();
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
		this.concreteIterations.addAll(_iteration.getConcreteIterations());
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

	/**
	 * Add a concreteIteration to an iteration
	 *
	 * @param _concreteIteration
	 */
	public void addConcreteIteration(ConcreteIteration _concreteIteration) {
		this.concreteIterations.add(_concreteIteration);
		_concreteIteration.setIteration(this);
	}

	/**
	 * Add a concreteIteration collection to the concreteIteration collection of a
	 * Iteration
	 *
	 * @param _concreteIteration
	 */
	public void addAllConcreteIterations(Set<ConcreteIteration> _concreteIterations) {
		for (ConcreteIteration ci : _concreteIterations) {
			ci.addIteration(this);
		}
	}

	/**
	 * Remove a concreteIteration to its iteration
	 *
	 * @param _concreteIteration
	 */
	public void removeConcreteIteration(ConcreteIteration _concreteIteration) {
		_concreteIteration.setIteration(null);
		this.concreteIterations.remove(_concreteIteration);
	}

	/**
	 * Remove all concreteIterations to its iteration
	 */
	public void removeAllConcreteIterations() {
		for (ConcreteIteration tmp : this.concreteIterations) {
			tmp.setIteration(null);
		}
		this.concreteIterations.clear();
	}

	/**
	 *
	 * @return
	 */
	public Set<ConcreteIteration> getConcreteIterations() {
		return concreteIterations;
	}

	/**
	 *
	 * @param _concreteIterations
	 */
	public void setConcreteIterations(Set<ConcreteIteration> _concreteIterations) {
		this.concreteIterations = _concreteIterations;
	}

}
