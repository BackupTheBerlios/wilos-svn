
package wilos.presentation.web.icefaces.tree ;

import javax.swing.tree.DefaultTreeModel ;

import wilos.business.services.process.ProcessService ;
import wilos.model.spem2.process.Process ;
import wilos.model.spem2.role.RoleDefinition ;
import wilos.model.spem2.role.RoleDescriptor ;
import wilos.model.spem2.task.Step ;
import wilos.model.spem2.task.TaskDefinition ;
import wilos.model.spem2.task.TaskDescriptor ;

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

	public TreeBean() {
		// None.
	}

	/**
	 * Gets the tree's default model.
	 * 
	 * @return tree model.
	 */
	public DefaultTreeModel getModel() {
		if(!this.alreadyBuilt){
			// Process process = buildProcess() ;
			this.processId = "ff8080810fb0c4b4010fb0c4c4650001";
			Process process = this.processService.getEntireProcess(this.processId) ;
			ProcessNode processNode = new ProcessNode(process);
			this.model = new DefaultTreeModel(processNode.obtainTasksForARoleFromProcess()) ;
			this.alreadyBuilt = true ;
		}
		return this.model ;
	}

	public Process buildProcess() {
		Process process = new Process() ;
		process.setGuid("myProcess") ;
		process.setName("TheProceSS") ;

		TaskDescriptor tskdes1 = new TaskDescriptor() ;
		tskdes1.setGuid("tsk1") ;
		tskdes1.setName("tsk1Name") ;
		process.addBreakdownElement(tskdes1) ;

		TaskDefinition taskDefinition = new TaskDefinition() ;
		taskDefinition.setName("tskdef1Name") ;
		tskdes1.addTaskDefinition(taskDefinition) ;

		Step step1 = new Step() ;
		step1.setName("step1") ;
		taskDefinition.addStep(step1) ;

		TaskDescriptor tskdes2 = new TaskDescriptor() ;
		tskdes2.setGuid("tsk2") ;
		tskdes2.setName("tsk2Name") ;
		process.addBreakdownElement(tskdes2) ;

		RoleDescriptor rdes1 = new RoleDescriptor() ;
		rdes1.setGuid("rdes1") ;
		rdes1.setName("rdes1Name") ;
		process.addBreakdownElement(rdes1) ;

		RoleDefinition roleDefinition = new RoleDefinition() ;
		roleDefinition.setName("rdef1Name") ;
		rdes1.addRoleDefinition(roleDefinition) ;

		return process ;
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
