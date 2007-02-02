package wilos.model.misc.concreteiteration;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.task.TaskDefinition;

/**
 * @author Sebastien
 *
 */
public class ConcreteIteration extends ConcreteActivity implements Cloneable {
	
	private Iteration iteration;

	/**
	 * Default constructor
	 * 
	 */
	public ConcreteIteration() {
		super() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public ConcreteIteration clone() throws CloneNotSupportedException {
		ConcreteIteration concreteIteration = new ConcreteIteration() ;
		concreteIteration.copy(this) ;
		return concreteIteration ;
	}

	/**
	 * Copy the object.
	 * 
	 * @param _iteration
	 *            The iteration to copy.
	 */
	protected void copy(final ConcreteIteration _concreteIteration) {
		super.copy(_concreteIteration) ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof ConcreteIteration == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		ConcreteIteration concreteIteration = (ConcreteIteration) obj ;
		return new EqualsBuilder().appendSuper(super.equals(concreteIteration)).append(this.iteration, concreteIteration.iteration).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.activity.Activity#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.iteration).toHashCode() ;
	}
	
	/**
	 * Attach a iteration to a concreteIteration
	 *
	 * @param _iteration
	 */
	public void addIteration(Iteration _iteration) {
		this.iteration = _iteration ;
		_iteration.getConcreteIterations().add(this) ;
	}

	/**
	 * Detach a taskDescriptor to its taskDefinition
	 *
	 * @param _taskDefinition
	 */
	public void removeIteration(Iteration _iteration) {
		_iteration.getConcreteIterations().remove(this) ;
		this.iteration = null ;
	}

	/**
	 * Get the iteration rattached to the concreteIteration
	 * @return
	 */
	public Iteration getIteration() {
		return iteration;
	}

	/**
	 * Set the iteration rattached to the concreteIteration
	 * @param iteration
	 */
	public void setIteration(Iteration iteration) {
		this.iteration = iteration;
	}

}
