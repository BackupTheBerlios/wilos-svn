package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;

public class ActivityNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private Activity activity;

	public ActivityNode(Activity _activity, Set<RoleDescriptor> _roleDescriptors) {
		super();
		this.activity = _activity;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.activity.getName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_activity.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_activity.gif");
		// node information
		iceUserObject.setId(this.activity.getId());
		iceUserObject.setPageId(WilosObjectNode.ACTIVITYNODE);
		
		for (BreakdownElement breakdownElement : this.activity
				.getBreakDownElements()) {
			if (breakdownElement instanceof Activity) {
				this.add(new ActivityNode(
						(Activity) breakdownElement, _roleDescriptors));
			}
			else if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor td = (TaskDescriptor) breakdownElement;
				for (ConcreteTaskDescriptor ctd : td.getConcreteTaskDescriptors())
					this.add(new ConcreteTaskDescriptorNode(ctd, _roleDescriptors));
			}
		}
	}

}