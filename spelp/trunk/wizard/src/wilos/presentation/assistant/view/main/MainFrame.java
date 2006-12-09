package wilos.presentation.assistant.view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.view.htmlViewer.HTMLViewer;
import wilos.presentation.assistant.view.panels.InfoPanel;
import wilos.presentation.assistant.view.panels.LoginPanel;
import wilos.presentation.assistant.view.panels.TreePanel;

public class MainFrame extends JFrame{
	private JMenuBar menu = null;
	private JMenu menuFichier = null;
	private JMenuItem itemFichier = null ;
	private JMenuItem itemExit = null ;
	private TreePanel tp = null ;
	private InfoPanel southpanel = null ;
	
	
	private LoginPanel myLoginPanel;
		
	public static Dimension dimFrame ;

	/**
	 * This method initializes this
	 * 
	 */
	
	private void initialize() {
		this.setSize(350, 300);
                
                this.setLocation(300,200);
                
                myLoginPanel = new LoginPanel(this);
                this.setContentPane(myLoginPanel);
                this.pack();
                myLoginPanel.setVisible(true);                
                
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ajout listener deplacement
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
	
	private InfoPanel getSouthPanel() {
		if (southpanel == null){
			southpanel =  new InfoPanel();
				
		}
		return  southpanel;
	}
	
	private JMenuItem getExitItem(){
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
	public static void main(String[] args) {
		MainFrame f = new MainFrame();
	}
	
	public MainFrame () {
		super(Bundle.getText("mainFrame.title"));
		this.setLayout(new BorderLayout());
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/3);
		this.setLocation(0,0);
		initialize();
		this.setVisible(true);
	}

	public Dimension getDimFrame() {
		return dimFrame;
	}
	
	public Point getHTMLLocation(){
		return new Point(this.getLocation().x+this.getWidth(),this.getLocation().y);
	}
	
	public void moveHTML(){
		Point p = getHTMLLocation();
		HTMLViewer.getInstance(p);
	}
}  

