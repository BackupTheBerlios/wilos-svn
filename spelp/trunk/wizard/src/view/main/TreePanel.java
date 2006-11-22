package view.main;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import org.jdesktop.swingx.JXTree;

public class TreePanel extends JScrollPane {
	
	private JXTree tree = null ;
	
	public TreePanel(){
		super();
		this.setLayout(new ScrollPaneLayout());
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		this.add(getTree());
	}
	
	public void putTree (JXTree t){
		tree= t ;
		this.setViewportView(t);
	}
	
	public JXTree getTree (){
		return tree;
	}
	
}
