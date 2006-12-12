package wilos.presentation.assistant.view.panels.tabs;

import java.util.Iterator;
import java.util.SortedSet;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;



public class StepPanel extends JPanel {
	private static StepPanel panel = null ;
	private TaskDescriptor currentElement = null ;
	private JTree stepTree = null;
	DefaultMutableTreeNode rootNode;
	DefaultMutableTreeNode taskNode;

	
	private StepPanel() {
		super();
		this.setVisible(false);
	}

	public static StepPanel getInstance() {
		if(panel == null) {
			panel = new StepPanel();
		}
		return panel;
	}

	public TaskDescriptor getCurrentElement() {
		return currentElement;
	}

	
	
	/**
	 * create the tree of the new TaskDescriptor
	 * @param currentElement : the new TaskDescriptor
	 */
	public void setCurrentElement(TaskDescriptor currentElement) {
		this.currentElement = currentElement;

		// if stepTree exists, we remove it to replace it
		if(stepTree != null)
			this.remove(stepTree);
		
		// the title of the tree is the name of the task
		rootNode = new DefaultMutableTreeNode(currentElement.getName());
		stepTree = new JTree(rootNode);
	
		TaskDefinition td = currentElement.getTaskDefinition();
		if(td != null) {
			SortedSet stepList = td.getSteps();
			if(stepList != null) {
				Iterator itSteps = stepList.iterator();
			
				while(itSteps.hasNext()) {
					taskNode= new DefaultMutableTreeNode(((Step)itSteps.next()).getName(),false);
					rootNode.add(taskNode);
				}
			}
		}
		this.add(stepTree);
	}
}
