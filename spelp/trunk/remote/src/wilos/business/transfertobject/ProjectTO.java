package wilos.business.transfertobject;

import java.io.Serializable;

import wilos.model.misc.concretephase.ConcretePhase;
import wilos.model.misc.project.Project;

public class ProjectTO extends Project implements Serializable {

	public ProjectTO() {}
	
	public ProjectTO(Project _myproject) {
		this.setProcess(new ProcessTO(_myproject.getProcess()));
		
		this.setDescription(_myproject.getDescription());
		this.setCreationDate(_myproject.getCreationDate());
		this.setLaunchingDate(_myproject.getLaunchingDate());
		this.setIsFinished(_myproject.getIsFinished());
		this.setId(_myproject.getId());
		this.setConcreteName(_myproject.getConcreteName());
		this.setPlannedStartingDate(_myproject.getPlannedStartingDate());
		this.setPlannedFinishingDate(_myproject.getPlannedFinishingDate());
		this.setPlannedTime(_myproject.getPlannedTime());
	}
}
