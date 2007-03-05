package wilos.presentation.web.tree;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

public class ConcretePhaseNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private ConcretePhase concretePhase;

	public ConcretePhaseNode(ConcretePhase _concretePhase,
			boolean _isConcreteTaskDescriptorsTree, HashMap<String, Object> _treeMap) {
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

		// add the concretePhase object with his id in the treeMap
		_treeMap.put(iceUserObject.getId(), _concretePhase);
		
		for (ConcreteBreakdownElement concreteBreakdownElement : this.concretePhase
				.getConcreteBreakdownElements()) {
			if (concreteBreakdownElement instanceof ConcreteIteration) {
				ConcreteIteration ci = (ConcreteIteration) concreteBreakdownElement;
				if (ci.getIsInUsed()) {
					this.add(new ConcreteIterationNode(ci,
							_isConcreteTaskDescriptorsTree, _treeMap));
				}
			} else if (concreteBreakdownElement instanceof ConcreteActivity) {
				ConcreteActivity ca = (ConcreteActivity) concreteBreakdownElement;
				if (ca.getIsInUsed()) {
					this.add(new ConcreteActivityNode(ca,
							_isConcreteTaskDescriptorsTree, _treeMap));
				}
			} else if (_isConcreteTaskDescriptorsTree) {
				if (concreteBreakdownElement instanceof ConcreteTaskDescriptor) {
					ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) concreteBreakdownElement;
					if (ctd.getIsInUsed()) {
						this.add(new ConcreteTaskDescriptorNode(ctd, _treeMap));
					}
				}
			} else {
				if (concreteBreakdownElement instanceof ConcreteRoleDescriptor) {
					ConcreteRoleDescriptor crd = (ConcreteRoleDescriptor) concreteBreakdownElement;
					this.add(new ConcreteRoleDescriptorNode(crd, _treeMap));
				}
			}
		}
	}

}
