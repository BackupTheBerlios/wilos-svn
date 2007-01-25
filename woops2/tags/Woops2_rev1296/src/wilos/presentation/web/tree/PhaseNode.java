package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class PhaseNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private Phase phase;

	public PhaseNode(Phase _phase, Set<RoleDescriptor> _roleDescriptors) {
		super();
		this.phase = _phase;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.phase.getName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_phase.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_phase.gif");
		// node informations
		iceUserObject.setId(this.phase.getId());
		iceUserObject.setPageId(WilosObjectNode.PHASENODE);
		
		for (BreakdownElement breakdownElement : this.phase
				.getBreakDownElements()) {
			if (breakdownElement instanceof Iteration) {
				this.add(new IterationNode((Iteration) breakdownElement, _roleDescriptors));
			} 
			else if (breakdownElement instanceof Activity) {
				this.add(new ActivityNode((Activity) breakdownElement, _roleDescriptors));
			} 
			else if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor td = (TaskDescriptor) breakdownElement;
				if ((_roleDescriptors == null)||(_roleDescriptors.contains(td.getMainRole())))
					for (ConcreteTaskDescriptor ctd : td
							.getConcreteTaskDescriptors())
						this.add(new ConcreteTaskDescriptorNode(ctd,
								_roleDescriptors));
			}
		}
	}

}
