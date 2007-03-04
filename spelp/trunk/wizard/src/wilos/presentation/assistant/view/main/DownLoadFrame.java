package wilos.presentation.assistant.view.main;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;

public class DownLoadFrame extends JFrame {
	
	private JLabel infoFile = null;
	private JLabel dLFile = null;
	private JPanel mainPanel = null;
	private static String textInfoFile = Bundle.getText("downloadFrame.FileInfo");
	private static String fileNameInfoFirst = Bundle.getText("downloadFrame.fileNameInfoFirst");
	private static String fileNameInfoLast = Bundle.getText("downloadFrame.fileNameInfoLast");
	
	
	/**
	 * Constructor DownLoadFrame
	 *
	 */
	public DownLoadFrame (String file, String filePathToDownload)	{
		super();
		this.initialize();
		this.displayInformation(file, filePathToDownload);
		startLoading();
	}
	
	
	private void displayInformation(String file, String filePathToDownload) {
		this.dLFile.setText(textInfoFile+ " " +filePathToDownload);
		this.infoFile.setText(fileNameInfoFirst+getFileName(file)+" "+fileNameInfoLast);		
		
	}


	private String getFileName(String path_file) {
		String fileToBeReturn = "";
		int indexSeparator = 0;
		
		indexSeparator = path_file.lastIndexOf('/');
		fileToBeReturn = path_file.substring(indexSeparator+1);		
		return fileToBeReturn;
	}


	private void initialize() {
		int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2-(605/2));
		int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2-(220/2)) ;
		
		this.setTitle(Bundle.getText("downloadFrame.downloadTitle"));
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
	
	private void startLoading() {
		JLabel img = new JLabel(ImagesService.getImageIcon("images.progressLoading"));
		
		img.setSize(150, 50);
		img.setBounds(new Rectangle(20,40,300,100));
		this.add(img, null);
		this.doLayout();
		this.repaint();
	}	
	
}
