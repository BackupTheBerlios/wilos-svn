package wilos.presentation.web.icefaces.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * 
 * @author garwind
 *
 */
public class WilosObjectNode extends IceUserObject {
	
	private String objectId = "";
	
	private Boolean isSelected = false;
	
	public WilosObjectNode(DefaultMutableTreeNode arg0) {
		super(arg0);
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

}
