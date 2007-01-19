package wilos.presentation.assistant.view.htmlViewer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.element.Element;
import wilos.presentation.assistant.ressources.Bundle;

public class HTMLViewer extends JFrame {
	private static HTMLViewer instance = null;
	private String HTMLCode; // Le code HTML affiché
	private JScrollPane myScrollPane;
	private JEditorPane myEditorPane;
	private JLabel myElementLabel ;
	private JButton prevButton;
	private JButton nextButton;
	private Stack prevStack;
	private Stack nextStack;
	
	
	private HTMLViewer(Point p) {
		super(Bundle.getText("htmlViewer.title"));
		this.setLayout(new BorderLayout());
		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		if (p!=null) this.setLocation(p);
		
		JPanel northPanel = new JPanel() ;
		northPanel.setLayout(new GridLayout(1,4,5,5));
		northPanel.add(new JLabel(Bundle.getText("htmlViewer.element")));
		this.myElementLabel = new JLabel() ;
		northPanel.add(this.myElementLabel);
		
		this.prevButton = new JButton("<");
		this.nextButton = new JButton(">");
		this.prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// traitement
			}
		});
		this.prevButton.setEnabled(false);
		this.nextButton.setEnabled(false);
		northPanel.add(this.prevButton);
		northPanel.add(this.nextButton);
		
		this.myEditorPane = new JEditorPane();
		this.myEditorPane.setVisible(true);
		this.myEditorPane.setEditable(false);
		this.myEditorPane.setOpaque(false);
		this.myEditorPane.setContentType("text/html");
		this.myEditorPane.setFocusable(false);
		
		this.myScrollPane = new JScrollPane(this.myEditorPane);
		
		JPanel southPanel = new JPanel() ;
		southPanel.setLayout(new GridLayout());
		southPanel.add(new JLabel(Bundle.getText("htmlViewer.guidelines")));
		
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.myScrollPane, BorderLayout.CENTER);
		this.getContentPane().add(southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Modifie le texte affiché par le HTMLViewer
	 * @deprecated
	 * @param message
	 */
	public void setMessage(String message) {
		this.HTMLCode = message;
		
		this.myEditorPane.setText(this.HTMLCode);
		
		if(message.length() != 0)
			this.myEditorPane.setCaretPosition(1); // revient au debut du texte
		
		this.setVisible(true);
	}

	/**
	 * Affiche les informations de l'element
	 * 
	 * @param BreakDownElement bde
	 */
	public void setBreakDownElement(Element bde) {
		/* Affichage du nom */
		this.myElementLabel.setText(bde.getName()) ;
		
		/* Affichage de la description (ancienne methode setMessage) */
		String description = bde.getDescription();
		this.HTMLCode = description;
		
		this.myEditorPane.setText(this.HTMLCode);
		
		if(description.length() != 0)
			this.myEditorPane.setCaretPosition(1); // revient au debut du texte
		
		this.setVisible(true);
		
		/* Empilement du bde pour l'historique */
		//this.prevStack.push(bde);
	}
	
	public void setPrevElement() {
		this.nextStack.push(this.prevStack.pop()) ;
		this.setBreakDownElement((BreakdownElement)this.prevStack.peek()) ;
	}
	
	public void setNextElement() {
		this.setBreakDownElement((BreakdownElement)this.nextStack.peek()) ;
		this.prevStack.push(this.nextStack.pop()) ;
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
