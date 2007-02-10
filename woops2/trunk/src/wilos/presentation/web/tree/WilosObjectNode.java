package wilos.presentation.web.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * Extend the IceUserObject in order to add object id, bean name and page Id
 * @see 
 * @author garwind
 *
 */
public class WilosObjectNode extends IceUserObject {
	
	// static fields
	// Project Node
	public final static String PROJECTNODE = "ProjectViewer";
	// Phase Node
	public final static String PHASENODE = "ConcretePhaseViewer";
	// Iteration Node
	public final static String ITERATIONNODE = "ConcreteIterationViewer";
	// ConcreteTask Node
	public final static String CONCRETETASKNODE = "ConcreteTaskViewer";
	// ConcreteTask Node
	public final static String ACTIVITYNODE = "ConcreteActivityViewer";
	
	// properties
	private String id = "";
	
	private String pageId = "";
	
	private Boolean isSelected = false;
	
	public WilosObjectNode(DefaultMutableTreeNode arg0) {
		super(arg0);
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
