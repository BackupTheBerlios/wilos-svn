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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import wilos.presentation.assistant.control.OptionsParser;
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

	/**
	 * This is the default constructor
	 */
	public OptionFrame() {
		super();
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
		
		//this.setSize(400, 300);
		this.setTitle(Bundle.getText("optionFrame.title"));
		this.setModal(true);
		this.setBounds(x, y, 420, 200);
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
			//panneau.add(getDelay(), null);
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
		if (langText ==null)
		{
			langText = new JLabel();
			langText.setBounds(new Rectangle(50,20,80,20));
			langText.setText(Bundle.getText("optionFrame.lang"));
			/*
			lang.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					dispose();
    			}				
			});*/
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
			lang.setBounds(new Rectangle(250,20,80,20));
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
		if (delayText ==null)
		{
			delayText = new JLabel();
			delayText.setBounds(new Rectangle(50,100,160,20));
			delayText.setText(Bundle.getText("optionFrame.delay"));
//			delay.paramString(Bundle.getText("optionFrame.delay"));
			/*
			delay.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					dispose();
    			}				
			});*/
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
		if (delay != null)
		{		
			delay = new JSpinner();
/*
			//Utilisation d un champ de saisie qui verifie et formate
			delay.setInputVerifier(new Verifier());
			delay.setHorizontalAlignment(SwingConstants.RIGHT);*/		
			
			delay.setBounds(new Rectangle(250,100,100,20));
			
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
			ok.setBounds(new Rectangle(50,140,100,20));
			ok.setText(Bundle.getText("optionFrame.ok"));
			/*ok.addActionListener(new ActionListener()
			{
				/*public void actionPerformed(ActionEvent e) 
    			{
					//WizardOptions wo = new WizardOptions(lang.get);
					JOptionPane opt1 = new JOptionPane();
					
					boolean valide = true;
					
					for (int i=0;i<servs.getRowCount()&&valide;i++)
					{
						if ((String)servs.getValueAt(i,1)==null)
						{
							valide = false;
							opt1.showMessageDialog(opt1,Bundle.getText("serversFrame.error"),"Error",JOptionPane.ERROR_MESSAGE);
							opt1.setVisible(true);
						}
						else
						{
							new_list_serv.add(new WizardServer((String)servs.getValueAt(i,0),(String)servs.getValueAt(i,1),i));
						}
					}
					if (valide)
					{
						LoginPanel.list_serv.saveServersList(new_list_serv);
						serverDialog.dispose();
					}
    			}			
			});*/
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
			cancel.setBounds(new Rectangle(160,140,100,20));
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
			apply.setBounds(new Rectangle(270,140,100,20));
			apply.setText(Bundle.getText("optionFrame.apply"));
			apply.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					dispose();
    			}				
			});
		}
		return apply;
	}	
}
