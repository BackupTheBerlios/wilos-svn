package wilos.presentation.web.expandabletable;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import wilos.business.services.spem2.process.ProcessService;
import wilos.model.spem2.process.Process;

/**
 * @author Sebastien
 * 
 */
public class ProcessBean {

	private ProcessService processService;

	private String selectedProcessGuid = "default";

	/**
	 * Give all the processes save in the database
	 * @return
	 */
	public List<SelectItem> getProjects() {

		List<SelectItem> processesList = new ArrayList<SelectItem>();

		ResourceBundle bundle = ResourceBundle.getBundle(
				"wilos.resources.messages", FacesContext.getCurrentInstance()
						.getApplication().getDefaultLocale());
		processesList.add(new SelectItem("default", bundle
				.getString("component.combobox.processchoice.defaultlabel")));

		List<Process> processes = this.processService.getProcessDao()
				.getAllProcesses();
		for (Process process : processes) {
			processesList.add(new SelectItem(process.getGuid(), process
					.getPresentationName()));
		}
		return processesList;
	}

	/**
	 * listener on the processes selection combobox
	 */
	public void changeProcessSelectionListener(ValueChangeEvent evt) {
		this.selectedProcessGuid = (String) evt.getNewValue();
	}

	/**
	 * @return the processService
	 */
	public ProcessService getProcessService() {
		return this.processService;
	}

	/**
	 * @param _processService
	 *            the processService to set
	 */
	public void setProcessService(ProcessService _processService) {
		this.processService = _processService;
	}

	/**
	 * @return the processGuid
	 */
	public String getSelectedProcessGuid() {
		return this.selectedProcessGuid;
	}

	/**
	 * @param _processGuid the processGuid to set
	 */
	public void setSelectedProcessGuid(String _processGuid) {
		this.selectedProcessGuid = _processGuid;
	}
}
