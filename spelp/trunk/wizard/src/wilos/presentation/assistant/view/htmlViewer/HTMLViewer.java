package wilos.presentation.assistant.view.htmlViewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXTaskPane;

import wilos.business.services.guide.GuidanceService;
import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.spem2.breakdownelement.BreakdownElement;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.guide.Guidance;
import wilos.model.spem2.task.TaskDescriptor;
import wilos.presentation.assistant.ressources.Bundle;
import wilos.presentation.assistant.ressources.ImagesService;

public class HTMLViewer extends JFrame {
	private static HTMLViewer instance = null;
	private String HTMLCode; // Le code HTML affich
	
	private JList guidesList = null  ;
	
	private JScrollPane myScrollPane;
	private JEditorPane myEditorPane;
	
	private JLabel myElementLabel ;
	
	private JButton prevButton;
	private JButton nextButton;
	private Stack<Element> historyStack;
	private int cursorStack=0;
	
	private JXTaskPane southPanel;
	private JScrollPane guidesScrollPane;
	
	private HTMLViewer(Point p) {
		super(Bundle.getText("htmlViewer.title"));
		this.setLayout(new BorderLayout());
		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		if (p!=null) this.setLocation(p);
		
		/* ----- NORTH PANEL -----*/
		
		JPanel elementPanel = new JPanel();
		elementPanel.setOpaque(false);
		this.myElementLabel = new JLabel() ;
		elementPanel.add(this.myElementLabel);
		
		/* Gestion des boutons d'historique */
		/*this.historyStack = new Stack<Element>() ;
		this.prevButton = new JButton(ImagesService.getImageIcon("images.iconLeft"));
		this.nextButton = new JButton(ImagesService.getImageIcon("images.iconRight"));
		this.prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setPrevElement();
			}
		});
		this.nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setNextElement();
			}
		});*/
		
		JToolBar northPanel = new JToolBar();
		northPanel.add(elementPanel);
		//northPanel.add(this.prevButton);
		//northPanel.add(this.nextButton);
		northPanel.setFloatable(false);
		
		/* ----- CENTER PANEL -----*/
		
		this.myEditorPane = new JEditorPane();
		this.myEditorPane.setVisible(true);
		this.myEditorPane.setEditable(false);
		this.myEditorPane.setContentType("text/html");
		this.myEditorPane.setFocusable(false);
		
		this.myScrollPane = new JScrollPane(this.myEditorPane);
		
		/* ----- SOUTH PANEL -----*/
		
		this.southPanel = new JXTaskPane() ;
		this.southPanel.setTitle(Bundle.getText("htmlViewer.guidelines"));
		this.southPanel.setExpanded(false);
		
		guidesList = new JList();

		// add a listener
		guidesList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList list = (JList)e.getSource();
				Object value = list.getSelectedValue();
				viewObject((Element)value);				
			}
		});
		
		guidesList.setVisible(false);
		//guidesList.setAutoscrolls(true);
		guidesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guidesList.setVisibleRowCount(2);
		//guidesList.setPreferredSize(new Dimension(25,25));
		this.guidesScrollPane = new JScrollPane(guidesList);
		this.southPanel.add(this.guidesScrollPane);
		this.guidesScrollPane.setVisible(true);
		//this.southPanel.add(guidesList);
		
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		this.getContentPane().add(this.myScrollPane, BorderLayout.CENTER);
		this.getContentPane().add(this.southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Modifie le texte affich par le HTMLViewer
	 * @deprecated
	 * @param message
	 */
	public void setMessage(String message) {
		this.HTMLCode = message;
		
		this.myEditorPane.setText(this.HTMLCode);
		
		if(message.length() != 0){
			this.myEditorPane.setCaretPosition(1); // revient au debut du texte
		}
		
		this.setVisible(true);
	}
	
	private void displayElement(Element e) {
		guidesList.setVisible(e instanceof Guidance || e instanceof TaskDescriptor);
			
		/* Affichage du nom */
		if (e instanceof BreakdownElement) {
			BreakdownElement bde = (BreakdownElement)e ;
			this.myElementLabel.setText(bde.getPresentationName()) ;
		}
		else {
			this.myElementLabel.setText(e.getName()) ;
		}
		
		/* Affichage de la description (ancienne methode setMessage) */
		String description = e.getDescription();
		this.HTMLCode = description;
		
		this.myEditorPane.setText(this.HTMLCode);
		
		if(description.length() != 0)
			this.myEditorPane.setCaretPosition(1); // revient au debut du texte
			
		this.setVisible(true);
		
	}

	private void manageArrows() {
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

	public void viewObject (Object o) {
		boolean ok = false ;
		if(o instanceof ConcreteTaskDescriptor){
			ConcreteTaskDescriptor t = (ConcreteTaskDescriptor)o;
			o = getConcreteTaskDescriptorAndDisplay(t);
			Element tmp = (Element)o;
			displayElement(tmp);
			ok = true ;
		}
		else if (o instanceof ConcreteIteration){
			ConcreteIteration r = (ConcreteIteration)o;
			displayElement(r.getIteration());
			ok = true ;
		}
		else if (o instanceof ConcretePhase){
			ConcretePhase r = (ConcretePhase)o;
			displayElement(r.getPhase());
			ok = true ;
		}
		else if (o instanceof ConcreteActivity){
			ConcreteActivity r = (ConcreteActivity)o;
			displayElement(r.getActivity());
			ok = true ;
		}
		else if (o instanceof ConcreteRoleDescriptor){
			ConcreteRoleDescriptor r = (ConcreteRoleDescriptor)o;
			displayElement(r.getRoleDescriptor());
			ok = true ;
		}
		else if (o instanceof Element){
			Element e = (Element)o;
			displayElement(e);
			ok = true ;
		}
		// if ok = true then object is an element
//		if (ok) {
//				Element e = (Element)o;
//				
//				if(!this.historyStack.empty()) {
//					while(this.cursorStack != this.historyStack.size()-1) {
//						this.historyStack.pop();
//					}
//				}
//				
//				this.historyStack.push(e);
//				
//				if(this.historyStack.size() > 6){
//					this.historyStack.remove(0);
//				}
//				else {
//					this.cursorStack = this.historyStack.size()-1;
//				}
//					
//				manageArrows();
//				
//		}
		
	}

	/**
	 * Affiche les informations de l'element
	 * et recupere les guides
	 * @param ConcreteTaskDescriptor ctd
	 */
	public TaskDescriptor getConcreteTaskDescriptorAndDisplay(ConcreteTaskDescriptor ctd) {
		TaskDescriptor td = ctd.getTaskDescriptor();
		
		/* Affichage des guides */
		Set<Guidance> guides = new HashSet<Guidance>(); 
		if (td.getTaskDefinition() != null) {
			guides = td.getTaskDefinition().getGuidances();
		}
		
		Vector<Guidance> vectGuides = new Vector<Guidance>();
		vectGuides.addAll(guides);
		
		guidesList.setListData(vectGuides);
		guidesList.setCellRenderer(new GuidesRenderer());
		
		
		if (guides.size() != 0){
			guidesList.setVisible(true);
			this.southPanel.setExpanded(true);
		}
		else {
			this.southPanel.setExpanded(false);
		}
		
		return td ;
	}
	
	private class GuidesRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			return super.getListCellRendererComponent(list, ((Element)value).getName(), index, isSelected, cellHasFocus);
		}
	}
	
	private void setPrevElement() {
		this.displayElement(this.historyStack.get(--this.cursorStack));
		manageArrows();
	}
	
	private void setNextElement() {
		this.displayElement(this.historyStack.get(++this.cursorStack)) ;
		manageArrows();
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
