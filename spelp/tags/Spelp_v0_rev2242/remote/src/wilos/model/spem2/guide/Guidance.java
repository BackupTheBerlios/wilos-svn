package wilos.model.spem2.guide;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import wilos.model.spem2.activity.Activity;
import wilos.model.spem2.element.Element;
import wilos.model.spem2.role.RoleDefinition;
import wilos.model.spem2.task.TaskDefinition;

/**
 * This class represents a Guidance and contains different guides type.
 * 
 * 
 */
public class Guidance extends Element {
	private Set<TaskDefinition> taskDefinitions;
	private Set<RoleDefinition> roleDefinitions;
	private Set<Activity> activities;
	
	private String type = "";
	
	private String presentationName = "";
	
	/* Type guide constant */
	public static final String guideline = "Guideline";
	public static final String checklist = "Checklist";
	public static final String concept = "Concept";
	public static final String estimationConsiderations = "EstimationConsiderations";
	public static final String example = "Example";
	public static final String practice = "Practice";
	public static final String report = "Report";
	public static final String reusableAsset = "ReusableAsset";
	public static final String roadMap = "RoadMap";
	public static final String supportingMaterial = "SupportingMaterial";
	public static final String template = "Template";
	public static final String termDefinition = "TermDefinition";
	public static final String toolMentor = "ToolMentor";
	public static final String whitepaper = "Whitepaper";
	
	
	/* constructor */
	public Guidance() {
		this.taskDefinitions = new HashSet<TaskDefinition>();
		this.roleDefinitions = new HashSet<RoleDefinition>();
		this.activities = new HashSet<Activity>();
	}
	
	
	/**
	 * @param _taskdefinition
	 *            the taskdefinition to be linked to
	 */
	public void addTaskDefinition(TaskDefinition _taskdefinition) {
		this.getTaskDefinitions().add(_taskdefinition);
		_taskdefinition.getGuidances().add(this);
	}

	/**
	 * addRoleDefinition
	 * @param _roleDefinition
	 */
	public void addRoleDefinition(RoleDefinition _roleDefinition) {
		this.getRoleDefinitions().add(_roleDefinition);
		_roleDefinition.getGuidances().add(this);
	}

	/**
	 * addActivity
	 * @param _activity
	 */
	public void addActivity(Activity _activity) {
		this.getActivities().add(_activity);
		_activity.getGuidances().add(this);
	}

	/**
	 * @param _process
	 *            the process to be unlinked to
	 */
	public void removeTaskDefinition(TaskDefinition _taskdefinition) {
		_taskdefinition.getGuidances().remove(this);
		this.getTaskDefinitions().remove(_taskdefinition);
	}
	
	/**
	 * removeFromRoleDefinition
	 * @param _roleDefinition
	 */
	public void removeRoleDefinition(RoleDefinition _roleDefinition) {
		_roleDefinition.getGuidances().remove(this);
		this.getRoleDefinitions().remove(_roleDefinition);
	}
	
	/**
	 * removeFromActivity
	 * @param _activity
	 */
	public void removeActivity(Activity _activity) {
		_activity.getGuidances().remove(this);
		this.getActivities().remove(_activity);		
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
		this.setType(_guidance.getType());
		this.getTaskDefinitions().addAll(_guidance.getTaskDefinitions());
		this.getActivities().addAll(_guidance.getActivities());
		this.getRoleDefinitions().addAll(_guidance.getRoleDefinitions());		
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
		return new EqualsBuilder().appendSuper(super.equals(guidance)).append(this.getTaskDefinitions(), guidance.getTaskDefinitions()).append(this.getRoleDefinitions(),
				guidance.getRoleDefinitions()).append(this.getActivities(), guidance.getActivities()).append(this.type, guidance.type).append(this.presentationName,guidance.getPresentationName()).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.type).append(this.presentationName).toHashCode();
	}


	/**
	 * addAllTaskDefinitions
	 * @param _taskDefinitions
	 */
	public void addAllTaskDefinitions(
			Set<TaskDefinition> _taskDefinitions) {
		for (TaskDefinition td : _taskDefinitions) {
			td.addGuidance(this);
		}
	}

	/**
	 * removeAllTaskDefinitions
	 *
	 */
	public void removeAllTaskDefinitions() {
		for (TaskDefinition td : this.getTaskDefinitions())
			td.removeGuidance(this);
		this.getTaskDefinitions().clear();
	}
	
	/**
	 * addAllRoleDefinitions
	 * @param _roleDefinitions
	 */
	public void addAllRoleDefinitions(
			Set<RoleDefinition> _roleDefinitions) {
		for (RoleDefinition rd : _roleDefinitions) {
			rd.addGuidance(this);
		}
	}

	/**
	 * removeAllRoleDefinitions
	 *
	 */
	public void removeAllRoleDefinitions() {
		for (RoleDefinition rd : this.getRoleDefinitions())
			rd.removeGuidance(this);
		this.getRoleDefinitions().clear();
	}
	
	/**
	 * addAllActivities
	 * @param _activities
	 */
	public void addAllActivities(
			Set<Activity> _activities) {
		for (Activity act : _activities) {
			act.addGuidance(this);
		}
	}

	/**
	 * removeAllActivities
	 */
	public void removeAllActivities() {
		for (Activity act : this.getActivities())
			act.removeGuidance(this);
		this.getActivities().clear();
	}
	
	
	/**
	 * getGuideType
	 * @return the String guideType 
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * setGuideType
	 * @param guideType
	 */
	public void setType(String _type) {
		this.type = _type;
	}


	/**
	 * getPresentationName
	 * @return
	 */
	public String getPresentationName() {
		return presentationName;
	}


	public void setPresentationName(String presentationName) {
		this.presentationName = presentationName;
	}


	/**
	 * @return the activities
	 */
	public Set<Activity> getActivities() {
		return activities;
	}


	/**
	 * @param activities the activities to set
	 */
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}


	/**
	 * @return the roleDefinitions
	 */
	public Set<RoleDefinition> getRoleDefinitions() {
		return roleDefinitions;
	}


	/**
	 * @param roleDefinitions the roleDefinitions to set
	 */
	public void setRoleDefinitions(Set<RoleDefinition> roleDefinitions) {
		this.roleDefinitions = roleDefinitions;
	}


	/**
	 * @return the taskDefinitions
	 */
	public Set<TaskDefinition> getTaskDefinitions() {
		return taskDefinitions;
	}


	/**
	 * @param taskDefinitions the taskDefinitions to set
	 */
	public void setTaskDefinitions(Set<TaskDefinition> taskDefinitions) {
		this.taskDefinitions = taskDefinitions;
	}
	
}
