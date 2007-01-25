package wilos.presentation.assistant.view.main;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;

import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JTree;

import org.jdesktop.swingx.JXTree;

import wilos.model.misc.wilosuser.Participant;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.panels.InfoPanel;
import wilos.presentation.assistant.view.panels.TreePanel;
import wilos.presentation.assistant.view.panels.WizardStateMachine;

public class WizardMainFrame extends JFrame {

	
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private ActionBar actionToolBar = null;

	private JButton jButtonPlayTask = null;

	private JMenuBar jJMenuBar = null;

	private JMenu jMenuFile = null;

	private JMenuItem jMenuItemQuit = null;

	private JMenu jMenuTools = null;

	private JMenuItem jMenuItemOptions = null;

	private JMenuItem jMenuItemServers = null;

	private JMenu jMenuHelp = null;

	private JMenuItem jMenuItemHelp = null;

	private JButton jButtonPauseTask = null;

	private JButton jButtonFinished = null;

	private JPanel jPanel = null;

	private TreePanel jTree = null;

	private JButton jButton = null;

	/**
	 * This is the default constructor
	 */
	public WizardMainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(378, 395);
		this.setJMenuBar(getJJMenuBar());
		//this.setContentPane(getJContentPane());
		this.setContentPane(getMainPanel());
		this.setTitle(Bundle.getText("mainFrame.title"));
		ContextualMenu menu = new ContextualMenu();
		WizardStateMachine.getInstance().initUIElements(actionToolBar,jTree,menu);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(new ComponentListener(){
	   		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub	
			}
			public void componentMoved(ComponentEvent e) {
				moveHTML();
			}
			public void componentResized(ComponentEvent e) {
				moveHTML() ;		
			}
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub	
			}
		 });
	}
	
	
	public Point getHTMLLocation(){
		return new Point(this.getLocation().x+this.getWidth(),this.getLocation().y);
	}
	
	public void moveHTML(){
		Point p = getHTMLLocation();
		HTMLViewer.getInstance(p);
	}
	
	private JPanel getMainPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(getJToolBar(),BorderLayout.NORTH);
		//panel.add(getJContentPane(),BorderLayout.CENTER);
		panel.add(getJPanel(),BorderLayout.CENTER);
		return panel ;
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 0, 0, 1);
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.ipadx = 217;
			gridBagConstraints3.ipady = 219;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = -90;
			gridBagConstraints2.ipady = 245;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.gridheight = 2;
			gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getJToolBar(), gridBagConstraints2);
			jContentPane.add(getJPanel(), gridBagConstraints3);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private ActionBar getJToolBar() {
//		if (jToolBar == null) {
//			jToolBar = new JToolBar();
//			jToolBar.setLayout(new FlowLayout());
//			jToolBar.add(getJButtonPlayTask());
//			jToolBar.add(getJButtonPauseTask());
//			jToolBar.add(getJButtonFinished());
//			jToolBar.setFloatable(false);
//		}
//		return jToolBar;
		if (actionToolBar == null) {
			actionToolBar = new ActionBar();
		}
		return actionToolBar;
	}

//	/**
//	 * This method initializes jButtonPlayTask	
//	 * 	
//	 * @return javax.swing.JButton	
//	 */
//	private JButton getJButtonPlayTask() {
//		if (jButtonPlayTask == null) {
//			jButtonPlayTask = new JButton();
//			jButtonPlayTask.setIcon(ImagesService.getImageIcon("images.iconPlay"));
//		}
//		return jButtonPlayTask;
//	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenuFile());
			jJMenuBar.add(getJMenuTools());
			jJMenuBar.add(getJMenuHelp());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenuFile	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setText("Fichier");
			jMenuFile.add(getJMenuItemQuit());
		}
		return jMenuFile;
	}

	/**
	 * This method initializes jMenuItemQuit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemQuit() {
		if (jMenuItemQuit == null) {
			jMenuItemQuit = new JMenuItem();
			jMenuItemQuit.setText(Bundle.getText("mainFrame.exit"));
			jMenuItemQuit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
		return jMenuItemQuit;
	}

	/**
	 * This method initializes jMenuTools	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuTools() {
		if (jMenuTools == null) {
			jMenuTools = new JMenu();
			jMenuTools.setText("Outils");
			jMenuTools.add(getJMenuItemOptions());
			jMenuTools.add(getJMenuItemServers());
		}
		return jMenuTools;
	}

	/**
	 * This method initializes jMenuItemOptions	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemOptions() {
		if (jMenuItemOptions == null) {
			jMenuItemOptions = new JMenuItem();
			jMenuItemOptions.setText("Options");
		}
		return jMenuItemOptions;
	}

	/**
	 * This method initializes jMenuItemServers	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemServers() {
		if (jMenuItemServers == null) {
			jMenuItemServers = new JMenuItem();
			jMenuItemServers.setText("Serveurs");
			jMenuItemServers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ServersFrame sf = new ServersFrame();
				}
			});
		}
		return jMenuItemServers;
	}

	/**
	 * This method initializes jMenuHelp	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("Aide");
			jMenuHelp.add(getJMenuItemHelp());
		}
		return jMenuHelp;
	}

	/**
	 * This method initializes jMenuItemHelp	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemHelp() {
		if (jMenuItemHelp == null) {
			jMenuItemHelp = new JMenuItem();
			jMenuItemHelp.setText("Aide");
		}
		return jMenuItemHelp;
	}

//	/**
//	 * This method initializes jButtonPauseTask	
//	 * 	
//	 * @return javax.swing.JButton	
//	 */
//	private JButton getJButtonPauseTask() {
//		if (jButtonPauseTask == null) {
//			jButtonPauseTask = new JButton();
//			jButtonPauseTask.setIcon(ImagesService.getImageIcon("images.iconPause"));
//		}
//		return jButtonPauseTask;
//	}
//
//	/**
//	 * This method initializes jButtonFinished	
//	 * 	
//	 * @return javax.swing.JButton	
//	 */
//	private JButton getJButtonFinished() {
//		if (jButtonFinished == null) {
//			jButtonFinished = new JButton();
//			jButtonFinished.setIcon(ImagesService.getImageIcon("images.iconFinished"));
//		}
//		return jButtonFinished;
//	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
//			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
//			gridBagConstraints1.gridx = 0;
//			gridBagConstraints1.gridy = 1;
//			GridBagConstraints gridBagConstraints = new GridBagConstraints();
//			gridBagConstraints.fill = GridBagConstraints.BOTH;
//			gridBagConstraints.gridy = 0;
//			gridBagConstraints.weightx = 1.0;
//			gridBagConstraints.weighty = 1.0;
//			gridBagConstraints.anchor = GridBagConstraints.EAST;
//			gridBagConstraints.gridwidth = 1;
//			gridBagConstraints.gridx = 0;
//			jPanel = new JPanel();
//			jPanel.setLayout(new GridBagLayout());
			
			
//			jPanel.add(getJTree(), gridBagConstraints);
//			jPanel.add(getJButton(), gridBagConstraints1);
			
			
			
			
			jPanel = new JPanel();
			
			
			jPanel.setLayout(new BorderLayout());
//			jPanel.setLayout(new GridBagLayout());
//			GridBagConstraints c = new GridBagConstraints();
//			c.gridx = 0;
//			c.gridy = 0;
//			c.ipady = 100;
//			c.weighty = 1.0; 
//			c.weightx = 1.0; 
//			
//			jPanel.add(getJTree(), c);
//			
//			c.gridx = 0;
//			c.gridy = 0;
//			c.ipady = 0;
//			jPanel.add(new InfoPanel(), c);
			
			jPanel.add(getJTree(), BorderLayout.CENTER);
//			jPanel.add(getJButton(), BorderLayout.SOUTH);
//			jPanel.add(getJButton(), BorderLayout.NORTH);
			jPanel.add(new InfoPanel(), BorderLayout.SOUTH);
			
		}
		return jPanel;
	}

	/**
	 * This method initializes jTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private TreePanel getJTree() {
		if (jTree == null) {
			jTree = new TreePanel();
			jTree.putTree(new JXTree());
		}
		return jTree;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
		}
		return jButton;
	}

	public void setParticipant(Participant participant) {
		jTree.setParticipant(participant);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
