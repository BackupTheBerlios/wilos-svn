package view.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import woops2.model.role.*;
import woops2.model.task.TaskDescriptor;

import org.jdesktop.swingx.JXTree;

import ressources.Bundle;

public class TaskPanel extends JPanel{
	
	private JTree taskTree = null;
	private JPanel panelTree = null;
	
	/**
	 * TaskPanel Constructor
	 *
	 */
	public TaskPanel() {
		init();
	}
	
	/**
	 * procedure init
	 * allow to initialise the panel
	 * 
	 */
	public void init() {
		
		this.setLayout(new BorderLayout());
		this.add(getTreePanel(),BorderLayout.CENTER);
	}
	
	/**
	 * This method initializes treePanel
	 * @return JPanel
	 */
	private JPanel getTreePanel() {
		if (panelTree == null)
		{
			RoleDescriptor r[] = new RoleDescriptor[2];			
			RoleDescriptor dev = new RoleDescriptor();
			
			dev.setName("developper");
			
			r[0] = dev;
			
			
			taskTree = getTreeWithTasks(r);
			panelTree = new JPanel();
			panelTree.setLayout(new GridLayout());
			panelTree.add(taskTree, null);
		}
		return panelTree;
	}
	
	private JTree getTreeWithTasks(RoleDescriptor roles[]){
		
		if (taskTree == null)
		{
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(Bundle.getText("taskPanel.rule"));
			taskTree = new JTree(root);
			System.out.println("apres root");
			for (int i = 0; i < roles.length; i++) {
				//RoleDescriptor aRole = new RoleDescriptor();
				TaskDescriptor task = new TaskDescriptor();
				
				
				//aRole.setName(roles[i].toString());
				DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(roles[i], true );
				// add the rule in the root node
				root.add(tmp);
			}			
		}
		return this.taskTree;
	}
	
}
