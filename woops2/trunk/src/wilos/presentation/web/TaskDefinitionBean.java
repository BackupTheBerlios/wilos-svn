package wilos.presentation.web;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TaskDefinitionBean {
	

	protected final Log logger = LogFactory.getLog(this.getClass()) ;
	
	public void chooseTaskDefinitionActionListener(ActionEvent e){
		FacesContext context = FacesContext.getCurrentInstance(); 
		Map map = context.getExternalContext().getRequestParameterMap();
		String taskId = (String) map.get("taskdefinition_id");
		StepBean stepBean = (StepBean) context.getApplication()
		.getVariableResolver().resolveVariable(context,"StepBean");
		logger.debug("### TaskDefinitionBean ### chooseProcessActionListener id = "+taskId);
		stepBean.setTaskDefinitionId(taskId);
	}
}
