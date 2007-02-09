package wilos.presentation.assistant.view.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTree;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.task.Step;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.utils.Constantes;

public class TreePanel extends JScrollPane implements TreeSelectionListener {
	
	private JXTree tree = null ;
	
	public TreePanel(){
		super();
		this.setLayout(new ScrollPaneLayout());
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void putTree (JXTree t){
		tree = t ;
		t.setCellRenderer(new WizardTreeRenderer());
		this.setViewportView(t);
		tree.addTreeSelectionListener(this); 
		tree.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent arg0) {
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent arg0) {
				// selection with right button
				if (arg0.getButton() == MouseEvent.BUTTON3){
					TreePanel.this.tree.setSelectionRow(TreePanel.this.tree.getRowForLocation(arg0.getX(), arg0.getY()));
				}
			}

			public void mouseReleased(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3){
					// displaying contextual menu
					TreePath path = TreePanel.this.tree.getPathForLocation(arg0.getX(), arg0.getY()); 
					
					if (path != null) { 
							if (path.getLastPathComponent() == TreePanel.this.tree.getLastSelectedPathComponent()){
								WizardControler.getInstance().showContextualMenu(arg0);
							}
					}
					
				}
				
			}
			
		});
		//DefaultTreeCellRenderer df;
		//df.getTreeCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}
	
	public JXTree getTree (){
		return tree;
	}
	
	private class WizardMutableTreeNode extends DefaultMutableTreeNode {
		
		public WizardMutableTreeNode(Object element) {
			super();
			this.userObject = element;
		}

		public Object getUserObject() {
			return userObject;
		}
		
		public String toString() {
			if (userObject instanceof ConcreteRoleDescriptor)
				return ((ConcreteRoleDescriptor) userObject).getRoleDescriptor().getPresentationName();
			else if (userObject instanceof ConcreteIteration)
				return ((ConcreteIteration) userObject).getIteration().getPresentationName();
			else if (userObject instanceof ConcretePhase)
				return ((ConcretePhase) userObject).getPhase().getPresentationName();
			else if (userObject instanceof ConcreteActivity)
				return ((ConcreteActivity) userObject).getActivity().getPresentationName();
			else if (userObject instanceof ConcreteTaskDescriptor) 
				return ((ConcreteTaskDescriptor) userObject).getTaskDescriptor().getPresentationName();
			else if (userObject instanceof Element)
				return ((Element)userObject).getName();
			else
				return "";
		}
	}
	
	private class WizardTreeModel extends DefaultTreeModel {
		Participant participant;
		//String root;
		
		public WizardTreeModel(Participant aParticipant) {
			super(null);
			this.participant = aParticipant;
			initTree();
		}
		
		// TODO : actuellement prise en compte d'une super activity a voir pour plusieurs
		private ConcreteActivity getActivity (Set<ConcreteActivity> s){
			ConcreteActivity ca = null ;
			try {
				ca = s.iterator().next();
			}
			catch(Exception e){
				
			}
			
			return ca ;
		}
		
		private void initTree() {
			TreePanel.this.tree.removeAll();
			HashMap<ConcreteActivity, WizardMutableTreeNode> mapActivity = new HashMap<ConcreteActivity, WizardMutableTreeNode>();
			
			this.root = new DefaultMutableTreeNode(participant.getName());
			
			Set<ConcreteRoleDescriptor> roles = participant.getConcreteRoleDescriptors();
			
			// browse all the concrete roles
			for (ConcreteRoleDescriptor crd : roles) {
				DefaultMutableTreeNode precedent = null;
				WizardMutableTreeNode rdWmt = new WizardMutableTreeNode(crd);

				ConcreteActivity ca = getActivity(crd.getSuperConcreteActivities());
				WizardMutableTreeNode nodeAct = null  ;
				if (ca == null){
					((DefaultMutableTreeNode)this.root).add(rdWmt);
				}
				else {
					while (ca != null){
						// search if the activity is already treated as a node
						if (!mapActivity.containsKey(ca)){
							nodeAct = new WizardMutableTreeNode(ca);
							mapActivity.put(ca, nodeAct);
						}
						else {
							nodeAct = mapActivity.get(ca);
						}
						if (precedent == null){
							nodeAct.add(rdWmt);
						}
						else {
							nodeAct.add(precedent);
						}
						ca = getActivity(ca.getSuperConcreteActivities());
						if (ca == null ){
							((DefaultMutableTreeNode)this.root).add(nodeAct);
						}
						precedent = nodeAct ;
					}				
				}
				// brows all the concrete tasks
				for(ConcreteTaskDescriptor ctd : crd.getConcreteTaskDescriptors()) {
					
					WizardMutableTreeNode ctdWmt = new WizardMutableTreeNode(ctd);
					rdWmt.add(ctdWmt);
					// browse all the steps of the task def in task descriptors of concretetaskdescriptor
					if (ctd.getTaskDescriptor().getTaskDefinition() != null) {
						for (Step s : ctd.getTaskDescriptor().getTaskDefinition().getSteps()) {
							WizardMutableTreeNode sWmt = new WizardMutableTreeNode(s);
							ctdWmt.add(sWmt);
						}
					}
				}
				//((DefaultMutableTreeNode) this.root).add(new DefaultMutableTreeNode(rd.getName()));
			}
		}

//		public void addTreeModelListener(TreeModelListener arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public Object getChild(Object arg0, int arg1) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		public int getChildCount(Object arg0) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public int getIndexOfChild(Object arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public Object getRoot() {
//			// TODO Auto-generated method stub
//			return root;
//		}
//
//		public boolean isLeaf(Object arg0) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		public void removeTreeModelListener(TreeModelListener arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void valueForPathChanged(TreePath arg0, Object arg1) {
//			// TODO Auto-generated method stub
//			
//		}		
	}
	
	private class WizardTreeRenderer extends DefaultTreeCellRenderer {
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
				boolean expanded, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			
			if (value.getClass().getSimpleName().equals("DefaultMutableTreeNode")) {
				this.setIcon(ImagesService.getImageIcon("images.iconParticipant"));
			}
			else {
				Object object = ((WizardMutableTreeNode) value).getUserObject();
				if (object instanceof ConcreteRoleDescriptor) {
					ConcreteRoleDescriptor crd = (ConcreteRoleDescriptor)object ;
					this.setIcon(ImagesService.getImageIcon("images.iconRole"));
				}
				else if(object instanceof ConcreteIteration){
					this.setIcon(ImagesService.getImageIcon("images.iconIteration"));
				}
				else if(object instanceof ConcretePhase){
					this.setIcon(ImagesService.getImageIcon("images.iconPhase"));
				}
				else if(object instanceof ConcreteActivity){
					this.setIcon(ImagesService.getImageIcon("images.iconActivity"));
				}
				else if (object instanceof ConcreteTaskDescriptor) {
					ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor)object ;
					this.setIcon(ImagesService.getImageIcon("images.iconTaskDescriptor"));
					if (ctd.getState() == Constantes.State.STARTED) {
						this.setForeground(Color.decode("#008800"));
					}
					else if (ctd.getState() == Constantes.State.READY) {
						this.setForeground(Color.decode("#FF9900"));
					}
					else if (ctd.getState() == Constantes.State.SUSPENDED) {
						this.setForeground(Color.pink);
					}
					else if (ctd.getState() == Constantes.State.FINISHED) {
						this.setForeground(Color.decode("#0066FF"));						
					}
					else if (ctd.getState() == Constantes.State.CREATED) {
						this.setForeground(Color.black);
					}					
				}
				else if (object instanceof Step) {
					this.setIcon(ImagesService.getImageIcon("images.iconStep"));
				}
			}
			
			return this;
		}
	}

	public void setParticipant(Participant participant) {
		WizardTreeModel wtm = new WizardTreeModel(participant);
		tree.setModel(wtm);
	}

	public void valueChanged(TreeSelectionEvent e) {
		if (tree.getLastSelectedPathComponent() != null) {
			Object objet = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
			WizardStateMachine.getInstance().setFocusedObject(objet);
		}
	}
}
