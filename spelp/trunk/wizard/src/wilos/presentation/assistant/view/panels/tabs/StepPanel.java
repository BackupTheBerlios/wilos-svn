package wilos.presentation.assistant.view.panels.tabs;

import javax.swing.JLabel;
import javax.swing.JPanel;

import wilos.model.spem2.task.TaskDescriptor;

public class StepPanel extends JPanel {
	private static StepPanel panel = null ;
	private TaskDescriptor currentElement = null ;
	private StepPanel() {
		super();
		this.add(new JLabel("coucou"));
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

	public void setCurrentElement(TaskDescriptor currentElement) {
		this.currentElement = currentElement;
		this.add(new JLabel(currentElement.getName()));
	}
	

}
