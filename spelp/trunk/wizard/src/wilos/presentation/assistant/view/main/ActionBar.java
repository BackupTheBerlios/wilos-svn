package wilos.presentation.assistant.view.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JToolBar;
import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.task.TaskDefinition;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.panels.WizardStateMachine;
import wilos.presentation.assistant.view.panels.WorkTabbedPane;
import wilos.presentation.assistant.view.panels.tabs.StepPanel;
import wilos.presentation.assistant.view.panels.tabs.TaskPanel;

public class ActionBar extends JToolBar {
	public final int INVISIBLE = 1;
	public final int ENABLED = 2;
	public final int DISABLED = 3;
	
	private JButton runButton ;
	
	private JButton jButtonPauseTask = null;
	private JButton jButtonFinished = null;
	private JButton jButtonPlayTask = null;
	private JButton jButtonRefresh = null;
	private JCheckBox jCheckBoxShowViewer;
	
	
	
	public ActionBar(){
		super ();
		this.setLayout(new FlowLayout());
		runButton = createButton(null,
				Bundle.getText("ActionBar.tooltip.run"), 
				ImagesService.getImageIcon("images.run"));
		//this.add(runButton);
		this.add(getJButtonPlayTask());
		this.add(getJButtonPauseTask());
		this.add(getJButtonFinished());
		this.addSeparator();
		this.addSeparator();
		this.add(getJButtonRefresh());
		this.addSeparator();
		this.addSeparator();
		this.add(getJCheckBoxShowViewer());
		this.setFloatable(false);
		//this.setBounds(0, 0, 150, 300);
		this.jButtonPlayTask.addActionListener(
			new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					DefaultMutableTreeNode dmt =  (DefaultMutableTreeNode)WizardStateMachine.getInstance().getTreePanel().getTree().getLastSelectedPathComponent();
					
					if(dmt != null) {
						ConcreteTaskDescriptor selectedTask = (ConcreteTaskDescriptor)dmt.getUserObject();
						if(selectedTask.getId() != null) {
							WizardStateMachine.getInstance().changeHTMLViewerBehavior(true);
							WizardControler.getInstance().startConcreteTaskDescriptor(selectedTask);
							WizardStateMachine.getInstance().refreshParticipant();
						}
					}
					
				}
		});
		
		/*runButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if (TaskPanel.selectedElement != null){
							WorkTabbedPane.getInstance().displayTask(WorkTabbedPane.getInstance().getTabCount() == 1);
							StepPanel.getInstance().setCurrentElement((TaskDefinition) TaskPanel.selectedElement);
						}
						
					}
				}
		);*/
	}
	
	private JButton createButton(String label, String toolTip,Icon image){
		JButton bt ;
		if (label == null){
			bt = new JButton(image);
		}
		else
		{
			bt = new JButton(label);
		}
		bt.setToolTipText(toolTip);
		return bt ;
	}
	
	private JButton getJButtonPlayTask() {
		if (jButtonPlayTask == null) {
			jButtonPlayTask = new JButton();
			jButtonPlayTask.setIcon(ImagesService.getImageIcon("images.iconPlay"));
		}
		return jButtonPlayTask;
	}
	
	public JCheckBox getJCheckBoxShowViewer() {
		if (jCheckBoxShowViewer == null) {
			jCheckBoxShowViewer = new JCheckBox("Informations");
			jCheckBoxShowViewer.setSize(100, 100);
			jCheckBoxShowViewer.setSelected(true);
			jCheckBoxShowViewer.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					WizardStateMachine.getInstance().changeHTMLViewerBehavior(jCheckBoxShowViewer.isSelected());
				}
		});
		}
		return jCheckBoxShowViewer;
	}
	
	/**
	 * This method initializes jButtonPauseTask	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonPauseTask() {
		if (jButtonPauseTask == null) {
			jButtonPauseTask = new JButton();
			jButtonPauseTask.setIcon(ImagesService.getImageIcon("images.iconPause"));
		}
		return jButtonPauseTask;
	}
	
	private JButton getJButtonRefresh() {
		if (jButtonRefresh == null) {
			jButtonRefresh = new JButton();
			jButtonRefresh.setIcon(ImagesService.getImageIcon("images.iconRefresh"));
			jButtonRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WizardStateMachine.getInstance().refreshParticipant();
				}
			});
		}
		return jButtonRefresh;
	}

	/**
	 * This method initializes jButtonFinished	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFinished() {
		if (jButtonFinished == null) {
			jButtonFinished = new JButton();
			jButtonFinished.setIcon(ImagesService.getImageIcon("images.iconFinished"));
		}
		return jButtonFinished;
	}

	public void setButtons(int buttonPlayTaskState, int buttonPauseTaskState, int buttonFinishedState) {
		switch (buttonPlayTaskState) {
		case INVISIBLE :
			jButtonPlayTask.setVisible(false);
			break;
		case ENABLED :
			jButtonPlayTask.setVisible(true);
			jButtonPlayTask.setEnabled(true);
			break;
		case DISABLED :
			jButtonPlayTask.setVisible(true);
			jButtonPlayTask.setEnabled(false);
		}
		
		switch (buttonPauseTaskState) {
		case INVISIBLE :
			jButtonPauseTask.setVisible(false);
			break;
		case ENABLED :
			jButtonPauseTask.setVisible(true);
			jButtonPauseTask.setEnabled(true);
			break;
		case DISABLED :
			jButtonPauseTask.setVisible(true);
			jButtonPauseTask.setEnabled(false);
		}
		
		switch (buttonFinishedState) {
		case INVISIBLE :
			jButtonFinished.setVisible(false);
			break;
		case ENABLED :
			jButtonFinished.setVisible(true);
			jButtonFinished.setEnabled(true);
			break;
		case DISABLED :
			jButtonFinished.setVisible(true);
			jButtonFinished.setEnabled(false);
		}
	}

	public void setJCheckBoxShowViewerEnabled(boolean value) {
		jCheckBoxShowViewer.setSelected(value);
	}
}
