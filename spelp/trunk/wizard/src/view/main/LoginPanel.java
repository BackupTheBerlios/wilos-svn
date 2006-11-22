package view.main;

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

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel introLabel = null;

	private JPanel buttonsPanel = null;

	private JPanel fieldsPanel = null;

	private JButton okButton = null;

	private JButton cancelButton = null;

	private JTextField loginTextField = null;

	private JPasswordField passwordPasswordField = null;

	private JLabel loginLabel = null;

	private JLabel passwordLabel = null;

	private JLabel adressLabel = null;

	private JTextField adressTextField = null;

	/**
	 * @param owner
	 */
	public LoginPanel() {
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
			buttonsPanel.add(getOkButton(), new GridBagConstraints());
			buttonsPanel.add(getCancelButton(), new GridBagConstraints());
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
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(Bundle.getText("loginPanel.ok"));
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(Bundle.getText("loginPanel.cancel"));
		}
		return cancelButton;
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
