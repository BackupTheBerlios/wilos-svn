package wilos.model.spem2.task;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.element.Element;
import wilos.model.spem2.guide.Guideline;

/**
 * 
 * This class represents a task is a content element that describes work being
 * performed by Roles. It defines one default performing RoleDefinition as well
 * as many additional performers.
 * 
 * @author Sebastien BALARD
 * @author eperico
 * @author Soosuske
 * 
 */
public class TaskDefinition extends Element implements Cloneable {

	/**
	 * Collection of Step
	 */
	private SortedSet<Step> steps;

	/**
	 * Collection of TaskDescriptor
	 */
	private Set<TaskDescriptor> taskDescriptors;

	// The Project of Process
	private Set<Guideline> guidelines;

	/**
	 * Default constructor
	 */
	public TaskDefinition() {
		super();
		this.steps = new TreeSet<Step>();
		this.taskDescriptors = new HashSet<TaskDescriptor>();
		this.guidelines = new HashSet<Guideline>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof TaskDefinition == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		TaskDefinition task = (TaskDefinition) obj;
		return new EqualsBuilder().appendSuper(super.equals(task)).append(
				this.steps, task.steps).append(this.taskDescriptors,
				task.taskDescriptors).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TaskDefinition clone() throws CloneNotSupportedException {
		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.copy(this);
		return taskDefinition;
	}

	/**
	 * Copy the _taskDefinition into this.
	 */
	protected void copy(final TaskDefinition _taskDefinition) {
		super.copy(_taskDefinition);
		this.setSteps(_taskDefinition.getSteps());
		this.setTaskDescriptors(_taskDefinition.getTaskDescriptors());
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
	 * Add a step collection to the step collection of a TaskDefinition
	 * 
	 * @param _steps
	 */
	public void addAllSteps(SortedSet<Step> _steps) {
		for (Step s : _steps) {
			s.addTaskDefinition(this);
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
	 * Add a taskDesciptor collection to the taskDesciptor collection of a
	 * TaskDefinition
	 * 
	 * @param _taskDesciptor
	 */
	public void addAllTaskDesciptors(Set<TaskDescriptor> _taskDesciptor) {
		for (TaskDescriptor td : _taskDesciptor) {
			td.addTaskDefinition(this);
		}
	}

	/**
	 * Remove a step from a taskDefinition
	 * 
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
			tmp.setTaskDefinition(null);
		}
		this.steps.clear();
	}

	/**
	 * Remove a taskDescriptor to its taskDefinition
	 * 
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
			tmp.setTaskDefinition(null);
		}
		this.taskDescriptors.clear();
	}

	/**
	 * Getter of steps.
	 * 
	 * @return the steps.
	 */
	public SortedSet<Step> getSteps() {
		return this.steps;
	}

	/**
	 * Setter of steps.
	 * 
	 * @param _steps
	 *            The steps to set.
	 */
	@SuppressWarnings("unused")
	private void setSteps(SortedSet<Step> _steps) {
		this.steps = _steps;
	}

	/**
	 * Getter of taskDescriptors.
	 * 
	 * @return the taskDescriptors.
	 */
	public Set<TaskDescriptor> getTaskDescriptors() {
		return this.taskDescriptors;
	}

	/**
	 * Setter of taskDescriptors.
	 * 
	 * @param _taskDescriptors
	 *            The taskDescriptors to set.
	 */
	@SuppressWarnings("unused")
	private void setTaskDescriptors(Set<TaskDescriptor> _taskDescriptors) {
		this.taskDescriptors = _taskDescriptors;
	}

	/**
	 * connection to guideline
	 */
	/**
	 * remove a guideline
	 * 
	 * @param _guideline
	 */
	public void removeGuideline(Guideline _guideline) {
		// _guideline.setTaskDefinition(null);
		this.guidelines.remove(_guideline);
	}

	/**
	 * add a guideline
	 * 
	 * @param _guideline
	 */
	public void addGuideline(Guideline _guideline) {
		this.guidelines.add(_guideline);
		_guideline.setTaskdefinition(this);
	}

	/**
	 * remove all Guideline
	 * 
	 */
	public void removeAllGuidelines() {
		for (Guideline guideline : this.guidelines) {
			guideline.setTaskdefinition(null);
		}
		this.guidelines.clear();
	}

	/**
	 * Add a RoleDescriptor collection to the RoleDescriptor collection of an
	 * RoleDefinition
	 * 
	 * @param _role
	 */
	public void addAllGuidelines(Set<Guideline> _guideline) {
		for (Guideline _guid1 : _guideline) {
			 _guid1.addTaskDefinition(this);
		}
	}

	public Set<Guideline> getGuidelines() {
		return guidelines;
	}

	public void setGuidelines(Set<Guideline> guidelines) {
		this.guidelines = guidelines;
	}

}
