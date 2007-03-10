package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.assistant.view.main.MainFrame;
import wilos.presentation.assistant.view.panels.tabs.TaskPanel;

public class WorkPane extends JPanel {
	private static WorkPane workPane;
	private TaskPanel taskPane;
	InfoPanel infoPanel;
	
	private WorkPane(MainFrame m, ArrayList<RoleDescriptor> r) {
		super();
		infoPanel = new InfoPanel();
		this.add(new TaskPanel(m,r), BorderLayout.NORTH);
		//this.add(infoPanel, BorderLayout.SOUTH);
		
	}

	public static WorkPane getInstance(MainFrame m, ArrayList<RoleDescriptor> r) {
		if (workPane == null){
			workPane = new WorkPane ( m,r);
		}
		return workPane ;
	}

}
