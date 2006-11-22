
package woops2.model.task ;

import java.util.HashSet;
import java.util.Set;

import woops2.model.element.Element;

/**
 * @author Sebastien BALARD
 * @author eperico
 * 
 * This class represents a task is a content element that describes work being
 * performed by Roles. It defines one default performing RoleDefinition as well
 * as many additional performers.
 * 
 */
public class TaskDefinition extends Element {

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
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
	private void setTaskDescriptors(Set<TaskDescriptor> _taskDescriptors) {
		this.taskDescriptors = _taskDescriptors ;
	}
	
	/**
	 * Add a step from a taskDefinition
	 * 
	 * @param _step
	 */
	public void addStep(Step _step) {
		this.steps.add(_step);
		_step.setTaskDefinition(this);
	}
	
	/**
	 * Add a step collection to the step collection of
	 * a TaskDefinition
	 * 
	 * @param _steps
	 */
	public void addToAllSteps(
			Set<Step> _steps) {
		for (Step s : _steps) {
			s.addToTaskDefinition(this);
		}
	}
	
	/**
	 * Add a taskDescriptor from a taskDefinition
	 * 
	 * @param _taskDescriptor
	 */
	public void addTaskDescriptor(TaskDescriptor _taskDescriptor) {
		this.taskDescriptors.add(_taskDescriptor);
		_taskDescriptor.setTaskDefinition(this);
	} 

	
	/**
	 * Add a taskDesciptor collection to the taskDesciptor collection of
	 * a TaskDefinition
	 * 
	 * @param _taskDesciptor
	 */
	public void addToAllTaskDesciptors(
			Set<TaskDescriptor> _taskDesciptor) {
		for (TaskDescriptor td : _taskDesciptor) {
			td.addToTaskDefinition(this);
		}
	}

	/**
	 * Remove a step from a taskDefinition
	 * @param _step
	 */
	public void removeStep(Step _step) {
		_step.setTaskDefinition(null);
		this.steps.remove(_step);
	} 

	/**
	 * Remove all steps from a TaskDefinition
	 */
	public void removeAllSteps() {
		for (Step tmp : this.steps) {
			tmp.removeFromTaskDefinition(this);
		}
		this.steps.clear();
	}
	
	/**
	 * Remove a taskDescriptor to its taskDefinition
	 * @param _taskDescriptor
	 */
	public void removeTaskDescriptor(TaskDescriptor _taskDescriptor) {
		_taskDescriptor.setTaskDefinition(null);
		this.taskDescriptors.remove(_taskDescriptor);
	} 

	/**
	 * Remove all taskDescriptors to its TaskDefinition
	 */
	public void removeAllTaskDescriptors() {
		for (TaskDescriptor tmp : this.taskDescriptors) {
			tmp.removeFromTaskDefinition(this);
		}
		this.taskDescriptors.clear();
	}
}
