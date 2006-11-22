package view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import modelWoops.role.RoleDescriptor;
import modelWoops.task.Task;
import modelWoops.task.TaskDescriptor;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.JXTree;

import ressources.Bundle;

/*import parser.Parser;*/
import view.htmlViewer.HTMLViewer;

public class MainFrame extends JFrame{
	private JMenuBar menu = null;
	private JMenu menuFichier = null;
	private JMenuItem itemFichier = null ;
	private JMenuItem itemExit = null ;
	private TreePanel tp = null ;
	private InfoPanel southpanel = null ;
	public static Dimension dimFrame ;

	/**
	 * This method initializes this
	 * 
	 */
	
	private void moveHTML(){
		Point p = getHTMLLocation();
		HTMLViewer.getInstance(p);
	}
	
	private void initialize() {
		this.setSize(300, 150);
		this.setContentPane(new LoginPanel());
       /*this.setJMenuBar(getMenu());
       dimFrame = Toolkit.getDefaultToolkit().getScreenSize();
       tp = new TreePanel();
       this.add(tp,BorderLayout.CENTER);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       this.add(getSouthPanel(),BorderLayout.SOUTH);
       
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
       });*/
	}
	
	private InfoPanel getSouthPanel() {
		if (southpanel == null){
			southpanel =  new InfoPanel();
				
		}
		return  southpanel;
	}
	
	/**
	 * This method initializes menu	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMenu() {
		if (menu == null) {
			menu = new JMenuBar();
			
			menu.setPreferredSize(new Dimension(0, 20));
			menu.add(getMenuFichier());
			
		}
		return menu;
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
	private JMenuItem getFichierItem(){
		if (itemFichier == null) {
			itemFichier = new JMenuItem(Bundle.getText("mainFrame.connection"));
			itemFichier.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					//new LoginPanel(MainFrame.this);
//					String myHome = "." + File.separator ;
//					System.out.println(myHome);
//					boolean wasHTMLViewerShown;
//					wasHTMLViewerShown = HTMLViewer.getInstance(null).isVisible();
//					HTMLViewer.getInstance(null).setVisible(false);
//					JFileChooser opening = new JFileChooser(myHome);
//					opening.setFileFilter(new myFileFilter());
//					if (opening.showOpenDialog(MainFrame.this) == JFileChooser.CANCEL_OPTION){
//						System.out.println("cancel");
//						if (wasHTMLViewerShown)
//							HTMLViewer.getInstance(null).setVisible(true);
//					}
//					else {
//						 
//						Parser.getInstance().setFileXML(opening.getSelectedFile().toString());*/
//						/*ArrayList<String> v = new ArrayList<String>()/*= Parser.getInstance().getRole()*/;
//						ArrayList<MyModelElement> listRoles = new ArrayList();
//						for(int i = 0 ; i < v.size() ; i++){
//							listRoles.add(new MyModelElement(v.get(i)));
//						}
//						
//						DialogRoles d = new DialogRoles(MainFrame.this,listRoles);
//						if (d.getChoix() == DialogRoles.CHOIX_OK){
//							MainFrame.this.tp.putTree(MainFrame.this.getTreeWithTasks(d.getModelElement()));
//							southpanel.setInfo(
//									"<html>Process used : " + /*Parser.getInstance().getMethodName() +*/ "<br>" +
//									"You are " + d.getRole() +"</html>"
//							);
//						}
						
						//d.dispose();
						
//					}
				}
				
			});
		}
		return itemFichier;
	}
	
	/**
	 * This method initializes menuFichier	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuFichier() {
		if (menuFichier == null) {
			menuFichier = new JMenu(Bundle.getText("mainFrame.file"));
			menuFichier.add(getFichierItem());
			menuFichier.add(getExitItem());
		}
		return menuFichier;
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
	private class myFileFilter extends javax.swing.filechooser.FileFilter{

		@Override
		public boolean accept(File arg0) {
			return(arg0.getName().endsWith("xml")||arg0.getName().endsWith("XML")||arg0.isDirectory());
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return Bundle.getText("mainFrame.descriptionFileChooser");
		}
	}
	
	private JXTree getTreeWithTasks(RoleDescriptor role){
		//ArrayList a = Parser.getInstance().getTask();
		ArrayList<String> a = new ArrayList<String>()/*= Parser.getInstance().getPrimaryTaskByRole(role.getRealName())*/;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(role.getName() , true ) ;
		for (Iterator<String> i = a.iterator() ; i.hasNext();){
			TaskDescriptor aTask = new TaskDescriptor ();
			aTask.setName(i.next());
			DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(aTask, true );
			
			root.add(tmp) ;
		}
		final JXTree t = new JXTree (root);
		t.addMouseListener(new MouseAdapter() { 
			  public void mouseClicked(MouseEvent evt) { 
			     if (evt.getClickCount() == 2){
			    	 TreePath path =
				         t.getPathForLocation(evt.getX(), evt.getY()); 
				      if (path != null && ((DefaultMutableTreeNode)path.getLastPathComponent()).isLeaf() && ((DefaultMutableTreeNode)path.getLastPathComponent()).getParent() != null){ 
				    	Point p = getHTMLLocation();
						HTMLViewer h = HTMLViewer.getInstance(p);
						TaskDescriptor aTask = ((TaskDescriptor)((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject());
						h.setMessage(aTask.getDescription());
						h.setVisible(true);
					} 
			     }		  
			  } 
			});
		return t ;
	}
	
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
