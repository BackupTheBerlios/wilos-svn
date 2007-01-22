package wilos.presentation.web.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.phase.Phase;
import wilos.model.spem2.task.TaskDescriptor;

public class PhaseNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private Phase phase;

	public PhaseNode(Phase _phase) {
		super();
		this.phase = _phase;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.phase.getName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_phase.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_phase.gif");
		iceUserObject.setObjectId(this.phase.getId());
		for (BreakdownElement breakdownElement : this.phase
				.getBreakDownElements()) {
			if (breakdownElement instanceof Iteration) {
				this.add(new IterationNode((Iteration) breakdownElement));
			} 
			else if (breakdownElement instanceof Activity) {
				this.add(new ActivityNode((Activity) breakdownElement));
			}
			// TODO Change with ConcreteTaskDescriptorNode !!!
			else if (breakdownElement instanceof TaskDescriptor) {
				this.add(new TaskDescriptorNode(
						(TaskDescriptor) breakdownElement));
			}
		}
	}

}
