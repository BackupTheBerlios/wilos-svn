package wilos.presentation.assistant.view.panels;

import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.spem2.element.Element;

public class WizardMutableTreeNode extends DefaultMutableTreeNode {
	
	public WizardMutableTreeNode(Object element) {
		super();
		this.userObject = element;
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