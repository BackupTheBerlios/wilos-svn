package wilos.presentation.web.icefaces.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.task.TaskDescriptor;

public class ProjectNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -9148715541110574441L;

	private Project project;

	public ProjectNode(Project _project) {
		super();
		this.project = _project;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.project.getName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_process.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_process.gif");
		//TODO iceUserObject.setObjectId(this.project.getId());
		for (BreakdownElement breakdownElement : this.project.getProcess()
				.getBreakDownElements()) {
			if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement;
				for(ConcreteTaskDescriptor concreteTaskDescriptor : taskDescriptor.getConcreteTaskDescriptors())
					//TODO if(concreteTaskDescriptor.getProjectId().equals(this.project.getId()))
					this.add(new ConcreteTaskDescriptorNode(concreteTaskDescriptor));
			}
		}
	}
}
