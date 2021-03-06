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

package wilos.presentation.assistant.view.main;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.tree.DefaultMutableTreeNode;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.panels.WizardStateMachine;

public class ActionBar extends JToolBar implements Observer{
	public final int INVISIBLE = 1;
	public final int ENABLED = 2;
	public final int DISABLED = 3;
	
	private JButton jButtonPauseTask = null;
	private JButton jButtonFinished = null;
	private JButton jButtonPlayTask = null;
	private JButton jButtonRefresh = null;
	private JLabel jLabelConnect = null ;
	private JCheckBox jCheckBoxShowViewer;
	
	private static ActionBar barre = null;
	
	public static ActionBar getInstance (){
		if (barre == null){
			barre = new ActionBar() ;
		}
		return barre ;
	}
	
	public ActionBar(){
		super ();
		this.setLayout(new FlowLayout());
		//this.add(runButton);
		this.add(getJLabelConnect());
		this.addSeparator();
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
		this.setButtons(DISABLED, DISABLED, DISABLED);
		//this.setBounds(0, 0, 150, 300);
		
		
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
	
	public JLabel getJLabelConnect() {
		if (jLabelConnect == null){
			jLabelConnect = new JLabel(ImagesService.getImageIcon("images.connection.idle"));
		}
		return jLabelConnect;
	}
	
	public JButton getJButtonPlayTask() {
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
			jCheckBoxShowViewer.setOpaque(false);
			jCheckBoxShowViewer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WizardControler.getInstance().changeHTMLViewerBehavior(jCheckBoxShowViewer.isSelected());
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
	public JButton getJButtonPauseTask() {
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
					WizardControler.getInstance().refreshParticipant();
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
	public JButton getJButtonFinished() {
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

	public void update(Observable o, Object arg) {
		switch (WizardStateMachine.getInstance().getCurrentState()){
		case WizardStateMachine.STATE_PARTICIPANT :
			setButtons(INVISIBLE,INVISIBLE, INVISIBLE);
			break;
		case WizardStateMachine.STATE_NOTHING :
			setButtons(DISABLED, DISABLED, DISABLED);
			break;
		case WizardStateMachine.STATE_TASK_CREATED :
			setButtons(DISABLED, DISABLED, DISABLED);
			break;
		case WizardStateMachine.STATE_TASK_READY :	
			if (WizardControler.getInstance().getNbThreadStarted() == 0){
				setButtons(ENABLED, DISABLED, DISABLED);
			}
			else {
				setButtons(DISABLED, DISABLED, DISABLED);
			}
			break;
		case WizardStateMachine.STATE_TASK_STARTED :	
			setButtons(DISABLED, ENABLED, ENABLED);
			break;
		case WizardStateMachine.STATE_TASK_SUSPENDED :	
			if (WizardControler.getInstance().getNbThreadStarted() == 0){
				setButtons(ENABLED, DISABLED, DISABLED);
			}
			else {
				setButtons(DISABLED, DISABLED, DISABLED);
			}
			break;
		case WizardStateMachine.STATE_TASK_FINISHED :	
			setButtons(DISABLED, DISABLED, DISABLED);
			break;
		case WizardStateMachine.STATE_STEP_FINISHED :	
			setButtons(DISABLED, DISABLED, DISABLED);
			break;
		case WizardStateMachine.STATE_STEP_CREATED:	
			setButtons(DISABLED, DISABLED, DISABLED);
			break;
		case WizardStateMachine.STATE_STEP_READY:	
			setButtons(DISABLED, DISABLED, ENABLED);
			break;
		}
	}
}
