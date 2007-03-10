package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTaskPane;

import wilos.presentation.assistant.ressources.Bundle;

public class InfoPanel extends JXPanel {
	private JXTaskPane tasks = null ;
	private JLabel editor = null;
	public InfoPanel () {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getTasks(),BorderLayout.CENTER);
	}


	public JXTaskPane getTasks (){
		if (tasks == null){
			tasks = new JXTaskPane();
			tasks.setTitle(Bundle.getText("infoPanel.informations"));
			tasks.setExpanded(false);
			editor = new JLabel(Bundle.getText("infoPanel.no_informations"));
			tasks.add(editor);
			//tasks.add(new JLabel("essai2"));
			
		}
		return tasks ;
	}
	
	public void setInfo(String i){
		editor.setText(i);
	}
}
