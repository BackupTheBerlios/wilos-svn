package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTaskPane;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.utils.Constantes;

public class InfoPanel extends JXPanel implements Observer {
	private JXTaskPane tasks = null ;
	private JLabel editor = null;
	private JPanel infos = null;
	private JButton modify;
	private JLabel info1;
	private JLabel info1_label;
	private JLabel info2;
	private JLabel info2_label;
	private JLabel tps_label;
	private JLabel tps_restant_label;
	private JTextField tps_restant;
	private JLabel tps;
	//private JLabel non_started;

	public InfoPanel () {
		initialize();
	}

	/**
	 * This method initializes this
	 */
	private void initialize() {
		infos = new JPanel();
		infos.setLayout(new GridLayout(4,2));
		info1 = new JLabel();
		info2 = new JLabel();
		info1_label = new JLabel();
		info2_label = new JLabel();
		tps_restant = new JTextField();
		tps_restant_label = new JLabel();
		tps = new JLabel();
		tps_label = new JLabel();

		infos.add(info1);
		infos.add(info1_label);
		infos.add(info2);
		infos.add(info2_label);
		infos.add(tps_restant_label);
		infos.add(tps_restant);
		infos.add(tps_label);
		infos.add(tps);		
		
		this.setLayout(new BorderLayout());
		this.add(getTasks(),BorderLayout.CENTER);
		
	
	}

	public JXTaskPane getTasks (){
		if (tasks == null){
			tasks = new JXTaskPane();
			//tasks.setLayout(new BorderLayout());
			tasks.setTitle(Bundle.getText("infoPanel.informations"));
			tasks.setExpanded(false);
			editor = new JLabel(Bundle.getText("infoPanel.no_informations"));
			modify = new JButton("Modifier");
			modify.setBounds(50, 50, 50, 50);
			modify.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					new InfoTpsPanel();
				}				
			});
			tasks.add(infos);
			
			tasks.add(modify);
			modify.setVisible(false);
		}
		return tasks ;
	}
	
	public void setInfo(String i){
		editor.setText(i);
	}
	
	public void update(Observable arg0, Object arg1) {
		if (WizardControler.getInstance().getLastCtd() instanceof ConcreteTaskDescriptor)
		{
			tasks.setExpanded(true);
			ConcreteTaskDescriptor c =(ConcreteTaskDescriptor)WizardControler.getInstance().getLastCtd();
			if (c.getState().equals(Constantes.State.STARTED) ||
					(c.getState().equals(Constantes.State.SUSPENDED) || (c.getState().equals(Constantes.State.FINISHED) )))
			{
				modify.setVisible(true);
				info1.setText("Date debut:");
				info2.setText("Date fin:");
				String dateD;
				if (c.getPlannedStartingDate()==null)
				{
					dateD = "Non renseignee";
				}else
				{
					dateD = String.valueOf(c.getPlannedStartingDate());
				}
				String dateF;
				if (c.getPlannedFinishingDate()==null)
				{
					dateF = "Non renseignee";
				}else
				{
					dateF = String.valueOf(c.getPlannedFinishingDate());
				}
				info1_label.setText(dateD);
				info2_label.setText(dateF);
				tps_restant_label.setText("Temps restant:");
				String min = String.valueOf(Math.round(c.getRemainingTime()-(int)c.getRemainingTime()));
				if (new Integer(min)!=0)
				{
					min = min.substring(2, min.length());
				}
				else
				{
					min = "00";
				}
				String time= new String((int)c.getRemainingTime()+":"
						+min+":00"); 				
				tps_restant.setText(String.valueOf(time));
				tps_label.setText("Temps effectue");
			String min1 = String.valueOf(Math.round(c.getAccomplishedTime()-(int)c.getAccomplishedTime()));
				if (new Integer(min1)!=0)
				{
					min1 = min1.substring(2, min1.length());
				}
				else
				{
					min1 = "00";
				}
				String time1= new String((int)c.getAccomplishedTime()+":"
						+min1+":00"); 		
				tps.setText(String.valueOf(time1));
				infos.setVisible(true);
				if (c.getState().equals(Constantes.State.FINISHED))
				{
					modify.setVisible(false);
					tps_restant.setEditable(false);
				}
				
			}
			else
			{
				tasks.setExpanded(false);
				modify.setVisible(false);
				info1.setText("Tache non demarree");
				infos.setVisible(false);
			}
		}
		else
		{
			tasks.setExpanded(false);
			infos.setVisible(false);
			modify.setVisible(false);
			
		}
		
	}

	public JLabel getTps_label() {
		return tps_label;
	}

	public void setTps_label(JLabel tps_label) {
		this.tps_label = tps_label;
	}

	public JLabel getTps() {
		return tps;
	}

	public void setTps(JLabel tps) {
		this.tps = tps;
	}
	
}	