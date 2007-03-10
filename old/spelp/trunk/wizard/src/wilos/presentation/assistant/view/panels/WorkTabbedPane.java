package wilos.presentation.assistant.view.panels;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.view.main.MainFrame;
import wilos.presentation.assistant.view.panels.tabs.StepPanel;
import wilos.presentation.assistant.view.panels.tabs.TaskPanel;

public class WorkTabbedPane extends JTabbedPane {
	private static WorkTabbedPane pane = null ;
	
	public static WorkTabbedPane getInstance(){
		return pane  ;
	}
	
	public static WorkTabbedPane getInstance (MainFrame m,ArrayList<RoleDescriptor> r) {
		if (pane == null){
			pane = new WorkTabbedPane ( m,r);
		}
		return pane ;
	}
	
	private WorkTabbedPane(MainFrame m,ArrayList<RoleDescriptor> r) {
		super();
		this.addTab(Bundle.getText("WorkTabbedPane.labelOverview"), new TaskPanel(m,r));
		
	}
	
	public void displayTask (boolean cond){
		if (cond){
			this.addTab(Bundle.getText("WorkTabbedPane.labelTask"), StepPanel.getInstance());
		}
		else {
			this.removeTabAt(1);
		}
	}
	
}
