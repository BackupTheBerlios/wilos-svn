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

package wilos.presentation.assistant.view.panels;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.element.Element;

public class WizardMutableTreeNode extends DefaultMutableTreeNode {
	
//	public boolean equals(Object obj) {
//		return (this.getUserObject().equals(this.getUserObject()));
//	}

	public WizardMutableTreeNode(Object element) {
		super();
		this.userObject = element;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultMutableTreeNode#clone()
	 */
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		WizardMutableTreeNode retour = (WizardMutableTreeNode) super.clone();
		for(int i = 0 ; i < this.getChildCount() ; i++ ){
			retour.add((WizardMutableTreeNode) ((WizardMutableTreeNode) this.getChildAt(i)).clone());
		}
		return retour ;
		
	}

	public Object getUserObject() {
		return userObject;
	}
	
	public String toString() {
//		if (userObject instanceof ConcreteRoleDescriptor)
//			return ((ConcreteRoleDescriptor) userObject).getRoleDescriptor().getPresentationName();
//		else if (userObject instanceof Project)
//			return ((Project) userObject).getConcreteName();
//		else if (userObject instanceof ConcreteIteration)
//			return ((ConcreteIteration) userObject).getIteration().getPresentationName();
//		else if (userObject instanceof ConcretePhase)
//			return ((ConcretePhase) userObject).getPhase().getPresentationName();
//		else if (userObject instanceof ConcreteActivity)
//			return ((ConcreteActivity) userObject).getActivity().getPresentationName();
//		else if (userObject instanceof ConcreteTaskDescriptor) 
//			return ((ConcreteTaskDescriptor) userObject).getTaskDescriptor().getPresentationName();
		if (userObject instanceof ConcreteBreakdownElement){
			return ((ConcreteBreakdownElement) userObject).getConcreteName();
		}
		else if (userObject instanceof Element)
			return ((Element)userObject).getName();
		else
			return "";
	}
}