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

			// Nested nodes.
			for (ConcreteBreakdownElement concreteBreakdownElement : this.project
					.getConcreteBreakdownElements()) {
				if (concreteBreakdownElement instanceof ConcretePhase) {
					ConcretePhase cp = (ConcretePhase) concreteBreakdownElement;
					if (cp.getIsInUsed()) {
						this.add(new ConcretePhaseNode(cp, _roleDescriptors));
					}
				} else if (concreteBreakdownElement instanceof ConcreteIteration) {
					ConcreteIteration ci = (ConcreteIteration) concreteBreakdownElement;
					if (ci.getIsInUsed()) {
						this.add(new ConcreteIterationNode(ci,
										_roleDescriptors));
					}
				} else if (concreteBreakdownElement instanceof ConcreteActivity) {
					ConcreteActivity ca = (ConcreteActivity) concreteBreakdownElement;
					if (ca.getIsInUsed()) {
						this.add(new ConcreteActivityNode(ca,
										_roleDescriptors));
					}
				} else if (concreteBreakdownElement instanceof ConcreteTaskDescriptor) {
					ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) concreteBreakdownElement;
					if (ctd.getIsInUsed()) {
						this.add(new ConcreteTaskDescriptorNode(ctd,
								_roleDescriptors));
					}
				}
			}
		}
	}
}
