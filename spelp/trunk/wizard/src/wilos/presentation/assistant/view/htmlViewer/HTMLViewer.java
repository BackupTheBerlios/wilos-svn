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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.ressources.Bundle;

public class HTMLViewer extends JFrame {
	private static HTMLViewer instance = null;
	private String HTMLCode; // Le code HTML affiché
	
	private JScrollPane myScrollPane;
	private JEditorPane myEditorPane;
	
	private JLabel myElementLabel ;
	
	private JButton prevButton;
	private JButton nextButton;
	private Stack historyStack;
	private int cursorStack=0;
	
	private JList guidesList ;
	
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
		
		this.historyStack = new Stack() ;
		
		this.prevButton = new JButton("<");
		this.nextButton = new JButton(">");
		this.prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setPrevElement();
			}
		});
		this.nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setNextElement();
			}
		});
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
		
		this.guidesList = new JList();
		this.guidesList.setVisibleRowCount(2);
		southPanel.add(new JScrollPane(this.guidesList));
		
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
	
	private void displayTaskDescriptor(TaskDescriptor td) {
		/* Affichage du nom */
		this.myElementLabel.setText(td.getName()) ;
		
		/* Affichage de la description (ancienne methode setMessage) */
		String description = td.getDescription();
		this.HTMLCode = description;
		
		this.myEditorPane.setText(this.HTMLCode);
		
		if(description.length() != 0)
			this.myEditorPane.setCaretPosition(1); // revient au debut du texte
		
		this.setVisible(true);
		
		/* Activation/Desactivation des boutons d'historique */
		if(this.cursorStack < 1)
			this.prevButton.setEnabled(false);
		else
			this.prevButton.setEnabled(true);
		
		if(this.cursorStack < this.historyStack.size()-1)
			this.nextButton.setEnabled(true);
		else
			this.nextButton.setEnabled(false);
	}

	/**
	 * Affiche les informations de l'element
	 * 
	 * @param ConcreteTaskDescriptor ctd
	 */
	public void setConcreteTaskDescriptor(ConcreteTaskDescriptor ctd) {
		TaskDescriptor td = ctd.getTaskDescriptor();
		
		if(!this.historyStack.empty())
			while(this.cursorStack != this.historyStack.size()-1)
				this.historyStack.pop();
		
		this.historyStack.push(td);
		
		if(this.historyStack.size() > 6)
			this.historyStack.remove(0);
		else
			this.cursorStack = this.historyStack.size()-1;
		
		this.displayTaskDescriptor(td);
	}
	
	private void setPrevElement() {
		this.displayTaskDescriptor((TaskDescriptor)this.historyStack.get(--this.cursorStack)) ;
	}
	
	private void setNextElement() {
		this.displayTaskDescriptor((TaskDescriptor)this.historyStack.get(++this.cursorStack)) ;
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
