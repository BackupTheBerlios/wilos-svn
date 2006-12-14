package wilos.presentation.web.icefaces.tree;

import javax.swing.tree.DefaultTreeModel;

import wilos.model.spem2.process.Process;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.role.RoleDescriptor;
import wilos.model.spem2.task.Step;
import wilos.model.spem2.task.TaskDefinition;
import wilos.model.spem2.task.TaskDescriptor;

/**
 * <p/>
 * A basic backing bean for a ice:tree component.  The only instance variable
 * needed is a DefaultTreeModel Object which is bound to the icefaces tree
 * component in the jspx code.</p>
 * <p/>
 * The tree created by this backing bean is used to control the selected
 * panel in a ice:panelStack.
 * </p>
 */
public class TreeBean {

    // tree default model, used as a value for the tree component
    private DefaultTreeModel model;

    public TreeBean() {
    	Process process = buildProcess();
    	this.model = new DefaultTreeModel(new ProcessNode(process));
    }
    
    public Process buildProcess() {
		Process process = new Process();
		process.setGuid("myProcess");
		process.setName("TheProceSS");

		TaskDescriptor tskdes1 = new TaskDescriptor();
		tskdes1.setGuid("tsk1");
		tskdes1.setName("tsk1Name");
		process.addBreakdownElement(tskdes1);

		TaskDefinition taskDefinition = new TaskDefinition();
		taskDefinition.setName("tskdef1Name");
		tskdes1.addTaskDefinition(taskDefinition);

		Step step1 = new Step();
		step1.setName("step1");
		taskDefinition.addStep(step1);

		TaskDescriptor tskdes2 = new TaskDescriptor();
		tskdes2.setGuid("tsk2");
		tskdes2.setName("tsk2Name");
		process.addBreakdownElement(tskdes2);

		RoleDescriptor rdes1 = new RoleDescriptor();
		rdes1.setGuid("rdes1");
		rdes1.setName("rdes1Name");
		process.addBreakdownElement(rdes1);

		RoleDefinition roleDefinition = new RoleDefinition();
		roleDefinition.setName("rdef1Name");
		rdes1.addRoleDefinition(roleDefinition);

		return process;
	}

    /**
     * Gets the tree's default model.
     *
     * @return tree model.
     */
    public DefaultTreeModel getModel() {
        return this.model;
    }
}