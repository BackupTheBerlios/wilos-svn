package wilos.model.spem2.guide;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.task.TaskDefinition;

/**
 * This class represents a Guideline.
 * 
 * @author Nicolas CASTEL
 */
public class Guidance extends Element {
	private TaskDefinition taskdefinition;
	private RoleDefinition roledefinition;
	private Activity activity;
	
	
	public Guidance() {
		this.taskdefinition = new TaskDefinition();
	}
	
	/**
	 * @param _taskdefinition
	 *            the taskdefinition to be linked to
	 */
	public void addTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = _taskdefinition ;		
		(_taskdefinition.getGuidances()).add(this);
	}

	public void addRoleDefinition(RoleDefinition _roleDefinition) {
		this.roledefinition = _roleDefinition;
		(_roleDefinition.getGuidances()).add(this);
	}

	public void addActivity(Activity _activity) {
		this.activity = _activity;
		(_activity.getGuidances()).add(this);
	}

	/**
	 * @param _process
	 *            the process to be unlinked to
	 */
	public void removeFromTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = null ;
		_taskdefinition.getGuidances().remove(this);
	}

	public TaskDefinition getTaskdefinition() {
		return taskdefinition;
	}

	public void setTaskdefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = _taskdefinition;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@ Override
	public Guidance clone() throws CloneNotSupportedException {
		Guidance guidance = new Guidance() ;
		guidance.copy(this) ;
		return guidance ;
	}
	
	/**
	 * Copy the object.
	 * 
	 * @param _guidance
	 *            The Guideline to copy.
	 */
	protected void copy(final Guidance _guidance) {
		super.copy(_guidance) ;
		this.setTaskdefinition(_guidance.getTaskdefinition()) ;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Guidance == false){
			return false ;
		}
		if(this == obj){
			return true ;
		}

		Guidance guidance = (Guidance) obj ;
		return new EqualsBuilder().appendSuper(super.equals(guidance)).append(this.taskdefinition, guidance.taskdefinition).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.taskdefinition).toHashCode() ;
	}

	
	public Activity getActivity() {
		return activity;
	}

	
	public void setActivity(Activity _activity) {
		this.activity = _activity;
	}

	
	public RoleDefinition getRoledefinition() {
		return roledefinition;
	}

	
	public void setRoledefinition(RoleDefinition _roleDefinition) {
		this.roledefinition = _roleDefinition;
	}
}
