
package woops2.model.task ;

import java.util.HashSet ;
import java.util.Set ;

import org.apache.commons.lang.builder.EqualsBuilder ;
import org.apache.commons.lang.builder.HashCodeBuilder ;

import woops2.model.element.Element ;

/**
 * @author Sebastien BALARD
 * @author eperico
 * 
 * This class represents a task is a content element that describes work being performed by Roles.
 * It defines one default performing RoleDefinition as well as many additional performers.
 * 
 */
public class TaskDefinition extends Element implements Cloneable {

	/**
	 * Collection of Step
	 */
	private Set<Step> steps ;

	/**
	 * Collection of TaskDescriptor
	 */
	private Set<TaskDescriptor> taskDescriptors ;

	/**
	 * Default constructor
	 */
	public TaskDefinition() {
		super() ;
		this.steps = new HashSet<Step>() ;
		this.taskDescriptors = new HashSet<TaskDescriptor>() ;
	}

	/**
	 * Indicates whether another object is "equal to" this one.
	 * 
	 * @return true if equal else false
	 */
	public boolean equals(Object obj) {
		if(obj instanceof TaskDefinition == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}
		TaskDefinition task = (TaskDefinition) obj ;
		return new EqualsBuilder().appendSuper(super.equals(task)).append(this.steps, task.steps).append(this.taskDescriptors, task.taskDescriptors).isEquals() ;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return a hash code
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public TaskDefinition clone() throws CloneNotSupportedException {
		TaskDefinition taskDefinition = new TaskDefinition() ;
		taskDefinition.copy(this) ;
		return taskDefinition ;
	}

	/**
	 * Copy the _taskDefinition into this.
	 */
	protected void copy(final TaskDefinition _taskDefinition) {
		super.copy(_taskDefinition) ;
		this.setSteps(_taskDefinition.getSteps()) ;
		this.setTaskDescriptors(_taskDefinition.getTaskDescriptors()) ;
	}

	/**
	 * Add a step from a taskDefinition
	 * 
	 * @param _step
	 */
	public void addStep(Step _step) {
		this.steps.add(_step) ;
		_step.setTaskDefinition(this) ;
	}

	/**
	 * Add a step collection to the step collection of a TaskDefinition
	 * 
	 * @param _steps
	 */
	public void addAllSteps(Set<Step> _steps) {
		for(Step s : _steps){
			s.addTaskDefinition(this) ;
		}
	}

	/**
	 * Add a taskDescriptor from a taskDefinition
	 * 
	 * @param _taskDescriptor
	 */
	public void addTaskDescriptor(TaskDescriptor _taskDescriptor) {
		this.taskDescriptors.add(_taskDescriptor) ;
		_taskDescriptor.setTaskDefinition(this) ;
	}

	/**
	 * Add a taskDesciptor collection to the taskDesciptor collection of a TaskDefinition
	 * 
	 * @param _taskDesciptor
	 */
	public void addAllTaskDesciptors(Set<TaskDescriptor> _taskDesciptor) {
		for(TaskDescriptor td : _taskDesciptor){
			td.addToTaskDefinition(this) ;
		}
	}

	/**
	 * Remove a step from a taskDefinition
	 * 
	 * @param _step
	 */
	public void removeStep(Step _step) {
		_step.setTaskDefinition(null) ;
		this.steps.remove(_step) ;
	}

	/**
	 * Remove all steps from a TaskDefinition
	 */
	public void removeAllSteps() {
		for(Step tmp : this.steps){
			tmp.setTaskDefinition(null) ;
		}
		this.steps.clear() ;
	}

	/**
	 * Remove a taskDescriptor to its taskDefinition
	 * 
	 * @param _taskDescriptor
	 */
	public void removeTaskDescriptor(TaskDescriptor _taskDescriptor) {
		_taskDescriptor.setTaskDefinition(null) ;
		this.taskDescriptors.remove(_taskDescriptor) ;
	}

	/**
	 * Remove all taskDescriptors to its TaskDefinition
	 */
	public void removeAllTaskDescriptors() {
		for(TaskDescriptor tmp : this.taskDescriptors){
			tmp.setTaskDefinition(null) ;
		}
		this.taskDescriptors.clear() ;
	}

	/**
	 * Getter of steps.
	 * 
	 * @return the steps.
	 */
	public Set<Step> getSteps() {
		return this.steps ;
	}

	/**
	 * Setter of steps.
	 * 
	 * @param _steps
	 *            The steps to set.
	 */
	@ SuppressWarnings ("unused")
	private void setSteps(Set<Step> _steps) {
		this.steps = _steps ;
	}

	/**
	 * Getter of taskDescriptors.
	 * 
	 * @return the taskDescriptors.
	 */
	public Set<TaskDescriptor> getTaskDescriptors() {
		return this.taskDescriptors ;
	}

	/**
	 * Setter of taskDescriptors.
	 * 
	 * @param _taskDescriptors
	 *            The taskDescriptors to set.
	 */
	@ SuppressWarnings ("unused")
	private void setTaskDescriptors(Set<TaskDescriptor> _taskDescriptors) {
		this.taskDescriptors = _taskDescriptors ;
	}
}
