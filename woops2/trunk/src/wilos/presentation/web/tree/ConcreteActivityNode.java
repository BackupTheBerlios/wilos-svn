/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.web.tree;

import java.util.HashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;

public class ConcreteActivityNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -5277094164644525311L;

	private ConcreteActivity concreteActivity;

	public ConcreteActivityNode(ConcreteActivity _concreteActivity,
			boolean _isConcreteTaskDescriptorsTree,
			HashMap<String, Object> _treeMap) {
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

		// add the concreteActivity object with his id in the treeMap
		_treeMap.put(iceUserObject.getId(), _concreteActivity);

		for (ConcreteBreakdownElement concreteBreakdownElement : this.concreteActivity
				.getConcreteBreakdownElements()) {
			if (concreteBreakdownElement instanceof ConcreteActivity) {
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