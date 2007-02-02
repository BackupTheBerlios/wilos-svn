package wilos.model.misc.concreterole;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteRoleDescriptor {

	private String id;

	private Project project;

	private String concreteName;

	private RoleDescriptor roleDescriptor;

	private Set<ConcreteTaskDescriptor> concreteTaskDescriptors;

	private Participant participant;

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
		this.setConcreteName(_concreteRoleDescriptor.getConcreteName());
		this.setParticipant(_concreteRoleDescriptor.getParticipant());
		this.setProject(_concreteRoleDescriptor.getProject());
		this.setRoleDescriptor(_concreteRoleDescriptor.getRoleDescriptor());
		this.setConcreteTaskDescriptors(_concreteRoleDescriptor
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
		return new EqualsBuilder().append(this.concreteName,concreteRoleDescriptor.concreteName)
								  .append(this.concreteTaskDescriptors,concreteRoleDescriptor.concreteTaskDescriptors)
								  .append(this.participant, concreteRoleDescriptor.participant)
								  .append(this.project, concreteRoleDescriptor.project)
								  .append(this.roleDescriptor, concreteRoleDescriptor.roleDescriptor)
								  .isEquals();
	}

	/*
	 * (non-Javadoc)
	 *
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(this.id).append(
				this.concreteName).append(this.participant)
				.append(this.project).append(this.roleDescriptor).toHashCode();
	}

	/*
	 * relation between ConcreteRoleDescriptor and Participant.
	 */

	public void addParticipant(Participant _participant) {
		this.participant = _participant;
		_participant.getConcreteRoleDescriptors().add(this);
	}

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
		_concreteTaskDescriptor.setConcreteRoleDescriptor(this);
	}

	public void removeConcreteTaskDescriptor(
			ConcreteTaskDescriptor _concreteTaskDescriptor) {
		this.concreteTaskDescriptors.remove(_concreteTaskDescriptor);
		_concreteTaskDescriptor.setConcreteRoleDescriptor(null);
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
			ctd.setConcreteRoleDescriptor(null);
		this.getConcreteTaskDescriptors().clear();
	}

	/*
	 * Getter and Setter.
	 *
	 */

	public String getConcreteName() {
		return concreteName;
	}

	public void setConcreteName(String concreteName) {
		this.concreteName = concreteName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}