package wilos.presentation.assistant.view.main;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;

public class ContextualMenu extends JPopupMenu
{
	
	public static final int INVISIBLE = 0;
	public static final int ENABLED = 1;
	public static final int DISABLED = 2;
	
	private JMenuItem jButtonPauseTask;
	private JMenuItem jButtonFinished;
	private JMenuItem jButtonPlayTask;

	public ContextualMenu(){
		super(Bundle.getText("mainFrame.option"));  
		jButtonPlayTask = new JMenuItem(ImagesService.getImageIcon("images.iconPlay"));
		jButtonPlayTask.setText(Bundle.getText("action.run"));
		this.add(jButtonPlayTask);
		jButtonPauseTask = new JMenuItem(ImagesService.getImageIcon("images.iconPause"));
		jButtonPauseTask.setText(Bundle.getText("action.pause"));
		this.add(jButtonPauseTask);
		jButtonFinished = new JMenuItem(ImagesService.getImageIcon("images.iconFinished"));
		jButtonFinished.setText(Bundle.getText("action.finish"));
		this.add(jButtonFinished);
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
}
