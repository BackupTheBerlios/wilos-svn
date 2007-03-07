package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class InfoPanel extends JXPanel implements Observer,ListenerTime {
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
	
	// attribute for accomplish time
	private int amin = 0 ;
	private int ahours = 0 ;
	private int aseconds =  0;
	
	// attribute of beginning
	private int bmin = 0 ;
	private int bhours = 0 ;
	private int bseconds = 0 ;
	private boolean start = true ;
	
	private static ConcreteTaskDescriptor CurrentTimedTask = null ;
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
			//modify.setVisible(false);
		}
		return tasks ;
	}
	
	public void setInfo(String i){
		editor.setText(i);
	}
	
	public void update(Observable arg0, Object arg1) {
		if (WizardControler.getInstance().getLastCtd() instanceof ConcreteTaskDescriptor)
		{
			ConcreteTaskDescriptor c =(ConcreteTaskDescriptor)WizardControler.getInstance().getLastCtd();
			if (c.getState().equals(Constantes.State.STARTED) ||
					(c.getState().equals(Constantes.State.SUSPENDED) || (c.getState().equals(Constantes.State.FINISHED) )))
			{
				manageUpdate(c);
				tasks.setExpanded(true);
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

	private void manageUpdate(ConcreteTaskDescriptor c) {
		
		info1.setText("Date debut : ");
		info2.setText("Date fin : ");
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
		tps_restant_label.setText("Temps restant : ");
		int min = WizardControler.getInstance().getDecimalValueInMinutes(c.getRemainingTime());
		String time= new String((int)c.getRemainingTime()+":"
				+min+":00"); 				
		tps_restant.setText(String.valueOf(time));
		tps_label.setText("Temps effectue : ");
		//System.out.println(c.getAccomplishedTime());
		// in the case where there is no task timed
		if (CurrentTimedTask == null || !CurrentTimedTask.equals(WizardControler.getInstance().getLastCtd())){
			String time1= new String((int)c.getAccomplishedTime()+":"
					+WizardControler.getInstance().getDecimalValueInMinutes(c.getAccomplishedTime())+ ":00");
			tps.setText(time1);
		}
		// if the task focused on is the task timed
		else if (aseconds != 0 && amin != 0 && ahours != 0){
			String time1= new String(ahours+":"
					+amin+":"+aseconds);
			tps.setText(time1);
		}
		if (c.getState().equals(Constantes.State.FINISHED))
		{
			modify.setVisible(false);
			tps_restant.setEditable(false);
		}
		else {
			modify.setVisible(true);
		}
		infos.setVisible(true);
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

	public static ConcreteTaskDescriptor getCurrentTimedTask() {
		return CurrentTimedTask;
	}

	public static void setCurrentTimedTask(ConcreteTaskDescriptor currentTimedTask) {
		CurrentTimedTask = currentTimedTask;
	}

	public void putValue(float tps_passe) {
		if (CurrentTimedTask.equals(WizardControler.getInstance().getLastCtd())) {
			//manageUpdate(CurrentTimedTask);
			//String time = tps.getText();
			if (start){
				bmin = WizardControler.getInstance().getDecimalValueInMinutes(CurrentTimedTask.getAccomplishedTime());
				bhours = (int)CurrentTimedTask.getAccomplishedTime() ;
				bseconds = 0 ;
				start = false ;
			}
			
			ahours = bhours + (int)tps_passe / 3600 ;
			amin = bmin + (int)tps_passe / 60 ;
			aseconds = bseconds + (int) tps_passe % 60 ;
						
//			for (int i=0;i	<time.length()&&occu!=2;i++) 
//			{
//				if (time.charAt(i)==':')occu++;
//				if(occu==0&&time.charAt(i)!=':')heure+=time.charAt(i);
//				if(occu==1&&time.charAt(i)!=':')min+=time.charAt(i);
//			}
			
		
//			if ((int)tps_passe == 5){
//				int val = Integer.valueOf(min)+1 ;
//				if (val == 60) {
//					int hr = Integer.valueOf(heure) + 1 ;
//					tps_passe = 0 ;
//					min = "00";
//					heure = String.valueOf(hr);
//				}
//				else {
//					min = String.valueOf(val);
//					tps_passe = 0 ;
//				}
				
				
			
//			if ((int)tps_passe==5)
//			{
//				System.out.println("treet");
//				tps_passe = 0;
//				Integer i = new Integer(min);							
//				min = String.valueOf(new Integer (i+1));
//			}
			
			tps.setText(ahours+":"+amin+":"+aseconds);
			
		}
	}

	/**
	 * @return the ahours
	 */
	public int getAhours() {
		return ahours;
	}

	/**
	 * @param ahours the ahours to set
	 */
	public void setAhours(int ahours) {
		this.ahours = ahours;
	}

	/**
	 * @return the amin
	 */
	public int getAmin() {
		return amin;
	}

	/**
	 * @param amin the amin to set
	 */
	public void setAmin(int amin) {
		this.amin = amin;
	}

	/**
	 * @return the aseconds
	 */
	public int getAseconds() {
		return aseconds;
	}

	/**
	 * @param aseconds the aseconds to set
	 */
	public void setAseconds(int aseconds) {
		this.aseconds = aseconds;
	}
	
	public void reinitializeTimes(){
		amin = 0 ;
		ahours = 0 ;
		aseconds = 0 ;
		CurrentTimedTask = null ;
		start = true; 
	}
}