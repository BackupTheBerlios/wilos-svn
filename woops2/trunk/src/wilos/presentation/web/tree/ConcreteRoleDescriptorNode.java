package wilos.presentation.web.tree;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreterole.ConcreteRoleDescriptor;

public class ConcreteRoleDescriptorNode extends DefaultMutableTreeNode {


	private static final long serialVersionUID = -3831221193711582741L;

	/**
	 *
	 * @param roleDescriptors
	 * @param _defaultMutableTreeNode
	 * @param _parentTree
	 */
	public ConcreteRoleDescriptorNode(
			ConcreteRoleDescriptor _concreteRoleDescriptor, HashMap<String, Object> _treeMap) {
		super();
		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setText(_concreteRoleDescriptor.getConcreteName());
		iceUserObject.setLeaf(true);

		// manage icon.
		iceUserObject.setLeafIcon("images/tree/icon_roledescriptor.gif");

		// node information
		iceUserObject.setId(_concreteRoleDescriptor.getId());
		iceUserObject.setPageId(WilosObjectNode.CONCRETEROLENODE);
		
		// add the concreteRoleDescriptor object with his id in the treeMap
		_treeMap.put(iceUserObject.getId(), _concreteRoleDescriptor);
	}

}

