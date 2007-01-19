package wilos.presentation.assistant.view.panels.tabs;

import java.awt.GridLayout;
import java.util.Iterator;
import java.util.SortedSet;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.spem2.element.Element;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;



public class StepPanel extends JPanel implements TreeSelectionListener {
	private JTree stepTree = null;
	private static StepPanel panel = null ;

	DefaultMutableTreeNode rootNode;
	DefaultMutableTreeNode taskNode;

	private TaskDefinition currentElement = null ;
	public static Element selectedElement = null;

	
	private StepPanel() {
		super();
		this.setVisible(false);
		this.setLayout(new GridLayout());
	}


	public static StepPanel getInstance() {
		if(panel == null) {
			panel = new StepPanel();
		}
		return panel;
	}

	
	public TaskDefinition getCurrentElement() {
		return currentElement;
	}

	/**
	 * create the tree of the new TaskDescriptor
	 * @param currentElement : the new TaskDescriptor
	 */
	public void setCurrentElement(TaskDefinition currentElement) {
		this.currentElement = currentElement;

		// if stepTree exists, we remove it to replace it
		if(stepTree != null)
			this.remove(stepTree);
		
		// the title of the tree is the name of the task
		rootNode = new DefaultMutableTreeNode(currentElement.getName());
		stepTree = new JTree(rootNode);
	
		TaskDefinition td = currentElement;
		if(td != null) {
			SortedSet stepList = td.getSteps();
			if(stepList != null) {
				Iterator itSteps = stepList.iterator();
			
				while(itSteps.hasNext()) {
					StepInfo tmpStep = new StepInfo((Step)itSteps.next());
					taskNode= new DefaultMutableTreeNode(tmpStep,false);
					rootNode.add(taskNode);
				}
			}
		}
		this.add(stepTree);
		stepTree.addTreeSelectionListener(this);
	}

	public void valueChanged(TreeSelectionEvent e) {
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
        		stepTree.getLastSelectedPathComponent();
		
		if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (nodeInfo instanceof StepInfo) {
        	selectedElement = ((StepInfo)nodeInfo).getStep() ;
        	StepInfo tmpStep = (StepInfo) nodeInfo;
     //       mainFrame.moveHTML();	// TODO JF : verifier
            //HTMLViewer.getInstance(null).setMessage(tmpStep.getStep().getDescription());
        	HTMLViewer.getInstance(null).setBreakDownElement(tmpStep.getStep());
            
        }
	}
	
	private class StepInfo {
		private Step myStep;
		
		public StepInfo(Step s) {
			myStep = s;
		}

		public String toString() {
			return myStep.getName();
		}
		
		public Step getStep() {
			return myStep;
		}
	}

}
