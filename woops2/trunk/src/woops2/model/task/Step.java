
package woops2.model.task ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.element.Element ;

/**
 * @author Sebastien BALARD
 * 
 * This class represents a section which represents structural subsections of a taskDefinition.
 * 
 */
public class Step extends Element implements Cloneable {

	/**
	 * the attached taskDefinition
	 */
	private TaskDefinition taskDefinition ;

	/**
	 * Default constructor
	 */
	public Step() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Step clone() throws CloneNotSupportedException {
		Step step = new Step() ;
		step.copy(this) ;
		return step ;
	}

	/**
	 * Copy the _step into this.
	 */
	protected void copy(final Step _step) {
		super.copy(_step) ;
		this.setTaskDefinition(_step.getTaskDefinition()) ;
	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Step == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		Step step = (Step) obj ;
		return new EqualsBuilder().appendSuper(super.equals(step)).append(this.taskDefinition, step.taskDefinition).isEquals() ;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {

		int i = new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.taskDefinition).toHashCode() ;
		System.out.println("step hascode -> " + i) ;
		return /*
				 * new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				 * .append(this.taskDefinition).toHashCode()
				 */i ;
	}

	/**
	 * Attach a step to a TaskDefiniton
	 * 
	 * @param _taskDefinition
	 */
	public void addToTaskDefinition(TaskDefinition _taskDefinition) {
		this.setTaskDefinition(_taskDefinition) ;
		_taskDefinition.getSteps().add(this) ;
	}

	/**
	 * Detach a step to its TaskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void removeFromTaskDefinition(TaskDefinition _taskDefinition) {
		_taskDefinition.getSteps().remove(this) ;
		this.taskDefinition = null ;
	}

	/**
	 * Getter of taskDefinition.
	 * 
	 * @return the taskDefinition.
	 */
	public TaskDefinition getTaskDefinition() {
		return this.taskDefinition ;
	}

	/**
	 * Setter of taskDefinition.
	 * 
	 * @param _taskDefinition
	 *            The taskDefinition to set.
	 */
	protected void setTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = _taskDefinition ;
	}

}
