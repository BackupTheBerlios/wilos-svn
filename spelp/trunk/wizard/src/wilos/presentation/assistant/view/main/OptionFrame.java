package wilos.presentation.assistant.view.main;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;

public class OptionFrame extends JDialog {
	private JPanel panneau = null;
	private JComboBox lang = null;
	private JTextField delay = null;
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
		this.setBounds(x, y, 420, 220);
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
			panneau.add(getDelay(), null);
			panneau.add(getLang(), null);
			panneau.add(getOk(), null);
			panneau.add(getCancel(), null);
			panneau.add(getApply(), null);
		}
		return panneau;
	}
	
	/**
	 * this method creates the cancel button with its dispose function
	 * 
	 * @return
	 */
	private JComboBox getLang()
	{
		if (lang ==null)
		{
			lang = new JComboBox();
			lang.setBounds(new Rectangle(50,20,80,20));
			/*
			lang.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
					dispose();
    			}				
			});*/
		}
		return lang;
	}
	/**
	 * this method creates the cancel button with its dispose function
	 * 
	 * @return
	 */
	private JTextField getDelay()
	{
		if (delay ==null)
		{
			delay = new JTextField();
			delay.setBounds(new Rectangle(150,100,160,20));
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
		return delay;
	}
	
	/**
	 * this method creates the cancel button with its dispose function
	 * 
	 * @return
	 */
	private JButton getOk()
	{
		if (ok ==null)
		{
			ok = new JButton();
			ok.setBounds(new Rectangle(50,140,80,20));
			ok.setText(Bundle.getText("optionFrame.ok"));
			ok.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
    			{
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
			cancel.setBounds(new Rectangle(160,140,80,20));
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
	 * this method creates the cancel button with its dispose function
	 * 
	 * @return
	 */
	private JButton getApply()
	{
		if (apply ==null)
		{
			apply = new JButton();
			apply.setBounds(new Rectangle(270,140,80,20));
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
