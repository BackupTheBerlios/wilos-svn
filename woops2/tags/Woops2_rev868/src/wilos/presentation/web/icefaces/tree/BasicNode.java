package wilos.presentation.web.icefaces.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public class BasicNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -34384102460214963L;

	private String basicNodeId;
	
	public BasicNode(){
		//None.
	}

	/**
	 * @return the basicNodeId
	 */
	public String getBasicNodeId() {
		return basicNodeId;
	}

	/**
	 * @param basicNodeId the basicNodeId to set
	 */
	public void setBasicNodeId(String basicNodeId) {
		this.basicNodeId = basicNodeId;
	}
}
