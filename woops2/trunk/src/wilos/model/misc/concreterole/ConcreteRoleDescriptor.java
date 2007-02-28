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

	private Set<ConcreteTaskDescriptor> concreteTaskDescriptors;

	public ConcreteRoleDescriptor() {
		this.concreteTaskDescriptors = new HashSet<ConcreteTaskDescriptor>();
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
		this.concreteTaskDescriptors.addAll(_concreteRoleDescriptor
				.getConcreteTaskDescriptors());
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
								  .append(this.concreteTaskDescriptors,concreteRoleDescriptor.concreteTaskDescriptors)
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

	public void addConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		this.concreteTaskDescriptors.add(_concreteTaskDescriptor);
		_concreteTaskDescriptor.setMainConcreteRoleDescriptor(this);
	}

	public void removeConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		_concreteTaskDescriptor.setMainConcreteRoleDescriptor(null);
		this.concreteTaskDescriptors.remove(_concreteTaskDescriptor);
	}

	public void addAllConcreteTaskDescriptors(
			Set<ConcreteTaskDescriptor> _concreteTaskDescriptors) {
		this.concreteTaskDescriptors.addAll(_concreteTaskDescriptors);

		for (ConcreteTaskDescriptor ctd : _concreteTaskDescriptors) {
			ctd.addConcreteRoleDescriptor(this);
		}
	}

	public void removeAllConcreteTaskDescriptors() {
		for (ConcreteTaskDescriptor ctd : this.getConcreteTaskDescriptors())
			ctd.setMainConcreteRoleDescriptor(null);
		this.getConcreteTaskDescriptors().clear();
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

	public Set<ConcreteTaskDescriptor> getConcreteTaskDescriptors() {
		return concreteTaskDescriptors;
	}

	public void setConcreteTaskDescriptors(
			Set<ConcreteTaskDescriptor> concreteTaskDescriptors) {
		this.concreteTaskDescriptors = concreteTaskDescriptors;
	}

	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

}