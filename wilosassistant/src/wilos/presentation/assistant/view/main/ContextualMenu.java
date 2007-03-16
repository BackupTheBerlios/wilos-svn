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

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.panels.WizardStateMachine;

public class ContextualMenu extends JPopupMenu implements Observer
{
	
	public static final int INVISIBLE = 0;
	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	
	private JMenuItem jButtonPauseTask;
	private JMenuItem jButtonFinished;
	private JMenuItem jButtonPlayTask;
	private JMenuItem jButtonNewHTML ;

	public ContextualMenu(){
		super(Bundle.getText("mainFrame.option"));  
		Font f = new Font(null, Font.BOLD, 11);
		jButtonPlayTask = new JMenuItem(ImagesService.getImageIcon("images.iconPlayS"));
		jButtonPlayTask.setText(Bundle.getText("action.run"));
		jButtonPlayTask.setFont(f);
		this.add(jButtonPlayTask);
		jButtonPauseTask = new JMenuItem(ImagesService.getImageIcon("images.iconPauseS"));
		jButtonPauseTask.setText(Bundle.getText("action.pause"));
		jButtonPauseTask.setFont(f);
		this.add(jButtonPauseTask);
		jButtonFinished = new JMenuItem(ImagesService.getImageIcon("images.iconFinishedS"));
		jButtonFinished.setText(Bundle.getText("action.finish"));
		jButtonFinished.setFont(f);
		this.add(jButtonFinished);
		jButtonNewHTML = new JMenuItem (Bundle.getText("htmlViewer.newWindow")) ;
		jButtonNewHTML.addActionListener(WizardControler.getInstance().getNewHTMLAction());
		jButtonNewHTML.setFont(f);
		this.addSeparator();
		this.add(jButtonNewHTML);
		WizardStateMachine.getInstance().addObserver(this);
	}
	
	public void setButtons(int buttonPlayTaskState, int buttonPauseTaskState, int buttonFinishedState,int buttonOpen) {
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
		switch (buttonOpen) {
		case INVISIBLE :
			jButtonNewHTML.setVisible(false);
			break;
		case ENABLED :
			jButtonNewHTML.setVisible(true);
			jButtonNewHTML.setEnabled(true);
			break;
		case DISABLED :
			jButtonNewHTML.setVisible(true);
			jButtonNewHTML.setEnabled(false);
		}
	}
	
	

	public void update(Observable o, Object arg) {
		switch (WizardStateMachine.getInstance().getCurrentState()){
		case WizardStateMachine.STATE_PARTICIPANT :
			setButtons(INVISIBLE,INVISIBLE, INVISIBLE,ENABLED);
			break;
		case WizardStateMachine.STATE_NOTHING :
			setButtons(DISABLED, DISABLED, DISABLED,ENABLED);
			break;
		case WizardStateMachine.STATE_TASK_CREATED :
			setButtons(DISABLED, DISABLED, DISABLED,ENABLED);
			break;
		case WizardStateMachine.STATE_TASK_READY :	
			if (WizardControler.getInstance().getNbThreadStarted() == 0){
				setButtons(ENABLED, DISABLED, DISABLED,ENABLED);
			}
			else {
				setButtons(DISABLED, DISABLED, DISABLED,ENABLED);
			}
			break;
		case WizardStateMachine.STATE_TASK_STARTED :	
			setButtons(DISABLED, ENABLED, ENABLED,ENABLED);
			break;
		case WizardStateMachine.STATE_TASK_SUSPENDED :	
			if (WizardControler.getInstance().getNbThreadStarted() == 0){
				setButtons(ENABLED, DISABLED, DISABLED,ENABLED);
			}
			else {
				setButtons(DISABLED, DISABLED, DISABLED,ENABLED);
			}
			break;
		case WizardStateMachine.STATE_TASK_FINISHED :	
			setButtons(DISABLED, DISABLED, DISABLED,ENABLED);
			break;
		case WizardStateMachine.STATE_STEP_FINISHED :	
			setButtons(DISABLED, DISABLED, DISABLED,ENABLED);
			break;
		case WizardStateMachine.STATE_STEP_CREATED:	
			setButtons(DISABLED, DISABLED, DISABLED,ENABLED);
			break;
		case WizardStateMachine.STATE_STEP_READY:	
			setButtons(DISABLED, DISABLED, ENABLED,ENABLED);
			break;
		}
	}

	public JMenuItem getJButtonFinished() {
		return jButtonFinished;
	}

	public JMenuItem getJButtonPauseTask() {
		return jButtonPauseTask;
	}

	public JMenuItem getJButtonPlayTask() {
		return jButtonPlayTask;
	}
}
