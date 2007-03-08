/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
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

package wilos.model.misc.concretetask;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptor extends ConcreteWorkBreakdownElement {

	private TaskDescriptor taskDescriptor;

	private ConcreteRoleDescriptor mainConcreteRoleDescriptor;

	private String state;

	private Date realStartingDate;

	private Date realFinishingDate;

	private float remainingTime;

	private float accomplishedTime;

	public ConcreteTaskDescriptor() {
		super();
		this.state = State.CREATED;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConcreteTaskDescriptor clone() throws CloneNotSupportedException {
		ConcreteTaskDescriptor concreteTaskDescriptor = new ConcreteTaskDescriptor();
		concreteTaskDescriptor.copy(this);
		return concreteTaskDescriptor;
	}

	/**
	 * Copy the _roleDescriptor into this.
	 */
	protected void copy(final ConcreteTaskDescriptor _concreteTaskDescriptor) {
		super.copy(_concreteTaskDescriptor);
		this.taskDescriptor = _concreteTaskDescriptor.getTaskDescriptor();
		this.mainConcreteRoleDescriptor = _concreteTaskDescriptor
				.getMainConcreteRoleDescriptor();
		this.accomplishedTime = _concreteTaskDescriptor
				.getAccomplishedTime();
		this.realFinishingDate = _concreteTaskDescriptor
				.getRealFinishingDate();
		this.realStartingDate = _concreteTaskDescriptor
				.getRealStartingDate();
		this.remainingTime = _concreteTaskDescriptor.getRemainingTime();
		this.state = _concreteTaskDescriptor.getState();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteTaskDescriptor == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteTaskDescriptor concreteTaskDescriptor = (ConcreteTaskDescriptor) obj;
		return new EqualsBuilder().appendSuper(
				super.equals(concreteTaskDescriptor)).append(
				this.accomplishedTime, concreteTaskDescriptor.accomplishedTime)
				.append(this.taskDescriptor,
						concreteTaskDescriptor.taskDescriptor).append(
						this.mainConcreteRoleDescriptor,
						concreteTaskDescriptor.mainConcreteRoleDescriptor).append(
						this.realFinishingDate,
						concreteTaskDescriptor.realFinishingDate).append(
						this.realStartingDate,
						concreteTaskDescriptor.realStartingDate).append(
						this.remainingTime,
						concreteTaskDescriptor.remainingTime).append(
						this.state, concreteTaskDescriptor.state).isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
				.append(this.accomplishedTime)
				.append(this.state)
				.append(this.taskDescriptor)
				.append(this.mainConcreteRoleDescriptor)
				.append(this.realFinishingDate).append(this.realStartingDate)
				.append(this.remainingTime).toHashCode();
	}

	/*
	 * Relation between ConcreteRoleDescriptor and ConcreteTaskDescriptor.
	 *
	 */

	public void addConcreteRoleDescriptor(
			ConcreteRoleDescriptor _concreteRoleDescriptor) {
		this.mainConcreteRoleDescriptor = _concreteRoleDescriptor;
		_concreteRoleDescriptor.getPrimaryConcreteTaskDescriptors().add(this);
	}

	public void removeConcreteRoleDescriptor(
			ConcreteRoleDescriptor _concreteRoleDescriptor) {
		_concreteRoleDescriptor.getPrimaryConcreteTaskDescriptors().remove(this);
		this.mainConcreteRoleDescriptor = null;
	}

	/*
	 * Relation between TaskDescriptor and ConcreteTaskDescriptor.
	 *
	 */

	public void addTaskDescriptor(TaskDescriptor _taskDescriptor) {
		this.taskDescriptor = _taskDescriptor;
		_taskDescriptor.getConcreteTaskDescriptors().add(this);
	}

	public void removeTaskDescriptor(TaskDescriptor _taskDescriptor) {
		_taskDescriptor.getConcreteTaskDescriptors().remove(this);
		this.taskDescriptor = null;
	}

	/*
	 * Getter & Setter.
	 *
	 */

	public float getAccomplishedTime() {
		return accomplishedTime;
	}

	public void setAccomplishedTime(float accomplishedTime) {
		this.accomplishedTime = accomplishedTime;
	}

	public TaskDescriptor getTaskDescriptor() {
		return taskDescriptor;
	}

	public void setTaskDescriptor(TaskDescriptor taskDescriptor) {
		this.taskDescriptor = taskDescriptor;
	}

	public ConcreteRoleDescriptor getMainConcreteRoleDescriptor() {
		return mainConcreteRoleDescriptor;
	}

	public void setMainConcreteRoleDescriptor(
			ConcreteRoleDescriptor _mainConcreteRoleDescriptor) {
		this.mainConcreteRoleDescriptor = _mainConcreteRoleDescriptor;
	}

	public Date getRealFinishingDate() {
		return realFinishingDate;
	}

	public void setRealFinishingDate(Date realFinishingDate) {
		this.realFinishingDate = realFinishingDate;
	}

	public Date getRealStartingDate() {
		return realStartingDate;
	}

	public void setRealStartingDate(Date realStartingDate) {
		this.realStartingDate = realStartingDate;
	}

	public float getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(float remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
