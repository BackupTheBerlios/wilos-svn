package view.main;

import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import ressources.Bundle;

import java.awt.Button;
import webservices.WizardServicesProxy;
import woops2.model.role.RoleDescriptor;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel introLabel = null;

	private JPanel buttonsPanel = null;

	private JPanel fieldsPanel = null;

	private JButton connectionButton = null;	

	private JTextField loginTextField = null;

	private JPasswordField passwordPasswordField = null;

	private JLabel loginLabel = null;

	private JLabel passwordLabel = null;

	private JLabel adressLabel = null;

	private JTextField adressTextField = null;
        
        private MainFrame mframe = null;
        
        private TaskPanel mTaskPanel = null;

	/**
	 * @param owner
	 */
	public LoginPanel(MainFrame mf) {
                this.mframe = mf;               
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		//this.setSize(300, 150);
		introLabel = new JLabel();
		introLabel.setText(Bundle.getText("loginPanel.labelLoginPasswd"));

		this.setLayout(new BorderLayout());
		this.add(introLabel, BorderLayout.NORTH);
		this.add(getButtonsPanel(), BorderLayout.SOUTH);
		this.add(getFieldsPanel(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes buttonsPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonsPanel() {
		if (buttonsPanel == null) {
			buttonsPanel = new JPanel();
			buttonsPanel.setLayout(new GridBagLayout());
			buttonsPanel.add(getConnectionButton(), new GridBagConstraints());			
		}
		return buttonsPanel;
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
			fieldsPanel.setLayout(new GridLayout(3,2));
			fieldsPanel.add(adressLabel, null);
			fieldsPanel.add(getAdressTextField(), null);
			fieldsPanel.add(loginLabel, null);
			fieldsPanel.add(getLoginTextField());
			fieldsPanel.add(passwordLabel, null);
			fieldsPanel.add(getPasswordPasswordField());
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
					
                                        ArrayList<RoleDescriptor> rolesListe = WizardServicesProxy.getRolesByUser(loginTextField.getText(),new String(passwordPasswordField.getPassword()));
                                        setVisible(false);
                                        mTaskPanel = new TaskPanel(mframe,rolesListe);
                                        
                                        if (rolesListe.isEmpty())
                                        {
                                            setVisible(true);                                        
                                        }  
                                        else
                                        {
                                            mframe.setContentPane(mTaskPanel);
                                            mTaskPanel.setVisible(true);                                         
                                        }
		
                                        
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
		}
		return loginTextField;
	}

	/**
	 * This method initializes passwordPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPasswordPasswordField() {
		if (passwordPasswordField == null) {
			passwordPasswordField = new JPasswordField();
		}
		return passwordPasswordField;
	}

	/**
	 * This method initializes adressTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getAdressTextField() {
		if (adressTextField == null) {
			adressTextField = new JTextField();			
		}
		return adressTextField;
	}
	
}
