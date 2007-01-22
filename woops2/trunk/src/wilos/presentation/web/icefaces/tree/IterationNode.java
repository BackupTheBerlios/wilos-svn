package wilos.presentation.web.icefaces.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.iteration.Iteration;
import wilos.model.spem2.task.TaskDescriptor;

public class IterationNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private Iteration iteration;

	public IterationNode(Iteration _iteration) {
		super();
		this.iteration = _iteration;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.iteration.getName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_iteration.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_iteration.gif");
		iceUserObject.setObjectId(_iteration.getId());
		for (BreakdownElement breakdownElement : this.iteration
				.getBreakDownElements()) {
			if (breakdownElement instanceof Activity) {
				Activity activity = (Activity) breakdownElement;
				this.add(new ActivityNode(activity));
			}
			else if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement;
				this.add(new TaskDescriptorNode(taskDescriptor));
			}
		}
	}

}

