package wilos.presentation.web.tree;

import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.model.misc.concreteactivity.ConcreteActivity;
import wilos.model.misc.concretebreakdownelement.ConcreteBreakdownElement;
import wilos.model.misc.concreteiteration.ConcreteIteration;
import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.concreterole.ConcreteRoleDescriptor;
import wilos.model.misc.concretetask.ConcreteTaskDescriptor;
import wilos.model.misc.project.Project;

public class ProjectNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -9148715541110574441L;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	private Project project;

	public ProjectNode(Project _project, boolean _isConcreteTaskDescriptorsTree, HashMap<String, Object> _treeMap) {
		super();
		this.project = _project;
		
		WilosObjectNode iceUserObject = new WilosObjectNode(this);
		this.setUserObject(iceUserObject);

		iceUserObject.setExpanded(true);
		iceUserObject.setText(this.project.getConcreteName());
		iceUserObject.setLeaf(false);
		iceUserObject.setBranchContractedIcon("images/tree/icon_process.gif");
		iceUserObject.setBranchExpandedIcon("images/tree/icon_process.gif");
		// node information
		iceUserObject.setId(this.project.getId());
		iceUserObject.setPageId(WilosObjectNode.PROJECTNODE);

		// add the project object with his id in the treeMap
		_treeMap.put(iceUserObject.getId(), _project);
		
		if (this.project.getProcess() != null) {
			iceUserObject.setText(this.project.getConcreteName() + " ("
					+ this.project.getProcess().getPresentationName() + ")");

			// Nested nodes.
			for (ConcreteBreakdownElement concreteBreakdownElement : this.project
					.getConcreteBreakdownElements()) {
				if (concreteBreakdownElement instanceof ConcretePhase) {
					ConcretePhase cp = (ConcretePhase) concreteBreakdownElement;
					
					if (cp.getIsInUsed()) {
						this.add(new ConcretePhaseNode(cp, _isConcreteTaskDescriptorsTree, _treeMap));
					}
				} else if (concreteBreakdownElement instanceof ConcreteIteration) {
					ConcreteIteration ci = (ConcreteIteration) concreteBreakdownElement;
					if (ci.getIsInUsed()) {
						this.add(new ConcreteIterationNode(ci,
								_isConcreteTaskDescriptorsTree, _treeMap));
					}
				} else if (concreteBreakdownElement instanceof ConcreteActivity) {
					ConcreteActivity ca = (ConcreteActivity) concreteBreakdownElement;
					if (ca.getIsInUsed()) {
						this.add(new ConcreteActivityNode(ca,
								_isConcreteTaskDescriptorsTree, _treeMap));
					}
				} else if (_isConcreteTaskDescriptorsTree) {
					if (concreteBreakdownElement instanceof ConcreteTaskDescriptor) {
						ConcreteTaskDescriptor ctd = (ConcreteTaskDescriptor) concreteBreakdownElement;
						if (ctd.getIsInUsed()) {
							this.add(new ConcreteTaskDescriptorNode(ctd, _treeMap));
						}
					}
				} else {
					if (concreteBreakdownElement instanceof ConcreteRoleDescriptor) {
						this.add(new ConcreteRoleDescriptorNode((ConcreteRoleDescriptor) concreteBreakdownElement, _treeMap));
					}
				}
			}
		}
		
		logger.debug("=========> HashMap"+_treeMap);
		logger.debug("=========> HashMap size" + _treeMap.size());
		
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean tb = (TreeBean) context.getApplication().getVariableResolver()
			.resolveVariable(context, "TreeBean");
		tb.setTreeMap(_treeMap);
	}
}
