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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import sun.misc.Launcher;

import wilos.presentation.assistant.control.ExceptionManager;
import wilos.presentation.assistant.control.OptionsParser;
import wilos.presentation.assistant.control.WizardControler;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;
import wilos.presentation.assistant.view.panels.InfoPanel;
import wilos.presentation.assistant.view.panels.LoginPanel;

public class MainFrame extends JFrame{
/*	private JMenuBar menu = null;
	private JMenu menuFichier = null;
	private JMenuItem itemFichier = null ;
*/	private JMenuItem itemExit = null ;
//	private TreePanel tp = null ;
	private InfoPanel southpanel = null ;
	
	
	private LoginPanel myLoginPanel;
		
	public static Dimension dimFrame ;

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() 
	{
	    myLoginPanel = new LoginPanel(this);
        this.setContentPane(myLoginPanel);  
        this.pack();
        int p1 = (int)(Toolkit.getDefaultToolkit().getScreenSize().width/2-this.getSize().width/2);
		int p2 = (int)(Toolkit.getDefaultToolkit().getScreenSize().height/2-this.getSize().height/2) ; 
		this.setLocation(new Point(
				p1,p2
        ));
		
        myLoginPanel.setVisible(true);                
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // put the frame icon
        try {
        	this.setIconImage(ImagesService.getImage("images.frameIcon"));
        } catch (IOException ex) {
			new ExceptionManager(ex);
        }
	}
	
	private InfoPanel getSouthPanel() 
	{
		if (southpanel == null){
			southpanel =  new InfoPanel();
				
		}
		return  southpanel;
	}
	
	private JMenuItem getExitItem()
	{
		if (itemExit == null){
			itemExit = new JMenuItem(Bundle.getText("mainFrame.exit"));
			itemExit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
						System.exit(0);
				}
			});
		}
		return (itemExit);
		
	}
	
	/**
	 * @param args ne sont pas utilises
	 */
	public static void main(String[] args) 
	{
		OptionsParser op = new OptionsParser ();
		Bundle.setCurrentLocale(op.getLocale());
		MainFrame f = new MainFrame();
	}
	
	public MainFrame () 
	{
		super(Bundle.getText("mainFrame.connectionTitle"));
		this.setLayout(new BorderLayout());
		//this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/3);
		initialize();
		this.addWindowFocusListener(new WindowFocusListener()
		{
			public void windowGainedFocus(WindowEvent arg0) 
			{
				MainFrame.this.myLoginPanel.refreshlist();
			}

			public void windowLostFocus(WindowEvent arg0) 
			{
				// TODO Auto-generated method stub
			}
		});
		this.setVisible(true);
	}

	public Dimension getDimFrame() 
	{
		return dimFrame;
	}
	
	public Point getHTMLLocation()
	{
		return new Point(this.getLocation().x+this.getWidth(),this.getLocation().y);
	}
	
	public void moveHTML()
	{
		Point p = getHTMLLocation();
		WizardControler.getInstance().getDefaultHTML(p);
	}
	
	
}  

