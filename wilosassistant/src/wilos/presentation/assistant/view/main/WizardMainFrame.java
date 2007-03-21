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

package wilos.presentation.assistant.view.main;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTree;

import wilos.model.misc.wilosuser.Participant;
import wilos.presentation.assistant.control.ExceptionManager;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.dialogs.AboutDialog;
import wilos.presentation.assistant.view.dialogs.ErrorDialog;
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
	private InfoPanel infoPanel = null ;
	private JMenuItem jMenuItemAbout = null;
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
		
		// init of controler
		WizardControler.getInstance().initUIElements(this,actionToolBar,jTree,menu,infoPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.moveHTML();
		this.addComponentListener(new ComponentListener(){
	   		public void componentHidden(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {
				moveHTML();
			}
			public void componentResized(ComponentEvent e) {
				moveHTML() ;		
			}
			public void componentShown(ComponentEvent e) {}
		 });
		this.addWindowListener(new WindowListener(){

			public void windowActivated(WindowEvent arg0) {}
			public void windowClosed(WindowEvent arg0) {}
			public void windowClosing(WindowEvent arg0) {
				tryToquit();
			}
			public void windowDeactivated(WindowEvent arg0) {
			}
			public void windowDeiconified(WindowEvent arg0) {
				WizardControler.getInstance().deiconifyAllHTMLViewers();
			}
			public void windowIconified(WindowEvent arg0) {
				WizardControler.getInstance().iconifyAllHTMLViewers();
			}
			public void windowOpened(WindowEvent arg0) {}
			
		});
		
		
		// you can close this window only if there isn't any activity launched
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		
        // put the frame icon
        try {
        	this.setIconImage(ImagesService.getImage("images.frameIcon"));
        } catch (Exception ex) {
			new ExceptionManager(ex);
        }
	}
	

	private void tryToquit() {
		if(WizardControler.getInstance().getNbThreadStarted() != 0) {
			new ErrorDialog(Bundle.getText("wizardMainFrame.canNotClose"));
		}
		else {
			System.exit(0);
		}
	}
	
	public Point getHTMLLocation(){
		return new Point(this.getLocation().x+this.getWidth(),this.getLocation().y);
	}
	
	public void moveHTML(){
		Point p = getHTMLLocation();
		WizardControler.getInstance().getDefaultHTML(p);
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
			jMenuFile.setText(Bundle.getText("wizardMainFrame.menuBar.file"));
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
					tryToquit();
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
			jMenuTools.setText(Bundle.getText("wizardMainFrame.menuBar.tools"));
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
			jMenuItemOptions.setText(Bundle.getText("wizardMainFrame.menuBar.tools.options"));
			jMenuItemOptions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new OptionFrame(WizardMainFrame.this);
				}
			});
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
			jMenuItemServers.setText(Bundle.getText("wizardMainFrame.menuBar.tools.servers"));
			jMenuItemServers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new ServersFrame(WizardMainFrame.this);
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
			jMenuHelp.setText(Bundle.getText("wizardMainFrame.menuBar.help"));
			jMenuHelp.add(getJMenuItemHelp());
			jMenuHelp.add(getJMenuAbout());
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
			jMenuItemHelp.setText(Bundle.getText("wizardMainFrame.menuBar.help.help"));
		}
		return jMenuItemHelp;
	}
	private JMenuItem getJMenuAbout()
	{
		if(jMenuItemAbout==null)
		{
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("About");
			jMenuItemAbout.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) {
					//JFrame f = new JFrame("About");
				
					(new AboutDialog(WizardMainFrame.this)).setVisible(true);
					
					//f.setVisible(true);
					//f.pack();
					
				}
			});
		}
		return jMenuItemAbout;
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
			infoPanel = new InfoPanel();
			WizardStateMachine.getInstance().addObserver(infoPanel);
			jPanel.add(infoPanel, BorderLayout.SOUTH);
			
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
