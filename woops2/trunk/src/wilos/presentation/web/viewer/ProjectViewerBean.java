package wilos.presentation.web.viewer;

import java.util.ArrayList;
import java.util.List;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteworkbreakdownelement.ConcreteWorkBreakdownElement;
import wilos.model.misc.project.Project;

public class ProjectViewerBean extends ViewerBean{

	private Project project;

	public void changeConcreteName() {
		super.getProjectService().saveProject(project);

		// Refresh the treebean.
		super.refreshProjectTree();
	}

	public List<ConcreteBreakdownElement> getConcreteBreakdownElementsList() {
		List<ConcreteBreakdownElement> list = new ArrayList<ConcreteBreakdownElement>();
		list.addAll(this.project.getConcreteBreakdownElements());

		// Filter to obtain only concreteworkbreakdownelement (without
		// concreterole).
		List<ConcreteBreakdownElement> cwbdes = new ArrayList<ConcreteBreakdownElement>();
		for (ConcreteBreakdownElement cbde : list)
			if (cbde instanceof ConcreteWorkBreakdownElement)
				cwbdes.add(cbde);

		return cwbdes;
	}

	public void saveProject() {
		super.getConcreteBreakdownElementService()
				.saveAllFirstSonsConcreteBreakdownElementsForConcreteActivity(this.project);

		// Reload the treebean.
		super.refreshProjectTree();
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}