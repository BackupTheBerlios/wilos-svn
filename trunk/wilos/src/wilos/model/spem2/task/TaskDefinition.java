/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <massie@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.model.spem2.task;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.element.Element;
import wilos.model.spem2.guide.Guidance;

/**
 * 
 * This class represents a task is a content element that describes work being
 * performed by Roles. It defines one default performing RoleDefinition as well
 * as many additional performers.
 * 
 * @author Sebastien BALARD
 * @author eperico
 * @author Soosuske
 * @author garwind
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

	private Set<Guidance> guidances;
	
	private String alternatives;
	
	private String purpose;

	/**
	 * Default constructor
	 */
	public TaskDefinition() {
		super();
		this.steps = new TreeSet<Step>();
		this.taskDescriptors = new HashSet<TaskDescriptor>();
		this.guidances = new HashSet<Guidance>();
		this.alternatives = "";
		this.purpose = "";
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
				task.taskDescriptors).append(this.guidances,
						task.guidances).append(this.alternatives,
								task.alternatives).append(this.purpose,
										task.purpose).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.element.Element#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.alternatives).append(this.purpose).toHashCode();
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
		this.steps.addAll(_taskDefinition.getSteps());
		this.taskDescriptors.addAll(_taskDefinition.getTaskDescriptors());
		this.guidances.addAll(_taskDefinition.getGuidances());
		this.alternatives = _taskDefinition.getAlternatives();
		this.purpose = _taskDefinition.getPurpose();
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
	public void setSteps(SortedSet<Step> _steps) {
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

	
	/*
	 * connection to guidances
	 */
	public void removeGuidance(Guidance _guidance) {
		_guidance.getTaskDefinitions().remove(this);
		this.guidances.remove(_guidance);
	}

	
	public void addGuidance(Guidance _guidance) {
		this.guidances.add(_guidance);
		_guidance.getTaskDefinitions().add(this);
	}

	
	public void removeAllGuidances() {
		for (Guidance guidance : this.guidances) {
			guidance.getTaskDefinitions().remove(this);
		}
		this.guidances.clear();
	}
	
	public void addAllGuidances(Set<Guidance> _guidances) {
		for (Guidance _guid1 : _guidances) {
			 this.addGuidance(_guid1);
		}
	}

	public Set<Guidance> getGuidances() {
		return this.guidances;
	}

	public void setGuidances(Set<Guidance> _guidances) {
		this.guidances = _guidances;
	}

	
	/**
	 * Getter of alternatives
	 * 
	 * @return the alternatives
	 */
	public String getAlternatives() {
		return alternatives;
	}
	
	/**
	 * Setter of alternatives
	 * 
	 * @param _alternatives
	 * 				the alternatives to set
	 */

	public void setAlternatives(String _alternatives) {
		this.alternatives = _alternatives;
	}
	
	/**
	 * Getter of purpose
	 * 
	 * @return the purpose
	 */

	public String getPurpose() {
		return purpose;
	}
	
	/**
	 * Setter of purpose
	 * 
	 * @param _purpose
	 * 				the purpose to set
	 */

	public void setPurpose(String _purpose) {
		this.purpose = _purpose;
	}
}
