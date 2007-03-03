package wilos.presentation.web.tree;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.utils.Constantes.State;

public class ConcreteTaskDescriptorNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -2090785940515259986L;

	/**
	 *
	 * @param roleDescriptors
	 * @param _defaultMutableTreeNode
	 * @param _parentTree
	 */
	public ConcreteTaskDescriptorNode(
			ConcreteTaskDescriptor _concreteTaskDescriptor, HashMap<String, Object> _treeMap) {
		super();
		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setText(_concreteTaskDescriptor.getConcreteName());
		iceUserObject.setLeaf(true);

		// manage icon.
		if (_concreteTaskDescriptor.getState().equals(State.STARTED))
			iceUserObject.setLeafIcon("images/tree/icon_task_play.gif");
		else if (_concreteTaskDescriptor.getState().equals(State.SUSPENDED))
			iceUserObject.setLeafIcon("images/tree/icon_task_pause.gif");
		else if (_concreteTaskDescriptor.getState().equals(State.FINISHED))
			iceUserObject.setLeafIcon("images/tree/icon_task_over.gif");
		else if(_concreteTaskDescriptor.getMainConcreteRoleDescriptor() != null)
			iceUserObject.setLeafIcon("images/tree/icon_task_busy.gif");
		else
			iceUserObject.setLeafIcon("images/tree/icon_task_free.gif");

		// node information
		iceUserObject.setId(_concreteTaskDescriptor.getId());
		iceUserObject.setPageId(WilosObjectNode.CONCRETETASKNODE);
		
		// add the concreteTaskDescriptor object with his id in the treeMap
		_treeMap.put(iceUserObject.getId(), _concreteTaskDescriptor);
	}

}
