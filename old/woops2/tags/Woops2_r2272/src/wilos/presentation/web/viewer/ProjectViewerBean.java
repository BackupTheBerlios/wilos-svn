package wilos.presentation.web.viewer;

import wilos.business.services.misc.project.ProjectService;
import wilos.model.misc.project.Project;


public class ProjectViewerBean {

	private Project project;
	private ProjectService projectService;
	private String projectId ="";

	public void buildProjectModel(){
		this.project = new Project();
		if (!(projectId.equals("")) || projectId != null){
			this.project = this.projectService.getProject(this.projectId);
		}
	}

	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public ProjectService getProjectService() {
		return projectService;
	}
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}