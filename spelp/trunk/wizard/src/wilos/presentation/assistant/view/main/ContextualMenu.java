package wilos.presentation.assistant.view.main;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

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

	public ContextualMenu(){
		super(Bundle.getText("mainFrame.option"));  
		 
		jButtonPlayTask = new JMenuItem(ImagesService.getImageIcon("images.iconPlayS"));
		jButtonPlayTask.setText(Bundle.getText("action.run"));
		this.add(jButtonPlayTask);
		jButtonPauseTask = new JMenuItem(ImagesService.getImageIcon("images.iconPauseS"));
		jButtonPauseTask.setText(Bundle.getText("action.pause"));
		this.add(jButtonPauseTask);
		jButtonFinished = new JMenuItem(ImagesService.getImageIcon("images.iconFinishedS"));
		jButtonFinished.setText(Bundle.getText("action.finish"));
		this.add(jButtonFinished);
		WizardStateMachine.getInstance().addObserver(this);
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
	
	

	public void update(Observable o, Object arg) {
		switch (WizardStateMachine.getInstance().getCurrentState()){
		case WizardStateMachine.STATE_PARTICIPANT :
			setButtons(INVISIBLE,INVISIBLE, INVISIBLE);
			break;
		case WizardStateMachine.STATE_NOTHING :
			setButtons(INVISIBLE, INVISIBLE, INVISIBLE);
			break;
		case WizardStateMachine.STATE_TASK_CREATED :	
			setButtons(DISABLED, DISABLED, DISABLED);
			break;
		case WizardStateMachine.STATE_TASK_READY :	
			setButtons(ENABLED, DISABLED, DISABLED);
			break;
		case WizardStateMachine.STATE_TASK_STARTED :	
			setButtons(DISABLED, ENABLED, ENABLED);
			break;
		case WizardStateMachine.STATE_TASK_SUSPENDED :	
			setButtons(ENABLED, DISABLED, DISABLED);		
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
