package wilos.presentation.assistant.view.panels;

import java.awt.Color;
import java.awt.Component;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.jdesktop.swingx.JXTree;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.utils.Constantes;

public class TreePanel extends JScrollPane implements TreeSelectionListener {
	
	private JXTree tree = null ;
	
	public TreePanel(){
		super();
		this.setLayout(new ScrollPaneLayout());
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void putTree (JXTree t){
		tree = t ;
		t.setCellRenderer(new WizardTreeRenderer());
		this.setViewportView(t);
		tree.addTreeSelectionListener(this); 
		
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
			if (userObject instanceof Element)
				return ((Element) userObject).getName();
			else if (userObject instanceof ConcreteTaskDescriptor) 
				return ((ConcreteTaskDescriptor) userObject).getConcreteName();
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
		
		private void initTree() {
			TreePanel.this.tree.removeAll();

			this.root = new DefaultMutableTreeNode(participant.getName());

			Set<RoleDescriptor> roles = participant.getRolesListForAProject();
			
			for (RoleDescriptor rd : roles) {
				WizardMutableTreeNode rdWmt = new WizardMutableTreeNode(rd);
				((DefaultMutableTreeNode) this.root).add(rdWmt);
				
				for(TaskDescriptor td : rd.getPrimaryTasks()) {
					
					for (ConcreteTaskDescriptor ctd : td.getConcreteTaskDescriptors()) {
						WizardMutableTreeNode ctdWmt = new WizardMutableTreeNode(ctd);
						rdWmt.add(ctdWmt);
						
						if (td.getTaskDefinition() != null) {
							for (Step s : td.getTaskDefinition().getSteps()) {
								WizardMutableTreeNode sWmt = new WizardMutableTreeNode(s);
								ctdWmt.add(sWmt);
							}
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
			//System.out.println(value.getClass().getSimpleName());
			//System.out.println((DefaultMutableTreeNode) value.);
			//System.out.println(selected);
//			DefaultMutableTreeNode tn = (DefaultMutableTreeNode) value;
//			JLabel label = new JLabel(tn.toString());
//			if (value.toString().startsWith("b")){
//				this.setForeground(Color.yellow);
//			}
//			if (value.toString().startsWith("v")){
//				this.setIcon(ImagesService.getImageIcon("images.iconPlay"));
//			}
//			return label;

			if (value.getClass().getSimpleName().equals("DefaultMutableTreeNode")) {
				this.setIcon(ImagesService.getImageIcon("images.iconParticipant"));
			}
			else {
				Object object = ((WizardMutableTreeNode) value).getUserObject();
				if (object instanceof RoleDescriptor) {
					this.setIcon(ImagesService.getImageIcon("images.iconRole"));
				}
				if (object instanceof ConcreteTaskDescriptor) {
					this.setIcon(ImagesService.getImageIcon("images.iconTaskDescriptor"));

					ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) object;
					if (ctd.getState() == Constantes.State.STARTED) {
						this.setForeground(Color.green);
					}
					else if (ctd.getState() == Constantes.State.READY) {
						this.setForeground(Color.yellow);
					}
					else if (ctd.getState() == Constantes.State.SUSPENDED) {
						this.setForeground(Color.pink);
					}
					else if (ctd.getState() == Constantes.State.FINISHED) {
						this.setForeground(Color.cyan);
					}
					else if (ctd.getState() == Constantes.State.CREATED) {
						this.setForeground(Color.black);
					}					
				}
//				if (selected) {
//					HTMLViewer.getInstance(p)
//				}
			}
			
			return this;
		}
		/*
		public Color getTextNonSelectionColor() {
			return Color.yellow;
		}*/
	}

	public void setParticipant(Participant participant) {
		WizardTreeModel wtm = new WizardTreeModel(participant);
		tree.setModel(wtm);
	}

	public void valueChanged(TreeSelectionEvent e) {
//		if (tree.getLastSelectedPathComponent() instanceof WizardMutableTreeNode) {
//			Element element = ((WizardMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
//			WizardStateMachine.getInstance().setFocusedObject(element);
//		}
//		else {
		if (tree.getLastSelectedPathComponent() != null) {
			Object objet = ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
			WizardStateMachine.getInstance().setFocusedObject(objet);
		}
		else {
			System.out.println("ERREUR : TreePanel.ValueChanged (enlever ce message quand pb resolu)");
		}
//		}
	}
}
