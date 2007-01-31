package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class IterationNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private Iteration iteration;

	public IterationNode(Iteration _iteration,
			Set<RoleDescriptor> _roleDescriptors) {
		super();
		this.iteration = _iteration;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.iteration.getPresentationName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_iteration.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_iteration.gif");
		// node information
		iceUserObject.setId(this.iteration.getId());
		iceUserObject.setPageId(WilosObjectNode.ITERATIONNODE);

		for (BreakdownElement breakdownElement : this.iteration
				.getBreakDownElements()) {
			//FIXME LazyExceptrion
			if (breakdownElement instanceof Activity) {
				this.add(new ActivityNode((Activity) breakdownElement,
						_roleDescriptors));
			}
			else if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor td = (TaskDescriptor) breakdownElement;
				if ((_roleDescriptors == null)||(_roleDescriptors.size() == 0)||(_roleDescriptors.contains(td.getMainRole())))
					for (ConcreteTaskDescriptor ctd : td
							.getConcreteTaskDescriptors())
						this.add(new ConcreteTaskDescriptorNode(ctd,
								_roleDescriptors));
			}
		}
	}

}
