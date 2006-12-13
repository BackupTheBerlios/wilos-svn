package wilos.presentation.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wilos.business.services.step.StepService;
import wilos.model.spem2.task.Step;

/**
 * 
 * @author Soosuske
 *
 */
public class StepBean {
	private List<Step> stepList;

	private Step step;

	private StepService stepService;

	private String taskDefinitionid = "";

	protected final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Constructor.
	 * 
	 */
	public StepBean() {
		this.logger.debug("--- StepBean --- == creating ..." + this);
		this.step = new Step();
	}

	/**
	 * Getter of activitiesList.
	 * 
	 * @return the activitiesList.
	 */
	public List<Step> getStepList() {
		this.stepList = new ArrayList<Step>();
		logger.debug("### TaskDescriptorBean ### getTaskDescriptorList id= "
				+ this.taskDefinitionid);
		stepList.addAll(this.stepService
				.getStepsFromTask("02f871620f7b976f010f7b99b2ea0043"));
		this.logger.debug("### StepBean ### stepList ="
				+ this.stepList);
		return this.stepList;
	}
	
	/**
	 * Getter of step.
	 * 
	 * @return the step.
	 */
	public Step getStep() {
		return this.step;
	}

	/**
	 * Setter of step.
	 * 
	 * @param _step
	 *            The step to set.
	 */
	public void setStep(Step _step) {
		this.step = _step;
	}

	/**
	 * Setter of stepList.
	 * 
	 * @param _stepList
	 *            The stepList to set.
	 */
	public void setStepList(List<Step> _stepList) {
		this.stepList = _stepList;
	}

	/**
	 * Getter of stepService.
	 * 
	 * @return the stepService.
	 */
	public StepService getStepService() {
		return this.stepService;
	}

	/**
	 * Setter of stepService.
	 * 
	 * @param _stepService
	 *            The stepService to set.
	 */
	public void setStepService(
			StepService _stepService) {
		this.stepService = _stepService;
	}

	public String getTaskDefinitionId() {
		return taskDefinitionid;
	}

	public void setTaskDefinitionId(String taskDefinitionid) {
		this.taskDefinitionid = taskDefinitionid;
	}
}
