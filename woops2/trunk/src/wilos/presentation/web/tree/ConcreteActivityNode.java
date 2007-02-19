package wilos.presentation.web.tree;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.role.RoleDescriptor;

public class ConcreteActivityNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private ConcreteActivity concreteActivity;

	public ConcreteActivityNode(ConcreteActivity _concreteActivity,
			Set<RoleDescriptor> _roleDescriptors) {
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
					this.add(new ConcreteActivityNode(ca, _roleDescriptors));
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