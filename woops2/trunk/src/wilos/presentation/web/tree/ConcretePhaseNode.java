package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcretePhaseNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private ConcretePhase concretePhase;

	public ConcretePhaseNode(ConcretePhase _concretePhase, Set<RoleDescriptor> _roleDescriptors) {
		super();
		this.concretePhase = _concretePhase;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.concretePhase.getConcreteName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_phase.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_phase.gif");
		// node informations
		iceUserObject.setId(this.concretePhase.getId());
		iceUserObject.setPageId(WilosObjectNode.PHASENODE);

		for (ConcreteBreakdownElement concreteBreakdownElement : this.concretePhase
				.getConcreteBreakdownElements()) {
			if (concreteBreakdownElement instanceof ConcreteIteration) {
				this.add(new ConcreteIterationNode((ConcreteIteration) concreteBreakdownElement, _roleDescriptors));
			}
			else if (concreteBreakdownElement instanceof ConcreteActivity) {
				this.add(new ConcreteActivityNode((ConcreteActivity) concreteBreakdownElement, _roleDescriptors));
			}
			else if (concreteBreakdownElement instanceof ConcreteTaskDescriptor) {
				this.add(new ConcreteTaskDescriptorNode((ConcreteTaskDescriptor) concreteBreakdownElement,
								_roleDescriptors));
			}
		}
	}

}
