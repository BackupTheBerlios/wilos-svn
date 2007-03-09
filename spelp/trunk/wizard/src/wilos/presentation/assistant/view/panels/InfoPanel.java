package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

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
	private HourTextField tps_restant;
	private HourTextField tps;
	
	
	// attribute for accomplish time
	private long currentTime = 0 ;
	private long startTime =  0;
	private boolean start = true ;
	
	private static ConcreteTaskDescriptor CurrentTimedTask = null ;
	private ConcreteTaskDescriptor currentConcreteTask = null ;
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
		tps_restant = new HourTextField(new HourFormat());
		tps_restant.addCaretListener(new CaretListener(){
			public void caretUpdate(CaretEvent arg0) {
				if(!tps_restant.getText().equals("") && tps_restant.getText().matches(HourTextField.pattern)){
					try {
						tps_restant.commitEdit();
						currentConcreteTask.setRemainingTime(getRemainingTimeForUpload());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		// definition of tps_restant
		
		
		tps_restant_label = new JLabel();
		tps = new HourTextField(new HourFormat());
		tps.setEditable(false);
		
	
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
			modify = new JButton(Bundle.getText("infopanel.modifier"));
			modify.setBounds(50, 50, 50, 50);
			modify.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					if (WizardControler.getInstance().getLastCtd() instanceof ConcreteTaskDescriptor){
						new InfoTpsPanel((ConcreteTaskDescriptor) WizardControler.getInstance().getLastCtd(),(Long)tps_restant.getValue(),(Long)tps.getValue());
					}
					
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
				currentConcreteTask = c ;
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
		info1.setText(Bundle.getText("infopanel.datedebut"));
		info2.setText(Bundle.getText("infopanel.datefin"));
		String pattern = "d MMM yyyy" ;
		String dateD;
		if (c.getPlannedStartingDate()==null)
		{
			dateD = Bundle.getText("infopanel.notrenseign");
		}else
		{	
			Date d = (Date)c.getPlannedStartingDate() ;
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			dateD = sdf.format(d);
		}
		String dateF;
		if (c.getPlannedFinishingDate()==null)
		{
			dateF = Bundle.getText("infopanel.notrenseign");
		}else
		{	
			Date df = (Date)c.getPlannedFinishingDate() ;
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			dateF = sdf.format(df);
		}
		info1_label.setText(dateD);
		info2_label.setText(dateF);
		tps_restant_label.setText(Bundle.getText("infopanel.remaining"));
//		int min = WizardControler.getInstance().getDecimalValueInMinutes(c.getRemainingTime());
//		String time= new String((int)c.getRemainingTime()+":"
//				+min+":00"); 	
		tps_restant.setValue(new Long(transformInSeconds(c.getRemainingTime())));
		tps_label.setText(Bundle.getText("infopanel.accomplish"));
//		//System.out.println(c.getAccomplishedTime());
//		// in the case where there is no task timed
//		if (CurrentTimedTask == null || !CurrentTimedTask.equals(WizardControler.getInstance().getLastCtd())){
//			String time1= new String((int)c.getAccomplishedTime()+":"
//					+WizardControler.getInstance().getDecimalValueInMinutes(c.getAccomplishedTime())+ ":00");
//			tps.setValue(c.getAccomplishedTime());
//		}
//		// if the task focused on is the task timed
//		else if (currentTime != 0){
//			String time1= new String(ahours+":"
//					+amin+":"+aseconds);
//			tps.setValue(new Float(currentTime));
//		}
		
		if (CurrentTimedTask == null || !CurrentTimedTask.equals(WizardControler.getInstance().getLastCtd())){
			tps.setValue(new Long(transformInSeconds(c.getAccomplishedTime())));
			currentTime = transformInSeconds(c.getAccomplishedTime());
		}
		else {
			tps.setValue(new Long(currentTime));
		}
		
		if (c.getState().equals(Constantes.State.FINISHED))
		{
			modify.setVisible(false);
			tps_restant.setEditable(false);
		}
		else {
			modify.setVisible(true);
			tps_restant.setEditable(true);
		}
		infos.setVisible(true);
	}
	
	public JLabel getTps_label() {
		return tps_label;
	}

	public void setTps_label(JLabel tps_label) {
		this.tps_label = tps_label;
	}

	public HourTextField getTps() {
		return tps;
	}

	public void setTps(HourTextField tps) {
		this.tps = tps;
	}

	public static ConcreteTaskDescriptor getCurrentTimedTask() {
		return CurrentTimedTask;
	}

	public static void setCurrentTimedTask(ConcreteTaskDescriptor currentTimedTask) {
		CurrentTimedTask = currentTimedTask;
	}

	public void putValue(long tps_passe) {
		if (CurrentTimedTask.equals(WizardControler.getInstance().getLastCtd())) {
			//manageUpdate(CurrentTimedTask);
			//String time = tps.getText();
			if (start){
				float tmp = CurrentTimedTask.getAccomplishedTime() ;
				startTime = transformInSeconds(tmp);
				start = false ;
			}
			
//			ahours = bhours + (int)tps_passe / 3600 ;
//			amin = bmin + (int)tps_passe / 60 ;
//			aseconds = bseconds + (int) tps_passe % 60 ;
						
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
			currentTime = startTime + tps_passe ;
			tps.setValue(new Long(currentTime));
			
		}
	}

	
	public void reinitializeTimes(){
		CurrentTimedTask = null ;
		currentTime = 0 ;
		startTime = 0 ;
		start = true; 
	}
	
	public long transformInSeconds(float val) {
		int retour ;
		int tmphour = (int)val *3600 ;
		float minutes = (float)(float)val - (int)val ;
		int tmpminutes = ((int) ((minutes)*60))*60   ;
		int sec = Math.round(((float)minutes-(int)minutes)*60);
		retour = tmphour + tmpminutes + sec;
		return (retour) ;
	}
	
	public float getRemainingTimeForUpload(){
		long val = (Long)tps_restant.getValue() ;
		float retour = (float)val/3600;
		return retour ;
	}
	
	
	public float getAccomplishTimeForUpload(){
		long val = (Long)tps.getValue() ;
		float retour = (float)val/3600;
		return retour ;
	}
	
	public void commitTextFields (){
		try {
			tps.commitEdit();
			tps_restant.commitEdit();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public HourTextField getTps_restant() {
		return tps_restant;
	}


	public void setTps_restant(HourTextField tps_restant) {
		this.tps_restant = tps_restant;
	}
	
}