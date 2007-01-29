package wilos.model.misc.concreterole;

import java.util.Set;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteRoleDescriptor {

	private String id;

	private String projectId;

	private String concreteName;

	private RoleDescriptor roleDescriptor;

	private Set<ConcreteTaskDescriptor> concreteTaskDescriptors;

	public void addRoleDescriptor(RoleDescriptor _roleDescriptor) {
		this.roleDescriptor = _roleDescriptor ;
		_roleDescriptor.getConcreteRoleDescriptors().add(this) ;
	}

	public void removeRoleDescriptor(RoleDescriptor _roleDescriptor) {
		_roleDescriptor.getConcreteRoleDescriptors().remove(this) ;
		this.roleDescriptor = null ;
	}

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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public RoleDescriptor getRoleDescriptor() {
		return roleDescriptor;
	}

	public void setRoleDescriptor(RoleDescriptor roleDescriptor) {
		this.roleDescriptor = roleDescriptor;
	}

	public Set<ConcreteTaskDescriptor> getConcreteTaskDescriptors() {
		return concreteTaskDescriptors;
	}

	public void setConcreteTaskDescriptors(
			Set<ConcreteTaskDescriptor> concreteTaskDescriptors) {
		this.concreteTaskDescriptors = concreteTaskDescriptors;
	}
}
