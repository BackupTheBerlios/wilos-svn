package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;
import wilos.model.spem2.role.RoleDescriptor;

public class ProjectNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -9148715541110574441L;

	private Project project;

	public ProjectNode(Project _project, Set<RoleDescriptor> _roleDescriptors) {
		super();
		this.project = _project;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.project.getConcreteName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_process.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_process.gif");
		// node information
		iceUserObject.setId(this.project.getId());
		iceUserObject.setPageId(WilosObjectNode.PROJECTNODE);

		if (this.project.getProcess() != null) {
			iceUserObject.setText(this.project.getConcreteName() + " ("
					+ this.project.getProcess().getPresentationName() + ")");

			//Nested nodes.
			for (ConcreteBreakdownElement concreteBreakdownElement : this.project.getConcreteBreakdownElements()) {
				if (concreteBreakdownElement instanceof ConcretePhase) {
					this.add(new ConcretePhaseNode((ConcretePhase) concreteBreakdownElement,
							_roleDescriptors));
				} else if (concreteBreakdownElement instanceof ConcreteIteration) {
					this.add(new ConcreteIterationNode((ConcreteIteration) concreteBreakdownElement,
							_roleDescriptors));
				} else if (concreteBreakdownElement instanceof ConcreteActivity) {
					this.add(new ConcreteActivityNode((ConcreteActivity) concreteBreakdownElement,
							_roleDescriptors));
				} else if (concreteBreakdownElement instanceof ConcreteTaskDescriptor) {
					this.add(new ConcreteTaskDescriptorNode((ConcreteTaskDescriptor) concreteBreakdownElement,
								_roleDescriptors));
				}
			}
		}
	}
}
