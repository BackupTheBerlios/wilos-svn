package wilos.presentation.web.icefaces.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.activity.Activity;

public class ActivityNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private Activity activity;

	public ActivityNode(Activity _activity) {
		super();
		this.activity = _activity;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.activity.getName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_activity.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_activity.gif");
		iceUserObject.setObjectId(this.activity.getId());
		/*for (BreakdownElement breakdownElement : this.phase
				.getBreakDownElements()) {
			if (breakdownElement instanceof TaskDescriptor) {
				TaskDescriptor taskDescriptor = (TaskDescriptor) breakdownElement;
				this.add(new TaskDescriptorNode(taskDescriptor));
			}
		}*/
	}

}