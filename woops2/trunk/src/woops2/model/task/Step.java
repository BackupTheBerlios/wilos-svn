package woops2.model.task;

import woops2.model.element.Element;

/**
 * @author Sebastien BALARD
 * 
 * This class represents a section which represents structural subsections of a
 * taskDefinition.
 * 
 */
public class Step extends Element {

	/**
	 * the attached taskDefinition
	 */
	private TaskDefinition taskDefinition;

	/**
	 * Default constructor
	 */
	public Step() {
	}

	/**
	 * Getter of taskDefinition.
	 * 
	 * @return the taskDefinition.
	 */
	public TaskDefinition getTaskDefinition() {
		return this.taskDefinition;
	}

	/**
	 * Setter of taskDefinition.
	 * 
	 * @param _taskDefinition
	 *            The taskDefinition to set.
	 */
	private void setTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = _taskDefinition;
	}

	/**
	 * Attach a step to a TaskDefiniton
	 * 
	 * @param _taskDefinition
	 */
	public void addToTaskDefinition(TaskDefinition _taskDefinition) {
		this.setTaskDefinition(_taskDefinition);
		_taskDefinition.getSteps().add(this);
	}

	/**
	 * Detach a step to its TaskDefinition
	 * 
	 * @param _taskDefinition
	 */
	public void removeFromTaskDefinition(TaskDefinition _taskDefinition) {
		this.taskDefinition = null;
		_taskDefinition.getSteps().remove(this);
	}

}
