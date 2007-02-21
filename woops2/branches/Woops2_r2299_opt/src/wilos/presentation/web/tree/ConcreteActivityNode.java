package wilos.presentation.web.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

public class ConcreteActivityNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private ConcreteActivity concreteActivity;

	public ConcreteActivityNode(ConcreteActivity _concreteActivity,
			boolean _isConcreteTaskDescriptorsTree) {
		super();
		this.concreteActivity = _concreteActivity;

		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.concreteActivity.getConcreteName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_activity.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_activity.gif");
		// node information
		iceUserObject.setId(this.concreteActivity.getId());
		iceUserObject.setPageId(WilosObjectNode.ACTIVITYNODE);

		for (ConcreteBreakdownElement concreteBreakdownElement : this.concreteActivity
				.getConcreteBreakdownElements()) {
			if (concreteBreakdownElement instanceof ConcreteActivity) {
				ConcreteActivity ca = (ConcreteActivity) concreteBreakdownElement;
				if (ca.getIsInUsed()) {
					this.add(new ConcreteActivityNode(ca,
							_isConcreteTaskDescriptorsTree));
				}
			} else if (_isConcreteTaskDescriptorsTree) {
				if (concreteBreakdownElement instanceof ConcreteTaskDescriptor) {
					ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) concreteBreakdownElement;
					if (ctd.getIsInUsed()) {
						this.add(new ConcreteTaskDescriptorNode(ctd));
					}
				}
			} else {
				if (concreteBreakdownElement instanceof ConcreteRoleDescriptor) {
					this.add(new ConcreteRoleDescriptorNode(
									(ConcreteRoleDescriptor) concreteBreakdownElement));
				}
			}
		}
	}

}