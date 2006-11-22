package view.htmlViewer;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import ressources.Bundle;

public class HTMLViewer extends JFrame {
	private static HTMLViewer instance = null;
	private String HTMLCode; // Le code HTML affiché
	private JScrollPane myScrollPane;
	private JEditorPane myEditorPane;
	
	private HTMLViewer(Point p) {
		super(Bundle.getText("htmlViewer.title"));
		this.setLayout(new BorderLayout());
		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		if (p!=null) this.setLocation(p);
		
		this.myEditorPane = new JEditorPane();
		this.myEditorPane.setVisible(true);
		this.myEditorPane.setEditable(false);
		this.myEditorPane.setOpaque(false);
		this.myEditorPane.setContentType("text/html");
		this.myEditorPane.setFocusable(false);
		
		this.myScrollPane = new JScrollPane(this.myEditorPane);
		this.getContentPane().add(this.myScrollPane);
	}
	
	/**
	 * Modifie le texte affiché par le HTMLViewer
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.HTMLCode = message;
		
		this.myEditorPane.setText(this.HTMLCode);
		
		if(message.length() != 0)
			this.myEditorPane.setCaretPosition(1); // revient au debut du texte
	}
	
	
	
	/**
	 * 
	 * @return l'instance HTMLViewer
	 */
	public static HTMLViewer getInstance(Point p) {
		if (HTMLViewer.instance == null){
			HTMLViewer.instance = new HTMLViewer(p);
		}
		else if (p != null) {
			HTMLViewer.instance.setLocation(p);
		}
		
		return HTMLViewer.instance;
	}
}
