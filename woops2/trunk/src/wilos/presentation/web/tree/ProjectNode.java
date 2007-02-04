package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class ProjectNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -9148715541110574441L;

	private Project project;

	public ProjectNode(Project _project, Set<RoleDescriptor> _roleDescriptors) {
		super();
		this.project = _project;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.project.getName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_process.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_process.gif");
		// node information
		iceUserObject.setId(this.project.getProject_id());
		iceUserObject.setPageId(WilosObjectNode.PROJECTNODE);

		if (this.project.getProcess() != null) {
			//Project node name.
			iceUserObject.setText(this.project.getName() + " ("
					+ this.project.getProcess().getPresentationName() + ")");

			//Nested nodes.
			for (BreakdownElement breakdownElement : this.project.getProcess().getBreakdownElements()) {
				if (breakdownElement instanceof Phase) {
					this.add(new PhaseNode((Phase) breakdownElement,
							_roleDescriptors));
				} else if (breakdownElement instanceof Iteration) {
					this.add(new IterationNode((Iteration) breakdownElement,
							_roleDescriptors));
				} else if (breakdownElement instanceof Activity) {
					this.add(new ActivityNode((Activity) breakdownElement,
							_roleDescriptors));
				} else if (breakdownElement instanceof TaskDescriptor) {
					TaskDescriptor td = (TaskDescriptor) breakdownElement;
					if ((_roleDescriptors == null) || (_roleDescriptors.size() == 0)
							|| (_roleDescriptors.contains(td.getMainRole())))
						for (ConcreteTaskDescriptor ctd : td
								.getConcreteTaskDescriptors())
							this.add(new ConcreteTaskDescriptorNode(ctd,
									_roleDescriptors));
				}
			}
		}
	}
}
