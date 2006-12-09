package wilos.presentation.assistant.view.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.panels.WorkTabbedPane;
import wilos.presentation.assistant.view.panels.tabs.StepPanel;
import wilos.presentation.assistant.view.panels.tabs.TaskPanel;

public class ActionBar extends JToolBar {
	private JButton runButton ;
	
	public ActionBar(){
		super ();
		this.setLayout(new FlowLayout());
		runButton = createButton(null,
				Bundle.getText("ActionBar.tooltip.run"), 
				ImagesService.getIcon("images.run"));
		this.add(runButton);
		runButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if (TaskPanel.selectedElement != null){
							WorkTabbedPane.getInstance().displayTask(WorkTabbedPane.getInstance().getTabCount() == 1);
							StepPanel.getInstance().setCurrentElement((TaskDescriptor) TaskPanel.selectedElement);
						}
						
					}
				}
		);
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

}
