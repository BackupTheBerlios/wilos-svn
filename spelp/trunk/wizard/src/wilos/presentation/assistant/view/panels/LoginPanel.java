package wilos.presentation.assistant.view.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import wilos.business.util.Security;

import wilos.model.spem2.role.RoleDescriptor;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ProfileReader;
import wilos.presentation.assistant.view.dialogs.ErrorDialog;
import wilos.presentation.assistant.view.main.MainFrame;
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

	private JTextField adressTextField = null;
        
        private MainFrame mframe = null;
        
        private MainPanel mTaskPanel = null;
        
        private ImagePanel iconPanel = null;

	/**
	 * @param owner
	 */
	public LoginPanel(MainFrame mf) {
                this.mframe = mf;               
                initialize();
                loadINI(path_file);		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		introLabel = new JLabel();
		introLabel.setText(Bundle.getText("loginPanel.labelLoginPasswd"));
                this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setLayout(new BorderLayout());
		//this.add(introLabel, BorderLayout.NORTH);
                this.add(getImagePanel(),BorderLayout.NORTH);
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
			buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		    buttonsPanel.setLayout(new GridBagLayout());
			buttonsPanel.add(getConnectionButton(), new GridBagConstraints());			
		}
		return buttonsPanel;
	}
        
        /**
         *
         */
        private ImagePanel getImagePanel() {
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
        private void loadINI(String file) 
        {
            ProfileReader pr = new ProfileReader();
            InputStream i;
               //i = new FileInputStream(file);
                i = ClassLoader.getSystemResourceAsStream(file);
                try {
                    pr.load(i);                    
                    String addr = pr.getProperty("remote","address");
                    adressTextField.setText(addr);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
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
					String passcript =  Security.encode(new String(passwordPasswordField.getPassword()));
                                        ArrayList<RoleDescriptor> rolesListe = WizardServicesProxy.getRolesByUser(loginTextField.getText(),passcript, adressTextField.getText());
                                        setVisible(false);
                                        mTaskPanel = new MainPanel(mframe,rolesListe);
                                        
                                        if (rolesListe.isEmpty())
                                        {
                                            setVisible(true);
                                            ErrorDialog Err = new ErrorDialog("Gestion des erreurs à faire !!");
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
