package wilos.presentation.assistant.view.main;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Locale;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import wilos.presentation.assistant.control.OptionsParser;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;

public class OptionFrame extends JDialog {
	private JPanel panneau = null;
	private JLabel langText = null;
	private JComboBox lang = null;
	private JLabel delayText = null;
	private JSpinner delay = null;
	private JButton ok = null;
	private JButton cancel = null;
	private JButton apply = null;
	private OptionsParser op = null; 

	/**
	 * This is the default constructor
	 */
	public OptionFrame(JFrame parent) {
		super(parent);
		this.op = new OptionsParser();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2-(605/2));
		int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2-(220/2)) ;
		this.setTitle(Bundle.getText("optionFrame.title"));
		this.setModal(true);
		this.setBounds(x, y, 380, 180);
		this.setResizable(false);
		this.setContentPane(getOptionPane());
		this.setVisible(true);
	}
	/**
	 * this method initializes the contentPane
	 * 
	 * @return JPanel
	 */
	private JPanel getOptionPane() {
		if (panneau == null) {
			panneau = new JPanel();
			panneau.setLayout(null);
			panneau.add(getLangText(), null);
			panneau.add(getLang(), null);
			panneau.add(getDelayText(), null);
			panneau.add(getDelay(), null);
			panneau.add(getOk(), null);
			panneau.add(getCancel(), null);
			panneau.add(getApply(), null);
		}
		return panneau;
	}
	
	/**
	 * this method creates the JLabel for the language
	 * 
	 * @return
	 */
	private JLabel getLangText()
	{
		if (langText == null)
		{
			langText = new JLabel();
			langText.setBounds(new Rectangle(20,20,250,20));
			langText.setText(Bundle.getText("optionFrame.lang"));
			langText.setLabelFor(lang);
		}
		return langText;
	}
	
	/**
	 * this method creates the JComboBox for the language
	 * 
	 * @return
	 */
	private JComboBox getLang()
	{
		if (lang == null)
		{
			//Locale[] locs = Locale.getAvailableLocales();
			
			Vector<Locale> locs = new Vector<Locale>();
			
			File locsDir = new File("src/wilos/presentation/assistant/ressources");
			File [] ficLocs = locsDir.listFiles(new FileFilter(){

				public boolean accept (File file){
			      String nomFichier = file.getName().toLowerCase(); 

			      return nomFichier.endsWith(".properties");
				}
			});
			for (File f : ficLocs)
			{
				locs.add (new Locale (f.getName().substring(6,8)));
			}
			
			lang = new JComboBox(locs);
			lang.setRenderer(new LocaleRenderer());
			lang.setBounds(new Rectangle(280,20,80,20));
			OptionsParser op = new OptionsParser ();
			lang.setSelectedItem(new Locale(op.getLocale().getLanguage()));
		}
		return lang;
	}
	
	
	private class LocaleRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Locale l = (Locale) value;
			String localeValue = l.getDisplayLanguage();
			super.getListCellRendererComponent(list, localeValue, index, isSelected, cellHasFocus);
			return this;
		}
	}
	
	/**
	 * this method creates the label of the JTextField delay
	 * 
	 * @return
	 */
	private JLabel getDelayText()
	{
		if (delayText == null)
		{
			delayText = new JLabel();
			delayText.setBounds(new Rectangle(20,60,250,40));
			delayText.setText(Bundle.getText("optionFrame.delay"));
			delayText.setLabelFor(delay);
		}
		return delayText;
	}
	
	/**
	 * this method creates the JTextField delay
	 * 
	 * @return
	 */
	private JSpinner getDelay()
	{
		if (delay == null)
		{		
			SpinnerNumberModel snp = new SpinnerNumberModel(this.op.getRefreshTime(),0,Integer.MAX_VALUE,1);
			delay = new JSpinner(snp);
			delay.setBounds(new Rectangle(280,60,80,20));
		}	
		return delay;
	}
	/*
	class Verifier extends InputVerifier{
		public boolean verify (JComponent input){
			if (input instanceof JFormattedTextField)
				JFormattedTextField ftf = (JFormattedTextField) input;
				JFormattedTextField.AbstractFormatter = ftf.getFormatter();
				if (formater != null){
						String text = ftf.getText();
					try{
						formatter.stringToValue(text);
						return true;
					}
					catch (ParseException pe){
						return false;
					}
				}
			}
			return true;
		}
	}*/
	
	/**
	 * this method creates the ok button
	 * 
	 * @return
	 */
	private JButton getOk()
	{
		if (ok ==null)
		{
			ok = new JButton();
			ok.setBounds(new Rectangle(20,120,100,20));
			ok.setText(Bundle.getText("optionFrame.ok"));
			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					OptionFrame.this.appliquer();
					dispose();
    			}			
			});
		}
		return ok;
	}
	
	/**
	 * this method creates the cancel button with its dispose function
	 * 
	 * @return
	 */
	private JButton getCancel()
	{
		if (cancel ==null)
		{
			cancel = new JButton();
			cancel.setBounds(new Rectangle(140,120,100,20));
			cancel.setText(Bundle.getText("optionFrame.cancel"));
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					dispose();
    			}				
			});
		}
		return cancel;
	}

	/**
	 * this method creates the apply button
	 * 
	 * @return
	 */
	private JButton getApply()
	{
		if (apply ==null)
		{
			apply = new JButton();
			apply.setBounds(new Rectangle(260,120,100,20));
			apply.setText(Bundle.getText("optionFrame.apply"));
			apply.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					OptionFrame.this.appliquer();
    			}				
			});
		}
		return apply;
	}
	private void appliquer()
	{
		System.out.println(((Integer)delay.getValue())*1000);
		System.out.println(WizardControler.getInstance().getTimeToRefresh());
		if ((!lang.getSelectedItem().toString().equals(WizardControler.getInstance().getLang().getLanguage()))
				|| (((Integer)delay.getValue()*1000)!=WizardControler.getInstance().getTimeToRefresh()))
		{
			System.out.println("save !");
		}
		
		//		=> setrefreshtime
		// si ancienneval == 0 && nouvelle non
		// 	 controler.getinstance.launchBackgroundThreadForTree
		// elseif val == 0 alors
		// 	controler.getinstance.cancelrefreshthread
	}
}
