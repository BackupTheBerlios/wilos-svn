package wilos.presentation.assistant.view.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.webservices.WizardServicesProxy;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.TaskDescriptor;
import javax.swing.JFrame;

public class TaskPanel extends JPanel implements TreeSelectionListener {
	
	private JTree taskTree = null;
	private JPanel panelTree = null;
	private MainFrame mainFrame = null;
        private ArrayList<RoleDescriptor> roleList;
	
	/**
	 * TaskPanel Constructor
	 *
	 */
	public TaskPanel(MainFrame aFrame, ArrayList<RoleDescriptor> roleL) {
                this.roleList = roleL;
		init();
		mainFrame = aFrame;
	}
	
	/**
	 * procedure init
	 * allow to initialise the panel
	 * 
	 */
	public void init() {
		this.setLayout(new BorderLayout());
		
		taskTree = initTaskTree(this.roleList);
		
		panelTree = initTreePanel();
		this.add(panelTree,BorderLayout.CENTER);
		
		taskTree.addTreeSelectionListener(this); 
	}
	
	/**
	 * This method initializes treePanel
	 * @return JPanel
	 */
	private JPanel initTreePanel() {
		if (taskTree == null)
			return null;
		
		if (panelTree == null)
			panelTree = new JPanel();
		
		JScrollPane myScrollPane = new JScrollPane(taskTree);
		panelTree.setLayout(new GridLayout());
		panelTree.add(myScrollPane, null);

		return panelTree;
	}
	
	private JTree initTaskTree(ArrayList<RoleDescriptor> rolesListe) {
		JTree aJTree;
		
		RoleDescriptorInfo tmpRoleDescriptor;
		TaskDescriptorInfo tmpTaskDescriptor;
		HashSet<TaskDescriptor> tasksListe;
		Iterator it;
		
		DefaultMutableTreeNode rootNode;
		DefaultMutableTreeNode roleNode;
		DefaultMutableTreeNode taskNode;
		
		rootNode = new DefaultMutableTreeNode(Bundle.getText("taskPanel.rule"));
		aJTree = new JTree(rootNode);			
		
		for (int i = 0; i < rolesListe.size(); i++) {
			tmpRoleDescriptor = new RoleDescriptorInfo((RoleDescriptor) rolesListe.get(i));
			roleNode = new DefaultMutableTreeNode(tmpRoleDescriptor, true );
			
			tasksListe = (HashSet<TaskDescriptor>) tmpRoleDescriptor.getPrimaryTasks();
			it = tasksListe.iterator();
			while (it.hasNext()) {
				tmpTaskDescriptor = new TaskDescriptorInfo((TaskDescriptor) it.next());
				taskNode = new DefaultMutableTreeNode(tmpTaskDescriptor, true );
				roleNode.add(taskNode);
			}
			
			rootNode.add(roleNode);
		}	
		return aJTree;
	}
	
	public void valueChanged(TreeSelectionEvent e) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
        		taskTree.getLastSelectedPathComponent();
		
		if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (nodeInfo instanceof TaskDescriptorInfo) {
            TaskDescriptorInfo tmpTaskDescriptor = (TaskDescriptorInfo) nodeInfo;
            mainFrame.moveHTML();
            HTMLViewer.getInstance(null).setMessage(tmpTaskDescriptor.getTaskDescriptor().getDescription());
            
        } else if (nodeInfo instanceof RoleDescriptorInfo){
        	RoleDescriptorInfo tmpRoleDescriptor = (RoleDescriptorInfo) nodeInfo;
        	mainFrame.moveHTML();
        	HTMLViewer.getInstance(null).setMessage(tmpRoleDescriptor.getRoleDescriptor().getDescription());
        	
        }
	}
	
	private class TaskDescriptorInfo {
		private TaskDescriptor myTaskDescriptor;
		
		public TaskDescriptorInfo(TaskDescriptor descriptor) {
			myTaskDescriptor = descriptor;
		}

		public String toString() {
			return myTaskDescriptor.getName();
		}
		
		public TaskDescriptor getTaskDescriptor() {
			return myTaskDescriptor;
		}
	}
	
	private class RoleDescriptorInfo {
		private RoleDescriptor myRoleDescriptor;
		
		public RoleDescriptorInfo(RoleDescriptor descriptor) {
			myRoleDescriptor = descriptor;
		}

		public HashSet<TaskDescriptor> getPrimaryTasks() {
			return (HashSet<TaskDescriptor>) myRoleDescriptor.getPrimaryTasks();
		}

		public String toString() {
			return myRoleDescriptor.getName();
		}
		
		public RoleDescriptor getRoleDescriptor() {
			return myRoleDescriptor;
		}
	}
	
}
