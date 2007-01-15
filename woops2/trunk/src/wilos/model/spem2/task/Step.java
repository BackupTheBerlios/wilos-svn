
package wilos.model.spem2.task ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import wilos.model.spem2.element.Element;

/**
 * 
 * This class represents a section which represents structural subsections of a taskDefinition.
 * 
 * @author Sebastien BALARD
 * 
 */
public class Step extends Element implements Cloneable, Comparable {

	/**
	 * the attached taskDefinition
	 */
	private TaskDefinition taskDefinition ;

	/**
	 * Default constructor
	 */
	public Step() {
		super() ;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#equals(java.lang.Object)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.taskDefinition).toHashCode() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object _arg0) {
		// Return 0, if this is equal to _arg0.
		if(this.equals((Step) _arg0))
			return 0 ;
		else
			return -1 ;
	}

	/**
	 * Add a step to a TaskDefiniton
	 * 
	 * @param _taskDefinition
	 */
	public void addTaskDefinition(TaskDefinition _taskDefinition) {
		this.setTaskDefinition(_taskDefinition) ;
		_taskDefinition.getSteps().add(this) ;
	}

	/**
	 * Detach a step to its TaskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void removeTaskDefinition(TaskDefinition _taskDefinition) {
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
	public void setTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = _taskDefinition ;
	}
}
