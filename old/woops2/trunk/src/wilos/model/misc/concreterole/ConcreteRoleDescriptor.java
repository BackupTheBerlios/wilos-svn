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

package wilos.model.misc.concreterole;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteRoleDescriptor extends ConcreteBreakdownElement {

	private RoleDescriptor roleDescriptor;

	private Participant participant;

	private Set<ConcreteTaskDescriptor> primaryConcreteTaskDescriptors;

	public ConcreteRoleDescriptor() {
		this.primaryConcreteTaskDescriptors = new HashSet<ConcreteTaskDescriptor>();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConcreteRoleDescriptor clone() throws CloneNotSupportedException {
		ConcreteRoleDescriptor concreteRoleDescriptor = new ConcreteRoleDescriptor();
		concreteRoleDescriptor.copy(this);
		return concreteRoleDescriptor;
	}

	/**
	 * Copy the _roleDescriptor into this.
	 */
	protected void copy(final ConcreteRoleDescriptor _concreteRoleDescriptor) {
		super.copy(_concreteRoleDescriptor);
		this.participant = _concreteRoleDescriptor.getParticipant();
		this.roleDescriptor = _concreteRoleDescriptor.getRoleDescriptor();
		this.primaryConcreteTaskDescriptors.addAll(_concreteRoleDescriptor
				.getPrimaryConcreteTaskDescriptors());
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteRoleDescriptor == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ConcreteRoleDescriptor concreteRoleDescriptor = (ConcreteRoleDescriptor) obj;
		return new EqualsBuilder().appendSuper(super.equals(concreteRoleDescriptor))
								  .append(this.primaryConcreteTaskDescriptors,concreteRoleDescriptor.primaryConcreteTaskDescriptors)
								  .append(this.participant, concreteRoleDescriptor.participant)
								  .append(this.roleDescriptor, concreteRoleDescriptor.roleDescriptor)
								  .isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.participant).append(this.roleDescriptor).toHashCode();
	}

	/*
	 * relation between ConcreteRoleDescriptor and Participant.
	 */

	/**
	 *
	 */
	public void addParticipant(Participant _participant) {
		this.participant = _participant;
		_participant.getConcreteRoleDescriptors().add(this);
	}

	/**
	 *
	 * @param _participant
	 */
	public void removeParticipant(Participant _participant) {
		_participant.getConcreteRoleDescriptors().remove(this);
		this.participant = null;
	}

	/*
	 * Relation between ConcreteRoleDescriptor and RoleDescriptor.
	 *
	 */

	public void addRoleDescriptor(RoleDescriptor _roleDescriptor) {
		this.roleDescriptor = _roleDescriptor;
		_roleDescriptor.getConcreteRoleDescriptors().add(this);
	}

	public void removeRoleDescriptor(RoleDescriptor _roleDescriptor) {
		_roleDescriptor.getConcreteRoleDescriptors().remove(this);
		this.roleDescriptor = null;
	}

	/*
	 * Relation between ConcreteRoleDescriptor and ConcreteTaskDescriptor.
	 *
	 */

	public void addPrimaryConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		this.primaryConcreteTaskDescriptors.add(_concreteTaskDescriptor);
		_concreteTaskDescriptor.setMainConcreteRoleDescriptor(this);
	}

	public void removePrimaryConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		_concreteTaskDescriptor.setMainConcreteRoleDescriptor(null);
		this.primaryConcreteTaskDescriptors.remove(_concreteTaskDescriptor);
	}

	public void addAllPrimaryConcreteTaskDescriptors(
			Set<ConcreteTaskDescriptor> _concreteTaskDescriptors) {
		this.primaryConcreteTaskDescriptors.addAll(_concreteTaskDescriptors);

		for (ConcreteTaskDescriptor ctd : _concreteTaskDescriptors) {
			ctd.addConcreteRoleDescriptor(this);
		}
	}

	public void removeAllPrimaryConcreteTaskDescriptors() {
		for (ConcreteTaskDescriptor ctd : this.getPrimaryConcreteTaskDescriptors())
			ctd.setMainConcreteRoleDescriptor(null);
		this.getPrimaryConcreteTaskDescriptors().clear();
	}

	/*
	 * Getter and Setter.
	 *
	 */

	public RoleDescriptor getRoleDescriptor() {
		return roleDescriptor;
	}

	public void setRoleDescriptor(RoleDescriptor _roleDescriptor) {
		this.roleDescriptor = _roleDescriptor;
	}

	public Set<ConcreteTaskDescriptor> getPrimaryConcreteTaskDescriptors() {
		return primaryConcreteTaskDescriptors;
	}

	public void setPrimaryConcreteTaskDescriptors(
			Set<ConcreteTaskDescriptor> concreteTaskDescriptors) {
		this.primaryConcreteTaskDescriptors = concreteTaskDescriptors;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

}