package wilos.presentation.assistant.view.main;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTree;

import wilos.model.misc.wilosuser.Participant;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.panels.InfoPanel;
import wilos.presentation.assistant.view.panels.TreePanel;
import wilos.presentation.assistant.view.panels.WizardStateMachine;

public class WizardMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private ActionBar actionToolBar = null;

	private JMenuBar jJMenuBar = null;

	private JMenu jMenuFile = null;

	private JMenuItem jMenuItemQuit = null;

	private JMenu jMenuTools = null;

	private JMenuItem jMenuItemOptions = null;

	private JMenuItem jMenuItemServers = null;

	private JMenu jMenuHelp = null;

	private JMenuItem jMenuItemHelp = null;

	private JPanel jPanel = null;

	private TreePanel jTree = null;

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
		this.setSize(420, 500);
		this.setJMenuBar(getJJMenuBar());
		//this.setContentPane(getJContentPane());
		this.setContentPane(getMainPanel());
		this.setTitle(Bundle.getText("mainFrame.title"));
		ContextualMenu menu = new ContextualMenu();
		WizardControler.getInstance().initUIElements(actionToolBar,jTree,menu);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.moveHTML();
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
		panel.add(getJPanel(),BorderLayout.CENTER);
		return panel ;
	}

	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private ActionBar getJToolBar() {
		if (actionToolBar == null) {
			actionToolBar = new ActionBar();
			WizardStateMachine.getInstance().addObserver(actionToolBar);
		}
		return actionToolBar;
	}

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
					new ServersFrame();
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

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();			
			jPanel.setLayout(new BorderLayout());
			
			jPanel.add(getJTree(), BorderLayout.CENTER);
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

	public void setParticipant(Participant participant) {
		jTree.setParticipant(participant);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
