package wilos.presentation.assistant.view.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import wilos.presentation.assistant.ressources.Bundle;

public class DownLoadFrame extends JFrame {
	
	private JLabel infoFile;
	
	/**
	 * Constructor DownLoadFrame
	 *
	 */
	public DownLoadFrame ()	{
		super();
		initialize();
	}
	
	
	private void initialize() {
		this.setSize(200, 200);
		this.setResizable(false);
		this.setContentPane(getMainPanel());
		this.setContentPane(getInfoPanel());
		
		this.setTitle(Bundle.getText("Telechargement"));
		this.setVisible(true);
	}


	private Container getInfoPanel() {
		// TODO Auto-generated method stub
		return null;
	}


	private Container getMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());		
		panel.add(infoFile,BorderLayout.NORTH);
		return panel ;
	}



}
