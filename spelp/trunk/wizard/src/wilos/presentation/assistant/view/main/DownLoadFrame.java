package wilos.presentation.assistant.view.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import wilos.presentation.assistant.ressources.Bundle;

public class DownLoadFrame extends JFrame {
	
	private JLabel infoFile = null;
	private JLabel dLFile = null;
	private JPanel infoPanel = null;
	private JPanel mainPanel = null;
	
	/**
	 * Constructor DownLoadFrame
	 *
	 */
	public DownLoadFrame ()	{
		super();
		this.initialize();
	}
	
	
	private void initialize() {
		int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2-(605/2));
		int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2-(220/2)) ;
		
		this.setTitle(Bundle.getText("Telechargement"));
		this.setBounds(x, y, 400, 200);
		this.setResizable(false);
		this.setContentPane(getMainPanel());
		
		this.setVisible(true);
	}

	private Container getMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(null);		
		mainPanel.add(getInfoFile(),null);
		mainPanel.add(getDLFile(),null);
		return mainPanel ;
	}

	private Component getDLFile() {
		if (this.dLFile == null)
		{
			this.dLFile = new JLabel();
			this.dLFile.setBounds(new Rectangle(20,140,400,20));
			this.dLFile.setText("Le fichier est téléchargé vers : X");
		}
		return this.dLFile;
	}


	private JLabel getInfoFile() {
		if (this.infoFile == null)
		{
			this.infoFile = new JLabel();
			this.infoFile.setBounds(new Rectangle(20,10,400,20));
			this.infoFile.setText("Le fichier X est en cours de téléchargement...");
		}
		return this.infoFile;
	}
}
