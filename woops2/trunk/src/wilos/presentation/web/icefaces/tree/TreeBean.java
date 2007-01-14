
package wilos.presentation.web.icefaces.tree ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.process.ProcessService;
import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

import com.icesoft.faces.component.tree.IceUserObject;

/**
 * <p/> A basic backing bean for a ice:tree component. The only instance variable needed is a
 * DefaultTreeModel Object which is bound to the icefaces tree component in the jspx code.
 * </p>
 * <p/> The tree created by this backing bean is used to control the selected panel in a
 * ice:panelStack.
 * </p>
 */
public class TreeBean {

	private ProcessService processService ;

	String processId = "" ;
	
	String roleId = "";

	boolean alreadyBuilt = false ;

	// tree default model, used as a value for the tree component
	private DefaultTreeModel model = null ;

	protected final Log logger = LogFactory.getLog(this.getClass()) ;

	public TreeBean() {
		DefaultMutableTreeNode defaultTree = new DefaultMutableTreeNode();
		IceUserObject iceUserObject = new IceUserObject(defaultTree) ;
		iceUserObject.setText("Choose a project ...") ;
		defaultTree.setUserObject(iceUserObject) ;
		this.model = new DefaultTreeModel(defaultTree) ;
	}

	/**
	 * Gets the tree's default model.
	 * 
	 * @return tree model.
	 */
	
	private void buildModel(){
		logger.debug("### TreeBean ### buildModel  processId = "+this.processId);
		if(!this.processId.equals("")){
			Process process = this.processService.getTaskDescriptorFromProcess(this.processId) ;
			ProcessNode processNode = new ProcessNode(process);
			this.model = new DefaultTreeModel(processNode.obtainTasksForARoleFromProcess()) ;
		}
	}
	
	public DefaultTreeModel getModel() {
		return this.model ;
	}
	
	public List<SelectItem> getProjects(){
		List<SelectItem> projectsList = new ArrayList<SelectItem>();
		for (Process process : this.processService.getProcessesList()) {
			projectsList.add(new SelectItem(process.getId(), process.getName()));
		}
		projectsList.add(new SelectItem("", ""));
		return projectsList;
	}
	
	public void changeTreeActionListener(ActionEvent evt) {
		logger.debug("### TreeBean ### changeTreeActionListener - id ="+this.processId);
		this.buildModel();
	}
	
	public void selectNodeActionListener(ActionEvent evt) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		Map map = context.getExternalContext().getRequestParameterMap();
		String basicNodeId = (String) map.get("basicNode_id");
		logger.debug("### TreeBean ### selectNodeActionListener - basicNodeId ="+basicNodeId);
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return this.processId ;
	}

	/**
	 * Setter of processId.
	 * 
	 * @param _processId
	 *            The processId to set.
	 */
	public void setProcessId(String _processId) {
		this.processId = _processId ;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return this.roleId ;
	}

	/**
	 * Setter of roleId.
	 *
	 * @param _roleId The roleId to set.
	 */
	public void setRoleId(String _roleId) {
		this.roleId = _roleId ;
	}

	/**
	 * @return the processService
	 */
	public ProcessService getProcessService() {
		return this.processService ;
	}

	/**
	 * Setter of processService.
	 * 
	 * @param _processService
	 *            The processService to set.
	 */
	public void setProcessService(ProcessService _processService) {
		this.processService = _processService ;
	}
}
