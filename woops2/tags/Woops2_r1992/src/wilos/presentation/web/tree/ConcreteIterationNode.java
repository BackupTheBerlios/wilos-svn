package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteIterationNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private ConcreteIteration concreteIteration;

	public ConcreteIterationNode(ConcreteIteration _concreteIteration,
			Set<RoleDescriptor> _roleDescriptors) {
		super();
		this.concreteIteration = _concreteIteration;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.concreteIteration.getConcreteName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_iteration.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_iteration.gif");
		// node information
		iceUserObject.setId(this.concreteIteration.getId());
		iceUserObject.setPageId(WilosObjectNode.ITERATIONNODE);

		for (ConcreteBreakdownElement concreteBreakdownElement : this.concreteIteration
				.getConcreteBreakdownElements()) {
			//FIXME LazyExceptrion
			if (concreteBreakdownElement instanceof ConcreteActivity) {
				this.add(new ConcreteActivityNode((ConcreteActivity) concreteBreakdownElement,
						_roleDescriptors));
			}
			else if (concreteBreakdownElement instanceof ConcreteTaskDescriptor) {
				this.add(new ConcreteTaskDescriptorNode((ConcreteTaskDescriptor) concreteBreakdownElement,
								_roleDescriptors));
			}
		}
	}

}
