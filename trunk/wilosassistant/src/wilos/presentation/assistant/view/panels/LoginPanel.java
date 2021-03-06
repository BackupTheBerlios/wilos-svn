/*
 * Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
 * Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package wilos.presentation.assistant.view.panels;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import wilos.business.util.Security;
import wilos.model.misc.wilosuser.Participant;
import wilos.model.spem2.guide.Guidance;
import wilos.presentation.assistant.control.ExceptionManager;
import wilos.presentation.assistant.control.ServersListParser;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.model.WizardServer;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.main.MainFrame;
import wilos.presentation.assistant.view.main.ServersFrame;
import wilos.presentation.assistant.view.main.WizardMainFrame;
import wilos.presentation.assistant.webservices.WizardServicesProxy;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static String path_file = "wilos/presentation/assistant/ressources/wizard_setting.ini";
	private JLabel introLabel = null;
	private JPanel buttonsPanel = null;
	private JPanel fieldsPanel = null;
	private JButton connectionButton = null;
	private JTextField loginTextField = null;
	private JPasswordField passwordPasswordField = null;
	private JLabel loginLabel = null;
	private JLabel passwordLabel = null;
	private JLabel adressLabel = null;
	public JComboBox adressTextField = null;
	private MainFrame mframe = null;
	private MainPanel mTaskPanel = null;
	private ImagePanel iconPanel = null;
	public static ServersListParser list_serv = null;

	/**
	 * @param owner
	 */
	public LoginPanel(MainFrame mf) 
	{
                this.mframe = mf;               
                initialize();
                //loadXML();		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public JComboBox getComboBox()
	{
		return this.adressTextField;
	}
	private void initialize() 
	{
		
		introLabel = new JLabel();
		introLabel.setText(Bundle.getText("loginPanel.labelLoginPasswd"));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout());
        this.add(getImagePanel(),BorderLayout.NORTH);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
		this.add(getFieldsPanel(), BorderLayout.CENTER);
		list_serv = new ServersListParser();
	}

	/**
	 * This method initializes buttonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonsPanel() 
	{
		if (buttonsPanel == null) 
		{
			buttonsPanel = new JPanel();
			buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		    buttonsPanel.setLayout(new GridBagLayout());
			buttonsPanel.add(getConnectionButton(), new GridBagConstraints());			
		}
		return buttonsPanel;
	}
        
        /**
         *
         */
        private ImagePanel getImagePanel() 
        {
            if (iconPanel == null)
            {
                iconPanel = new ImagePanel("images.wilos");     
                iconPanel.setLayout(new GridBagLayout());		
            }
            return this.iconPanel;
        }
        
        /**
         *
         */
        private void loadXML() 
        {
            /*ProfileReader pr = new ProfileReader();
            InputStream i;
               //i = new FileInputStream(file);
                i = ClassLoader.getSystemResourceAsStream(file);
                try {
                	
                	pr.load(i);                    
                    String addr = pr.getProperty("remote","address");
                    adressTextField.setText(addr);
                    
                } catch (Exception ex) {
                    new ExceptionManager(ex);
                }*/
       
    		if (this.adressTextField.getItemCount()!=0)this.adressTextField.removeAllItems();
        	
        	ArrayList<WizardServer> list = list_serv.getServersList();
        	        	        	 
        	for (int i=0;i<list.size();i++)
        	{
        		this.adressTextField.addItem(list.get(i));
        	}
        	this.adressTextField.addItem(new WizardServer(Bundle.getText("loginPanel.ajouter"),"",0));
        	this.adressTextField.setRenderer(new AliasRenderer());
        	
        	this.adressTextField.addActionListener(new ActionListener() 
        	{
    			public void actionPerformed(ActionEvent e) 
    			{
    				if (adressTextField.getItemCount()!=0&&((WizardServer)adressTextField.getSelectedItem()).getAlias().equals(Bundle.getText("loginPanel.ajouter")))
    				{
    					adressTextField.setSelectedIndex(0);
    					new ServersFrame(LoginPanel.this.mframe);
    				}
    			}
    		});

        }

        private class AliasRenderer extends DefaultListCellRenderer {
        	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        		WizardServer s = (WizardServer)value;
        		String aliasAdress = s.getAlias();
        		super.getListCellRendererComponent(list, aliasAdress, index, isSelected, cellHasFocus);
        		return this;
        	}
        }


	/**
	 * This method initializes fieldsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getFieldsPanel() {
		if (fieldsPanel == null) {
			adressLabel = new JLabel();
			adressLabel.setText(Bundle.getText("loginPanel.remoteAddress"));
			passwordLabel = new JLabel();
			passwordLabel.setText(Bundle.getText("loginPanel.password"));
			loginLabel = new JLabel();
			loginLabel.setText(Bundle.getText("loginPanel.login"));
			fieldsPanel = new JPanel();
			fieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			fieldsPanel.setLayout(new GridLayout(3,2));
			fieldsPanel.add(adressLabel, null);
			fieldsPanel.add(getAdressTextField(), null);
			fieldsPanel.add(loginLabel, null);
			fieldsPanel.add(getLoginTextField());
			fieldsPanel.add(passwordLabel, null);
			fieldsPanel.add(getPasswordPasswordField());
			/* for (Component c : fieldsPanel.getComponents()) {
				if (c instanceof JLabel) ((javax.swing.JComponent) c).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
			} */
		}
		return fieldsPanel;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getConnectionButton() {
		if (connectionButton == null) {
			connectionButton = new JButton();
			connectionButton.setText(Bundle.getText("loginPanel.connection"));
            connectionButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					startConnection();
				}
			});
		}
		return connectionButton;
	}

	
	/**
	 * This method initializes loginTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLoginTextField() {
		if (loginTextField == null) {
			loginTextField = new JTextField();
			loginTextField.addKeyListener(new myKeyListener());
		}
		return loginTextField;
	}
	
	/**
	 * This methods makes the connexion, gets the Participant and calls WizardMainFrame
	 *
	 */
	private void startConnection() 
	{
		// before : <alias> => <adress>
		// after : <adress>
		String serverAdress = ((WizardServer)adressTextField.getSelectedItem()).getAddress();

		/*check if there is a server selected and not "Ajouter"*/
		if (!((WizardServer)adressTextField.getSelectedItem()).getAlias().equals(Bundle.getText("loginPanel.ajouter")))
		{
			String passcript =  Security.encode(new String(passwordPasswordField.getPassword()));
			WizardServicesProxy.setIdentificationParamaters(loginTextField.getText(),passcript, serverAdress);
			//System.out.print(String.valueOf(adressTextField.getSelectedItem()));

			/*Save the last server used*/
			list_serv.lastUsedServer(adressTextField.getSelectedIndex()+1);
			
			startLoading();
		}
	}
	

	/**
	 * This method initializes passwordPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPasswordPasswordField() {
		if (passwordPasswordField == null) {
			passwordPasswordField = new JPasswordField();
			passwordPasswordField.addKeyListener(new myKeyListener());
		}
		return passwordPasswordField;
	}

	/**
	 * This method initializes adressTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JComboBox getAdressTextField() {
		if (adressTextField == null) {
			adressTextField = new JComboBox();
		}
		return adressTextField;
	}
	
	private class myKeyListener implements KeyListener{
		public void keyPressed(KeyEvent e) {
			// Nothing to do here
		}

		public void keyReleased(KeyEvent e) {
			if (KeyEvent.VK_ENTER == e.getKeyCode()) {
				startConnection();
			}			
		}

		public void keyTyped(KeyEvent e) {
			// Nothing to do here			
		}
		
	}
	
	public void refreshlist() 
	{
		loadXML();	
		
	}
	
	private void startLoading() {
		JLabel img = new JLabel(ImagesService.getImageIcon("images.startLoading"));
		img.setSize(150, 80);

		this.removeAll();
		
		this.add(getImagePanel(),BorderLayout.NORTH);
		this.add(img, BorderLayout.CENTER);
		this.add(new JLabel(Bundle.getText("loginPanel.loading")), BorderLayout.SOUTH);

		this.doLayout();
		this.repaint();

		// launch the loading
		(new LoadingThread(this)).start();
	}
	
	private void finishLoading(Participant p)
	{
		if (p == null)
		{
			this.removeAll();
			
			this.add(getImagePanel(),BorderLayout.NORTH);
			this.add(this.getFieldsPanel(), BorderLayout.CENTER);
			this.add(this.getButtonsPanel(), BorderLayout.SOUTH);

			this.doLayout();
			this.repaint();
			
			setVisible(true);
			// TODO : Verifier ce "if-else"
			// new ErrorDialog(Bundle.getText("loginPanel.unknownUser"));
		}  
		else
		{

			WizardMainFrame wmf = new WizardMainFrame();
			wmf.setParticipant(p);
			WizardControler.getInstance().launchFirstThread() ;
			wmf.setVisible(true);
    	
			mframe.setVisible(false);
		}
	}
	
	private class LoadingThread extends Thread {
		private LoginPanel parentClass;
		
		public LoadingThread (LoginPanel obj) {
			this.parentClass = obj;
		}
		
		public void run ()
		{
			Participant participant = WizardServicesProxy.getParticipant();
			this.parentClass.finishLoading(participant);
		}
	}
}
