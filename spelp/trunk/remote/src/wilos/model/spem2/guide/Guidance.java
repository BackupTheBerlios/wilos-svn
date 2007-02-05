package wilos.model.spem2.guide;

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
	private TaskDefinition taskdefinition;
	private RoleDefinition roledefinition;
	private Activity activity;
	
	private String guideType = "";
	
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
		this.taskdefinition = new TaskDefinition();
		this.roledefinition = new RoleDefinition();
		this.activity = new Activity();
	}
	
	
	/**
	 * @param _taskdefinition
	 *            the taskdefinition to be linked to
	 */
	public void addTaskDefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = _taskdefinition ;		
		(_taskdefinition.getGuidances()).add(this);
	}

	/**
	 * addRoleDefinition
	 * @param _roleDefinition
	 */
	public void addRoleDefinition(RoleDefinition _roleDefinition) {
		this.roledefinition = _roleDefinition;
		(_roleDefinition.getGuidances()).add(this);
	}

	/**
	 * addActivity
	 * @param _activity
	 */
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
	
	/**
	 * removeFromRoleDefinition
	 * @param _roleDefinition
	 */
	public void removeFromRoleDefinition(RoleDefinition _roleDefinition) {
		this.roledefinition = null;
		_roleDefinition.getGuidances().remove(this);
	}
	
	/**
	 * removeFromActivity
	 * @param _activity
	 */
	public void removeFromActivity(Activity _activity) {
		this.activity = null;
		_activity.getGuidances().remove(this);
	}

	/**
	 * getTaskdefinition
	 * @return the taskDefinition
	 */
	public TaskDefinition getTaskdefinition() {
		return taskdefinition;
	}

	/**
	 * setTaskdefinition
	 * @param _taskdefinition
	 */
	public void setTaskdefinition(TaskDefinition _taskdefinition) {
		this.taskdefinition = _taskdefinition;
	}
	
	/**
	 * getActivity
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * setActivity
	 * @param _activity
	 */
	public void setActivity(Activity _activity) {
		this.activity = _activity;
	}

	/**
	 * getRoledefinition
	 * @return the roleDefinition
	 */
	public RoleDefinition getRoledefinition() {
		return roledefinition;
	}

	/**
	 * setRoledefinition
	 * @param _roleDefinition
	 */
	public void setRoledefinition(RoleDefinition _roleDefinition) {
		this.roledefinition = _roleDefinition;
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
		this.setActivity(_guidance.getActivity());
		this.setRoledefinition(_guidance.getRoledefinition());
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
		return new EqualsBuilder().appendSuper(super.equals(guidance)).append(this.taskdefinition, guidance.taskdefinition).append(this.roledefinition,
				guidance.roledefinition).append(this.activity, guidance.activity).isEquals() ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see woops2.model.breakdownelement.BreakdownElement#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(this.taskdefinition).append(this.roledefinition).append(this.activity).toHashCode();
	}

	/**
	 * getGuideType
	 * @return the String guideType 
	 */
	public String getGuideType() {
		return guideType;
	}

	/**
	 * setGuideType
	 * @param guideType
	 */
	public void setGuideType(String guideType) {
		this.guideType = guideType;
	}
	
}
