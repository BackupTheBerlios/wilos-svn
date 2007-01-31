package wilos.model.misc.concreterole;

import java.util.HashSet;
import java.util.Set;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteRoleDescriptor {

	private String id;

	//TODO
	//private Project project;

	private String projectId;
	
	private String concreteName;

	private RoleDescriptor roleDescriptor;

	private Set<ConcreteTaskDescriptor> concreteTaskDescriptors;

	private Participant participant;

	public ConcreteRoleDescriptor() {
		this.concreteTaskDescriptors = new HashSet<ConcreteTaskDescriptor>();
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
		this.concreteTaskDescriptors.addAll(_concreteTaskDescriptors) ;
		
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}